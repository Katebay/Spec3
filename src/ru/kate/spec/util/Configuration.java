package ru.kate.spec.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by KateBay on 31.01.17.
 */
public class Configuration implements IConfiguration {

    private final static String suffix = ".config";

    private final String name;
    private final Map<String, Object> properties = new HashMap<>();

    public Configuration(String name) {
        this.name = name;
        reload();
    }

    @Override
    public void save() {
        File file = new File(name + suffix);
        try {
            try(PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                properties.forEach((k, v) -> pw.println(k + "=" + v));
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void reload() {
        properties.clear();
        File file = new File(name + suffix);
        try {
            if(!file.exists())
                file.createNewFile();
            try(Scanner scan = new Scanner(new FileReader(file))) {
                while(scan.hasNextLine()) {
                    String line = scan.nextLine();
                    String[] args = line.split("=");
                    String propertyName = args[0];
                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < args.length; ++i)
                        sb.append(args[i]);
                    String propertyValue = sb.toString();
                    if(propertyValue.isEmpty())
                        throw new IllegalStateException("Configuration file " + name + suffix + " is broken for property " + propertyName + "!");
                    try {
                        long l = Long.parseLong(propertyValue);
                        properties.put(propertyName, l);
                        continue;
                    }catch(NumberFormatException ex) {}
                    try {
                        float f = Float.parseFloat(propertyValue);
                        properties.put(propertyName, f);
                        continue;
                    }catch(NumberFormatException ex) {}
                    try {
                        double d = Double.parseDouble(propertyValue);
                        properties.put(propertyName, d);
                        continue;
                    }catch(NumberFormatException ex) {}
                    properties.put(propertyName, propertyValue);
                }
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getString(String name, String defaultValue) {
        String value = getString(name);
        if(value != null)
            return value;
        properties.put(name, defaultValue);
        save();
        return defaultValue;
    }

    @Override
    public String getString(String name) {
        return (String) properties.get(name);
    }

    @Override
    public long getLong(String name, long defaultValue) {
        Long value = (Long) properties.get(name);
        if(value != null)
            return value;
        properties.put(name, defaultValue);
        save();
        return defaultValue;
    }

    @Override
    public long getLong(String name) {
        return (long) properties.get(name);
    }

    @Override
    public float getFloat(String name, float defaultValue) {
        Float value = (Float) properties.get(name);
        if(value != null)
            return value;
        properties.put(name, defaultValue);
        save();
        return defaultValue;
    }

    @Override
    public float getFloat(String name) {
        return (float) properties.get(name);
    }

    @Override
    public double getDouble(String name, double defaultValue) {
        Double value = (Double) properties.get(name);
        if(value != null)
            return value;
        properties.put(name, defaultValue);
        save();
        return defaultValue;
    }

    @Override
    public double getDouble(String name) {
        return (double) properties.get(name);
    }

    @Override
    public void set(String name, Object value) {
        if(!(value instanceof String || value instanceof Long ||
                value instanceof Float || value instanceof Double))
            throw new IllegalArgumentException("Unsupported type of data for property " + name + "!");
        properties.put(name, value);
        save();
    }
}
