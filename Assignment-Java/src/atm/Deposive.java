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
 * @author Pham Hong Sáng
 */
public class Deposive {
    /**
     * Đây là phương thức nạp tiền và tài khoản của user
     * @param tenfile ID của user
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void congTien(String tenfile) throws FileNotFoundException, IOException{
        ITOA itoa =new ITOA();
        ATOI atoi = new ATOI();
        
        
        Date date = new Date();
        String tienrut ="0";
        String[] lsngay=new String[100];
        String[] lstien=new String[100];
        int Thanhcong=0;//Nhận biết khi rút thành công
        int soluongls=0;//Số lượng giao dịch có trong file nạp của user
        int soluongls_user=0; //Sos lượng lịch sử có trong file lịch sử của user
        int check_ls_file_nap=0; //Check xem trong file lịch sử nạp có tồn tại lịch sử hay không 
        int check_ls_file_lsUser=0;// check reong file lịch sử có dữ liệu hay không
        File lsUser = new File("D:\\ATM\\LichSu\\"+tenfile + ".txt");
        String[] Ls_User_Loai_GD=new String[100];
        String[] Ls_User_Ngay=new String[100];
        String[] Ls_User_Tien=new String[100];
        //Coppy lịch sử vào mãng
        try (Scanner LichSuUser = new Scanner(lsUser)) {
            //Coppy lịch sử vào mãng
            if (LichSuUser.hasNext()){
                for (int i = 0; i < 100; i++) {
                    check_ls_file_lsUser++;
                    Ls_User_Loai_GD[i]=LichSuUser.nextLine();
                    Ls_User_Ngay[i]=LichSuUser.nextLine();
                    Ls_User_Tien[i]=LichSuUser.nextLine();
                    soluongls_user=i; 
                    if (!LichSuUser.hasNext())break;
                }
            }
        }
        
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String Ngay = format.format(date);
        
        File lsNap = new File("D:\\ATM\\Nap\\"+tenfile + ".txt");
        
        File x = new File("D:\\ATM\\TaiKhoan\\"+tenfile + ".txt");
        int kiemtra=0;//Dieu kien do while tinh nang nap tien
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
            
            Scanner LichSu = new Scanner(lsNap);
           if (LichSu.hasNext()){
               for (int i = 0; i < 100; i++) {
                check_ls_file_nap++;
                lsngay[i]=LichSu.nextLine();
                lstien[i]=LichSu.nextLine();
                soluongls=i;
                 if (!LichSu.hasNext())break;
                } 
           }
            //Bên dưới là phàn điều kiện để được nạp tiền
            Scanner Rut = new Scanner(lsNap);
            int Lan=0;
            int tong=0;
            String tien;
            String ngay = null; 
            if (Rut.hasNext()){
                ngay = Rut.nextLine();
                tien=Rut.nextLine();
                Lan++;
                tong=tong+ atoi.atoi(tien);
            }
            for (int i = 1; i < 100; i++) {

                if (!Rut.hasNext()) {
                    break;
                }else{
                    ngay = Rut.nextLine();
                    tien=Rut.nextLine();
                    tong=tong+ atoi.atoi(tien);
                    Lan++;
                }
                if(!ngay.equals(Ngay)){
                    tong=tong- atoi.atoi(tien);
                    Lan--;
                    break;
                }
            }
            if (Lan<5&&tong<25000) {
            }
            else {
                if (Lan>=5) {
                    System.out.println("You have exceeded the number of times you "
                            + "are Deposit (Less than equal to 5)");
                    break;
                }
                else{
                        System.out.println("You have Deposit the amount of "
                                       + "the Deposit allowed in the day (Less"
                                       + " than 25000 USD)");
                        break;
                    }
            }

            //Bên dưới là phần nạp tiền
            System.out.println("Enter the amount you want to deposit");
            String pattern="\\d*";
            String tiengui= input.nextLine();
            if(tiengui.matches(pattern)){
                int gui =atoi.atoi(tiengui);
                tienrut=itoa.iToA(gui);
                if (tong+gui>25000) {
                    System.out.println("Unable to withdraw because the amoun"
                            + "t exceeds the amount allowed for deposit in a day");
                    break;}
                if(gui<0){
                    System.out.println("Only accept positive integers");
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
                    money=money+gui;
                    kiemtra=1;
                    System.out.println("The remaining amount in your account is "+ money+" USD");
                    Thanhcong=1;
                }
            }
            else{
                System.out.println("Only accept positive integers");
            }    
        }while(kiemtra==0);
        //Sau khi nạ thành công thì in lịch sử vào file.
        String Tien =itoa.iToA(money);
        DataOutputStream dos;
        try (FileOutputStream fos = new FileOutputStream("D:\\ATM\\TaiKhoan\\"+ tenfile + ".txt")) {
            dos = new DataOutputStream(fos);
            dos.writeBytes(block + "\n");
            dos.writeBytes(matkhau+"\n");
            dos.writeBytes(Tien);
        }
        dos.close();  
        if (Thanhcong==1) {
            try (FileOutputStream kthuc = new FileOutputStream(lsNap)) {
                kthuc.write(Ngay.getBytes());
                kthuc.write('\n');
                kthuc.write(tienrut.getBytes());
                kthuc.write('\n');
                if (check_ls_file_nap!=0){
                    for (int i = 0; i <=soluongls; i++) {
                        kthuc.write(lsngay[i].getBytes());
                        kthuc.write('\n');
                        kthuc.write(lstien[i].getBytes());
                        kthuc.write('\n');
                    }
                }}
        }
        if (Thanhcong==1) {
            try (FileOutputStream kthuc = new FileOutputStream(lsUser)) {
                String type="Type: Deposit";
                kthuc.write(type.getBytes());
                kthuc.write('\n');
                kthuc.write(Ngay.getBytes());
                kthuc.write('\n');
                kthuc.write(tienrut.getBytes());
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
                }}
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Calendar cal = Calendar.getInstance();
            String st = sdf.format(cal.getTime());
            File obj10= new File("D:\\ATM\\Nap\\"+st+".txt");
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
            try (FileOutputStream ldNap = new FileOutputStream("D:\\ATM\\Nap\\"+st+".txt"); 
                    DataOutputStream IDNAP = new DataOutputStream(ldNap)) {
                IDNAP.writeBytes(tenfile+"\n");
                for (int i = 0; i < f; i++) {
                    IDNAP.writeBytes(IDnap[i]+"\n");
                }
            }
           
        }
    }
}
