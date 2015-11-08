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

package com.twofortyfouram.locale.sdk.host.ui.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.assertion.BundleAssertions;
import com.twofortyfouram.locale.sdk.host.internal.BundleSerializer;
import com.twofortyfouram.locale.sdk.host.internal.PluginEditDelegate;
import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginErrorEdit;
import com.twofortyfouram.locale.sdk.host.model.PluginInstanceData;
import com.twofortyfouram.locale.sdk.host.model.PluginTest;
import com.twofortyfouram.spackle.AndroidSdkVersion;

public final class AbstractPluginEditFragmentTest extends AndroidTestCase {

    @SmallTest
    public static void testNewArgs_without_previous_values() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final Bundle bundle = AbstractPluginEditFragment
                    .newArgs(PluginTest.newDefaultPlugin(), null);

            BundleAssertions.assertKeyCount(bundle, 1);
            assertEquals(
                    PluginTest.newDefaultPlugin(),
                    bundle.getParcelable(
                            AbstractPluginEditFragment.ARG_EXTRA_PARCELABLE_CURRENT_PLUGIN)
            );
        }
    }

    @SmallTest
    public static void testNewArgs_with_previous_values() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final Plugin plugin = PluginTest.newDefaultPlugin();
            final PluginInstanceData pluginInstanceData = new PluginInstanceData(plugin.getType(),
                    plugin.getRegistryName(), new byte[]{},
                    "foo");  //$NON-NLS-1$

            final Bundle bundle = AbstractPluginEditFragment.newArgs(PluginTest.newDefaultPlugin(),
                    pluginInstanceData);

            BundleAssertions.assertKeyCount(bundle, 2);
            assertEquals(
                    plugin,
                    bundle.getParcelable(
                            AbstractPluginEditFragment.ARG_EXTRA_PARCELABLE_CURRENT_PLUGIN)
            );
            assertEquals(pluginInstanceData, bundle.getParcelable(
                    AbstractPluginEditFragment.ARG_EXTRA_PARCELABLE_PREVIOUS_PLUGIN_INSTANCE_DATA));
        }
    }

    @SmallTest
    public static void testGetPluginStartIntent_new_condition() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final Plugin plugin = PluginTest.newDefaultPlugin();

            final Intent i = PluginEditDelegate.getPluginStartIntent(
                    PluginTest.newDefaultPlugin(), null, "Edit Situation"); //$NON-NLS-1$

            assertNotNull(i);
            assertEquals(com.twofortyfouram.locale.api.Intent.ACTION_EDIT_CONDITION, i.getAction());
            assertEquals(new ComponentName(plugin.getPackageName(), plugin.getActivityClassName()),
                    i.getComponent());
            assertNull(i.getData());

            BundleAssertions.assertHasString(i.getExtras(),
                    com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BREADCRUMB,
                    "Edit Situation"); //$NON-NLS-1$
            BundleAssertions.assertKeyCount(i.getExtras(), 1);
        }
    }

    @SmallTest
    public static void testGetPluginStartIntent_old_condition() throws Exception {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final Plugin plugin = PluginTest.newDefaultPlugin();
            final PluginInstanceData pluginInstanceData = new PluginInstanceData(plugin.getType(),
                    plugin.getRegistryName(), BundleSerializer.serializeToByteArray(new Bundle()),
                    "bar");
            final Intent i = PluginEditDelegate.getPluginStartIntent(
                    plugin,
                    pluginInstanceData, "Edit Situation"); //$NON-NLS-1$ //$NON-NLS-2$

            assertNotNull(i);
            assertEquals(com.twofortyfouram.locale.api.Intent.ACTION_EDIT_CONDITION, i.getAction());
            assertEquals(new ComponentName(plugin.getPackageName(), plugin.getActivityClassName()),
                    i.getComponent());
            assertNull(i.getData());

            BundleAssertions.assertHasString(i.getExtras(),
                    com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BREADCRUMB,
                    "Edit Situation"); //$NON-NLS-1$
            BundleAssertions.assertKeyCount(i.getExtras(), 3);
        }
    }

    @SmallTest
    public static void testIsIntentValid_null() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertFalse(
                    PluginEditDelegate.isIntentValid(null, PluginTest.newDefaultPlugin())
                            .isEmpty()
            );
        }
    }

    @SmallTest
    public static void testIsIntentValid_empty() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertFalse(PluginEditDelegate
                    .isIntentValid(new Intent(), PluginTest.newDefaultPlugin()).isEmpty());
        }
    }

    @SmallTest
    public static void testIsIntentValid_missing_blurb() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertTrue(PluginEditDelegate.isIntentValid(
                    new Intent().putExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE,
                            new Bundle()), PluginTest.newDefaultPlugin()
            )
                    .contains(PluginErrorEdit.BLURB_MISSING));
        }
    }

    @SmallTest
    public static void testIsIntentValid_missing_bundle() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertFalse(PluginEditDelegate.isIntentValid(
                    new Intent().putExtra(com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BLURB,
                            "foo"), PluginTest.newDefaultPlugin()
            ).isEmpty()); //$NON-NLS-1$
        }
    }

    @SmallTest
    public static void testIsIntentValid_null_blurb() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertFalse(PluginEditDelegate.isIntentValid(
                    new Intent().putExtras(generateBundle(new Bundle(), null)),
                    PluginTest.newDefaultPlugin()).isEmpty());
        }
    }

    @SmallTest
    public static void testIsIntentValid_null_empty() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertTrue(PluginEditDelegate.isIntentValid(
                    new Intent().putExtras(generateBundle(new Bundle(), "")),
                    PluginTest.newDefaultPlugin()).isEmpty()); //$NON-NLS-1$
        }
    }

    @SmallTest
    public static void testIsIntentValid_null_bundle() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertFalse(PluginEditDelegate.isIntentValid(
                    new Intent().putExtras(generateBundle(null, "foo")),
                    PluginTest.newDefaultPlugin())
                    .isEmpty()); //$NON-NLS-1$
        }
    }

    @SmallTest
    public static void testIsIntentValid_valid() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            assertTrue(PluginEditDelegate.isIntentValid(
                    new Intent().putExtras(generateBundle(new Bundle(), "foo")),
                    PluginTest.newDefaultPlugin()).isEmpty()); //$NON-NLS-1$
        }
    }

    @NonNull
    private static Bundle generateBundle(@Nullable final Bundle bundle,
            @Nullable final String blurb) {
        final Bundle result = new Bundle();
        result.putString(com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BLURB, blurb);
        result.putBundle(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE, bundle);

        return result;
    }
}
