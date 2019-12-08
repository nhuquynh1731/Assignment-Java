/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import static atm.AdminManager.ip;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Asus
 */
public class ATM {


    public static void main(String[] args) throws IOException, ParseException {
        AdminManager.createProgram();
        String ch;
        System.out.println("You are?");
        System.out.println("1.  Admin.");
        System.out.println("2.  User.");
        ch = ip.nextLine();
        switch (ch) {
            case "1":
                AdminManager.checkAdmin();
                break;
            case "2":
                AdminManager.createfile();
                UserATM.Usermanager();
                break;
        }
    }

}
