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

import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.twofortyfouram.spackle.bundle.BundleComparer;

import java.io.NotSerializableException;
import java.util.ArrayList;

/**
 * Tests serialization and de-serialization of {@code Bundle} objects.
 */
public class BundleSerializerTest extends AndroidTestCase {

    /**
     * Tests {@link BundleSerializer#serializeToByteArray(Bundle)} with a null
     * bundle.
     *
     * @throws Exception if the test fails
     */
    public void testSerializeNull() throws Exception {
        try {
            BundleSerializer.serializeToByteArray(null);
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }
    }

    /**
     * Tests {@link BundleSerializer#deserializeFromByteArray(byte[])} with a
     * null byte[].
     *
     * @throws Exception if the test fails
     */
    public void testDeserializeNull() throws Exception {
        try {
            BundleSerializer.deserializeFromByteArray(null);
            fail();
        } catch (final AssertionError e) {
            // expected exception
        }
    }

    /**
     * Tests storing boolean types
     * <p/>
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testBooleans() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putBoolean("com.test.extra_false", false); //$NON-NLS-1$
        inputBundle.putBoolean("com.test.extra_true", true); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing boolean[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testBooleanArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putBooleanArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putBooleanArray("com.test.extra_empty", new boolean[0]); //$NON-NLS-1$
        inputBundle.putBooleanArray("com.test.extra", new boolean[]{false, true}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing byte types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testByte() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putByte("com.test.extra_min", Byte.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putByte("com.test.extra_max", Byte.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing byte[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testByteArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putByteArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putByteArray("com.test.extra_empty", new byte[0]); //$NON-NLS-1$
        inputBundle.putByteArray("com.test.extra", new byte[]{10}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing short types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testShort() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putShort("com.test.extra_min", Short.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putShort("com.test.extra_max", Short.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing short[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testShortArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putShortArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putShortArray("com.test.extra_empty", new short[0]); //$NON-NLS-1$
        inputBundle.putShortArray(
                "com.test.extra_min", new short[]{Short.MIN_VALUE, Short.MAX_VALUE}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing int types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testInts() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putInt("com.test.extra_min", Integer.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putInt("com.test.extra_one", 1); //$NON-NLS-1$
        inputBundle.putInt("com.test.extra_two", 2); //$NON-NLS-1$
        inputBundle.putInt("com.test.extra_max", Integer.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing int[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testIntArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putIntArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putIntArray("com.test.extra_empty", new int[0]); //$NON-NLS-1$
        inputBundle.putIntArray(
                "com.test.extra",
                new int[]{Integer.MIN_VALUE, 0, 1, 2, 3, Integer.MAX_VALUE}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing ArrayList<Integer> types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testIntegerArrayList() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putIntegerArrayList("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putIntegerArrayList("com.test.extra_empty",
                new ArrayList<Integer>()); //$NON-NLS-1$

        final ArrayList<Integer> random = new ArrayList<Integer>();
        random.add(Integer.valueOf(Integer.MIN_VALUE));
        random.add(Integer.valueOf(Integer.MAX_VALUE));
        inputBundle.putIntegerArrayList("com.test.extra_random", random); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing long types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testLongs() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putLong("com.test.extra_min", Long.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putLong("com.test.extra_one", 1); //$NON-NLS-1$
        inputBundle.putLong("com.test.extra_two", 2); //$NON-NLS-1$
        inputBundle.putLong("com.test.extra_max", Long.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing long[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testLongArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putLongArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putLongArray("com.test.extra_empty", new long[0]); //$NON-NLS-1$
        inputBundle.putLongArray(
                "com.test.extra",
                new long[]{Long.MIN_VALUE, 0, 1, 2, 3, Long.MAX_VALUE}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing double types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testDoubles() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putDouble("com.test.extra_min", Double.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putDouble("com.test.extra_one", 1); //$NON-NLS-1$
        inputBundle.putDouble("com.test.extra_two", 2); //$NON-NLS-1$
        inputBundle.putDouble("com.test.extra_max", Double.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing double[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testDoubleArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putDoubleArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putDoubleArray("com.test.extra_empty", new double[0]); //$NON-NLS-1$
        inputBundle.putDoubleArray(
                "com.test.extra",
                new double[]{Double.MIN_VALUE, 0, 1, 2, 3, Double.MAX_VALUE}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing float types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testFloats() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putFloat("com.test.extra_min", Float.MIN_VALUE); //$NON-NLS-1$
        inputBundle.putFloat("com.test.extra_one", 1); //$NON-NLS-1$
        inputBundle.putFloat("com.test.extra_two", 2); //$NON-NLS-1$
        inputBundle.putFloat("com.test.extra_max", Float.MAX_VALUE); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing float[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testFloatArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putFloatArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putFloatArray("com.test.extra_empty", new float[0]); //$NON-NLS-1$
        inputBundle.putFloatArray(
                "com.test.extra",
                new float[]{Float.MIN_VALUE, 0, 1, 2, 3, Float.MAX_VALUE}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing char types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testChar() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putChar("com.test.extra_min", Character.MIN_LOW_SURROGATE); //$NON-NLS-1$
        inputBundle.putChar("com.test.extra_max", Character.MAX_HIGH_SURROGATE); //$NON-NLS-1$
        inputBundle.putChar("com.test.extra_random", 'a'); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing char[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testCharArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putCharArray("com.test.null", null); //$NON-NLS-1$
        inputBundle.putCharArray("com.test.extra_empty", new char[0]); //$NON-NLS-1$
        inputBundle.putCharArray("com.test.extra", new char[]{'a', 'b', 'c'}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing CharSequence types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testCharSequence() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putCharSequence("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putCharSequence("com.test.extra_empty", ""); //$NON-NLS-1$ //$NON-NLS-2$
        inputBundle.putCharSequence("com.test.extra_random",
                "I am a test sequence!"); //$NON-NLS-1$ //$NON-NLS-2$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing CharSequence[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testCharSequenceArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putCharSequenceArray("com.test.null", null); //$NON-NLS-1$
        inputBundle
                .putCharSequenceArray("com.test.extra_empty", new CharSequence[]{}); //$NON-NLS-1$
        inputBundle
                .putCharSequenceArray(
                        "com.test.extra", new CharSequence[]{
                                Resources.getSystem().getString(android.R.string.cancel)}); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing ArrayList<CharSequence> types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testCharSequenceArrayList() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putCharSequenceArrayList("com.test.null", null); //$NON-NLS-1$
        inputBundle.putCharSequenceArrayList("com.test.extra_empty",
                new ArrayList<CharSequence>()); //$NON-NLS-1$

        final ArrayList<CharSequence> list = new ArrayList<CharSequence>();
        list.add(Resources.getSystem().getString(android.R.string.cancel));
        inputBundle.putCharSequenceArrayList("com.test.extra", list); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing string types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testString() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putString("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putString("com.test.extra_empty", ""); //$NON-NLS-1$ //$NON-NLS-2$
        inputBundle.putString("com.test.extra_random",
                "I am a test string!"); //$NON-NLS-1$ //$NON-NLS-2$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing string[] types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testStringArray() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putStringArray("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putStringArray("com.test.extra_empty", new String[0]); //$NON-NLS-1$
        inputBundle.putStringArray("com.test.extra_array",
                new String[]{"foo", "bar"}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing ArrayList<String> types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testStringArrayList() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putStringArrayList("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle
                .putStringArrayList("com.test.extra_empty", new ArrayList<String>()); //$NON-NLS-1$

        final ArrayList<String> random = new ArrayList<String>();
        random.add("foo"); //$NON-NLS-1$
        random.add("bar"); //$NON-NLS-1$
        inputBundle.putStringArrayList("com.test.extra_random", random); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests Parcelable
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testParcelable() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putParcelable("com.test.parcelable",
                new Location("foo")); //$NON-NLS-1$ //$NON-NLS-2$

        boolean threw = false;
        try {
            BundleSerializer.serializeToByteArray(inputBundle);
        } catch (final NotSerializableException e) {
            threw = true;
        }
        assertTrue(threw);
    }

    /**
     * Tests storing Bundle types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testBundles() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putBundle("com.test.extra_null", null); //$NON-NLS-1$
        inputBundle.putBundle("com.test.extra_empty", new Bundle()); //$NON-NLS-1$

        final Bundle random = new Bundle();
        random.putInt("a", 1); //$NON-NLS-1$
        random.putLong("b", 2); //$NON-NLS-1$
        random.putBoolean("c", false); //$NON-NLS-1$
        inputBundle.putBundle("com.test.extra_random", random); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }

    /**
     * Tests storing string types
     *
     * @throws Exception if the test fails
     */
    @SmallTest
    public void testNullKey() throws Exception {
        final Bundle inputBundle = new Bundle();
        inputBundle.putString(null, "foo"); //$NON-NLS-1$

        final Bundle resultBundle = BundleSerializer.deserializeFromByteArray(BundleSerializer
                .serializeToByteArray(inputBundle));

        assertTrue(BundleComparer.areBundlesEqual(inputBundle, resultBundle));
    }
}
