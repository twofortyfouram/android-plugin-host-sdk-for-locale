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

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.test.MoreAsserts;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.sdk.host.internal.BundleSerializer;

import junit.framework.TestCase;

import java.util.Arrays;

public final class PluginInstanceDataTest extends TestCase {

    @NonNull
    private static final PluginType DEFAULT_TYPE = PluginType.SETTING;

    @NonNull
    private static final String DEFAULT_REGISTRY_NAME = Plugin.generateRegistryName("foo", "bar");
    //$NON-NLS-1$ //$NON-NLS-2$

    @NonNull
    private static final byte[] DEFAULT_BUNDLE = getDefaultBundle();

    @NonNull
    private static final String DEFAULT_BLURB = "Thanks Obama"; //$NON-NLS-1$


    private static byte[] getDefaultBundle() {
        try {
            return BundleSerializer.serializeToByteArray(new Bundle());
        } catch (Exception e) {
            // Should never occur
            return new byte[0];
        }
    }

    @SmallTest
    public static void testGetType() {
        final PluginInstanceData data = newDefaultPluginInstanceData();

        assertEquals(PluginType.SETTING, data.getType());
    }

    @SmallTest
    public static void testGetRegistryName() {
        final PluginInstanceData data = newDefaultPluginInstanceData();

        assertEquals(DEFAULT_REGISTRY_NAME, data.getRegistryName());
    }

    @SmallTest
    public static void testGetBlurb() {
        final PluginInstanceData data = newDefaultPluginInstanceData();

        assertEquals(DEFAULT_BLURB, data.getBlurb());
    }

    @SmallTest
    public static void testGetSerializedBundle() {
        final PluginInstanceData data = newDefaultPluginInstanceData();

        assertTrue(Arrays.equals(DEFAULT_BUNDLE, data.getSerializedBundle()));
    }

    @SmallTest
    public static void testGetSerializedBundle_copy() {
        final PluginInstanceData data = newDefaultPluginInstanceData();

        assertNotSame(data.getSerializedBundle(), data.getSerializedBundle());
    }

    @SmallTest
    public static void testEqualsAndHashcode() {
        PluginInstanceData defaultData = newDefaultPluginInstanceData();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultData, defaultData, true);
        MoreAsserts
                .checkEqualsAndHashCodeMethods(defaultData, newDefaultPluginInstanceData(), true);

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultData,
                new PluginInstanceData(PluginType.CONDITION, DEFAULT_REGISTRY_NAME, DEFAULT_BUNDLE,
                        DEFAULT_BLURB), false
        );

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultData,
                new PluginInstanceData(DEFAULT_TYPE, Plugin.generateRegistryName("bar", "foo"),
                        DEFAULT_BUNDLE,
                        DEFAULT_BLURB), false
        );

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultData,
                new PluginInstanceData(DEFAULT_TYPE, DEFAULT_REGISTRY_NAME, "bar".getBytes(),
                        DEFAULT_BLURB), false
        );

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultData,
                new PluginInstanceData(DEFAULT_TYPE, DEFAULT_REGISTRY_NAME, DEFAULT_BUNDLE,
                        "bork"), false
        );
    }

    @SmallTest
    public static void testToString() {
        final PluginInstanceData defaultData = newDefaultPluginInstanceData();
        final String stringified = defaultData.toString();

        assertNotNull(stringified);

        assertTrue(stringified.contains(DEFAULT_TYPE.name()));
        assertTrue(stringified.contains(DEFAULT_REGISTRY_NAME));
        assertTrue(stringified.contains(DEFAULT_BLURB));
    }

    @SmallTest
    public static void testParcelable() {
        final Parcel parcel = Parcel.obtain();
        try {
            final PluginInstanceData data = newDefaultPluginInstanceData();

            data.writeToParcel(parcel, 0);

            parcel.setDataPosition(0);
            final PluginInstanceData unparceled = PluginInstanceData.CREATOR
                    .createFromParcel(parcel);

            assertEquals(data, unparceled);
        } finally {
            parcel.recycle();
        }
    }

    @NonNull
    private static PluginInstanceData newDefaultPluginInstanceData() {
        return new PluginInstanceData(DEFAULT_TYPE, DEFAULT_REGISTRY_NAME, DEFAULT_BUNDLE,
                DEFAULT_BLURB);
    }


}
