package com.zongcc.staticTest;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptUtil {

    /**
     * 字符串转MD5带字符编码
     *
     * @param key
     * @param charset
     * @return
     */
    public static String EncoderByMD5(String key, String charset) {
        try {
            byte[] btInput = key.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String EncodeByRC4(String aInput, String aKey) {
        int[] iS = new int[256];
        byte[] iK = new byte[256];

        for (int i = 0; i < 256; i++)
            iS[i] = i;

        int j = 1;

        for (short i = 0; i < 256; i++) {
            iK[i] = (byte) aKey.charAt((i % aKey.length()));
        }

        j = 0;

        for (int i = 0; i < 255; i++) {
            j = (j + iS[i] + iK[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
        }


        int i = 0;
        j = 0;
        char[] iInputChar = aInput.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];
        for (short x = 0; x < iInputChar.length; x++) {
            i = (i + 1) % 256;
            j = (j + iS[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
            int t = (iS[i] + (iS[j] % 256)) % 256;
            int iY = iS[t];
            char iCY = (char) iY;
            iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
        }

        return new String(iOutputChar);

    }

    public static String EncodeCookieValue(String aInput, String aKey) {
        String str = EncodeByRC4(aInput, aKey);
        String base64Str = "";
        try {
            base64Str = Base64.encodeBase64String(ConvertUtil.getBytesFromObject(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Str.replace("\r\n", "");
    }

    public static String DecodeCookieValue(String base64Str, String aKey) {
        String str = "";
        try {
            str = EncodeByRC4(ConvertUtil.getObjectFromBytes(Base64.decodeBase64(base64Str)).toString(), aKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * MD5加密
     *
     * @param input
     * @return
     */
    public static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA1加密
     *
     * @param decript
     * @return
     */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA加密
     *
     * @param decript
     * @return
     */
    public static String SHA(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * AES加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encryptAES(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decryptAES(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key) {
        return encodeBase64String(key);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        return decodeBase64String(key);
    }

    private static String encodeBase64String(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    private static byte[] decodeBase64String(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * Hmac-SHA1加密
     *
     * @param key  密钥
     * @param data 加密数据
     * @return
     */
    public static byte[] encryptHmacSHA1(String key, String data) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(spec);
            byteHMAC = mac.doFinal(data.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException ignore) {
        }
        return byteHMAC;
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        String v = "lijiahui@sohu-inc.com#123455";
//        String v2 = EncryptUtil.EncodeCookieValue(v, Constant.KEY);
//
//        System.out.println(v2);
//        System.out.println(EncryptUtil.DecodeCookieValue(v2, Constant.KEY));

        String s = "POST" + "\n" + "Wed, 10 Sep 2014 13:12:56 GMT" + "\n" + "/v1/passports/?email=111@sohu-inc.com&phone=13012349876";
        String HmacSHA1 = encryptBASE64(encryptHmacSHA1("test", s));
        System.out.println(HmacSHA1);
    }

}
