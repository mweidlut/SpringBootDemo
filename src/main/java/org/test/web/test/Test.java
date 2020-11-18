package org.test.web.test;

import cn.hutool.core.io.file.FileReader;
import java.nio.charset.StandardCharsets;

import java.io.File;


public class Test {

    public static void main(String[] args) throws Exception {
        //BufferedReader reader = new BufferedReader(new FileReader(new File("D:\\log\\test.txt")));
        String content = FileReader.create(new File("D:\\log\\test.txt"), StandardCharsets.UTF_8).readString();
        System.out.println(content.length());

        System.out.println(content.trim().length());

    }
}
