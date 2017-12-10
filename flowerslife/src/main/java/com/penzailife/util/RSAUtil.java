package com.penzailife.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.penzailife.util.base64.BASE64Util;

/**
 * RSA加密解密 此工具类能使用指定的字符串，每次生成相同的公钥和私钥且在linux和windows密钥也相同；相同的原文和密钥生成的密文相同
 */
public class RSAUtil {
	private static final String ALGORITHM_RSA = "RSA";
	private static final String ALGORITHM_SHA1PRNG = "SHA1PRNG";
	private static final int KEY_SIZE = 1024;
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	private static final String TRANSFORMATION = "RSA/None/NoPadding";
	private static final String SEED = "foreverking";// 种子
	private static final Map<String, Object> KEYMAP = new HashMap<String, Object>();
	
	static{
		try {
			initKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解密 用私钥解密，解密字符串，返回字符串
	 * 
	 * @param data
	 * @return String
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data) throws Exception  {
		return new String(decryptByPrivateKey(BASE64Util.decode(data), getKey(PRIVATE_KEY)));
	}
 
	/**
	 * 加密 用公钥加密，加密字符串，返回用base64加密后的字符串
	 * @param data String
	 * @return String
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data) throws Exception {
		return encryptByBytePublicKey(data.getBytes(), getKey(PUBLIC_KEY));
	}

	/**
	 * 加密 用公钥加密，加密byte数组，返回用base64加密后的字符串
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static String encryptByBytePublicKey(byte[] data, String key) throws Exception {
		return BASE64Util.encode(encryptByPublicKey(data, key));
	}

	/**
	 * 解密 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = BASE64Util.decode(key);// 对私钥解密
		/* 取得私钥 */
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		/* 对数据解密 */
		Cipher cipher = Cipher.getInstance(TRANSFORMATION, new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 加密 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		byte[] keyBytes = BASE64Util.decode(key);// 对公钥解密
		/* 取得公钥 */
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		/* 对数据加密 */
		Cipher cipher = Cipher.getInstance(TRANSFORMATION, new BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}


	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 */
	private static String getKey(String keyType) {
		Key key = (Key) KEYMAP.get(PUBLIC_KEY);
		return BASE64Util.encode(key.getEncoded());
	}

	/**
	 * 初始化公钥和私钥
	 * 
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	private static void initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		SecureRandom random = SecureRandom.getInstance(ALGORITHM_SHA1PRNG);
		random.setSeed(SEED.getBytes());// 使用种子则生成相同的公钥和私钥
		keyPairGen.initialize(KEY_SIZE, random);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();// 公钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();// 私钥
		KEYMAP.put(PUBLIC_KEY, publicKey);
		KEYMAP.put(PRIVATE_KEY, privateKey);
	}

	/**
	 * 使用示例
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String source = "wangyong1221";// 原文
		System.out.println("原文：" + source);
		String encodedStr = RSAUtil.encryptByPublicKey(source);// 加密
		System.out.println("密文：" + encodedStr);
		String decodedStr = RSAUtil.decryptByPrivateKey(encodedStr);// 解密
		System.out.println("解密后的结果：" + decodedStr);
	}
}
