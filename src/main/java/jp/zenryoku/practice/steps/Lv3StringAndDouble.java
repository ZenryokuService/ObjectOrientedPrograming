package jp.zenryoku.practice.steps;

import java.util.Scanner;

public class Lv3StringAndDouble {
	/**
	 * 10信数を2信数に変換する処理。
	 * @param args
	 */
	public static void main(String[] args) {
		Lv3StringAndDouble cls = new Lv3StringAndDouble();
		Scanner scan = new Scanner(System.in);
		// 標準入力の受付
		String input = scan.next();

		// 入力チェック
		if (cls.checkIsNumber(input)) {
			System.out.println("2進数: " + cls.converBinary(input, input.contains(".")));
		}
	}

	/**
	 * 入力チェックを行う。整数2桁、少数3桁まで。
	 *
	 * @param input 入力された文字列
	 * @return true: 数字(小数点を含む) false; 数字でない
	 */
	public boolean checkIsNumber(String input) {
		boolean isNumber = false;
		if (input.matches("[0-9]{0,2}\\.[0-9]{0,3}|[0-9]{1,5}")) {
			isNumber = true;
		}
		return isNumber;
	}

	/**
	 * 2進数に変換して文字列として返却する。
	 *
	 * @param input 入力文字列
	 * @param isInteger 整数ならばTrue
	 * @return 2信数の文字列
	 */
	public String converBinary(String input, boolean isInteger) {
		System.out.println("整数");
		int num = 0;
		double shosu = 0.0;

		String result = null;
		if (isInteger) {
			num = Integer.parseInt(input);
			result = Integer.toBinaryString(num);
		} else {
			shosu = Double.parseDouble(input);
			result = convertBinalry(shosu);
		}

		return result;
	}

	private String convertBinalry(double shosu) {
		System.out.println("少数");
		return "";
	}
}
