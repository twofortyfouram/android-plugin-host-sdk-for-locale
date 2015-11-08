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

import java.util.LinkedList;
import java.util.List;

public final class PluginConfigurationTest extends AndroidTestCase {

    /**
     * Fixture to obtain a new plug-in configuration.
     */
    @NonNull
    public static PluginConfiguration newPluginConfiguration() {
        return new PluginConfiguration(false, false, false, false, false, false,
                new LinkedList<String>());
    }

    @SmallTest
    public void testGetIsBackwardsCompatibilityEnabled_false() {
        assertFalse(newPluginConfiguration().isBackwardsCompatibilityEnabled());
    }

    @SmallTest
    public void testGetIsBackwardsCompatibilityEnabled_true() {
        final PluginConfiguration configuration = new PluginConfiguration(true, false, false,
                false, false, false, new LinkedList<String>());

        assertTrue(configuration.isBackwardsCompatibilityEnabled());
    }

    @SmallTest
    public void testIsRequiresConnectivity_false() {
        assertFalse(newPluginConfiguration().isRequiresConnectivity());
    }

    @SmallTest
    public void testIsRequiresConnectivity_true() {
        final PluginConfiguration configuration = new PluginConfiguration(false, true, false,
                false, false, false, new LinkedList<String>());

        assertTrue(configuration.isRequiresConnectivity());
    }


    @SmallTest
    public void testIsDisruptsConnectivity_false() {
        assertFalse(newPluginConfiguration().isDisruptsConnectivity());
    }

    @SmallTest
    public void testIsDisruptsConnectivity_true() {
        final PluginConfiguration configuration = new PluginConfiguration(false, false, true,
                false, false, false, new LinkedList<String>());

        assertTrue(configuration.isDisruptsConnectivity());
    }

    @SmallTest
    public void testIsBuggy_false() {
        assertFalse(newPluginConfiguration().isBuggy());
    }

    @SmallTest
    public void testIsBuggy_true() {
        final PluginConfiguration configuration = new PluginConfiguration(false, false, false,
                true, false, false, new LinkedList<String>());

        assertTrue(configuration.isBuggy());
    }

    @SmallTest
    public void testIsDrainsBattery_false() {
        assertFalse(newPluginConfiguration().isDrainsBattery());
    }

    @SmallTest
    public void testIsDrainsBattery_true() {
        final PluginConfiguration configuration = new PluginConfiguration(false, false, false,
                false, true, false, new LinkedList<String>());

        assertTrue(configuration.isDrainsBattery());
    }


    @SmallTest
    public void testIsBlacklisted_false() {
        assertFalse(newPluginConfiguration().isBlacklisted());
    }

    @SmallTest
    public void testIsBlacklisted_true() {
        final PluginConfiguration configuration = new PluginConfiguration(false, false, false,
                false, false, true, new LinkedList<String>());

        assertTrue(configuration.isBlacklisted());
    }

    @SmallTest
    public void testParcelable() {
        final PluginConfiguration configuration = newPluginConfiguration();

        Parcel parcel = Parcel.obtain();

        try {
            configuration.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            final PluginConfiguration unparceled = PluginConfiguration.CREATOR
                    .createFromParcel(parcel);

            assertEquals(configuration, unparceled);
        } finally {
            parcel.recycle();
        }
    }

    @SmallTest
    public void testEqualsAndHashcode_same() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration, defaultConfiguration, true);
    }

    @SmallTest
    public void testEqualsAndHashcode_equal() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration, newPluginConfiguration(),
                true);
    }

    @SmallTest
    public void testEqualsAndHashcode_different_backwards_compatibility() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(true, false, false, false, false, false,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_requires_connectivity() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, true, false, false, false, false,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_disrupts_connectivity() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, false, true, false, false, false,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_buggy() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, false, false, true, false, false,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_drains_battery() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, false, false, false, true, false,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_blacklisted() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, false, false, false, false, true,
                        new LinkedList<String>()), false
        );
    }

    @SmallTest
    public void testEqualsAndHashcode_different_alternatives() {
        final PluginConfiguration defaultConfiguration = newPluginConfiguration();

        final List<String> nonEmptyList = new LinkedList<String>();
        nonEmptyList.add("foo");

        MoreAsserts.checkEqualsAndHashCodeMethods(defaultConfiguration,
                new PluginConfiguration(false, false, false, false, false, false,
                        nonEmptyList), false
        );
    }
}
