/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 * @author Phạm Hồng Sáng
 */
public class UserATM {

    public static void Usermanager() throws IOException, ParseException {
        Deposive deposit = new Deposive();
        Transfer transfer = new Transfer();
        Sodu sodu = new Sodu();
        TakeID xyz = new TakeID();
        ChangePASSWORD change = new ChangePASSWORD();
        String tenfile = xyz.takeIDUser();
        TakeInForMation abc = new TakeInForMation();
        abc.takeInForMation(tenfile);
        Scanner ip = new Scanner(System.in);
        Withdrawal abcd = new Withdrawal();
        inlichsu nhat = new inlichsu();
        int chay = 0;
        if (abc.Dieukien == 1) {
            String choice;
            do {
                System.out.println("**WELCOME TO XXX BANK**");
                System.out.println("    Please choose the service");
                System.out.println("    1. Deposit.");
                System.out.println("    2. Withdrawal.");
                System.out.println("    3. Balance Enquiry.");
                System.out.println("    4. Change Password.");
                System.out.println("    5. Transfer money.");
                System.out.println("    6. Print the 5 latest transaction history.");
                System.out.println("    7. EXIT");
                System.out.println("\n");
                choice = ip.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("\n");
                        deposit.congTien(tenfile);
                        break;
                    case "2":System.out.println("\n");
                        abcd.withDrawal(tenfile);
                        break;
                    case "3":System.out.println("\n");
                        sodu.soDu(tenfile);
                        break;
                    case "4":System.out.println("\n");
                        change.changePassWord(tenfile);
                        break;
                    case "5":System.out.println("\n");
                        transfer.transferMoney(tenfile);
                        break;
                    case "6":System.out.println("\n");
                        nhat.inLichSu(tenfile);
                        break;
                    case "7":System.out.println("\n");
                        System.out.println("GOODBYE <3 SEE YOU AGAIN <3 !!!");
                        chay = 1;
                        break;
                    default:System.out.println("\n");
                        System.out.println("ONLY 1 to 6");
                        System.out.println("\n");
                }
            } while (chay == 0);
        }
    }
}
