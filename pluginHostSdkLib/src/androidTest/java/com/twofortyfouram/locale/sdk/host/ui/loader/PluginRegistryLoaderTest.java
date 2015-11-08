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

package com.twofortyfouram.locale.sdk.host.ui.loader;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.FlakyTest;
import android.test.LoaderTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import com.twofortyfouram.locale.sdk.host.api.PluginRegistry;
import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.spackle.AndroidSdkVersion;

import java.util.Map;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public final class PluginRegistryLoaderTest extends LoaderTestCase {

    /*
     * This test is flaky because the registry can change at any time.
     */
    @MediumTest
    @FlakyTest(tolerance = 3)
    public void testLoad_conditions() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final PluginRegistryLoader loader = new PluginRegistryLoader(getContext(),
                    PluginType.CONDITION);

            Map<String, Plugin> loaderRegistry = getLoaderResultSynchronously(loader);
            Map<String, Plugin> actualRegistry = PluginRegistry.getInstance(getContext())
                    .getPluginMap(PluginType.CONDITION);

            assertSame(actualRegistry, loaderRegistry);
        }
    }

    /*
     * This test is flaky because the registry can change at any time.
     */
    @MediumTest
    @FlakyTest(tolerance = 3)
    public void testLoad_settings() {
        if (AndroidSdkVersion.isAtLeastSdk(Build.VERSION_CODES.HONEYCOMB)) {
            final PluginRegistryLoader loader = new PluginRegistryLoader(getContext(),
                    PluginType.SETTING);

            Map<String, Plugin> loaderRegistry = getLoaderResultSynchronously(loader);
            Map<String, Plugin> actualRegistry = PluginRegistry.getInstance(getContext())
                    .getPluginMap(PluginType.SETTING);

            assertSame(actualRegistry, loaderRegistry);
        }
    }
}
