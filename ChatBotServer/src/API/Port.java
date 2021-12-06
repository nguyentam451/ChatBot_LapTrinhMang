/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Port {
      public static void CheckPort(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập địa chỉ IP bạn muốn kiểm tra port: ");
        String ip = sc.nextLine();        
        System.out.print("Kiểm tra port từ: ");
        int x = sc.nextInt();
        System.out.print("đến port: ");
        int y = sc.nextInt();
        while (y<=x) {
            System.out.println("Hãy nhập lại!!!");
            System.out.print("đến port: ");
            y = sc.nextInt();
        }
        System.out.println("Các port đang mở: ");
        for(int i=x; i <=y; i++)
        {   
            try{
                Socket ServerSok = new Socket(ip,i);
                System.out.print(i +"\n");
                ServerSok.close();
            }
            catch (Exception e){
                System.out.println("Port đang đóng: " + i);
            }
        }
    }
    public static void main(String args[]){
        CheckPort();
    }
}
