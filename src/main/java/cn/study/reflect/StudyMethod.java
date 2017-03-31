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
package cn.study.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import cn.study.annotation.MethodAnnotation;

public class StudyMethod {

    public static void main(String[] args) {
        Class clazz = Student.class;
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            if(method.isAnnotationPresent(MethodAnnotation.class)){
              System.out.println(method.getName()+":"+true);  
              Annotation[][] annotations = method.getParameterAnnotations();
              for(int i=0;i<annotations[0].length;i++){
                  
              }
            }
            System.out.println(method.getName()+":"+false);
        }
    }
    
    
}
