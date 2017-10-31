package com.stephen.springgenerator;


import com.stephen.springgenerator.base.ConfigurationManager;
import com.stephen.springgenerator.inflater.PomInflater;
import com.stephen.springgenerator.inflater.SpringConfigInflater;
import com.stephen.springgenerator.inflater.WebAppInflater;
import com.stephen.springgenerator.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/***
 * 这是一个自动生成Spring项目的插件
 */
public class Main {

    public static void main(String[] args) throws IOException {

        String configFile;

        if (args != null && args.length > 0) {
            configFile = args[0];
        } else {
            configFile = new File("config.properties").getAbsolutePath();
        }

        System.out.println("User dir: " + FileUtils.USER_DIR.getAbsolutePath());
        System.out.println("Using configuration file " + configFile);
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(configFile)));
        ConfigurationManager.init(properties);

        FileUtils.createProjectStructure();
        new PomInflater().inflate();
        new SpringConfigInflater().inflate();
        new WebAppInflater().inflate();
    }
}
