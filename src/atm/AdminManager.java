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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class AdminManager {

    static Scanner ip = new Scanner(System.in);

    /**
     * Method to create file to store data in the fist time the program run
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    static void createProgram() throws FileNotFoundException, IOException {
        File file = new File("D:/ATM");
        boolean checkcr = file.mkdir();
        //check if cannot create file ATM, the program has been run in this pc before
        //else create folder to save data
        if (checkcr) {
            File filenap = new File("D:/ATM/Nap");
            filenap.mkdir();
            File filechuyen = new File("D:/ATM/Chuyen");
            filechuyen.mkdir();
            File filenhan = new File("D:/ATM/Nhan");
            filenhan.mkdir();
            File filerut = new File("D:/ATM/Rut");
            filerut.mkdir();
            File filelichsu = new File("D:/ATM/LichSu");
            filelichsu.mkdir();
            File filetaikhoan = new File("D:/ATM/TaiKhoan");
            filetaikhoan.mkdir();
            File filethongtin = new File("D:/ATM/ThongTin");
            filethongtin.mkdir();
            FileOutputStream fos = new FileOutputStream("D:/ATM/Data.txt");
            DataOutputStream dos = new DataOutputStream(fos);
            DecimalFormat dcf = new DecimalFormat("00000000");
            String st = dcf.format(0);
            dos.writeBytes(st);
            FileOutputStream fosad = new FileOutputStream("D:/ATM/Admin.txt");
            DataOutputStream dosad = new DataOutputStream(fosad);
            dosad.writeBytes("NhanPVT\n");
            dosad.writeBytes("trongnhan1907");
        }
    }

    /**
     * Method to display admin's menu
     */
    static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("    1.  CREATE CUSTOMER'S INFORMATION.");
        System.out.println("    2.  REPORT.");
        System.out.println("    3.  EXIT.");
        System.out.print("Enter your choice: ");
    }

    /**
     * method to get number of customer
     *
     * @return
     * @throws FileNotFoundException
     */
    public static int getNumberOfCus() throws FileNotFoundException {
        int countCus;
        //load data from file store number of customer
        Scanner readfile = new Scanner(new File("D:/ATM/Data.txt"));
        countCus = readfile.nextInt();
        return countCus;
    }

    static String checkDate(String tod) {

        while (true) {
            try {
                int ch = 1;
                System.out.print(tod + "(dd/MM/yyyy): ");
                String date = ip.nextLine();
                char[] checkbd = date.toCharArray();
                int day, month, year;
                day = (checkbd[0] - 48) * 10 + checkbd[1] - 48;
                month = (checkbd[3] - 48) * 10 + checkbd[4] - 48;
                year = (checkbd[6] - 48) * 1000 + (checkbd[7] - 48) * 100 + (checkbd[8] - 48) * 10 + checkbd[9] - 48;
                if (checkbd[2] != '/' || checkbd[5] != '/') {
                    System.out.println("-Between day, month and year must be \"/\"");
                    ch = 0;
                }
                if (day < 1 || day > 31) {
                    System.out.println("-Day does not exist!");
                    ch = 0;
                }
                if (month < 1 || month > 12) {
                    System.out.println("-Month does not exist!");
                    ch = 0;
                }
                if (ch == 1) {
                    return date;
                }
            } catch (Exception e) {
                System.out.println("Format of day is wrong!");
            }

        }

    }

    /**
     * method to calculate age based on date of birth and current time
     *
     * @param bd birthday
     * @return
     * @throws ParseException
     */
    static int getAge(String bd) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int birthyear = sdf.parse(bd).getYear();
        Calendar cal = Calendar.getInstance();
        return cal.getWeekYear() - birthyear - 1900;

    }

    /**
     * method to get random password
     *
     * @return
     */
    static String getRandomPass() {
        java.util.Random rd = new java.util.Random();
        DecimalFormat dcf = new DecimalFormat("0000");
        int pass;
        pass = rd.nextInt();
        if (pass < 0) {
            pass = pass * (-1);
        }
        pass = pass % 10000;
        String j = dcf.format(pass);
        return j;
    }

    /**
     * method to add customer
     *
     * @param cus object of class customer
     * @param arl object of array list
     * @param countCus number of customer
     * @throws ParseException
     * @throws IOException
     */
    static void addCustomer(Customer cus, ArrayList<Customer> arl, int countCus) throws ParseException, IOException {
        DecimalFormat dcf = new DecimalFormat("000000000");
        arl.add(cus);
        System.out.println("Add customer's information:");
        arl.get(countCus).ID = dcf.format(countCus);
        System.out.println("ID: " + arl.get(countCus).ID);
        System.out.print("Name: ");
        arl.get(countCus).name = ip.nextLine();
        arl.get(countCus).pass = getRandomPass();
        arl.get(countCus).birthday = checkDate("Birthday ");
        int check = 0;
        String gender = null;
        while (check == 0) {
            System.out.print("Gender (Male/Female): ");
            gender = ip.nextLine();
            if (gender.equals("Male") || gender.equals("Female")) {
                check = 1;
            } else {
                System.out.println("Only enter Male or Female!");
            }
        }
        arl.get(countCus).gender = gender;
        check = 0;
        String phonenum = null;
        while (check != 1) {
            check = 1;
            System.out.print("Phone number: ");
            phonenum = ip.nextLine();
            char[] pn = phonenum.toCharArray();
            if (pn.length != 10) {
                System.out.println("Phone number must be have 10 numbers!");
                check = 0;
            } else {
                int count = 0;
                for (int i = 0; i < 10; i++) {
                    if (pn[i] < 48 || pn[i] > 57) {
                        count += 1;
                    }
                }
                if (count > 0) {
                    System.out.println("All numbers in phone number must be positive integer or 0!");
                    check = 0;
                }
            }
        }
        arl.get(countCus).phonenumber = phonenum;
        check = 0;
        int bala = 0;
        while (check != 1) {
            check = 1;
            try {
                System.out.print("Balance: ");
                bala = ip.nextInt();
            } catch (Exception e) {
                System.out.println("Format of balance is wrong!");
                check = 0;
            }
            if (bala <= 0) {
                System.out.println("Balance must be positive integer or 0!");
                String get = ip.nextLine();
            }
        }
        arl.get(countCus).balance = bala;
        storeInfo(cus, arl, countCus);
    }

    /**
     * method to create file ddMMyyyy.txt every time user login
     *
     * @throws FileNotFoundException
     */
    static void createfile() throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Calendar cal = Calendar.getInstance();
        String st = sdf.format(cal.getTime());
        File file = new File("D:/ATM/Chuyen/" + st + ".txt");
        if (file.exists()) {
        } else {
            FileOutputStream fosch = new FileOutputStream("D:/ATM/Chuyen/" + st + ".txt");
            FileOutputStream fosnap = new FileOutputStream("D:/ATM/Nap/" + st + ".txt");
            FileOutputStream fosnhan = new FileOutputStream("D:/ATM/Nhan/" + st + ".txt");
            FileOutputStream fosrut = new FileOutputStream("D:/ATM/Rut/" + st + ".txt");
        }
    }

    /**
     * method to add the date on the time provided
     *
     * @param date time provided
     * @param value value of day to add
     * @return the date after add value to time provided
     * @throws ParseException
     */
    static Date plusday(Date date, int value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String d = sdf.format(date);
        char[] c = d.toCharArray();
        c[1] += value;
        d = "";
        for (int i = 0; i < 10; i++) {
            d = d + c[i];
        }
        date = sdf.parse(d);
        return date;
    }

    /**
     * method to auto unlock account
     *
     * @param id id need ti unlock
     * @throws FileNotFoundException
     * @throws ParseException
     * @throws IOException
     */
    static void unlock(String id) throws FileNotFoundException, ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date lockday, unlockday;
        String da;
        String s1, s2;
        //if the first line of file is date, this file is locked
        File fileTK = new File("D:/ATM/TaiKhoan/" + id + ".txt");
        if (fileTK.exists()) {
            Scanner read = new Scanner(fileTK);
            da = read.nextLine();
            s1 = read.nextLine();
            s2 = read.nextLine();
            read.close();
            Pattern pat = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
            if (pat.matcher(da).matches()) {
                lockday = sdf.parse(da);
                //check if this account can unlock
                if (plusday(cal.getTime(), -1).after(lockday) || plusday(cal.getTime(), -1).equals(lockday)) {
                    FileOutputStream fos = new FileOutputStream("D:/ATM/TaiKhoan/" + id + ".txt");
                    DataOutputStream dos = new DataOutputStream(fos);
                    dos.writeBytes("ABCD\n");
                    dos.writeBytes(s1 + "\n");
                    dos.writeBytes(s2);
                }
            }
        }
    }

    /**
     * method to store information of customer to file
     *
     * @param cus object of class customer
     * @param arl object of class arraylist
     * @param countCus number of customer
     * @throws FileNotFoundException
     * @throws IOException
     */
    static void storeInfo(Customer cus, ArrayList<Customer> arl, int countCus) throws FileNotFoundException, IOException {
        FileOutputStream fosinfo = new FileOutputStream("D:/ATM/ThongTin/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dosinfo = new DataOutputStream(fosinfo);
        FileOutputStream fostk = new FileOutputStream("D:/ATM/TaiKhoan/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dostk = new DataOutputStream(fostk);
        FileOutputStream fosls = new FileOutputStream("D:/ATM/LichSu/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dosls = new DataOutputStream(fosls);
        FileOutputStream fosnap = new FileOutputStream("D:/ATM/Nap/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dosnap = new DataOutputStream(fosnap);
        FileOutputStream fosrut = new FileOutputStream("D:/ATM/Rut/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dosrut = new DataOutputStream(fosrut);
        FileOutputStream foschuyen = new FileOutputStream("D:/ATM/Chuyen/" + arl.get(countCus).ID + ".txt");
        DataOutputStream doschuyen = new DataOutputStream(fosrut);
        FileOutputStream fosnhan = new FileOutputStream("D:/ATM/Nhan/" + arl.get(countCus).ID + ".txt");
        DataOutputStream dosnhan = new DataOutputStream(fosrut);
        dostk.writeBytes("ABCD1\n");
        dostk.writeBytes(arl.get(countCus).pass + "\n");
        dostk.writeBytes(arl.get(countCus).balance.toString());
        dosinfo.writeBytes(arl.get(countCus).ID + "\n");
        dosinfo.writeBytes(arl.get(countCus).name + "\n");
        dosinfo.writeBytes(arl.get(countCus).pass + "\n");
        dosinfo.writeBytes(arl.get(countCus).balance.toString() + "\n");
        dosinfo.writeBytes(arl.get(countCus).birthday + "\n");
        dosinfo.writeBytes(arl.get(countCus).phonenumber + "\n");
        dosinfo.writeBytes(arl.get(countCus).gender + "\n");
        System.out.println("_____________________________________");
        System.out.println("Customer's information created!");
        System.out.println("Customer's information:");
        System.out.println("ID: " + arl.get(countCus).ID);
        System.out.println("Password: " + arl.get(countCus).pass);
        System.out.println("_____________________________________");
        //count new numbber of customer
        countCus++;
        FileOutputStream fos = new FileOutputStream("D:/ATM/Data.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        DecimalFormat dcf = new DecimalFormat("00000000");
        //store new number of customer
        dos.writeBytes(dcf.format(countCus));
        dos.close();
        fos.close();
        dosinfo.close();
        fosinfo.close();
        dostk.close();
        fostk.close();
    }

    /**
     * method to cut String in date format dd/MM/yyyy to ddMMyyyy
     *
     * @param st date
     * @param cha character need to cut (/)
     * @return
     */
    static String cutcharacters(String st, char cha) {
        String[] cut = st.split("" + cha + "");
        st = cut[0] + cut[1] + cut[2];
        return st;
    }

    /**
     * method to report withdrawal or deposit
     *
     * @param trp type of report
     * @param rp filename store information of report
     * @throws FileNotFoundException
     */
    static void report(String trp, String rp) throws FileNotFoundException {

        String day = checkDate("Enter day ");
        System.out.println("_____________________________________");
        System.out.println("List customer " + rp + " in " + day);
        System.out.println("_____________________________________");
        String daycut = cutcharacters(day, '/');
        HashSet<String> hs = new HashSet<>();
        File file = new File("D:/ATM/" + trp + "/" + daycut + ".txt");
        if (!file.exists()) {
            System.out.println("No one " + rp + " in " + day);
        } else {
            Scanner read = new Scanner(file);
            while (read.hasNext()) {
                hs.add(read.nextLine());
            }
            for (String str : hs) {
                File filegd = new File("D:/ATM/" + trp + "/" + str + ".txt");
                Scanner readid = new Scanner(filegd);

                Scanner readinfo = new Scanner(new File("D:/ATM/ThongTin/" + str + ".txt"));
                readinfo.nextLine();

                System.out.println("Name: " + readinfo.nextLine());

                System.out.println("ID: " + str);
                while (readid.hasNext()) {
                    if (readid.nextLine().endsWith(day)) {
                        System.out.println("Amount: " + readid.nextLine());
                    }
                }
                readinfo.nextLine();
                System.out.println("Balance: " + readinfo.nextLine());
                System.out.println("_____________________________________");
            }
        }
    }

    /**
     * method to report the user transfer
     *
     * @throws FileNotFoundException
     */
    static void transferReport() throws FileNotFoundException {
        try {
            String day = checkDate("Enter day ");
            System.out.println("________________________________________________");
            String daycut = cutcharacters(day, '/');
            ArrayList<String> hsnb = new ArrayList<>();
            ArrayList<String> hsna = new ArrayList<>();
            ArrayList<String> hscb = new ArrayList<>();
            ArrayList<String> hsca = new ArrayList<>();
            File file = new File("D:/ATM/Nhan/" + daycut + ".txt");
            if (!file.exists()) {
                System.out.println("No one transfer in " + day);
            } else {
                Scanner readn = new Scanner(file);
                Scanner readc = new Scanner(new File("D:/ATM/Chuyen/" + daycut + ".txt"));
                while (readn.hasNext()) {
                    hsnb.add(readn.nextLine());
                    hscb.add(readc.nextLine());
                }
                Iterator iterc = hscb.iterator();
                while (iterc.hasNext()) {
                    String o = (String) iterc.next();
                    if (!hsca.contains(o)) {
                        hsca.add(o);
                    }
                }
                Iterator itern = hsnb.iterator();
                while (itern.hasNext()) {
                    String o = (String) itern.next();
                    if (!hsna.contains(o)) {
                        hsna.add(o);
                    }
                }
                for (int i = 0; i < hsna.size(); i++) {
                    String[] storeid = new String[2];
                    Scanner readidn = new Scanner(new File("D:/ATM/Nhan/" + hsna.get(i) + ".txt"));
                    Scanner readinfoc = new Scanner(new File("D:/ATM/ThongTin/" + hsca.get(i) + ".txt"));
                    Scanner readinfon = new Scanner(new File("D:/ATM/ThongTin/" + hsna.get(i) + ".txt"));
                    storeid[0] = readinfoc.nextLine();
                    storeid[1] = readinfon.nextLine();
                    System.out.println("From: " + readinfoc.nextLine() + " to " + readinfon.nextLine());
                    System.out.println("ID of crediter: " + storeid[1]);
                    System.out.println("ID of debiter: " + storeid[0]);
                    while (readidn.hasNext()) {
                        if (readidn.nextLine().endsWith(day)) {
                            System.out.println("Amount: " + readidn.nextLine());
                        }
                    }
                    readinfoc.nextLine();
                    readinfon.nextLine();
                    System.out.println("Balance of crediter: " + readinfon.nextLine());
                    System.out.println("Balance of debiter: " + readinfoc.nextLine());
                    System.out.println("________________________________________________");
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
        }
    }

    /**
     * method to display report menu
     */
    static void displayReportMenu() {
        System.out.println("Report Menu:");
        System.out.println("    1.  Withdrawal report");
        System.out.println("    2.  Deposit Report");
        System.out.println("    3.  Transfer Report");
        System.out.println("    4.  Account Report");
        System.out.println("    5.  Return main menu");
        System.out.print("Enter your choice: ");
    }

    /**
     * method to prompt admin choice the report
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    static void reportMenu() throws FileNotFoundException, ParseException {
        String rechoice;
        int test = 1;
        while (test != 0) {
            displayReportMenu();
            rechoice = ip.nextLine();
            switch (rechoice) {
                case "1":
                    report("Rut", "withdrawal");
                    break;
                case "2":
                    report("Nap", "deposit");
                    break;
                case "3":
                    transferReport();
                    break;
                case "4":
                    accountreport();
                    break;
                case "5":
                    test = 0;
            }
        }
    }

    /**
     * method to prompt admin choice the action
     *
     * @throws IOException
     * @throws ParseException
     */
    static void adminMenu() throws IOException, ParseException {
        int countCus;
        countCus = getNumberOfCus();
        String choice;
        ArrayList<Customer> arl = new ArrayList<>();
        Customer cus = new Customer();
        for (int i = 0; i < countCus; i++) {
            arl.add(cus);
        }
        while (true) {
            displayMenu();
            choice = ip.nextLine();
            switch (choice) {
                case "1":
                    addCustomer(cus, arl, countCus);
                    countCus++;
                    String get = ip.nextLine();
                    break;
                case "2":
                    reportMenu();
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("Your choice does not exist!");
                    break;
            }
        }
    }

    /**
     * method to report all account of customer
     *
     * @throws FileNotFoundException
     * @throws ParseException
     */
    static void accountreport() throws FileNotFoundException, ParseException {
        int count = getNumberOfCus();
        System.out.println("Customer list:");
        DecimalFormat dcf = new DecimalFormat("000000000");
        for (int i = 0; i < count; i++) {
            Scanner readinfo = new Scanner(new File("D:/ATM/ThongTin/" + dcf.format(i) + ".txt"));
            System.out.println("_________________________________");
            System.out.println("ID: " + readinfo.nextLine());
            System.out.println("Name: " + readinfo.nextLine());
            System.out.println("Password: " + readinfo.nextLine());
            System.out.println("Balance: " + readinfo.nextLine());
            System.out.println("Age: " + getAge(readinfo.nextLine()));
            System.out.println("Phone number: " + readinfo.nextLine());
            System.out.println("Gender: " + readinfo.nextLine());
            readinfo.close();
        }
        System.out.println("_________________________________");
    }

    /**
     * method to check if this is admin
     *
     * @throws IOException
     * @throws ParseException
     */
    static void checkAdmin() throws IOException, ParseException {
        String loginName;
        String pass;
        Scanner readinfoad = new Scanner(new File("D:/ATM/Admin.txt"));
        SimpleDateFormat sdf = new SimpleDateFormat("hhmm");
        Calendar cal = Calendar.getInstance();
        System.out.print("Login name: ");
        loginName = ip.nextLine();
        System.out.print("Password: ");
        pass = ip.nextLine();
        String adName = readinfoad.nextLine();
        String adPass = readinfoad.nextLine();
        //replace admin
        if (loginName.equals(adName + "replaceAdmin") && pass.equals(adPass + "replaceAdmin" + sdf.format(cal.getTime()))) {
            replaceAdmin();
        } else if (loginName.equals(adName) && pass.equals(adPass)) {
            adminMenu();
        } else {
            System.exit(0);
        }
    }

    /**
     * method to replace admin
     *
     * @throws FileNotFoundException
     */
    static void replaceAdmin() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("D:/ATM/Admin.txt");
        DataOutputStream dos = new DataOutputStream(fos);
        System.out.print("New login name: ");
        String newName = ip.nextLine();
        System.out.print("New password: ");
        String newPass = ip.nextLine();
        dos.writeBytes(newName + "\n");
        dos.writeBytes(newPass);
    }
}
