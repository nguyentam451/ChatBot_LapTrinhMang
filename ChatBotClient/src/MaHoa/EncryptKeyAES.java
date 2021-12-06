/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaHoa;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author Tam
 */
public class EncryptKeyAES {
    private static final String publicKeyString =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ1PVGCI/pxRg6ZQ4MH6n7nlmpLcEbK8CQ9ndSayQnOPaPf/zuI7O7vO+Hw2lWbWi+R/3QAEArKeNjsMU2BBO1UlaiaDXHNUg1Xl/n9XXRk8dT8aNqZV+5uKT3qGgkqms5pRY/JhC28PPk3SqQsG/vFVaCgSu8/+6bH0BiZFixdwIDAQAB";
    private static PublicKey publicKey;
    
    private static void encodeKey(){
        try{
            X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(publicKeyString));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpecPublic);
            
            System.out.println("ok");
            
        } catch (Exception ignored){}
        
    }
    
    public static String EncryptKeyWithRSA() throws Exception{
        encodeKey();
        byte[] messageToBytes = AESClient.secretKey.getEncoded(); // key AESClient vừa tạo
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = cipher.doFinal(messageToBytes);
        
        return encode(encryptedMessage);
    }
    
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
