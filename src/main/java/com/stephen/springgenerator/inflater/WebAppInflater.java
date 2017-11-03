package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.util.FileUtils;

import java.io.File;

import static java.io.File.separator;

public class WebAppInflater {

    public void inflate() {
        System.out.println("Start generating webapp directory");

        File des = new File(FileUtils.getProjectBaseDir(), "src" + separator + "main" + separator+ "webapp" +
                separator + "WEB-INF" + separator + "web.xml");
        FileUtils.writeToFile(des, FileUtils.readFromIs(this.getClass().getClassLoader().getResourceAsStream("web.xml")));

        System.out.println("web.xml was successfully generated");
    }

}
