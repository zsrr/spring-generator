package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.base.Config;
import com.stephen.springgenerator.util.FileUtils;

import java.io.File;

import static java.io.File.separator;

public class WebAppInflater {

    public void inflate() {
        System.out.println("Start generating webapp directory");

        File des = new File(FileUtils.getProjectBaseDir(), "src" + separator + "main" + separator+ "webapp" +
                separator + "WEB-INF" + separator + "web.xml");
        String webxmlStr = FileUtils.readFromIs(this.getClass().getClassLoader().getResourceAsStream("web.xml"));
        webxmlStr = webxmlStr.replace("{groupId}", Config.getInstance().getGroupId()).replace("{artifactId}", Config.getInstance().getArtifactId());
        FileUtils.writeToFile(des, webxmlStr);

        System.out.println("web.xml was successfully generated");
    }

}
