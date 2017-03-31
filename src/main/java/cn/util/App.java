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
package cn.util;

public class App {
    private static App a = new App();
    static {
        System.out.println("App 静态块");
    }
    
    {
        System.out.println("App 动态块");
    }
    
    private subClass s = new subClass();
    public App() {
        System.out.println("App 构造方法");
    }
    
    public static void main(String[] args){
        System.out.println("hello");
    }
}


class superClass {
    
    static {
        System.out.println("父类  静态块");
        //System.out.println(1);
    }
    {
        System.out.println("父类  动态块");
    }
    public superClass() {
        System.out.println("父类  构造方法");
       // System.out.println("superClass constructor");
    }
}


class subClass extends superClass {
    static {
        System.out.println("子类  静态块");
        //System.out.println(1);
    }
    {
        System.out.println("子类  动态块");
    }
    public subClass() {
        System.out.println("子类  构造方法");
        //System.out.println(2);
    }
}

