package com.skyline.anyposibility.runner.register;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * [FEATURE INFO]<br/>
 * 配置类
 *
 * @author Skyline
 * @create 2022-10-28 20:35
 * @since 1.0.0
 */
public class RunnerProperties {

    private static final String CONFIG_PATH = "runner.properties";

    private final HashMap<String, String> registeredApplicationMap = new HashMap<>();

    public void load() {
        Properties properties = loadProperties();

        Set<String> propertyNames = properties.stringPropertyNames();
        propertyNames.forEach(name -> registeredApplicationMap.put(name, properties.getProperty(name)));
    }

    public void register(String name, String property) {
        Properties properties = loadProperties();

        writeProperties(properties, name, property);

        load();
    }

    private Properties loadProperties() {
        Properties properties = new Properties();

        InputStream in;
        InputStreamReader reader;
        try {
            in = RunnerProperties.class.getClassLoader().getResourceAsStream(CONFIG_PATH);
            reader = new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8);
            properties.load(reader);

            reader.close();
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("从路径: " + CONFIG_PATH + " 加载配置文件失败.");
        }

        return properties;
    }

    private void writeProperties(Properties properties, String name, String property) {
        OutputStream out;
        OutputStreamWriter writer;
        try {
            URL url = RunnerProperties.class.getClassLoader().getResource(CONFIG_PATH);
            File file = new File(Objects.requireNonNull(url).getFile());
            out = new FileOutputStream(file);
            writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            properties.setProperty(name, property);
            properties.store(writer, "store new property.");

            writer.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException("向路径: " + CONFIG_PATH + " 写入配置文件失败.");
        }
    }

    public Set<String> listRegisteredName() {
        return registeredApplicationMap.keySet();
    }

    public String getCommand(String name) {
        return registeredApplicationMap.get(name);
    }

    public HashMap<String, String> getRunnerCommandRegisterMap() {
        return registeredApplicationMap;
    }
}
