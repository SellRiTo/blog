package com.blog.blog.module;

import java.util.ArrayList;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-05-15 09:35
 */
public class HeadArea {
    byte[]  a = new byte[1024*100];

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeadArea> testGc = new ArrayList<>();
        while (true){
            testGc.add(new HeadArea());
            Thread.sleep(50);
        }


    }
}
