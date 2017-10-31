package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.util.FileUtils;

import java.io.File;

public class WebAppInflater {

    public void inflate() {
        System.out.println("Start generating webapp directory");

        File des = new File(FileUtils.getProjectBaseDir(), "src/main/webapp/WEB-INF/web.xml");
        FileUtils.writeToFile(des, FileUtils.readFromIs(this.getClass().getClassLoader().getResourceAsStream("web.xml")));

        System.out.println("web.xml was successfully generated");
    }

}
