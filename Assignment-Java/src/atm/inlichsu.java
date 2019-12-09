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
public class inlichsu {
    /**
     * Đây là phương thức in lịch sử giao dịch của user
     * @param tenfile ID của user
     * @throws FileNotFoundException 
     */
    void inLichSu(String tenfile) throws FileNotFoundException{
        File obj =new File("D:\\ATM\\LichSu\\"+tenfile + ".txt");
        try (Scanner obj2 = new Scanner(obj)) {
            String type;
            String Day;
            String Money;
            for (int i = 0; i < 5; i++) {
                if(obj2.hasNext()){
                    type=obj2.nextLine();
                    System.out.println(type);
                    Day=obj2.nextLine();
                    System.out.println("Day: "+Day);
                    Money=obj2.nextLine();
                    System.out.println("Amount of transaction money: "+Money);
                    System.out.println("\n");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
