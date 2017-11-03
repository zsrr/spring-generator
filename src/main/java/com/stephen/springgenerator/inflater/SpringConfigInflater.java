package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.base.Config;
import com.stephen.springgenerator.util.FileUtils;

import java.io.File;

import static java.io.File.separator;

public class SpringConfigInflater {

    public void inflate() {
        System.out.println("Start generating configuration");

        ClassLoader cl = this.getClass().getClassLoader();

        File rootDes = new File(FileUtils.getDefaultPackageDir(), "RootConfig.java");
        File webDes = new File(FileUtils.getDefaultPackageDir(), "WebConfig.java");
        File resDes = new File(FileUtils.getProjectBaseDir(), "src" + separator + "main" +
                separator + "resources" + separator + "hibernate.properties");

        FileUtils.writeToFile(rootDes, inflateBasePackage(FileUtils.readFromIs(cl.getResourceAsStream("RootConfig.java"))));
        FileUtils.writeToFile(webDes, inflateBasePackage(FileUtils.readFromIs(cl.getResourceAsStream("WebConfig.java"))));
        FileUtils.writeToFile(resDes, FileUtils.readFromIs(cl.getResourceAsStream("hibernate.properties")));

        System.out.println("Configuration was successfully generated");
    }

    private String inflateBasePackage(String source) {
        Config config = Config.getInstance();
        String jndi = config.getJndi() == null ? "Type your jndi here" : config.getJndi();
        return source.replace("${basePackage}", config.getDefaultPackage())
                .replace("${jndi}", jndi);
    }
}
