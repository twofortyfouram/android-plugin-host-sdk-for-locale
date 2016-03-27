package com.twofortyfouram.locale.sdk.host.test.fixture;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.twofortyfouram.locale.sdk.host.model.Plugin;
import com.twofortyfouram.locale.sdk.host.model.PluginType;
import com.twofortyfouram.locale.sdk.host.test.condition.receiver.PluginConditionReceiver;
import com.twofortyfouram.locale.sdk.host.test.condition.ui.activity.PluginConditionActivity;
import com.twofortyfouram.locale.sdk.host.test.setting.receiver.PluginSettingReceiver;
import com.twofortyfouram.locale.sdk.host.test.setting.ui.activity.PluginSettingActivity;

import net.jcip.annotations.ThreadSafe;

/*
 * Fixture for the test plug-ins embedded in the test package.
 */
@ThreadSafe
public final class DebugPluginFixture {
    @NonNull
    public static Plugin getDebugPluginCondition() {
        return new Plugin(PluginType.CONDITION,
                InstrumentationRegistry.getContext().getPackageName(),
                PluginConditionActivity.class.getName(),
                PluginConditionReceiver.class.getName(), 0,
                PluginConfigurationFixture.newPluginConfiguration());
    }

    @NonNull
    public static Plugin getDebugPluginSetting() {
        return new Plugin(PluginType.SETTING,
                InstrumentationRegistry.getContext().getPackageName(),
                PluginSettingActivity.class.getName(),
                PluginSettingReceiver.class.getName(), 0,
                PluginConfigurationFixture.newPluginConfiguration());
    }

    /**
     * Private constructor prevents instantiation.
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private DebugPluginFixture() {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
}