package com.stephen.springgenerator.base;

import com.stephen.springgenerator.util.FileUtils;
import com.stephen.springgenerator.util.RegexUtils;

import java.io.File;
import java.util.*;

public class DependenciesResolver {

    private static Map<String, VersionNumber> dependencyMap = new HashMap<>(7);

    static {
        dependencyMap.put("springVersion", VersionNumber.parse("4.3.12.RELEASE"));
        dependencyMap.put("j2eeVersion", VersionNumber.parse("7.0"));
        dependencyMap.put("servletVersion", VersionNumber.parse("3.1.0"));
        dependencyMap.put("jedisVersion", VersionNumber.parse("2.9.0"));
        dependencyMap.put("jacksonVersion", VersionNumber.parse("2.8.10"));
        dependencyMap.put("junitVersion", VersionNumber.parse("4.12"));
        dependencyMap.put("hibernateVersion", VersionNumber.parse("5.2.10.Final"));
    }

    public VersionNumber getSpringVersion() {
        String vnFromConfig = Config.getInstance().getSpringVersion();
        if (vnFromConfig != null) {
            System.out.println("Using spring version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("org.springframework", "spring-core");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("springVersion");
            System.out.println("Using default spring version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using spring version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getJ2EEVersion() {
        String vnFromConfig = Config.getInstance().getJ2eeVersion();
        if (vnFromConfig != null) {
            System.out.println("Using java-ee version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("javax", "javaee-api");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("j2eeVersion");
            System.out.println("Using default java-ee version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using java-ee version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getServletVersion() {
        String vnFromConfig = Config.getInstance().getServletVersion();
        if (vnFromConfig != null) {
            System.out.println("Using servlet version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("javax.servlet", "javax.servlet-api");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("servletVersion");
            System.out.println("Using default servlet version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using servlet version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getJedisVersion() {
        String vnFromConfig = Config.getInstance().getJedisVersion();
        if (vnFromConfig != null) {
            System.out.println("Using jedis version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("redis.clients", "jedis");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("jedisVersion");
            System.out.println("Using default jedis version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using jedis version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getJunitVersion() {
        String vnFromConfig = Config.getInstance().getJunitVersion();
        if (vnFromConfig != null) {
            System.out.println("Using junit version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("junit", "junit");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("junitVersion");
            System.out.println("Using default junit version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using junit version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getJackson2Version() {
        String vnFromConfig = Config.getInstance().getJackson2Version();
        if (vnFromConfig != null) {
            System.out.println("Using jackson version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("com.fasterxml.jackson.core", "jackson-core");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("jacksonVersion");
            System.out.println("Using default jackson version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using jackson version in maven local repo: " + local);
            return local;
        }
    }

    public VersionNumber getHibernateVersion() {
        String vnFromConfig = Config.getInstance().getHibernateVersion();
        if (vnFromConfig != null) {
            System.out.println("Using hibernate version defined in conf.json: " + vnFromConfig);
            return VersionNumber.parse(vnFromConfig);
        }
        VersionNumber local = getVNFromLocalRepo("org.hibernate", "hibernate-core");
        if (local == null) {
            VersionNumber defaultV = dependencyMap.get("hibernateVersion");
            System.out.println("Using default hibernate version: " + defaultV);
            return defaultV;
        } else {
            System.out.println("Using hibernate version in maven local repo: " + local);
            return local;
        }
    }

    private VersionNumber getVNFromLocalRepo(String groupId, String artifactId) {

        System.out.println("Trying get dependency from local repo: " + groupId + "." + artifactId);

        File repoDir = FileUtils.getFileFromArtifact(groupId, artifactId);
        if (repoDir == null || !repoDir.exists())
            return null;

        File[] fileArray = repoDir.listFiles();
        if (fileArray == null || fileArray.length == 0)
            return null;

        List<VersionNumber> vns = new ArrayList<>(fileArray.length);
        for (File f : fileArray) {
            if (RegexUtils.isArtifactValid(f.getName())) {
                vns.add(VersionNumber.parse(f.getName()));
            }
        }
        Collections.sort(vns);
        return vns.get(vns.size() - 1);
    }
}
