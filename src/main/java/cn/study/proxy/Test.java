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
package cn.study.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

public class Test {

    public static void main(String[] args) {
        /*JDKPersonPorxy jdkPersonPorxy = new JDKPersonPorxy();
        JDKPerson jdkPerson = (JDKPerson) jdkPersonPorxy.getProxy(new JDKPersonImpl(),jdkPersonPorxy);        
        jdkPerson.run(1000);*/
        JDKPerson target =  new JDKPersonImpl();
        JDKPerson jdkPerson = (JDKPerson)Proxy.newProxyInstance(JDKPerson.class.getClassLoader(), new Class[]{JDKPerson.class}, new InvocationHandler() {
            
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("开始");
                System.out.println(proxy.getClass().getName());
                method.invoke(new JDKPersonImpl(), args);
                System.out.println("结束");
                return null;
            }
        });
        
        byte[] bytes = ProxyGenerator.generateProxyClass("TempProxy", jdkPerson.getClass().getInterfaces());
        
        File file = new File("E:\\INV\\index.class");
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {            
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
       /* byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", JDKPersonImpl.class.getInterfaces());  
        
        FileOutputStream out = null;  
          
        try {  
            out = new FileOutputStream("E:\\$Proxy0.class");  
            out.write(classFile);  
            out.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  */
        System.out.println(jdkPerson.getClass().getInterfaces());
        jdkPerson.run(1111);
        
      /*  JDKPerson jdkStudent = (JDKPerson) jdkPersonPorxy.getProxy(new JDKStudentImpl());
        jdkStudent.run(1);
                        
        CGLIBPersonProxy cglibPersonProxy = new CGLIBPersonProxy();
        CGLIBPerson cglibPerson = (CGLIBPerson) cglibPersonProxy.getProxy(CGLIBPerson.class);
        cglibPerson.run(2000);*/
    }
}