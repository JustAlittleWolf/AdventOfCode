package me.wolfii.automation;

import java.io.*;
import java.util.ArrayList;

public class EnvReader {
    private static final ArrayList<String> configContents = new ArrayList<>();
    private static final File configFile = new File("config.env");

    static {
        try {
            InputStream inputStream = new FileInputStream(configFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                configContents.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": Could not read config. Downloading inputs will be disabled.");
        }
    }

    public static String get(String configName) {
        for(String option : configContents) {
            if(!option.startsWith(configName)) continue;

            option = option.substring(configName.length()).trim();
            if(!option.startsWith("=")) continue;

            return  option.substring(1).trim();
        }
        return null;
    }
}
