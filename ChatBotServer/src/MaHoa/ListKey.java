/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MaHoa;

import java.util.ArrayList;
import javax.crypto.SecretKey;

/**
 *
 * @author ADMIN
 */
public class ListKey {
    private static ArrayList<AESServer> listKey = new ArrayList<>();
    private static int indexKey=-1;
    
    public static int addKey(SecretKey secretKey){
        AESServer tmp = new AESServer(secretKey);
        listKey.add(tmp);
        indexKey++;
        System.out.println("Key ở vị trí " + indexKey + " : " + tmp.toString());
        return indexKey; // để biết dc key của client gửi nằm ở đâu
    }
    
    public static String decryptWithIndexKey(String mess, int index){
        return listKey.get(index).decrypt(mess);
    }
    
    public static String encryptWithIndexKey(String mess, int index){
        return listKey.get(index).encrypt(mess);
    }
}
