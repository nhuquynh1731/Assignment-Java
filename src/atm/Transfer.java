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
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Transfer {
    private String tenfile;
    /**
     * Đây là phương thức chuyển tiền
     * @param tenfile ID user
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void transferMoney(String tenfile) throws FileNotFoundException,
            IOException{
        String IDnhan;
        Scanner ab = new Scanner(System.in);
        int a=0;//Check ID chuyển có tồn tại hay không hoặc nhập ID của chính 
                 //mình sẽ thoát vòng lập nhập ID
        int dung=0;//Check nhập sai ID nhận quá 3 lần sẽ thoát 
        int tienchuyen = 0;//Số tiền chuyển đi chính thức khi đúng cá điều kiện 
        String tienChuyenDi = null;////Hàm phụ để xét điều kiện nhập
        
        int soluongls_user=0;//Số lượng giao dịch có trong file lịch suwr của user 
        int check_ls_file_lsUser=0;//Check dữ liệu trng  file lịch sử của user
        int Thanhcong=0;
        //Coppy lịch sử của user ra mãng
        File lsUser = new File("D:\\ATM\\LichSu\\"+tenfile + ".txt");
        String[] Ls_User_Loai_GD=new String[100];
        String[] Ls_User_Ngay=new String[100];
        String[] Ls_User_Tien=new String[100];
        try (Scanner LichSuUser = new Scanner(lsUser)) {
            if (LichSuUser.hasNext()){
                for (int i = 0; i < 100; i++) {
                    check_ls_file_lsUser++;
                    Ls_User_Loai_GD[i]=LichSuUser.nextLine();
                    Ls_User_Ngay[i]=LichSuUser.nextLine();
                    Ls_User_Tien[i]=LichSuUser.nextLine();
                    soluongls_user = i;
                    if (!LichSuUser.hasNext())break;
                } 
            }
        }
        
        
        do{
            do{
                System.out.println("Enter the receiving ID");
                dung++;
                IDnhan = ab.nextLine();
                File abc = new File("D:\\ATM\\TaiKhoan\\"+ 
                        IDnhan + ".txt");
                if(abc.exists()&&!IDnhan.equals(tenfile)){
                     a=1;   
                }
                else{
                    if(IDnhan.equals(tenfile)){
                        System.out.println("Do not transfer to yourself");
                    }
                       else System.out.println("ID number "+IDnhan+" does not exist");
                }
            }while(a==0&&dung<3);
            //Thoát  khi nhập sai ID 3 lần 
            if(dung==3&&a==0){
                System.out.println("You have entered incorrect "
                        + "ID 3 times -> EXIT");
                break;
            }
            ITOA itoa =new ITOA();
            ATOI atoi = new ATOI();
            File x = new File("D:\\ATM\\TaiKhoan\\"+tenfile + ".txt");
            int kiemtra=0;//Dieu kien do while tinh nang rut tien
            Scanner input =new Scanner(System.in);
            String block;
            String matkhau;
            int money;
            try (Scanner Doc = new Scanner(x)) {
                block = Doc.nextLine();     
                matkhau = Doc.nextLine();
                money = Doc.nextInt();
            }
            do{
                System.out.println("Enter the money you want to Transfer");
                String pattern="\\d*";
                tienChuyenDi= input.nextLine();
                if(tienChuyenDi.matches(pattern)){
                    int rut =atoi.atoi(tienChuyenDi);
                    tienchuyen=rut;
                    if(rut>money){
                        System.out.println("The amount in your account is not "
                                + "enough");
                        System.out.println("The remaining amount in your accoun"
                                + "t is "+ money+" USD");
                        int thoat=0;
                        do{
                            System.out.println("Do you want to exit?");
                            System.out.println("1:CONTINUES      0:EXIT");
                            String kt ="[1,0]";
                            String tieptuc=input.nextLine();
                            if(tieptuc.matches(kt)){
                                if(tieptuc.equals("0")){
                                     kiemtra=1;
                                     thoat=1;
                                } else {
                                    kiemtra=0;
                                    thoat=1;
                                }

                            }
                            else {
                                System.out.println("Only 1 or 0");
                                kiemtra=0;
                            }
                        }while(thoat==0);

                    } 
                    else{
                        money=money-rut;
                        kiemtra=1;
                        System.out.println("The remaining amount in your accoun"
                                + "t is "+ money+" USD");
                        Thanhcong=1;
                    }
                }
                else{
                    System.out.println("You are only allowed to enter integers");
                }    
            }while(kiemtra==0);
               //Bên dưới là phần ghi lịch sử về file
            String tien =itoa.iToA(money);
            DataOutputStream dos;
            try (FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"
                    + tenfile + ".txt")) {
                dos = new DataOutputStream(fos);
                dos.writeBytes(block + "\n");
                dos.writeBytes(matkhau+"\n");
                dos.writeBytes(tien);
            }
            dos.close();             
            taikhoannhan nhan = new taikhoannhan();
            nhan.congTien(tienchuyen, IDnhan,tenfile);
            }while(a==0);    
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String Ngay = format.format(date);
            if (Thanhcong==1) {
            try (FileOutputStream kthuc = new FileOutputStream(lsUser)) {
                String type="Type: Transfer money to ID "+IDnhan;
                kthuc.write(type.getBytes());
                kthuc.write('\n');
                kthuc.write(Ngay.getBytes());
                kthuc.write('\n');
                kthuc.write(tienChuyenDi.getBytes());
                kthuc.write('\n');
                if (check_ls_file_lsUser!=0){
                    for (int i = 0; i <=soluongls_user; i++) {
                        kthuc.write(Ls_User_Loai_GD[i].getBytes());
                        kthuc.write('\n');
                        kthuc.write(Ls_User_Ngay[i].getBytes());
                        kthuc.write('\n');
                        kthuc.write(Ls_User_Tien[i].getBytes());
                        kthuc.write('\n');
                    }
                }
            }
             if (Thanhcong==1) {
                 String[] LS_Ngaychuyen=new String[100];
                 String[] LS_Tienchuyen=new String[100];
                 int k=0;
                 File obj6=new File("D:\\ATM\\Chuyen\\"+tenfile+".txt");
                 Scanner obj7= new Scanner(obj6);
                 while(true){
                     if (obj7.hasNext()) {
                         LS_Ngaychuyen[k]=obj7.nextLine();
                         LS_Tienchuyen[k]=obj7.nextLine();
                         k++;
                     }else break;
                 }
                 obj7.close();
                  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                  Calendar cal = Calendar.getInstance();
                  String st = sdf.format(cal.getTime());
                  FileOutputStream ldNap = new FileOutputStream("D:\\ATM\\Chuyen\\"+tenfile+".txt"); 
                  DataOutputStream IDNAP = new DataOutputStream(ldNap);
                  IDNAP.writeBytes(st+"\n");
                  IDNAP.writeBytes(tienChuyenDi+"\n");
                  for (int i = 0; i < k; i++) {
                     IDNAP.writeBytes(LS_Ngaychuyen[i]+"\n");
                     IDNAP.writeBytes(LS_Tienchuyen[i]+"\n");
                 }
                 
             }
           SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Calendar cal = Calendar.getInstance();
            String st = sdf.format(cal.getTime());
            File obj10= new File("D:\\ATM\\Chuyen\\"+st+".txt");
            String[] IDnap= new String[100];
            int f=0;
            try (Scanner obj11 = new Scanner(obj10)) {
                while(true){
                    if(obj11.hasNext()){
                        IDnap[f]=obj11.nextLine();
                        f++;
                    }
                    else break;
                }
            }
            try (FileOutputStream ldNap = new FileOutputStream("D:\\ATM\\Chuyen\\"+st+".txt"); 
                    DataOutputStream IDNAP = new DataOutputStream(ldNap)) {
                IDNAP.writeBytes(tenfile+"\n");
                for (int i = 0; i < f; i++) {
                    IDNAP.writeBytes(IDnap[i]+"\n");
                }
            }
        }
    }   
}
class taikhoannhan{
    /**
     * Đây là phương thức chuyển tiền vào tài khoản nhận
     * @param tienchuyen số tiền được chuyển 
     * @param IDnhan ID được chuyển 
     * @param tenfile ID chuyển 
     * @throws FileNotFoundException
     * @throws IOException 
     */
    void congTien(int tienchuyen,String IDnhan,String tenfile) throws FileNotFoundException, IOException{
        ITOA itoa =new ITOA();
        ATOI atoi = new ATOI();
        
        int soluongls_user=0;//Số lượng lịch sử có sẵn trong file lịch sử của user
        int check_ls_file_lsUser=0;//Check file lịch sử của user
        File lsUser = new File("D:\\ATM\\LichSu\\"+IDnhan + ".txt");
        String[] Ls_User_Loai_GD=new String[100];//Các loại mãng coppy lịch sử của user 
        String[] Ls_User_Ngay=new String[100];
        String[] Ls_User_Tien=new String[100];
        //Coppy lịch sử vò mãng
        try (Scanner LichSuUser = new Scanner(lsUser)) {
            //Coppy lịch sử vò mãng
            if (LichSuUser.hasNext()){
                for (int i = 0; i < 100; i++) {
                    check_ls_file_lsUser++;
                    Ls_User_Loai_GD[i]=LichSuUser.nextLine();
                    Ls_User_Ngay[i]=LichSuUser.nextLine();
                    Ls_User_Tien[i]=LichSuUser.nextLine();
                    soluongls_user = i;
                    if (!LichSuUser.hasNext())break; 
                }
            }
        }
        
        //Cộng tiền vào tài khoản 
        File x = new File("D:\\ATM\\TaiKhoan\\"+IDnhan + ".txt");
        int kiemtra=0;//Dieu kien do while tinh nang rut tien
        Scanner input =new Scanner(System.in);
        String block;
        String matkhau;
        int money;
        try (Scanner Doc = new Scanner(x)) {
            block = Doc.nextLine();   
            matkhau = Doc.nextLine();
            money = Doc.nextInt();
        }
        money = money+tienchuyen;
        String tien = itoa.iToA(money);
        DataOutputStream dos;
        try (FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"
                + IDnhan + ".txt")) {
            dos = new DataOutputStream(fos);
            dos.writeBytes(block + "\n");
            dos.writeBytes(matkhau+"\n");
            dos.writeBytes(tien);
        }
        dos.close();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String Ngay = format.format(date);
        String tienNhanDuoc=itoa.iToA(tienchuyen);
        
        try ( //Ghi lại lịch sử vào file
                FileOutputStream kthuc = new FileOutputStream(lsUser)) {
            String type="Type: Receive money from ID: "+tenfile;
            kthuc.write(type.getBytes());
            kthuc.write('\n');
            kthuc.write(Ngay.getBytes());
            kthuc.write('\n');
            kthuc.write(tienNhanDuoc.getBytes());
            kthuc.write('\n');
            if (check_ls_file_lsUser!=0){
                for (int i = 0; i <=soluongls_user; i++) {
                    kthuc.write(Ls_User_Loai_GD[i].getBytes());
                    kthuc.write('\n');
                    kthuc.write(Ls_User_Ngay[i].getBytes());
                    kthuc.write('\n');
                    kthuc.write(Ls_User_Tien[i].getBytes());
                    kthuc.write('\n');
                } 
             }
        }  
       SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Calendar cal = Calendar.getInstance();
            String st = sdf.format(cal.getTime());
            File obj10= new File("D:\\ATM\\Nhan\\"+st+".txt");
            String[] IDnap= new String[100];
            int f=0;
        try (Scanner obj11 = new Scanner(obj10)) {
            while(true){
                if(obj11.hasNext()){
                    IDnap[f]=obj11.nextLine();
                    f++;
                }
                else break;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        try (FileOutputStream ldNap = new FileOutputStream("D:\\ATM\\Nhan\\"+st+".txt"); 
                DataOutputStream IDNAP = new DataOutputStream(ldNap)) {
            IDNAP.writeBytes(IDnhan+"\n");
            for (int i = 0; i < f; i++) {
                IDNAP.writeBytes(IDnap[i]+"\n");
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        String[] LS_NgayNhan=new String[100];
        String[] LS_TienNhan=new String[100];
        int k=0;
        File obj6=new File("D:\\ATM\\Nhan\\"+IDnhan+".txt");
        try (Scanner obj7 = new Scanner(obj6)) {
            while(true){
                if (obj7.hasNext()) {
                    LS_NgayNhan[k]=obj7.nextLine();
                    LS_TienNhan[k]=obj7.nextLine();
                    k++;
                }else break;
            }
        }
         SimpleDateFormat abc = new SimpleDateFormat("dd/MM/yyyy");
         Calendar xyz = Calendar.getInstance();
         String st1 = abc.format(xyz.getTime());
         FileOutputStream ldNap = new FileOutputStream("D:\\ATM\\Nhan\\"+IDnhan+".txt"); 
         DataOutputStream IDNAP = new DataOutputStream(ldNap);
         IDNAP.writeBytes(st1+"\n");
         IDNAP.writeBytes(tienNhanDuoc+"\n");
         for (int i = 0; i < k; i++) {
            IDNAP.writeBytes(LS_NgayNhan[i]+"\n");
            IDNAP.writeBytes(LS_TienNhan[i]+"\n");
        }
    }
}
