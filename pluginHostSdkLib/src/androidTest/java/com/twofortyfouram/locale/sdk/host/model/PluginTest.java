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

package com.twofortyfouram.locale.sdk.host.model;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.MoreAsserts;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.sdk.host.test.R;
import com.twofortyfouram.locale.sdk.host.test.condition.receiver.PluginConditionReceiver;
import com.twofortyfouram.locale.sdk.host.test.condition.ui.activity.PluginConditionActivity;
import com.twofortyfouram.locale.sdk.host.test.setting.receiver.PluginSettingReceiver;
import com.twofortyfouram.locale.sdk.host.test.setting.ui.activity.PluginSettingActivity;

import java.util.LinkedList;

/**
 * Tests Plugin.
 */
public final class PluginTest extends AndroidTestCase {

    @NonNull
    private static final String DEFAULT_PACKAGE = "com.twofortyfouram.locale"; //$NON-NLS-1$

    @NonNull
    private static final String DEFAULT_ACTIVITY
            = "com.twofortyfouram.locale.ui.activity.SomeActivity"; //$NON-NLS-1$

    @NonNull
    private static final String DEFAULT_RECEIVER
            = "com.twofortyfouram.locale.receiver.SomeReceiver"; //$NON-NLS-1$

    private static final int DEFAULT_VERSION_CODE = 1;

    @NonNull
    private static final PluginConfiguration DEFAULT_CONFIGURATION = PluginConfigurationTest
            .newPluginConfiguration();

    @NonNull
    public static final Plugin newDefaultPlugin() {
        return new Plugin(PluginType.CONDITION, DEFAULT_PACKAGE, DEFAULT_ACTIVITY,
                DEFAULT_RECEIVER, DEFAULT_VERSION_CODE,
                PluginConfigurationTest.newPluginConfiguration());
    }

