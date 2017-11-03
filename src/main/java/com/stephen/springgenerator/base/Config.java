package com.stephen.springgenerator.base;

import com.stephen.springgenerator.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private Properties properties;

    private static Config sInstance;

    public String getProjectName() {
        String project = properties.getProperty("project");
        return project == null ? getArtifactId() : project;
    }

    public String getMavenLocalRepoDir() {
        return properties.getProperty("localRepo");
    }

    public String getSpringVersion() {
        return properties.getProperty("springVersion");
    }

    public String getJ2eeVersion() {
        return properties.getProperty("j2eeVersion");
    }

    public String getServletVersion() {
        return properties.getProperty("servletVersion");
    }

    public String getJedisVersion() {
        return properties.getProperty("jedisVersion");
    }

    public String getJunitVersion() {
        return properties.getProperty("junitVersion");
    }

    public String getJackson2Version() {
        return properties.getProperty("jackson2Version");
    }

    public String getHibernateVersion() {
        return properties.getProperty("hibernateVersion");
    }

    public String getGroupId() {
        return properties.getProperty("groupId");
    }

    public String getArtifactId() {
        return properties.getProperty("artifactId");
    }

    public String getVersion() {
        return properties.getProperty("version", "0.0.1");
    }

    public String getJndi() {
        return properties.getProperty("jndi");
    }

    public String getDefaultPackage() {
        return getGroupId() + "." + getArtifactId().replace("-", "");
    }

    private Config(Properties properties) {
        checkProperties(properties);
        this.properties = properties;
    }

    private static void checkProperties(Properties properties) {
        if (!properties.containsKey("groupId") ||
                !properties.containsKey("artifactId")) {
            throw new IllegalArgumentException("The properties file must contain 'groupId' and 'artifactId'");
        }
    }

    public static Config getInstance() {
        if (sInstance == null) {
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(new File(FileUtils.USER_DIR, "conf.properties")));
                sInstance = new Config(properties);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return sInstance;
    }
}
