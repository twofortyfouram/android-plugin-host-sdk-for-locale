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

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.sdk.host.internal.BundleSerializer;
import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginConfigurationTest;
import com.twofortyfouram.locale.sdk.host.model.PluginInstanceData;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.test.context.ReceiverContextWrapper;

import java.util.Collection;


/**
 * Tests {@link Setting}.
 */
public final class SettingTest extends AndroidTestCase {

    @SmallTest
    public void testFire_intent() {
        final ReceiverContextWrapper context = new ReceiverContextWrapper(getContext());

        final Setting setting = new Setting(context, new Plugin(PluginType.SETTING,
                "foo", "bar", "baz", 1, PluginConfigurationTest
                .newPluginConfiguration()
        )); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$

        final Bundle bundle = new Bundle();
        bundle.putString("test_key", "test_value"); //$NON-NLS-1$//$NON-NLS-2$

        final byte[] bytes;
        try {
            bytes = BundleSerializer.serializeToByteArray(bundle);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

        setting.fire(new PluginInstanceData(PluginType.SETTING,
                Plugin.generateRegistryName("foo", "bar"),//$NON-NLS-1$//$NON-NLS-2$
                bytes, "")); //$NON-NLS-1$
        setting.destroy();

        final Collection<ReceiverContextWrapper.SentIntent> intents = context
                .getAndClearSentIntents();
        assertEquals(2, intents.size());

        for (final ReceiverContextWrapper.SentIntent sentIntent : intents) {
            assertFalse(sentIntent.getIsSticky());
            assertNull(sentIntent.getPermission());

            final Intent intent = sentIntent.getIntent();

            assertEquals(com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING,
                    intent.getAction());
            assertEquals(new ComponentName("foo", "baz"),
                    intent.getComponent()); //$NON-NLS-1$ //$NON-NLS-2$
            assertNotNull(intent.getExtras());
            assertEquals(1, intent.getExtras().size());
            assertTrue(intent.hasExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE));

            final Bundle extraBundle = intent
                    .getBundleExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE);

            assertEquals(1, extraBundle.size());
            assertEquals("test_value", extraBundle.get("test_key")); //$NON-NLS-1$//$NON-NLS-2$
        }
    }
}
