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

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.locale.api.Intent;

public final class PluginTypeTest extends AndroidTestCase {

    @SmallTest
    public static void testGetActivityIntentAction_condition() {
        assertEquals(Intent.ACTION_EDIT_CONDITION,
                PluginType.CONDITION.getActivityIntentAction());
    }

    @SmallTest
    public static void testGetActivityIntentAction_setting() {
        assertEquals(Intent.ACTION_EDIT_SETTING,
                PluginType.SETTING.getActivityIntentAction());
    }

    @SmallTest
    public static void testGetReceiverIntentAction_condition() {
        assertEquals(Intent.ACTION_QUERY_CONDITION,
                PluginType.CONDITION.getReceiverIntentAction());
    }

    @SmallTest
    public static void testGetReceiverIntentAction_setting() {
        assertEquals(Intent.ACTION_FIRE_SETTING,
                PluginType.SETTING.getReceiverIntentAction());
    }
}
