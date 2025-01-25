package com.jenkins;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class DemoTest {

    @Test
    public void test1(){
        System.out.println("Inside Test 1");
        Assert.assertTrue(true);
    }

    @Test
    public void test2(){
        System.out.println("Inside Test 2");
        Assert.assertTrue(false);
    }

    @Test
    public void test3(){
        System.out.println("Inside Test 3");
        throw new SkipException("Skipping test");
    }


}
