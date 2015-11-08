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

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Tests {@link PackageResult}.
 */
public final class PackageResultTest extends TestCase {

    @SmallTest
    public static void testget_conditions() {
        assertEquals(PackageResult.CONDITIONS_CHANGED, PackageResult.get(true, false));
    }

    @SmallTest
    public static void testget_settings() {
        assertEquals(PackageResult.SETTINGS_CHANGED, PackageResult.get(false, true));
    }

    @SmallTest
    public static void testget_conditions_and_settings() {
        assertEquals(PackageResult.CONDITIONS_AND_SETTINGS_CHANGED, PackageResult.get(true, true));
    }

    @SmallTest
    public static void testget_nothing() {
        assertEquals(PackageResult.NOTHING_CHANGED, PackageResult.get(false, false));
    }
}
