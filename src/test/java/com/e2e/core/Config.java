package com.e2e.core;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new IllegalStateException("config.properties not found");
            props.load(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String baseUrl() { return props.getProperty("baseUrl").trim(); }
    public static String apiBaseUrl() { return props.getProperty("apiBaseUrl").trim(); }

    public static String browser() { return props.getProperty("browser", "chrome").trim(); }
    public static boolean headless() { return Boolean.parseBoolean(props.getProperty("headless", "false")); }

    public static int timeoutSeconds() { return Integer.parseInt(props.getProperty("timeoutSeconds", "15").trim()); }

    private Config() {}
}
