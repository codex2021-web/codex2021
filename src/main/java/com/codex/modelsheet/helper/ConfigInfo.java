package com.codex.modelsheet.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigInfo {

    private static Properties configs = new Properties();

    public static void loadConfigurations() throws IOException {
        FileReader reader = new FileReader("config.properties");
        configs.load(reader);
    }
    public static Properties getConfigs() {
        return configs;
    }
}
