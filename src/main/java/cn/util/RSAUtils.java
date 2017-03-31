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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAUtils {
    
    private static byte[] publickKeyData;
    
    private static byte[] privateKeyData;
    
    static{
        loadProperties("rsa_public_app_key.pem","pkcs8_private_app_key.pem");
    }
    
    
    /**
     * 生成公钥和私钥
     * @throws NoSuchAlgorithmException 
     *
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }
    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     * 
     * @param modulus
     *            模
     * @param exponent
     *            指数
     * @return
     */
    public static RSAPublicKey getPublicKey() {
        try {
            return (RSAPublicKey)KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publickKeyData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     * 
     * @param modulus
     *            模
     * @param exponent
     *            指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey() {

        try {
            return (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyData));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密,最长只能有116个字节,超过的话，以#分割
     * 
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data)
            throws Exception {
        if(null == data){
            return null;
        }
        RSAPublicKey publicKey=getPublicKey();
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-12（116字节）  防止汉字所以设为50
        String[] datas=null;
        if(data.getBytes().length>key_len-12){
            datas = splitString(data, 30);
        }else{
            datas=new String[1];
            datas[0]=data;
        }
        
        String mi = "";
        //如果明文长度大于模长-11则要分组加密       
        for (int i = 0; i < datas.length; i++){
            if(i<datas.length-1){
                mi += Base64Util.encryptBASE64(cipher.doFinal(datas[i].getBytes()))+"#";
            }else{
                mi += Base64Util.encryptBASE64(cipher.doFinal(datas[i].getBytes()));
            }           
        }
        
        //去除\r\n
        mi = mi.replace("\r", "");
        mi = mi.replace("\n", "");
        return mi;
    }
    

    /**
     * 私钥解密 分组解密  
     * 
     * @param data  超过116字符，用#隔开分组加密
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKeySplit(String data)
            throws Exception {
        
        RSAPrivateKey privateKey=getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        String stringarray[]=data.split("#");  
        
        List<byte[]> srcArrays =new ArrayList<byte[]>();
        
        for(String stemp:stringarray){  
            byte[] byte_temp = Base64Util.decryptBASE64(stemp);
            byte_temp=cipher.doFinal(byte_temp);
            srcArrays.add(byte_temp);
            
        }
        
        byte[] byte_sta=sysCopy(srcArrays);
        
        return new String(byte_sta);
        
    }
    
    
    /**
     * 私钥解密 分组解密
     * 
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data)
            throws Exception {
        RSAPrivateKey privateKey=getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        int key_len = privateKey.getModulus().bitLength() / 8;
//      byte[] bytes = data.getBytes();
//      byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//      System.err.println(bcd.length);
//      //如果密文长度大于模长则要分组解密
        String ming = "";
        
        byte[][] arrays = splitArray(Base64Util.decryptBASE64(data), key_len);
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr));
        }
        
        return ming;
        
        
    }
    /**
     * ASCII码转BCD码
     * 
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }
    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }
    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
    
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i=0; i<x+z; i++) {
            if (i==x+z-1 && y!=0) {
                str = string.substring(i*len, i*len+y);
            }else{
                str = string.substring(i*len, i*len+len);
            }
            strings[i] = str;
        }
        return strings;
    }
    /**
     *拆分数组 
     */
    public static byte[][] splitArray(byte[] data,int len){
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if(y!=0){
            z = 1;
        }
        byte[][] arrays = new byte[x+z][];
        byte[] arr;
        for(int i=0; i<x+z; i++){
            arr = new byte[len];
            if(i==x+z-1 && y!=0){
                System.arraycopy(data, i*len, arr, 0, y);
            }else{
                System.arraycopy(data, i*len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
    
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get("public");
        byte[] publicKey =key.getEncoded();
        return Base64Util.encryptBASE64(publicKey);
    }
    
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get("private");
        byte[] privateKey =key.getEncoded();
        return Base64Util.encryptBASE64(privateKey);
    }
    
    /**
     * 读取文件公钥/私钥文件
     * @return void
     * @author 冯明雷
     * @time 2015-6-6下午12:54:36
     */
    @SuppressWarnings("static-access")
    private static void loadProperties(String publicFileName,String privateFileName){
        InputStream publicIn = null;
        InputStream privateIn = null;
        InputStreamReader inReader = null;
        BufferedReader brKeyword = null;
        try{
            publicIn = RSAUtils.class.getResourceAsStream("/key/" + publicFileName);
            privateIn = RSAUtils.class.getResourceAsStream("/key/" + privateFileName);
            
            inReader = new InputStreamReader(publicIn);
            brKeyword = new BufferedReader(inReader);
            StringBuffer sb = new StringBuffer();
            String keyword=null;
            
            while ((keyword=brKeyword.readLine()) != null){
                sb.append(keyword);
            }
            keyword=sb.toString().replaceAll("-----\\w+ PUBLIC KEY-----", "").replace("\n", "");            
            publickKeyData=(new Base64()).decodeBase64(keyword.getBytes());
            
            
            inReader = new InputStreamReader(privateIn);
            brKeyword = new BufferedReader(inReader);
            sb=new StringBuffer();
            while ((keyword=brKeyword.readLine()) != null){
                sb.append(keyword);
            }           
            keyword=sb.toString().replaceAll("-----\\w+ PRIVATE KEY-----", "").replace("\n", "");           
            privateKeyData=(new Base64()).decodeBase64(keyword.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (null != brKeyword)
                    brKeyword.close();
                if (null != inReader)
                    inReader.close();
                if (null != inReader)
                    publicIn.close();
                    privateIn.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
//      HashMap<String, Object> map = RSAUtils.getKeys();
//      //生成公钥和私钥
//      RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
//      RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        
        //明文
        String ming = "wow";
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAUtils.getPublicKey();
        RSAPrivateKey priKey = RSAUtils.getPrivateKey();
        System.err.println("公钥:"+Base64Util.encryptBASE64(pubKey.getEncoded()));
        System.err.println("私钥:"+Base64Util.encryptBASE64(priKey.getEncoded()));
        
        //加密后的密文
        String mi = RSAUtils.encryptByPublicKey(ming);
        System.err.println("加密后的密文:"+mi);
        //解密后的明文
        //mi="aYlJTFE+cQjwsVkhmQrFnlwSPSidfPFbSaez8qw47xEfqwhPHX9na+7KoFwere3F2Incr0OoBhNt\nK8cqKwHtzuvJMW3/rz0X8n9Jld22lrsh9TOcEiq3Xwk3jTfNhGNnOEQIsOYVZs3Qqumdm0tAu7y6\nBHSE8kbfhRIPsqOys+0=";
        
        String[] mis=mi.split("#");
        
        String ming1="";
        for (String string : mis){
            ming1+= RSAUtils.decryptByPrivateKey(string);
        }
        System.err.println("解密前的明文:"+ming);
        System.err.println("解密后的明文:"+ming1);
       // String a = "cVqjXrpazESBCFmZy5HxIy+C5o0PbYk9iCtBHBCGi8TMRPp2LpsVblrZIGrCw4s/fPEJp5pL7pBfZw4HDx412OfQpMCxezTltNNRWQBbFYCz/FHj6GeX7KAnkZhTC4hTZSHp1EyIa5EMsF6xdFtHuki98KS+75L3Z1Iln1efk2Y=";
//      System.out.println(RSAUtils.decryptByPrivateKey(a));
//      System.out.println(RSAUtils.decryptByPrivateKey(null));
//      
        
        RSAPrivateKey privateKey=getPrivateKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //模长
        //int key_len = privateKey.getModulus().bitLength() / 8;
    
        String srcstring="NKUsmMdJpSeAVFSk3hMwBoWsA7t/wOMc8hfzo2dwPIULOo1y0AUwPTDxVLw7AK1888rdyp/3U38y5eQUjuNQH68XN4jm7MHdRm+bnO6XUHuZLiXNFkLpVCAM5hVh6TTcOz3QIE9rhP+Up1WRxnq/PDuM/cXIeNWjBLbW4Iic9/w=#fRdhSf+a3xVktmJ6J0AJTW30wqpwQslN0Bxdyzpyr6u6VEj/Vo/5MpzZsVeIyBU/CzVvvkrvs5LKTagaA3M4thH3mz4gfMTrw746n4NhujZEUEehvNQIFdUBjKWPAI0XUtdcQQTtdUJbIWmuhTYxKParm0G8cNz5DSTRUTIw2fQ=#a9CaDtGkhVQNwgxHl0oJ8I/383pE3kE8yF76OyDtfeQsT50izuk44xQ34ersgzKkKSA8IB0W3SSw2bedsaj8A9dxrSf+S8S8w/Sg3G3/IA8pKaQqJD4idTCUFxjczf32aQbv+eUbs7fenAPKb3rpn+R+Q/c5y6jTEG5h+H27MsY=#N/dn52TTgJ+EoxTSp3weagZ+ggrIPl4rtKfzo0f/j2lMtXMfCQN7xgGiwDpv4Rele/58NV2X0ezDXjB3wtVugIA3nhKSNnsjEs/os1Zl2XO1EKuTnLGSJFSPS6yyr4o0wxHEeA52qA1UXaYS9clpjraXCDWuRrTpyQfONVDXGZ4=#P48NIZj+L8stk0CCNwAQmKZPH0/7GfOhhc9s/MONVPHBPRYo9N47Kb71PJszhrLsoEM2/VZD1Ech4HH8UmWhW6XPnVAWy/lS1nZDD/GEoTZvoN4Rw7oiWUGj2i+2SMyjcET9mnVPBUrgTmP8He5OFV6oIa1mAN95V4UkbXwYApw=#h79I/zTTAc2TA7j4k2wq5hrotElhskZudnuTViRvR2Ot9rAlFzdl1WasY0e+9pi7dCi40F5+2U7eVNuWLA9qvFKwgUSxA+z6FI++p0fAGsboJna9qwcplByYWh6DFPiPVFlRD2CTOZU+U/rfFn8wRT/qgLGyNI23/2el0fC1Bb0=";
        
        String stringarray[]=srcstring.split("#");  
        
        List<byte[]> srcArrays =new ArrayList<byte[]>();
        
        for(String stemp:stringarray){  
            byte[] byte_temp = Base64Util.decryptBASE64(stemp);
            byte_temp=cipher.doFinal(byte_temp);
            srcArrays.add(byte_temp);
            
        }
        
        byte[] byte_sta=sysCopy(srcArrays);
        
        System.err.println(new String(byte_sta));
    }
    
    /**
     * 拼接byte数组
     * @param srcArrays
     * @return
     * @author lzq
     * @date 2015年11月21日下午2:10:08
     */
    public static byte[] sysCopy(List<byte[]> srcArrays) {
         int len = 0;
         for (byte[] srcArray:srcArrays) {
             len+= srcArray.length;
         }
         byte[] destArray = new byte[len];   
         int destLen = 0;
         for (byte[] srcArray:srcArrays) {
             System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);   
             destLen += srcArray.length;   
         }   
         return destArray;
     }  

    
}
