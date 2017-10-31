package com.stephen.springgenerator.base;

import com.stephen.springgenerator.util.ArtifactUtils;
import com.stephen.springgenerator.util.CloseUtils;
import com.stephen.springgenerator.util.RegexUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;

public class DependenciesResolver {

    public final Object loc = new Object();

    // only for test
    public VersionNumber getVersionNumber(String groupId, String artifactId) {
        VersionNumber local = getVNFromLocalRepo(groupId, artifactId);
        return local == null ? getVNFromRemoteRepo(groupId, artifactId) : local;
    }

    public VersionNumber getSpringVersion() {
        VersionNumber local = getVNFromLocalRepo("org.springframework", "spring-core");
        return local == null ? getVNFromRemoteRepo("org.springframework", "spring-core") : local;
    }

    public VersionNumber getJ2EEVersion() {
        VersionNumber local = getVNFromLocalRepo("javax", "javaee-api");
        return local == null ? getVNFromRemoteRepo("javax", "javaee-api") : local;
    }

    public VersionNumber getServletVersion() {
        VersionNumber local = getVNFromLocalRepo("javax.servlet", "javax.servlet-api");
        return local == null ? getVNFromRemoteRepo("javax.servlet", "javax.servlet-api") : local;
    }

    public VersionNumber getJedisVersion() {
        VersionNumber local = getVNFromLocalRepo("redis.clients", "jedis");
        return local == null ? getVNFromRemoteRepo("redis.clients", "jedis") : local;
    }

    public VersionNumber getJunitVersion() {
        VersionNumber local = getVNFromLocalRepo("junit", "junit");
        return local == null ? getVNFromRemoteRepo("junit", "junit") : local;
    }

    public VersionNumber getJackson2Version() {
        VersionNumber local = getVNFromLocalRepo("com.fasterxml.jackson.core", "jackson-core");
        return local == null ? getVNFromRemoteRepo("com.fasterxml.jackson.core", "jackson-core") : local;
    }

    public VersionNumber getHibernateVersion() {
        VersionNumber local = getVNFromLocalRepo("org.hibernate", "hibernate-core");
        return local == null ? getVNFromRemoteRepo("org.hibernate", "hibernate-core") : local;
    }

    private VersionNumber getVNFromLocalRepo(String groupId, String artifactId) {

        System.out.println("Trying get dependency from local repo: " + groupId + "." + artifactId);

        File repoDir = ArtifactUtils.getFileFromArtifact(groupId, artifactId);
        if (!repoDir.exists())
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

    private VersionNumber getVNFromRemoteRepo(String groupId, String artifactId) {
        System.out.println("Trying get dependency from https://mvnrepository.com" );
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        try {
            HttpClientContext context = HttpClientContext.create();
            HttpGet httpGet = new HttpGet(ArtifactUtils.getRemoteUrl(groupId, artifactId) + "/latest");
            httpclient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();

            URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
            String redirectUri = location.toASCIIString();
            Matcher matcher = RegexUtils.getArtifactMatcher(redirectUri);
            // 此处还需要改进一下，如何得到最新的RELEASE稳定版本呢
            matcher.find();
            return VersionNumber.parse(matcher.group());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        } finally {
            CloseUtils.closeHttpClient(httpclient);
        }
    }
}
