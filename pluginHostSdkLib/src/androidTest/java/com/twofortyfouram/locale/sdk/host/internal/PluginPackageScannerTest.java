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

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.spackle.AppBuildInfo;
import com.twofortyfouram.test.assertion.MoarAsserts;

import java.util.Collection;

public final class PluginPackageScannerTest extends AndroidTestCase {

    @SmallTest
    public void testNonInstantiable() {
        MoarAsserts.assertNoninstantiable(PluginPackageScanner.class);
    }

    @SmallTest
    public void testFindActivities_conditions() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findActivities(getContext(),
                PluginType.CONDITION, null);
        assertNotNull(infos);
        assertTrue(1 <= infos.size());
    }

    @SmallTest
    public void testFindActivities_settings() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findActivities(getContext(),
                PluginType.SETTING, null);
        assertNotNull(infos);
        assertTrue(1 <= infos.size());
    }

    @SmallTest
    public void testFindActivities_debug_condition() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findActivities(getContext(),
                PluginType.CONDITION, getContext().getPackageName());
        assertNotNull(infos);
        assertEquals(1, infos.size());
    }

    @SmallTest
    public void testFindActivities_debug_setting() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findActivities(getContext(),
                PluginType.SETTING, getContext().getPackageName());
        assertNotNull(infos);
        assertEquals(1, infos.size());
    }

    @SmallTest
    public void testFindReceivers_conditions() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findReceivers(getContext(),
                PluginType.CONDITION, null);
        assertNotNull(infos);
        assertTrue(1 <= infos.size());
    }

    @SmallTest
    public void testFindReceivers_settings() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findReceivers(getContext(),
                PluginType.SETTING, null);
        assertNotNull(infos);
        assertTrue(1 <= infos.size());
    }

    @SmallTest
    public void testFindReceivers_debug_condition() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findReceivers(getContext(),
                PluginType.CONDITION, getContext().getPackageName());
        assertNotNull(infos);
        assertEquals(1, infos.size());
    }

    @SmallTest
    public void testFindReceivers_debug_setting() {
        final Collection<ResolveInfo> infos = PluginPackageScanner.findReceivers(getContext(),
                PluginType.SETTING, getContext().getPackageName());
        assertNotNull(infos);
        assertEquals(1, infos.size());
    }

    @SmallTest
    public void testGetVersionCode_unknown() {
        final int actualVersionCode = PluginPackageScanner.getVersionCode(getContext()
                .getPackageManager(), "com.twofortyfouram.locale.bork"); //$NON-NLS-1$

        assertEquals(-1, actualVersionCode);
    }

    @SmallTest
    public void testGetVersionCode_known() {
        final int expectedVersionCode = AppBuildInfo.getVersionCode(getContext());
        final int actualVersionCode = PluginPackageScanner.getVersionCode(getContext()
                .getPackageManager(), getContext().getPackageName());

        assertEquals(expectedVersionCode, actualVersionCode);
    }

    @SmallTest
    public void testIsTargetSdkCorrect_true() {
        assertTrue(PluginPackageScanner
                .isTargetSdkCorrect(getContext(), getResolveInfoWithTargetSdkVersion(getContext()
                        .getApplicationInfo().targetSdkVersion)));
    }

    @SmallTest
    public void testIsTargetSdkCorrect_false() {
        assertFalse(PluginPackageScanner
                .isTargetSdkCorrect(getContext(), getResolveInfoWithTargetSdkVersion(getContext()
                        .getApplicationInfo().targetSdkVersion - 1)));
    }

    @SmallTest
    public void testIsApplicationEnabled_true() {
        assertTrue(PluginPackageScanner
                .isApplicationEnabled(getResolveInfoWithApplicationEnabled(true)));
    }

    @SmallTest
    public void testIsApplicationEnabled_false() {
        assertFalse(PluginPackageScanner
                .isApplicationEnabled(getResolveInfoWithApplicationEnabled(false)));
    }

    @SmallTest
    public void testIsComponentEnabled_true() {
        assertTrue(
                PluginPackageScanner.isComponentEnabled(getResolveInfoWithActivityEnabled(true)));
    }

    @SmallTest
    public void testIsComponentEnabled_false() {
        assertFalse(PluginPackageScanner
                .isComponentEnabled(getResolveInfoWithActivityEnabled(false)));
    }

    @SmallTest
    public void testIsComponentExported_true() {
        assertTrue(PluginPackageScanner
                .isComponentExported(getResolveInfoWithActivityExported(true)));
    }

    @SmallTest
    public void testIsComponentExported_false() {
        assertFalse(PluginPackageScanner
                .isComponentExported(getResolveInfoWithActivityExported(false)));
    }

    @SmallTest
    public void testIsComponentPermissionGranted_true() {
        assertTrue(PluginPackageScanner.isComponentPermissionGranted(getContext(),
                getResolveInfoWithPermission(null)));
    }

    @SmallTest
    public void testIsComponentPermissionGranted_false() {
        assertFalse(PluginPackageScanner.isComponentPermissionGranted(getContext(),
                getResolveInfoWithPermission(
                        "com.nefarious.app.permission.NO_SOUP_FOR_YOU")
        )); //$NON-NLS-1$
    }

    @SmallTest
    public void testIsInstallLocationCorrect_internal() {
        assertTrue(PluginPackageScanner.isInstallLocationCorrect(getContext(),
                getResolveInfoWithInstallLocation(false)));
    }

    @SmallTest
    public void testIsInstallLocationCorrect_external() {
        assertFalse(PluginPackageScanner.isInstallLocationCorrect(getContext(),
                getResolveInfoWithInstallLocation(true)));
    }

    // @SmallTest
    // public void testIsPluginValid() {
    // final ResolveInfo activityResolveInfo = new ResolveInfo();
    // final List<ResolveInfo> receiverResolveInfos = new LinkedList<ResolveInfo>();
    //
    // assertTrue(PluginPackageScanner.isPluginValid(getContext(), PluginType.CONDITION,
    // activityResolveInfo, receiverResolveInfos))
    // }

    @NonNull
    private ResolveInfo getResolveInfoWithTargetSdkVersion(final int targetSdkVersion) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.applicationInfo = new ApplicationInfo();
        info.activityInfo.applicationInfo.targetSdkVersion = targetSdkVersion;
        return info;
    }

    @NonNull
    private ResolveInfo getResolveInfoWithApplicationEnabled(final boolean isApplicationEnabled) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.applicationInfo = new ApplicationInfo();
        info.activityInfo.applicationInfo.enabled = isApplicationEnabled;
        return info;
    }

    @NonNull
    private ResolveInfo getResolveInfoWithActivityEnabled(final boolean isActivityEnabled) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.enabled = isActivityEnabled;
        return info;
    }

    @NonNull
    private ResolveInfo getResolveInfoWithActivityExported(final boolean isActivityExported) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.exported = isActivityExported;
        return info;
    }

    @NonNull
    private ResolveInfo getResolveInfoWithPermission(final String permissionString) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.permission = permissionString;
        return info;
    }

    @NonNull
    private ResolveInfo getResolveInfoWithInstallLocation(final boolean isExternal) {
        final ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.applicationInfo = new ApplicationInfo();
        info.activityInfo.packageName = getContext().getPackageName();
        info.activityInfo.applicationInfo.flags = isExternal ? ApplicationInfo.FLAG_EXTERNAL_STORAGE
                : 0;
        return info;
    }
}
