/*
 * android-plugin-host-sdk-for-locale https://github.com/twofortyfouram/android-plugin-host-sdk-for-locale
 * Copyright 2015 two forty four a.m. LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twofortyfouram.locale.sdk.host.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.format.DateUtils;

import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.log.Lumberjack;
import com.twofortyfouram.spackle.ThreadUtil;
import com.twofortyfouram.spackle.ThreadUtil.ThreadPriority;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Tests the plug-in registry.
 */
public final class PluginRegistryTest extends AndroidTestCase {

    public static final long REGISTRY_LOAD_WAIT_MILLIS = 15 * DateUtils.SECOND_IN_MILLIS;

    @SmallTest
    public void testGetInstance_non_null() {
        final PluginRegistry registrySingleton = PluginRegistry
                .getInstance(getContext());

        assertNotNull(registrySingleton);
    }

    @SmallTest
    public void testGetInstance_cached() {
        final PluginRegistry registrySingleton = PluginRegistry
                .getInstance(getContext());

        assertSame(registrySingleton, PluginRegistry.getInstance(getContext()));
    }

    @MediumTest
    public void testGetInstance_initialized() {
        final PluginRegistry registrySingleton = PluginRegistry
                .getInstance(getContext());

        assertNotNull(registrySingleton);
        assertSame(registrySingleton, PluginRegistry.getInstance(getContext()));

        // If this test hangs, then there is a bug with getInstance() initializing the registry.
        registrySingleton.blockUntilLoaded();
    }

    @SmallTest
    public void testDestroy_singleton() {
        final PluginRegistry registrySingleton = PluginRegistry
                .getInstance(getContext());

        try {
            registrySingleton.destroy();
            fail();
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }

    @MediumTest
    public void testGetPluginMap_conditions() {
        final PluginRegistry registry = new PluginRegistry(getContext(),
                getIntentAction());

        try {
            registry.init();

            final Map<String, Plugin> conditions = registry.getPluginMap(PluginType.CONDITION);
            assertNotNull(conditions);
        } finally {
            registry.destroy();
        }
    }

    @MediumTest
    public void testGetPluginMap_settings() {
        final PluginRegistry registry = new PluginRegistry(getContext(),
                getIntentAction());

        try {
            registry.init();

            final Map<String, Plugin> conditions = registry.getPluginMap(PluginType.SETTING);
            assertNotNull(conditions);
        } finally {
            registry.destroy();
        }
    }

    @SmallTest
    public void testPeekPluginMap_non_blocking() {
        final PluginRegistry registry = new PluginRegistry(getContext(),
                getIntentAction());

        try {
            assertNull(registry.peekPluginMap(PluginType.CONDITION));
            assertNull(registry.peekPluginMap(PluginType.SETTING));

            registry.init();
        } finally {
            registry.destroy();
        }
    }

    @MediumTest
    public void testPeekPluginMap_after_blocking() {
        final PluginRegistry registry = new PluginRegistry(getContext(),
                getIntentAction());

        try {
            registry.init();
            registry.blockUntilLoaded();

            assertNotNull(registry.peekPluginMap(PluginType.CONDITION));
            assertNotNull(registry.peekPluginMap(PluginType.SETTING));
        } finally {
            registry.destroy();
        }
    }

    @MediumTest
    public void testLoadCompleteNotification() {
        final PluginRegistry registry = new PluginRegistry(getContext(),
                getIntentAction());

        final HandlerThread backgroundThread = ThreadUtil.newHandlerThread(getName(),
                ThreadPriority.DEFAULT);
        try {

            final CountDownLatch latch = new CountDownLatch(1);

            final BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    Lumberjack.v("Received %s", intent); //$NON-NLS-1$
                    latch.countDown();
                }
            };

            getContext().registerReceiver(receiver, new IntentFilter(getIntentAction()), null,
                    new Handler(backgroundThread.getLooper()));

            registry.init();

            try {
                assertTrue(latch.await(REGISTRY_LOAD_WAIT_MILLIS, TimeUnit.MILLISECONDS));
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }

            getContext().unregisterReceiver(receiver);
        } finally {
            registry.destroy();
            backgroundThread.quit();
        }
    }

    @NonNull
    private String getIntentAction() {
        return String
                .format(Locale.US, "com.twofortyfouram.locale.intent.%s", getName()); //$NON-NLS-1$
    }
}
