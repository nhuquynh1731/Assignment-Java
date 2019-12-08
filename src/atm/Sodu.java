/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Phạm Hồng Sáng
 */
public class Sodu {
    /**
     * Đây là phương thức in số dư trong tài khoản
     * @param tenfile
     * @throws FileNotFoundException 
     */
    public void soDu(String tenfile) throws FileNotFoundException{
        File x = new File("D:\\ATM\\TaiKhoan\\"+tenfile + ".txt");
        Scanner Doc =new Scanner(x);
        String block = Doc.nextLine();
        String matkhau = Doc.nextLine();
        int money = Doc.nextInt();
        System.out.println("The remaining amount in your account is " + money+"USD");
    }
}
