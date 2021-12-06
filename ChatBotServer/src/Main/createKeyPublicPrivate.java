/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Tam
 */
public class createKeyPublicPrivate {
    
    private static SecretKey keyDoiXung; // tương ứng với khóa đối xứng AES
    private static PrivateKey privateKey;
    private static PublicKey publicKey;
//    private static final String publicKeyString =  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSUtHjb0U3Fld4o7sToHOh6VBv8k4Pm3yTQOqt09S2KUcESTXuhcpsqZjVanxagjIGZSXBqPsRxYr3VLAekNKbPfXRJIg/d1Zuo6otu+xcfdLx/UwqU7o63+x1hMQilckcUDzYDVNOkO+jKpmcZL+r7vYjuokWNbVWBI3bgAFhkwIDAQAB";
//    private static final String privateKeyString = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJJS0eNvRTcWV3ijuxOgc6HpUG/yTg+bfJNA6q3T1LYpRwRJNe6FymypmNVqfFqCMgZlJcGo+xHFivdUsB6Q0ps99dEkiD93Vm6jqi277Fx90vH9TCpTujrf7HWExCKVyRxQPNgNU06Q76MqmZxkv6vu9iO6iRY1tVYEjduAAWGTAgMBAAECgYEAgCLUuLVlSINhID1Jgzt8Z2p4zT/EijhgVFeB661YVhk5npA6EWHKTXQLacDxmxTul9kVwkynaP2z3zmjbXDnbEQlL+JBJdthVMRYLVBUsz5gflE9cv57etfm52ZkjUWMpeL9qZq246TJ++Plz9YjQzB9fWLXzN76MylMaFXqSYECQQDYuMtI2WvVckJZ32tW4Z5CctQ0dcvHI7hwwW/F4vagFpDlvqALu6w+60YOHFLecctjcyz02kM7h2e2d+w0Rv3TAkEArNfENqqOr8EMofKctYL8WIpMI/ynZD1KfwpERf4FJbmzs5iJzbaG25Na51+hMa5gfql0jTJ2imZySdLAGLD1QQJBANTAFE/foMxY7mnruiQAOKxirY8SVImZt2Z5fpB0zkc53+SpzdO22NCrhoozo+ZbL57hYjC8U2QM+PNXprlrN6ECQQCpOiBMfbVycwsRTN8OAI9diRp/aZHi3PehZsXt2YXkp+s//it+FCX5uk6YltLWN6sWmIca77uMSj2/9e5KvoeBAkBKQAbm/+PIFZMNijK4ZqI3LPjrRYHN5/MBJ7zRO0qatNdlpNbg5tHxbymhKI1qyQQtm2N3HnulJjohCjs/Wj3W";
    
    private static byte[] encryptedMessage;
    private static byte[] decryptedMessage;
    
    public createKeyPublicPrivate(){
        
    }
    
    // tạo mới key, thay thế khi cần
     public static void init(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            PrivateKey privateKey2 = pair.getPrivate();
            PublicKey publicKey2 = pair.getPublic();
            System.out.println("pub key moi: "+ Base64.getEncoder().encodeToString(publicKey2.getEncoded()));
            System.out.println("pri key " + Base64.getEncoder().encodeToString(privateKey2.getEncoded()));
        } catch (Exception ignored) {
        }
    }
    
    
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
     
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
     
}

