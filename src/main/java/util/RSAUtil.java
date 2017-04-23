package util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil extends EncryptCode {
	public static final String KEY_ALGORITHM="RSA";
	public static final String SIGNATURE_ALGORITHM="MD5withRSA";
	private static final String PUBLIC_KEY="RSAPublicKey";
	private static final String PRIVATE_KEY="RSAPrivateKey";
	
	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	/*
	 * 用私钥对信息生成数字签名
	 */
	public static String sign(byte[] data,String privatekey) throws Exception{
		byte[] keyBytes=decryptBASE64(privatekey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(keyBytes);
		
		//KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
		
		//取私钥对象
		PrivateKey priKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		//用私钥对信息进行数字签名
		Signature signature=Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return encryptBASE64(signature.sign());
	}
	
	/*
	 * 校验数字签名
	 */
	public static boolean verify(byte[] data,String publicKey,String sign) throws Exception
	{
		//解密由base64编码的公钥
		byte[] keyBytes=decryptBASE64(publicKey);
		
		//构造X509EncoderKeySpec对象
		X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
		
		PublicKey pubKey=keyFactory.generatePublic(x509EncodedKeySpec);
		
		Signature signature=Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(decryptBASE64(sign));
	}
	
	//解密
	public static byte[] decryptByPrivateKey(byte[] data,String key) throws Exception{
		//对密钥解密
		byte[] keyBytes=decryptBASE64(key);
		
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
		Key priKey=keyFactory.generatePrivate(keySpec);
		//对数据解密
		Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return cipher.doFinal(data);
	}
	
	//解密 用公钥
		public static byte[] decryptByPublicKey(byte[] data,String key) throws Exception{
			//对密钥解密
			byte[] keyBytes=decryptBASE64(key);
			
			X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
			
			KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
			Key pubKey=keyFactory.generatePublic(keySpec);
			//对数据解密
			Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, pubKey);
			return cipher.doFinal(data);
		}
		
		/**
		 * 加密<br>
		 * 用公钥加密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptByPublicKey(byte[] data, String key)
				throws Exception {
			// 对公钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			return cipher.doFinal(data);
		}

		/**
		 * 加密<br>
		 * 用私钥加密
		 * 
		 * @param data
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static byte[] encryptByPrivateKey(byte[] data, String key)
				throws Exception {
			// 对密钥解密
			byte[] keyBytes = decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			return cipher.doFinal(data);
		}

		/**
		 * 取得私钥
		 * 
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPrivateKey(Map<String, Object> keyMap)
				throws Exception {
			Key key = (Key) keyMap.get(PRIVATE_KEY);

			return encryptBASE64(key.getEncoded());
		}

		/**
		 * 取得公钥
		 * 
		 * @param keyMap
		 * @return
		 * @throws Exception
		 */
		public static String getPublicKey(Map<String, Object> keyMap)
				throws Exception {
			Key key = (Key) keyMap.get(PUBLIC_KEY);

			return encryptBASE64(key.getEncoded());
		}

}
