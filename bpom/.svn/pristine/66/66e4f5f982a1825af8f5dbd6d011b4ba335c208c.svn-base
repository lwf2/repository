package com.aspire.bpom.util;

public class StringUtil {

	/**
	 * 当参数为null时返回空字符串，否则返回原字符串去掉前后空格
	 * 
	 * @param str
	 * @return
	 */
	public static String nullToBlank(String str) {
		return str == null ? "" : str.trim();
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().equals("");
	}

	/**
	 * 分为单位的int数值，转换为元为单位的字符串
	 * 
	 * @param amount
	 * @return
	 */
	public static String centToYuan(int amount) {
		String amt = String.valueOf(amount);
		String orderAmount = "";
		switch (amt.length()) {
		case 0:
			orderAmount = "0.00";
			break;
		case 1:
			orderAmount = "0.0" + amt;
			break;
		case 2:
			orderAmount = "0." + amt;
			break;
		default:
			orderAmount = amt.substring(0, amt.length() - 2) + "." + amt.substring(amt.length() - 2);
			break;
		}
		return orderAmount;
	}

	public static int yuanToCent(String amount) {
		double d = Double.valueOf(amount);
		return (int) Math.round((d * 100));
	}
	
	public static void main(String[] args) {
		
		/*String a = "4.896";
		String b = "0.116";
		a = a.replace(".", "");
		int aa = Integer.parseInt(a);
		System.out.println(aa);
		int bb = Integer.parseInt(b.replace(".", ""));
		System.out.println(bb);
		System.out.println(aa+bb);*/
		
		System.out.println((int)(Double.valueOf("4.89") * 100));
		long du = (long) (Double.valueOf("4.89") * 100);
		System.out.println(du);
		System.out.println(Math.round(Double.valueOf("4.89") * 100));
		System.out.println(yuanToCent("4.89")+yuanToCent("0.11"));
	}

	public static String lpad(String str, char padding, int length) throws IndexOutOfBoundsException {
		if (str.length() > length) {
			throw new IndexOutOfBoundsException();
		}
		int size = length - str.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(padding);
		}
		sb.append(str);
		return sb.toString();
	}

}
