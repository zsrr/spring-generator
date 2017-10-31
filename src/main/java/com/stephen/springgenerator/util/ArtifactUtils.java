package com.stephen.springgenerator.util;

import com.stephen.springgenerator.base.ConfigurationManager;

import java.io.File;

public class ArtifactUtils {

    public static File getFileFromArtifact(String groupId, String artifactId) {
        String[] parts = groupId.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
            sb.append("/");
        }
        sb.append(artifactId);
        return new File(ConfigurationManager.getInstance().getMavenLocalRepoDir(), sb.toString());
    }

    public static String getRemoteUrl(String groupId, String artifactId) {
        return "https://mvnrepository.com/" + "artifact/" + groupId + "/" + artifactId;
    }

}
