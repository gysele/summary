package org.wxm.core.util;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.H64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.wxm.core.base.bean.Encryption;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * <b>标题: </b>加密工具 <br/>
 * <b>描述: </b>参考文摘：http://www.cnblogs.com/snidget/p/3817763.html <br/>
 * <b>版本: </b>V1.0 <br/>
 * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
 * <b>时间: </b>2016-8-25 下午10:35:09 <br/>
 * <b>修改记录: </b>
 * 
 */
public class EncryptionUtils {
    /**
     * 运算法则:MD5
     */
    public static final String ALGORITHM_MD5 = "MD5";
    /**
     * 运算法则:SHA-512
     */
    public static final String ALGORITHM_SHA_512 = "SHA-512";

    /**
     * 
     * <b>标题: </b>base64进制加密 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-26 下午7:27:29 <br/>
     * <b>修改记录: </b>
     * 
     * @param text
     * @return
     */
    public static String encrytBase64(String text) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(text), "不能为空");
        byte[] bytes = text.getBytes();
        return Base64.encodeToString(bytes);
    }

    /**
     * 
     * <b>标题: </b>base64进制解密 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-26 下午7:27:49 <br/>
     * <b>修改记录: </b>
     * 
     * @param cipherText
     * @return
     */
    public static String decryptBase64(String cipherText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "消息摘要不能为空");
        return Base64.decodeToString(cipherText);
    }

    /**
     * 
     * <b>标题: </b>16进制加密 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-26 下午7:27:58 <br/>
     * <b>修改记录: </b>
     * 
     * @param text
     * @return
     */
    public static String encrytHex(String text) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(text), "不能为空");
        byte[] bytes = text.getBytes();
        return Hex.encodeToString(bytes);
    }

    /**
     * 
     * <b>标题: </b>16进制解密 <br/>
     * <b>描述: </b> <br/>
     * <b>版本: </b>V1.0 <br/>
     * <b>作者: </b>吴晓敏 15109870670@139.com <br/>
     * <b>时间: </b>2016-8-26 下午7:28:16 <br/>
     * <b>修改记录: </b>
     * 
     * @param cipherText
     * @return
     */
    public static String decryptHex(String cipherText) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "消息摘要不能为空");
        return new String(Hex.decode(cipherText));
    }

    public static String generateKey() {
        AesCipherService aesCipherService = new AesCipherService();
        Key key = aesCipherService.generateNewKey();
        return Base64.encodeToString(key.getEncoded());
    }

    /**
     * 对密码进行md5加密,并返回密文和salt，包含在User对象中
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return 密文和salt
     */
    public static Encryption md5Password(String username, String password) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(username), "username不能为空");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(password), "password不能为空");
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        // 组合username,两次迭代，对密码进行加密
        String password_cipherText = new Md5Hash(password, username + salt, 2).toBase64();
        Encryption encryption = new Encryption();
        encryption.setPassword(password_cipherText);
        encryption.setSalt(salt);
        return encryption;
    }

    public static void main(String[] args) {
        String password = "admin";
        String cipherText = encrytHex(password);
        System.out.println(password + "hex加密之后的密文是：" + cipherText);
        String decrptPassword = decryptHex(cipherText);
        System.out.println(cipherText + "hex解密之后的密码是：" + decrptPassword);
        String cipherText_base64 = encrytBase64(password);
        System.out.println(password + "base64加密之后的密文是：" + cipherText_base64);
        String decrptPassword_base64 = decryptBase64(cipherText_base64);
        System.out.println(cipherText_base64 + "base64解密之后的密码是：" + decrptPassword_base64);
        String h64 = H64.encodeToString(password.getBytes());
        System.out.println(h64);
        String salt = "7road";
        String cipherText_md5 = new Md5Hash(password, salt, 4).toHex();
        System.out.println(password + "通过md5加密之后的密文是：" + cipherText_md5);
        System.out.println(generateKey());
        System.out.println("==========================================================");
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        Key key = aesCipherService.generateNewKey();
        String aes_cipherText = aesCipherService.encrypt(password.getBytes(), key.getEncoded()).toHex();
        System.out.println(password + " aes加密的密文是：" + aes_cipherText);
        String aes_mingwen = new String(aesCipherService.decrypt(Hex.decode(aes_cipherText), key.getEncoded()).getBytes());
        System.out.println(aes_cipherText + " aes解密的明文是：" + aes_mingwen);
    }
}
