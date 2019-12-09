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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class TakeInForMation {
    public int Dieukien=0;//Dieu kiện khi đăng nhập thành công
    /**
     * Đây là phuong thức lấy thông tin tài khoản mật khẩu của user
     * @param tenfile
     * @throws FileNotFoundException
     * @throws IOException 
     */
        void takeInForMation(String tenfile) throws FileNotFoundException, IOException {
        Scanner ab = new Scanner(System.in);
        int b=0;
        String block;
        String money;
        String matkhau;
       do{   
            File x = new File("D:\\ATM\\TaiKhoan\\"+ tenfile + ".txt");
                Scanner Doc =new Scanner(x);
                block=Doc.nextLine();
                matkhau= Doc.nextLine();
                money=Doc.nextLine();
                int stopnew=0;//Gía trị nhận biết dừng chương trình khi đổi xong mật khẩu của new user
                if(block.equals("ABCD1")){
                    passWord obj = new passWord();
                    int kiemtra;
                    do{              
                        kiemtra=obj.matKhau(matkhau); 
                        if(kiemtra==0){
                             System.out.println(" Incorrect password!!");
                        }
                       
                    }while(kiemtra!=1);
                    int newuser=0;//giá trị nhận biết điều kiện dừng của tạo mật khẩu mới
                    System.out.println("This is the fist time you connect to sever");
                    System.out.println("You much change your passwork");
                    System.out.println("The password is only valid for 4 integers");
                    System.out.println("Ex. 1 2 3 4");
                    do{
                        System.out.println("Enter your NEW PASWORK");
                        String pattern="\\d\\d\\d\\d";
                        String matkhaumoi=ab.nextLine();//Mật khẩu nhập lần 1
                        String matkhaumoi2;//Mật khẩu nhập lần 2
                        int lan2=2;//Số lần tối đa được nhập mật khâu lần 2 
                        int tieptuc=0;//Xet dieu kien De tiep tuc hay thoat
                        if(matkhaumoi.matches(pattern)){
                            do{//Usẻ nhập lại mật khẩu 
                                System.out.println("Enter the password again "+ lan2);
                                lan2--;
                                matkhaumoi2=ab.nextLine();
                                if(matkhaumoi.equals(matkhaumoi2)){
                                    System.out.println("You have successfully changed"
                                            + " your password");
                                    stopnew=1;
                                    newuser=1;
                                    tieptuc=1;
                                    matkhau=matkhaumoi;
                                }
                                else{
                                    System.out.println("Incorrect");
                                  }
                                if(lan2==0&&newuser==0){ //KHi nhập mậy khẩu lại sai 2 lần                                 
                                    System.out.println("Password Change Failed");
                                    String kt ="[1,0]";
                                    while(tieptuc==0){ 
                                        System.out.println("Do you want to exit?");
                                        System.out.println("1:CONTINUES      0:EXIT");
                                        String kiemtra1=ab.nextLine();
                                        if(kiemtra1.matches(kt)){
                                            if(kiemtra1.equals("0")){
                                                 stopnew=1;
                                                 tieptuc=1;
                                                 newuser=1;
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
                    }while(newuser==0);              
                }
                if(stopnew==1){//dừng chương trình khi new user thay đổi mật khẩu
                    Doc.close();
                    DataOutputStream dos;
                try (FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"+ tenfile + ".txt")) {
                    dos = new DataOutputStream(fos);
                    dos.writeBytes("ABCD" + "\n");
                    dos.writeBytes(matkhau+"\n");
                    dos.writeBytes(money+"\n");
                }
                    dos.close();             
                    break;
                }
                if(block.equals("ABCD")){//ABCD có nghĩa là tài khoản của user k bị khóa 
                    passWord obj = new passWord();
                    int solannhap=0;
                    int kiemtra;
                    do{              
                        kiemtra=obj.matKhau(matkhau); 
                        solannhap++; 
                        if(kiemtra==0){
                             System.out.println(" Incorrect password!!");
                             System.out.println(" You have "+solannhap +" incorrect");
                        }
                        else b=1;
                       
                    }while(solannhap<5&&kiemtra!=1);
                    if(solannhap==5&&b==0){//Khi user nhập sai mật khẩu 5 lần sẽ bị khỏe tài khoản
                        System.out.println("Your account has been locked "
                            + "because you have entered more than the"
                            + " specified number of times (5)");
                        FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"
                                + tenfile + ".txt");
                        DataOutputStream dos = new DataOutputStream(fos);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar cal = Calendar.getInstance();
                        String st = sdf.format(cal.getTime());
                        dos.writeBytes(st + "\n");
                        dos.writeBytes(matkhau+"\n");
                        dos.writeBytes(money+"\n");                        
                        break;
                    }else {
                        b=1;
                        Dieukien=1;
                    }
                } 
                else{
                    System.out.println("Your account has been locked "
                            + "because you have entered more than the"
                            + " specified number of times");
                    b=1;
                }
        } while(b==0);    
    }
}


class passWord{
    /**
     * Đây là phương thức nhập mật khẩu của user
     * @param matkhau
     * @return giá trị 1 khi user nhập đúng mật khẩu
     * @throws FileNotFoundException 
     */
    int matKhau(String matkhau) throws FileNotFoundException{
        int dk = 0;
        String MkNhap; 
        Scanner ab = new Scanner(System.in);   
        System.out.println("----------------------Enter your passwork----"
                + "-----------------------");
        System.out.println("The account will be locked when you enter the"
                + " wrong password 5 times");
        MkNhap= ab.nextLine();
        if(MkNhap.equals(matkhau)){
            dk=1;
        }
        return dk;
    }
}