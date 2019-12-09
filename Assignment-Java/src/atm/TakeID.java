/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class TakeID{
    int a=0;
    String tenfile;
    /**
     * Đây là phương thức lấy ID của user
     * @return giá trị đúng khi ID tồn tại.
     */
    String takeIDUser() throws ParseException, IOException{
       
        Scanner ab = new Scanner(System.in);
        do{
            System.out.println("*WELCOME DEPOSIT SERVICE*");
            System.out.println(" Please enter your card number: ");
            this.tenfile = ab.nextLine();
            AdminManager.unlock(this.tenfile);
            File abc = new File("D:\\ATM\\TaiKhoan\\"+ 
                    tenfile + ".txt");
            if(abc.exists()){
                 a=1;   
            }
            else{
                 System.out.println(" Your card number "+tenfile+" does not exist");
            }
        }while(a==0);
        return tenfile;   
    } 
}
