package com.stephen.springgenerator.base;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stephen.springgenerator.util.FileUtils;

import java.io.File;
import java.io.IOException;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Config {

    @JsonProperty("project")
    private String project;

    @JsonProperty("local_repo")
    private String mavenLocalRepoDir;

    @JsonProperty("spring_version")
    private String springVersion;

    @JsonProperty("j2ee_version")
    private String j2eeVersion;

    @JsonProperty("servlet_version")
    private String servletVersion;

    @JsonProperty("jedis_version")
    private String jedisVersion;

    @JsonProperty("junit_version")
    private String junitVersion;

    @JsonProperty("jackson2_version")
    private String jackson2Version;

    @JsonProperty("hibernate_version")
    private String hibernateVersion;

    @JsonProperty(value = "group_id", required = true)
    private String groupId;

    @JsonProperty(value = "artifact_id", required = true)
    private String artifactId;

    @JsonProperty(required = true)
    private String version;

    private String jndi;

    private static Config sInstance;

    public String getProjectName() {
        return project == null ? artifactId : project;
    }

    public String getMavenLocalRepoDir() {
        return mavenLocalRepoDir;
    }

    public String getSpringVersion() {
        return springVersion;
    }

    public String getJ2eeVersion() {
        return j2eeVersion;
    }

    public String getServletVersion() {
        return servletVersion;
    }

    public String getJedisVersion() {
        return jedisVersion;
    }

    public String getJunitVersion() {
        return junitVersion;
    }

    public String getJackson2Version() {
        return jackson2Version;
    }

    public String getHibernateVersion() {
        return hibernateVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getJndi() {
        return jndi;
    }

    public String getDefaultPackage() {
        return getGroupId() + "." + getArtifactId().replace("-", "");
    }

    public static Config getInstance() {
        if (sInstance == null) {
            String content = FileUtils.readFromFile(new File(FileUtils.USER_DIR, "settings.json"));
            if (content != null) {
                try {
                    ObjectMapper om = new ObjectMapper();
                    sInstance = om.readValue(content, Config.class);
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return sInstance;
    }

    @Override
    public String toString() {
        return "Config{" +
                "project='" + project + '\'' +
                ", mavenLocalRepoDir='" + mavenLocalRepoDir + '\'' +
                ", springVersion='" + springVersion + '\'' +
                ", j2eeVersion='" + j2eeVersion + '\'' +
                ", servletVersion='" + servletVersion + '\'' +
                ", jedisVersion='" + jedisVersion + '\'' +
                ", junitVersion='" + junitVersion + '\'' +
                ", jackson2Version='" + jackson2Version + '\'' +
                ", hibernateVersion='" + hibernateVersion + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", jndi='" + jndi + '\'' +
                '}';
    }
}
