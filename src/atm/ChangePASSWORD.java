/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Phạm Hồng Sáng
 */
public class ChangePASSWORD {
    /**
     * Đây là phương thức thay dổi mật khẩu của user
     * @param tenfile
     * @throws FileNotFoundException
     * @throws IOException 
     */
    void changePassWord(String tenfile) throws FileNotFoundException, IOException{
         Scanner ab = new Scanner(System.in);
         File x = new File("D:\\ATM\\TaiKhoan\\"+tenfile + ".txt");
         String block = null;
         String matkhau = null;
         String money = null;
        try (Scanner Doc = new Scanner(x)) {
            block = Doc.nextLine();
            matkhau = Doc.nextLine();
            money = Doc.nextLine();
            int doimatkhau=0;//giá trị nhận biết điều kiện dừng của doi matkhau
            System.out.println("Enter your OLD PASWORD");
            String oldpass= ab.nextLine();
            do{
                if(!oldpass.equals(matkhau)){
                    System.out.println("INCORREC -> EXIT");break;
                }
                System.out.println("Enter your NEW PASWORD");
                System.out.println("The password is only valid for 4 integers");
                System.out.println("Ex. 1 2 3 4");
                String pattern="\\d\\d\\d\\d";
                String matkhaumoi=ab.nextLine();//Mật khẩu nhập lần 1
                String matkhaumoi2;//Mật khẩu nhập lần 2
                int lan2=2;//Số lần tối đa được nhập mật khâu lần 2
                int tieptuc=0;//Xet dieu kien De tiep tuc hay thoat
                int stopnew = 0;
                if(matkhaumoi.matches(pattern)){
                    do{
                        if(matkhaumoi.equals(matkhau)){
                            System.out.println("Not allowed to resemble "
                                    + "the old password ->EXIT");
                            doimatkhau=1;
                            break;
                        }
                        System.out.println("Enter the password again "+ lan2);
                        lan2--;
                        matkhaumoi2=ab.nextLine();
                        if(matkhaumoi.equals(matkhaumoi2)){
                            System.out.println("You have successfully changed"
                                    + " your password");
                            stopnew=1;
                            doimatkhau=1;
                            tieptuc=1;
                            matkhau=matkhaumoi;
                        }
                        else{
                            System.out.println("Incorrect");
                        }
                        if(lan2==0){
                            System.out.println("Password Change Failed");
                            
                            String kt ="[1,0]";
                            while(tieptuc==0){
                                System.out.println("Do you want to exit?");
                                System.out.println("1:CONTINUES      0:EXIT");
                                String kiemtra=ab.nextLine();
                                if(kiemtra.matches(kt)){
                                    if(kiemtra.equals("0")){
                                        stopnew=1;
                                        tieptuc=1;
                                        doimatkhau=1;
                                    }
                                    else tieptuc=1;
                                }
                                else {
                                    System.out.println("Only 1 or 0");
                                    tieptuc=0;
                                }
                            }
                        }
                    }while(stopnew==0&&lan2>0);
                }else{
                    System.out.println("Wrong format");
                }
            }while(doimatkhau==0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        DataOutputStream dos;
        try (FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"+ tenfile + ".txt")) {
            dos = new DataOutputStream(fos);
            dos.writeBytes(block + "\n");
            dos.writeBytes(matkhau+"\n");
            dos.writeBytes(money+"\n");
        }
        dos.close();             
    }
}
