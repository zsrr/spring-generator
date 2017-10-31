package com.stephen.springgenerator.base;

import java.util.Properties;

public class ConfigurationManager {

    private static final String DEFAULT_MAVEN_HOME = "/Users/zhangshirui/apache-maven-3.5.0";

    static final String DEFAULT_MAVEN_VERSION = "3.5.0";

    static final String DEFAULT_MAVEN_USER_SETTING_HOME = "/Users/zhangshirui/.m2";

    static final String DEFAULT_MAVEN_LOCAL_REPOSITORY = "/Users/zhangshirui/.m2/repository";

    private final Properties properties;

    private static ConfigurationManager sInstance;

    private ConfigurationManager(Properties properties) {
        this.properties = properties;
    }

    public static void init(Properties properties) {
        sInstance = new ConfigurationManager(properties);
    }

    public static ConfigurationManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Call init() before get the singleton instance");
        }
        return sInstance;
    }

    public String getMavenHome() {
        return properties.getProperty("mavenHome", DEFAULT_MAVEN_HOME);
    }

    public VersionNumber getMavenVersion() {
        return VersionNumber.parse(properties.getProperty("mavenVersion", DEFAULT_MAVEN_VERSION));
    }

    public String getMavenUserSettingHome() {
        return properties.getProperty("userSettingHome", DEFAULT_MAVEN_USER_SETTING_HOME);
    }

    public String getMavenLocalRepoDir() {
        return properties.getProperty("localRepository", DEFAULT_MAVEN_LOCAL_REPOSITORY);
    }

    public String getGroupId() {
        return properties.getProperty("groupId", "com.stephen");
    }

    public String getArtifactId() {
        return properties.getProperty("artifactId", "spring-template");
    }

    public VersionNumber getVersion() {
        return VersionNumber.parse(properties.getProperty("version", "0.0.1"));
    }

    public String getDefaultPackage() {
        return getGroupId() + "." + getArtifactId().replace("-", "");
    }

    public String getProjectName() {
        return properties.getProperty("project", getArtifactId());
    }
}
