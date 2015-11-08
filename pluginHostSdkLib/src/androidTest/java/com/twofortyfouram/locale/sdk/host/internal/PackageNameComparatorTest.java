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
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class PackageNameComparatorTest extends AndroidTestCase {

    @SmallTest
    public void testCompare() {
        List<ResolveInfo> toSort = new LinkedList<ResolveInfo>();
        final ResolveInfo org = getResolveInfoWithPackage("org.charitable.android"); //$NON-NLS-1$
        final ResolveInfo com = getResolveInfoWithPackage("com.megacorp.android"); //$NON-NLS-1$
        final ResolveInfo gov = getResolveInfoWithPackage("gov.thanksobama.android"); //$NON-NLS-1$
        final ResolveInfo net = getResolveInfoWithPackage("net.interwebs.android"); //$NON-NLS-1$
        toSort.add(org);
        toSort.add(com);
        toSort.add(gov);
        toSort.add(net);

        Collections.sort(toSort, new PackageNameComparator());

        assertEquals(com, toSort.get(0));
        assertEquals(gov, toSort.get(1));
        assertEquals(net, toSort.get(2));
        assertEquals(org, toSort.get(3));
    }

    @NonNull
    private static ResolveInfo getResolveInfoWithPackage(@NonNull final String packageName) {
        ResolveInfo info = new ResolveInfo();
        info.activityInfo = new ActivityInfo();
        info.activityInfo.packageName = packageName;

        return info;
    }
}
