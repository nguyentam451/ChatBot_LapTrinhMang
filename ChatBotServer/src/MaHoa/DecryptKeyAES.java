/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaHoa;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Tam
 */
public class DecryptKeyAES {
    private static final String privateKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANnU9UYIj+nFGDplDgwfqfueWaktwRsrwJD2d1JrJCc49o9//O4js7u874fDaVZtaL5H/dAAQCsp42OwxTYEE7VSVqJoNcc1SDVeX+f1ddGTx1Pxo2plX7m4pPeoaCSqazmlFj8mELbw8+TdKpCwb+8VVoKBK7z/7psfQGJkWLF3AgMBAAECgYAJO8ex0kcXZ61hPEteJDF2Yv319+SJnE8rVec07Sm5J+b7qjc0ePrzaEzW+meKrFSWTELsADa4Haxv21xgr8eOAf7/JL8XcQsg+x7B+MDAwPlcvjSwWKw2jNR78N7fFPJedYH4nnAEBGAYAk32A8KpEhLXrOdvT9LQeHEwwctSgQJBAP3X48sVZPq3G5I9iNhKk7Wq+KNfV1BaKE+e5/lJ4x8OrVUEQwUKQzxJntu0t33Mr90qxo0mS6ZTTKhn+UP7oWECQQDbrr5E0pbteYHoLDkxMQAmiDOJHJUTfumYqdZssYUzf57wYE2N5CEZ1qfVyGNEoyudi/b0SZ2m0aZDFjoJr8nXAkAco5jdtsI3zyTbMPoZfQCBRJLcy8rsE9/sa788TuvC+0LvDpYJIYlIaf30R8VT+h31U/gmwKVUVJ2XenOTCfbBAkBOu5H1mIP/GE02qKLWvnkPVkEiItxK4HFYBYaT7guPKW4NOHaTI++5EvWT6P2q29AF5uPbKYcXAoxT/uNOFXKBAkEAgwb28uk8TENaFTb5mZ4j4yimn7JZLrQXhgGlynRzWmcIB96JoTMDDbn8dAxGHY224a0soSfox7C2IRn9MVvS0g==";
    private static PrivateKey privateKey;
    
    private static void encodeKey(){
        try{
            PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(privateKeyString));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpecPrivate);
            
            System.out.println("ok");
            
        }catch (Exception ignored){}
    }
    
    public static SecretKeySpec DecryptKeyWithRSA(String key) throws Exception {
        encodeKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] encryptedMessage = decode(key);
        byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
        
        SecretKeySpec keyDoiXungGiaiMa = new SecretKeySpec(decryptedMessage, "AES");
        return keyDoiXungGiaiMa;
    }
    
    
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
