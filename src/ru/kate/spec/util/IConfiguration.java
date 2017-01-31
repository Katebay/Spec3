package ru.kate.spec.util;

/**
 * Created by KateBay on 31.01.17.
 */
public interface IConfiguration {

    void save();

    void reload();

    String getString(String name, String defaultValue);

    String getString(String name);

    long getLong(String name, long defaultValue);

    long getLong(String name);

    float getFloat(String name, float defaultValue);

    float getFloat(String name);

    double getDouble(String name, double defaultValue);

    double getDouble(String name);

    void set(String name, Object value);

}
