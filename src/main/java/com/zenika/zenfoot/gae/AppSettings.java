package com.zenika.zenfoot.gae;

import restx.config.Settings;
import restx.config.SettingsKey;

/**
 * Interface to access app's settings. Settings are loaded in AppModule
 * Created by armel on 30/09/15.
 */
@Settings
public interface AppSettings {
    @SettingsKey(key = "mail.from.address")
    String mailFromAddress();

    @SettingsKey(key = "mail.from.name")
    String mailFromName();
}
