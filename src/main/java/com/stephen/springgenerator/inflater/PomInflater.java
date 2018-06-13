package com.stephen.springgenerator.inflater;

import com.stephen.springgenerator.base.Config;
import com.stephen.springgenerator.base.DependenciesResolver;
import com.stephen.springgenerator.util.FileUtils;

import java.io.*;

public class PomInflater {

    private final DependenciesResolver dr;

    public PomInflater() {
        this.dr = new DependenciesResolver();
    }

    public void inflate() {
        System.out.println("Start writing to pom.xml");

        File targetFile = new File(FileUtils.getProjectBaseDir(), "pom.xml");
        String content = FileUtils.readFromIs(this.getClass().getClassLoader().getResourceAsStream("pom.xml"));
        FileUtils.writeToFile(targetFile, resolveDependencies(inflateProjectInfo(content)));

        System.out.println("pom.xml was successfully written");
    }

    private String resolveDependencies(String s) {
        return s.replace("${springVersion}", dr.getSpringVersion().toString())
                .replace("${javaeeVersion}", dr.getJ2EEVersion().toString())
                .replace("${servletVersion}", dr.getServletVersion().toString())
                .replace("${jedisVersion}", dr.getJedisVersion().toString())
                .replace("${junitVersion}", dr.getJunitVersion().toString())
                .replace("${jacksonVersion}", dr.getJackson2Version().toString())
                .replace("${hibernateVersion}", dr.getHibernateVersion().toString());
    }

    private String inflateProjectInfo(String content) {
        Config cm = Config.getInstance();

        return content.replace("${groupId}", cm.getGroupId()).
                replace("${artifactId}", cm.getArtifactId()).
                replace("${version}", cm.getVersion())
                .replace("${deployPath}", cm.getProjectName());
    }
}
