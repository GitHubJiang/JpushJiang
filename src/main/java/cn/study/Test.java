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
package cn.study;

import java.text.Collator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {

        String str = "《分手》,《分手快乐》,《好心分手》,《分手没有什么大不了》,《和平分手》,《天亮以后说分手》,《第二代次分手》,"
                + "《爱到尽头是分手》,《不该和你分手》,《选择分手》,《流着泪说分手》,《不能和你分手》,《我们能不能不分手》,"
                + "《快乐的时候说分手》,《坚强分手》,《来生不分手》,《笑着说分手》,《和寂寞说分手》,《无痛分手》,《全世界分手》,《和她分手》,"
                + "《风一样分手》,《不能分手的分手》,《分开也不一定分手》,《不爱了就分手》,《不能分手的分手》,《就这样分手》,《动不动说分手》,《永远不要说分手》,"
                + "《不要轻易说分手》,《你凭什么说分手》,《分手快乐》,《好心分手》,《分手没有什么大不了》,《和平分手》,《天亮以后说分手》,《第二代次分手》,《爱到尽头是分手》,"
                + "《不该和你分手》,《选择分手》,《流着泪说分手》,《不能和你分手》,《我们能不能不分手》,《快乐的时候说分手》,《坚强分手》,《来生不分手》,《笑着说分手》,《和寂寞说分手》,"
                + "《无痛分手》,《全世界分手》,《和她分手》,《风一样分手》,《不能分手的分手》,《分开也不一定分手》,《不爱了就分手》,《不能分手的分手》,《就这样分手》,《动不动说分手》,"
                + "《永远不要说分手》,《不要轻易说分手》,《你凭什么说分手》,《分手》,《分手快乐》,《好心分手》,《分手没有什么大不了》,《和平分手》,《天亮以后说分手》,《第二代次分手》,"
                + "《爱到尽头是分手》,《不该和你分手》,《选择分手》,《流着泪说分手》,《不能和你分手》,《我们能不能不分手》,《快乐的时候说分手》,《坚强分手》,《来生不分手》,《笑着说分手》,"
                + "《和寂寞说分手》,《无痛分手》,《全世界分手》,《和她分手》,《风一样分手》,《不能分手的分手》,《分开也不一定分手》,《不爱了就分手》,《不能分手的分手》,《就这样分手》,《动不动说分手》,"
                + "《永远不要说分手》,《不要轻易说分手》,《你凭什么说分手》,《分手》,《分手快乐》,《好心分手》,《分手没有什么大不了》,《和平分手》,《天亮以后说分手》,《第二代次分手》,《爱到尽头是分手》,"
                + "《不该和你分手》, 《选择分手》, 《流着泪说分手》, 《不能和你分手》, 《我们能不能不分手》, 《快乐的时候说分手》, 《坚强分手》, 《来生不分手》, 《笑着说分手》, 《和寂寞说分手》, 《无痛分手》,"
                + " 《全世界分手》";
        String arr[] = str.split(",");
        Set<String> set = new TreeSet<String>(Collator.getInstance(Locale.CHINA));
        for(String s:arr){
            set.add(s); 
        }
        @SuppressWarnings("rawtypes")
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next());
        }
    }
}
