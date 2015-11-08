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

package com.twofortyfouram.locale.sdk.host.internal;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginConfigurationTest;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.locale.sdk.host.test.condition.ui.activity.PluginConditionActivity;
import com.twofortyfouram.locale.sdk.host.test.setting.ui.activity.PluginSettingActivity;
import com.twofortyfouram.spackle.ThreadUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests {@link PluginRegistryHandler}.
 */
public final class PluginRegistryHandlerTest extends AndroidTestCase {

    @MediumTest
    public void testHandleInit() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            assertNull(registryHandler.mMutableConditionMap);
            assertNull(registryHandler.mMutableSettingMap);

            registryHandler.handleInit();

            assertNotNull(registryHandler.mMutableConditionMap);
            assertNotNull(registryHandler.mMutableSettingMap);

            assertFalse(registryHandler.mMutableConditionMap.isEmpty());
            assertFalse(registryHandler.mMutableSettingMap.isEmpty());

            /*
             * Verify the debug plug-in condition and debug plug-in setting were
             * detected.
             */
            assertTrue(registryHandler.mMutableConditionMap.containsKey(Plugin
                    .generateRegistryName(getContext().getPackageName(),
                            PluginConditionActivity.class.getName())));
            assertTrue(registryHandler.mMutableSettingMap.containsKey(Plugin.generateRegistryName(
                    getContext().getPackageName(), PluginSettingActivity.class.getName())));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleRemovePackage_condition_and_setting() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            /*
             * Insert a fake condition
             */
            final Plugin testPluginCondition = new Plugin(
                    PluginType.CONDITION,
                    "com.twofortyfouram.locale.condition.hack",
                    "com.twofortyfouram.locale.condition.hack.EditActivity",
                    "com.twofortyfouram.locale.condition.hack.QueryReceiver",
                    1, PluginConfigurationTest
                    .newPluginConfiguration()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            registryHandler.mMutableConditionMap.put(testPluginCondition.getRegistryName(),
                    testPluginCondition);

            /*
             * Insert a fake setting
             */
            final Plugin testPluginSetting = new Plugin(
                    PluginType.CONDITION,
                    "com.twofortyfouram.locale.condition.hack",
                    "com.twofortyfouram.locale.condition.hack.EditActivity",
                    "com.twofortyfouram.locale.condition.hack.FireReceiver",
                    1, PluginConfigurationTest
                    .newPluginConfiguration()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            registryHandler.mMutableSettingMap.put(testPluginSetting.getRegistryName(),
                    testPluginCondition);

            assertEquals(PackageResult.CONDITIONS_AND_SETTINGS_CHANGED,
                    registryHandler
                            .handlePackageRemoved(
                                    "com.twofortyfouram.locale.condition.hack")); //$NON-NLS-1$

            /*
             * Verify the maps are back to their original states
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleRemovePackage_no_plugin() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(PackageResult.NOTHING_CHANGED,
                    registryHandler
                            .handlePackageRemoved("com.twofortyfouram.locale.hack")); //$NON-NLS-1$

            /*
             * Verify the maps were unchanged
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleRemovePackage_condition() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            /*
             * Insert a fake condition
             */
            final Plugin testPlugin = new Plugin(
                    PluginType.CONDITION,
                    "com.twofortyfouram.locale.condition.hack",
                    "com.twofortyfouram.locale.condition.hack.EditActivity",
                    "com.twofortyfouram.locale.condition.hack.QueryReceiver",
                    1, PluginConfigurationTest
                    .newPluginConfiguration()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            registryHandler.mMutableConditionMap.put(testPlugin.getRegistryName(), testPlugin);

            assertEquals(PackageResult.CONDITIONS_CHANGED,
                    registryHandler
                            .handlePackageRemoved(
                                    "com.twofortyfouram.locale.condition.hack")); //$NON-NLS-1$

            /*
             * Verify the maps are back to their original states
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleRemovePackage_setting() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            /*
             * Insert a fake setting
             */
            final Plugin testPlugin = new Plugin(
                    PluginType.SETTING,
                    "com.twofortyfouram.locale.setting.hack",
                    "com.twofortyfouram.locale.setting.hack.EditActivity",
                    "com.twofortyfouram.locale.setting.hack.FireReceiver",
                    1, PluginConfigurationTest
                    .newPluginConfiguration()); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
            registryHandler.mMutableSettingMap.put(testPlugin.getRegistryName(), testPlugin);

            assertEquals(PackageResult.SETTINGS_CHANGED,
                    registryHandler.handlePackageRemoved(
                            "com.twofortyfouram.locale.setting.hack")); //$NON-NLS-1$

            /*
             * Verify the maps are back to their original states
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }

    }

    @MediumTest
    public void testHandleAddPackage_no_package() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(PackageResult.NOTHING_CHANGED,
                    registryHandler
                            .handlePackageAdded("com.twofortyfouram.locale.hack")); //$NON-NLS-1$

            /*
             * Verify the maps were unchanged
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleAddPackage_no_plugin() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(PackageResult.NOTHING_CHANGED,
                    registryHandler.handlePackageAdded("com.google.maps")); //$NON-NLS-1$

            /*
             * Verify the maps were unchanged
             */
            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleAddPackage_condition() {
        final ComponentName disabledComponent = new ComponentName(getContext(), PluginSettingActivity.class.getName());

        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            getContext().getPackageManager().setComponentEnabledSetting(disabledComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            registryHandler.handleInit();

            registryHandler.mMutableConditionMap.clear();
            registryHandler.mMutableSettingMap.clear();

            assertEquals(PackageResult.CONDITIONS_CHANGED,
                    registryHandler
                            .handlePackageAdded(
                                    getContext().getPackageName()));

            final String conditionKey = Plugin.generateRegistryName(getContext().getPackageName(), PluginConditionActivity.class.getName());

            assertTrue(registryHandler.mMutableConditionMap.containsKey(conditionKey));
            assertTrue(registryHandler.mMutableSettingMap.isEmpty());
        } finally {
            getContext().getPackageManager().setComponentEnabledSetting(disabledComponent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);

            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleAddPackage_setting() {
        final ComponentName disabledComponent = new ComponentName(getContext(), PluginConditionActivity.class.getName());

        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            getContext().getPackageManager().setComponentEnabledSetting(disabledComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            registryHandler.handleInit();

            registryHandler.mMutableConditionMap.clear();
            registryHandler.mMutableSettingMap.clear();

            assertEquals(PackageResult.SETTINGS_CHANGED,
                    registryHandler
                            .handlePackageAdded(
                                    getContext().getPackageName()));

            final String settingKey = Plugin.generateRegistryName(getContext().getPackageName(), PluginSettingActivity.class.getName());

            assertTrue(registryHandler.mMutableConditionMap.isEmpty());
            assertTrue(registryHandler.mMutableSettingMap.containsKey(settingKey));
        } finally {
            getContext().getPackageManager().setComponentEnabledSetting(disabledComponent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);

            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    @MediumTest
    public void testHandleAddPackage_condition_and_setting() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            registryHandler.mMutableConditionMap.clear();
            registryHandler.mMutableSettingMap.clear();

            assertEquals(PackageResult.CONDITIONS_AND_SETTINGS_CHANGED,
                    registryHandler
                            .handlePackageAdded(
                                    getContext().getPackageName()));

            final String conditionKey = Plugin.generateRegistryName(getContext().getPackageName(), PluginConditionActivity.class.getName());
            final String settingKey = Plugin.generateRegistryName(getContext().getPackageName(), PluginSettingActivity.class.getName());

            assertTrue(registryHandler.mMutableConditionMap.containsKey(conditionKey));
            assertTrue(registryHandler.mMutableSettingMap.containsKey(settingKey));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    public void testHandleAddPackage_no_change() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(
                    PackageResult.NOTHING_CHANGED,
                    registryHandler
                            .handlePackageChanged(
                                    getContext().getPackageName()));

            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }

    public void testHandleChangePackage_no_change() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(PackageResult.NOTHING_CHANGED,
                    registryHandler.handlePackageChanged("com.google.maps")); //$NON-NLS-1$

            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
        }
    }

    @MediumTest
    public void testHandleChangePackage_no_package() {
        final HandlerThread thread = ThreadUtil.newHandlerThread(getName(), ThreadUtil.ThreadPriority.DEFAULT);
        final PluginRegistryHandler registryHandler = new PluginRegistryHandler(thread.getLooper(),
                getContext(), getName(), getName());
        try {
            registryHandler.handleInit();

            final Map<String, Plugin> conditionsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableConditionMap);
            final Map<String, Plugin> settingsBefore = new HashMap<String, Plugin>(
                    registryHandler.mMutableSettingMap);

            assertEquals(PackageResult.NOTHING_CHANGED,
                    registryHandler
                            .handlePackageChanged("com.twofortyfouram.locale.hack")); //$NON-NLS-1$

            assertTrue(conditionsBefore.equals(registryHandler.mMutableConditionMap));
            assertTrue(settingsBefore.equals(registryHandler.mMutableSettingMap));
        } finally {
            registryHandler.handleDestroy();
            thread.quit();
        }
    }
}
