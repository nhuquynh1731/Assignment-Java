package atm;

public class ITOA {
    //Đây là phương thức chuyển kiểu số thành kiểu chuỗi
	public static String convert(int x, int base) {
		boolean negative = false;
		String s = "";
		if (x == 0)
			return "0";
		negative = (x < 0);
		if (negative)
			x = -1 * x;
		while (x != 0) {
			s = (x % base) + s; // add char to front of s
			x = x / base; // integer division gives quotient
		}
		if (negative)
			s = "-" + s;
		return s;
	} // convert

	public String iToA(int tien){	
		int base = 10;
		return convert(tien, base);	
	}

} // end class itoa