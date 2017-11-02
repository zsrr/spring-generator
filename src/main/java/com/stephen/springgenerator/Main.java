package com.stephen.springgenerator;


import com.stephen.springgenerator.inflater.PomInflater;
import com.stephen.springgenerator.inflater.SpringConfigInflater;
import com.stephen.springgenerator.inflater.WebAppInflater;
import com.stephen.springgenerator.util.FileUtils;

import java.io.IOException;

/***
 * 这是一个自动生成Spring项目的插件
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("User dir: " + FileUtils.USER_DIR.getAbsolutePath());
        FileUtils.createProjectStructure();
        new PomInflater().inflate();
        new SpringConfigInflater().inflate();
        new WebAppInflater().inflate();
    }
}