    @SmallTest
    public static void testGetPackageName() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(DEFAULT_PACKAGE, defaultPlugin.getPackageName());
    }

    @SmallTest
    public static void testGetActivityClassName() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(DEFAULT_ACTIVITY, defaultPlugin.getActivityClassName());
    }

    @SmallTest
    public static void testGetReceiverClassName() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(DEFAULT_RECEIVER, defaultPlugin.getReceiverClassName());
    }

    @SmallTest
    public static void testGetVersionCode() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(DEFAULT_VERSION_CODE, defaultPlugin.getVersionCode());
    }

    @SmallTest
    public static void testGetConfiguration() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(DEFAULT_CONFIGURATION, defaultPlugin.getConfiguration());
    }

    @SmallTest
    public static void testEqualsAndHashCode() {
        Plugin defaultPlugin = newDefaultPlugin();
        Plugin defaultPlugin2 = newDefaultPlugin();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, defaultPlugin, true);
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, defaultPlugin2, true);

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                DEFAULT_PACKAGE, DEFAULT_ACTIVITY, DEFAULT_RECEIVER, DEFAULT_VERSION_CODE,
                DEFAULT_CONFIGURATION), true);

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.SETTING,
                DEFAULT_PACKAGE, DEFAULT_ACTIVITY, DEFAULT_RECEIVER, DEFAULT_VERSION_CODE,
                DEFAULT_CONFIGURATION), false);
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                        "foo", //$NON-NLS-1$
                        DEFAULT_ACTIVITY, DEFAULT_RECEIVER, DEFAULT_VERSION_CODE,
                        DEFAULT_CONFIGURATION),
                false
        );
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                DEFAULT_PACKAGE, "foo", //$NON-NLS-1$
                DEFAULT_RECEIVER, DEFAULT_VERSION_CODE, DEFAULT_CONFIGURATION), false);
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                        DEFAULT_PACKAGE, DEFAULT_ACTIVITY, "foo", DEFAULT_VERSION_CODE,
                        DEFAULT_CONFIGURATION),
                false
        ); //$NON-NLS-1$
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                        DEFAULT_PACKAGE, DEFAULT_ACTIVITY, DEFAULT_RECEIVER,
                        DEFAULT_VERSION_CODE + 1,
                        DEFAULT_CONFIGURATION),
                false
        );
        MoreAsserts.checkEqualsAndHashCodeMethods(defaultPlugin, new Plugin(PluginType.CONDITION,
                        DEFAULT_PACKAGE, DEFAULT_ACTIVITY, DEFAULT_RECEIVER, DEFAULT_VERSION_CODE,
                        new PluginConfiguration(true, false, false, false, false, false,
                                new LinkedList<String>())
                ),
                false
        );
    }

    @SmallTest
    public static void testToString() {
        final Plugin plugin = newDefaultPlugin();
        final String pluginString = plugin.toString();

        assertNotNull(pluginString);

        assertTrue(pluginString.contains(plugin.getType().toString()));
        assertTrue(pluginString.contains(plugin.getPackageName()));
        assertTrue(pluginString.contains(plugin.getActivityClassName()));
        assertTrue(pluginString.contains(plugin.getReceiverClassName()));
        assertTrue(pluginString.contains(Integer.toString(plugin.getVersionCode())));
        assertTrue(pluginString.contains(plugin.getConfiguration().toString()));
    }

    @SmallTest
    public static void testParcelable() {
        final Plugin plugin = newDefaultPlugin();

        final Parcel parcel = Parcel.obtain();
        try {
            plugin.writeToParcel(parcel, 0);

            /*
             * Reset parcel for reading.
             */
            parcel.setDataPosition(0);

            final Plugin pluginUnparceled = Plugin.CREATOR.createFromParcel(parcel);
            assertEquals(plugin, pluginUnparceled);
        } finally {
            parcel.recycle();
        }
    }

    @SmallTest
    public void testGetLabel_none() {
        Plugin plugin = newDefaultPlugin();
        assertEquals(plugin.getActivityClassName(), plugin.getActivityLabel(getContext()));
    }

    @SmallTest
    public void testGetLabel_debug_condition() {
        final Plugin plugin = new Plugin(PluginType.SETTING, getContext().getPackageName(),
                PluginConditionActivity.class.getName(), PluginConditionReceiver.class.getName(),
                1, PluginConfigurationTest.newPluginConfiguration());
        assertEquals(
                getContext().getString(R.string.com_twofortyfouram_locale_sdk_host_condition_name),
                plugin.getActivityLabel(getContext())); //$NON-NLS-1$
    }

    @SmallTest
    public void testGetLabel_debug_setting() {
        final Plugin plugin = new Plugin(PluginType.SETTING, getContext().getPackageName(),
                PluginSettingActivity.class.getName(), PluginSettingReceiver.class.getName(), 1,
                PluginConfigurationTest.newPluginConfiguration());
        assertEquals(
                getContext().getString(R.string.com_twofortyfouram_locale_sdk_host_setting_name),
                plugin.getActivityLabel(getContext())); //$NON-NLS-1$
    }

    @SmallTest
    public void testGetIcon_none() {
        Plugin plugin = newDefaultPlugin();
        assertNotNull(plugin.getActivityIcon(getContext()));
    }

    @SmallTest
    public static void testGetRegistryName() {
        final Plugin defaultPlugin = newDefaultPlugin();

        assertEquals(Plugin.generateRegistryName(DEFAULT_PACKAGE, DEFAULT_ACTIVITY),
                defaultPlugin.getRegistryName());
    }

    @SmallTest
    public static void testGenerateRegistryName() {
        assertEquals("foo:bar", Plugin.generateRegistryName("foo",
                "bar")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @SmallTest
    public static void testGenerateRegistryName_badParameters() {
        try {
            Plugin.generateRegistryName(null, "foo"); //$NON-NLS-1$
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }

        try {
            Plugin.generateRegistryName("foo", null); //$NON-NLS-1$
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }

        try {
            Plugin.generateRegistryName("", "foo"); //$NON-NLS-1$ //$NON-NLS-2$
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }

        try {
            Plugin.generateRegistryName("foo", ""); //$NON-NLS-1$ //$NON-NLS-2$
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }
    }
}
