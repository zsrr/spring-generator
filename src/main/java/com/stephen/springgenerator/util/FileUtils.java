package com.stephen.springgenerator.util;

import com.stephen.springgenerator.base.ConfigurationManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static final File USER_DIR = new File(System.getProperty("user.dir"));

    private static List<File> fileToBeCreated;

    public static File getProjectBaseDir() {
        return new File(USER_DIR, ConfigurationManager.getInstance().getProjectName());
    }

    public static File getDefaultPackageDir() {
        File sourceCodeDir = new File(USER_DIR, ConfigurationManager.getInstance().getProjectName() + "/src/main/java");
        return new File(sourceCodeDir, ConfigurationManager.getInstance().getDefaultPackage().replace(".", "/"));
    }

    public static String readFromIs(InputStream is) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            CloseUtils.closeReader(br);
        }
    }

    public static String readFromFile(File file) {
        InputStream is = null;
        BufferedReader br = null;

        try {
            is = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(is));

            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            CloseUtils.closeInputStream(is);
            CloseUtils.closeReader(br);
        }
    }

    public static void writeToFile(File file, String content) {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } finally {
            CloseUtils.closeWriter(fw);
            CloseUtils.closeWriter(bw);
        }
    }

    public static void createProjectStructure() {
        try {
            System.out.println("Start creating project structure");

            fileToBeCreated = new ArrayList<>(20);

            File baseDir = getProjectBaseDir();
            fileToBeCreated.add(baseDir);
            fileToBeCreated.add(new File(baseDir, "pom.xml"));
            File srcDir = new File(baseDir, "src");
            fileToBeCreated.add(srcDir);
            initSrc(srcDir);

            doCreate();

            System.out.println("Project structure created");
        } finally {
            fileToBeCreated = null;
        }
    }

    private static void doCreate() {
        try {
            for (File f : fileToBeCreated) {
                if (f.getName().contains(".")) {
                    f.createNewFile();
                } else {
                    f.mkdir();
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static void initWebApp(File webapp) {
        File webInf = new File(webapp, "WEB-INF");
        fileToBeCreated.add(webInf);
        initWEBINF(webInf);
    }

    private static void initWEBINF(File webInf) {
        fileToBeCreated.add(new File(webInf, "classes"));
        fileToBeCreated.add(new File(webInf, "lib"));
        fileToBeCreated.add(new File(webInf, "web.xml"));
    }

    private static void initJavaFolder(File javaFolder) {
        String[] parts = ConfigurationManager.getInstance().getDefaultPackage().split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
            fileToBeCreated.add(new File(javaFolder, sb.toString()));
            sb.append("/");
        }

        initSourceCodeStructure(getDefaultPackageDir());
    }

    private static void initSourceCodeStructure(File defaultPackage) {
        fileToBeCreated.add(new File(defaultPackage, "dao"));
        fileToBeCreated.add(new File(defaultPackage, "domain"));
        fileToBeCreated.add(new File(defaultPackage, "service"));
        fileToBeCreated.add(new File(defaultPackage, "web"));
        fileToBeCreated.add(new File(defaultPackage, "RootConfig.java"));
        fileToBeCreated.add(new File(defaultPackage, "WebConfig.java"));
    }

    private static void initMain(File main) {
        File javaFolder = new File(main, "java");
        File webapp = new File(main, "webapp");

        fileToBeCreated.add(javaFolder);
        fileToBeCreated.add(webapp);
        fileToBeCreated.add(new File(main, "resources"));

        initJavaFolder(javaFolder);
        initWebApp(webapp);
    }

    private static void initTest(File test) {
        fileToBeCreated.add(new File(test, "java"));
        fileToBeCreated.add(new File(test, "resources"));
    }

    private static void initSrc(File srcFile) {
        File main = new File(srcFile, "main");
        File test = new File(srcFile, "test");

        fileToBeCreated.add(main);
        fileToBeCreated.add(test);

        initMain(main);
        initTest(test);
    }

}
