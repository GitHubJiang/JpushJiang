/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package cn.study.innerclass;

public class Exsample {

    private static String str = "我只是一个字符串";

    private String name;

    private int age;

    static {
        System.out.println("我是Exsample的静态块！！！！");
    }

    {
        System.out.println("我是Exsample的初始化模块！！！！");
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }


    public void setAge(int age) {
        this.age = age;
    }


    //静态内部类
    public static class StaticInnerClass {

        {
            System.out.println("我是Exsample.StaticInnerClass的初始化模块！！！！");
        }

        private String prName;

        public String getPrName() {
            return prName;
        }

        public void setPrName(String prName) {
            this.prName = prName;
        }

        public void test() {
            System.out.println(Exsample.class.getName());
        }
    }

    //成员内部类
    public class PublicInnerClass {


        {
            System.out.println("我是Exsample.PublicInnerClass的初始化模块！！！！");
        }

        private int puAge;

        public int getPuAge() {
            return puAge;
        }

        public void setPuAge(int puAge) {
            this.puAge = puAge;
        }

        public void test() {
            System.out.println(Exsample.class.getName());
        }
    }


}
