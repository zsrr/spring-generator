package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.base.ConfigurationManager;
import com.stephen.springgenerator.util.FileUtils;

import java.io.File;

public class SpringConfigInflater {

    public void inflate() {
        System.out.println("Start generating configuration");

        ClassLoader cl = this.getClass().getClassLoader();

        File rootDes = new File(FileUtils.getDefaultPackageDir(), "RootConfig.java");
        File webDes = new File(FileUtils.getDefaultPackageDir(), "WebConfig.java");

        FileUtils.writeToFile(rootDes, inflateBasePackage(FileUtils.readFromIs(cl.getResourceAsStream("RootConfig.java"))));
        FileUtils.writeToFile(webDes, inflateBasePackage(FileUtils.readFromIs(cl.getResourceAsStream("WebConfig.java"))));

        System.out.println("Configuration was successfully generated");
    }

    private String inflateBasePackage(String source) {
        return source.replace("${basePackage}", ConfigurationManager.getInstance().getDefaultPackage());
    }
}
