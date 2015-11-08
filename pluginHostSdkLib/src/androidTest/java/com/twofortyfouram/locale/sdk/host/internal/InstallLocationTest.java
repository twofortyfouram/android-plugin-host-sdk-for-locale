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

import android.content.pm.PackageManager;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Tests {@link InstallLocation}.
 */
public final class InstallLocationTest extends AndroidTestCase {

    @SmallTest
    public static void testGetInstallLocation() {
        assertEquals(InstallLocation.auto,
                InstallLocation.getInstallLocation(InstallLocation.MANIFEST_INSTALL_LOCATION_AUTO));

        assertEquals(
                InstallLocation.internalOnly,
                InstallLocation
                        .getInstallLocation(
                                InstallLocation.MANIFEST_INSTALL_LOCATION_INTERNAL_ONLY));

        assertEquals(
                InstallLocation.preferExternal,
                InstallLocation
                        .getInstallLocation(
                                InstallLocation.MANIFEST_INSTALL_LOCATION_PREFER_EXTERNAL));

        assertEquals(InstallLocation.UNKNOWN,
                InstallLocation.getInstallLocation(Integer.MIN_VALUE));
    }

    @SmallTest
    public void testGetManifestInstallLocation() throws IOException, XmlPullParserException,
            PackageManager.NameNotFoundException {
        assertEquals(InstallLocation.internalOnly, InstallLocation.getManifestInstallLocation(
                getContext(), getContext().getPackageName()));
    }
}
