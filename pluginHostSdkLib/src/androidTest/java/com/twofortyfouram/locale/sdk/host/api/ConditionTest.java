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

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.sdk.host.internal.BundleSerializer;
import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginConfigurationTest;
import com.twofortyfouram.locale.sdk.host.model.PluginInstanceData;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.locale.sdk.host.test.condition.bundle.PluginBundleManager;
import com.twofortyfouram.locale.sdk.host.test.condition.receiver.PluginConditionReceiver;
import com.twofortyfouram.locale.sdk.host.test.condition.ui.activity.PluginConditionActivity;
import com.twofortyfouram.spackle.AndroidSdkVersion;
import com.twofortyfouram.test.context.ReceiverContextWrapper;

import java.util.Collection;

/**
 * Tests {@link Condition} via the debug plug-in.
 */
public final class ConditionTest extends AndroidTestCase {

    @SuppressLint("NewApi")
    @SmallTest
    public void testGetQueryIntent() {
        final Plugin plugin = getDebugPlugin();
        final Bundle bundle = PluginBundleManager.generateBundle(getContext(),
                com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);

        final Intent intent = Condition.newQueryIntent(getDebugPlugin(), bundle);
        assertNotNull(intent);

        final int expectedFlags;
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB_MR1)) {
            expectedFlags = Intent.FLAG_FROM_BACKGROUND | Intent.FLAG_INCLUDE_STOPPED_PACKAGES;
        } else {
            expectedFlags = Intent.FLAG_FROM_BACKGROUND;
        }
        assertEquals(expectedFlags, intent.getFlags());

        assertEquals(com.twofortyfouram.locale.api.Intent.ACTION_QUERY_CONDITION,
                intent.getAction());
        assertEquals(new ComponentName(plugin.getPackageName(), plugin.getReceiverClassName()),
                intent.getComponent());

        assertNull(intent.getData());
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.JELLY_BEAN)) {
            assertNull(intent.getClipData());
        }
        assertEquals(1, intent.getExtras().size());

        final Bundle extraBundle = intent
                .getBundleExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE);
        assertNotNull(extraBundle);
        PluginBundleManager.isBundleValid(extraBundle);
    }

    @SmallTest
    public void testQuery_intent() {
        final ReceiverContextWrapper context = new ReceiverContextWrapper(getContext());

        final Condition condition = new Condition(context, new Plugin(
                PluginType.CONDITION, "foo", "bar", "baz",
                1, PluginConfigurationTest
                .newPluginConfiguration()
        )); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
        try {
            final Bundle bundle = new Bundle();
            bundle.putString("test_key", "test_value"); //$NON-NLS-1$//$NON-NLS-2$

            assertEquals(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN,
                    condition.query(getPluginInstanceData(bundle),
                            com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN)
            );
        } finally {
            condition.destroy();
        }

        final Collection<ReceiverContextWrapper.SentIntent> intents = context
                .getAndClearSentIntents();
        assertEquals(1, intents.size());

        for (final ReceiverContextWrapper.SentIntent sentIntent : intents) {
            assertFalse(sentIntent.getIsSticky());
            assertNull(sentIntent.getPermission());
            assertTrue(sentIntent.getIsOrdered());

            // Don't worry about the action, extras, etc.  Those are handled by testGetQueryIntent().
        }
    }

    @MediumTest
    public void testQuery_satisfied() {
        assertQuerySatisfiedWithState(
                com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_SATISFIED);
    }

    @MediumTest
    public void testQuery_unsatisfied() {
        assertQuerySatisfiedWithState(
                com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNSATISFIED);
    }

    @MediumTest
    public void testQuery_unknown() {
        assertQuerySatisfiedWithState(
                com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN);
    }

    @MediumTest
    public void testQuery_bad_state() {
        final Condition condition = getCondition();

        try {
            final Bundle bundle = new Bundle();
            bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
            bundle.putInt(PluginBundleManager.BUNDLE_EXTRA_INT_RESULT_CODE,
                    1); //$NON-NLS-1$

            assertEquals(com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN,
                    condition.query(getPluginInstanceData(bundle),
                            com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN)
            );
        } finally {
            condition.destroy();
        }
    }

    private void assertQuerySatisfiedWithState(final int state) {
        final Condition condition = getCondition();

        try {
            final Bundle bundle = PluginBundleManager.generateBundle(getContext(), state);
            assertEquals(state, condition
                    .query(getPluginInstanceData(bundle),
                            com.twofortyfouram.locale.api.Intent.RESULT_CONDITION_UNKNOWN));
        } finally {
            condition.destroy();
        }
    }

    @NonNull
    private Condition getCondition() {
        return new Condition(getContext(), getDebugPlugin());
    }

    @NonNull
    private PluginInstanceData getPluginInstanceData(@NonNull final Bundle bundle) {
        final Plugin debugPlugin = getDebugPlugin();

        final byte[] bytes;
        try {
            bytes = BundleSerializer.serializeToByteArray(bundle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new PluginInstanceData(debugPlugin.getType(), debugPlugin.getRegistryName(),
                bytes, "");
    }

    @NonNull
    private Plugin getDebugPlugin() {
        return new Plugin(PluginType.CONDITION, getContext().getPackageName(),
                PluginConditionActivity.class.getName(),
                PluginConditionReceiver.class.getName(), 0,
                PluginConfigurationTest.newPluginConfiguration());
    }
}
