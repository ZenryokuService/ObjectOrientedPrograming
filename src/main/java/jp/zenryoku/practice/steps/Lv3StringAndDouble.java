package jp.zenryoku.practice.steps;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	public String convertDecimal(String target,boolean isInteger) {
		String res = null;
		if (isInteger) {
			res = convertDecimalSeisu(target);
		} else {
			res = convertDecimalShosu(target);
		}
		return res;
	}
	/**
	 * 2進数に変換して文字列として返却する。
	 *
	 * @param input 入力文字列
	 * @param isInteger 整数ならばTrue
	 * @return 2信数の文字列
	 */
	public String converBinary(String input, boolean isInteger) {
		int num = 0;
		double shosu = 0.0;

		String result = null;
		if (isInteger) {
			System.out.println("整数");
			num = Integer.parseInt(input);
			result = Integer.toBinaryString(num);
		} else {
			result = convertBinalry(input);
		}

		return result;
	}

	private String convertBinalry(String shosu) {
		System.out.println("少数: " + shosu);
		String[] nums = shosu.split("\\.");
		String seisu = nums[0];
		String sho = nums[1];
		System.out.println("少数: " + shosu);
		String res1 = calcSeisu(seisu);
		String res2 = calcShosu(sho);
		return res1 + "." + res2;
	}


	private String calcSeisu(String seisu) {
		int sisu = 0;
		int seisuGokei = 0;
		char[] ch = seisu.toCharArray();
		for (int i = ch.length -1; i >= 0; i--) {
			if (ch[i] == '1') {
				double num = Math.pow(2, sisu);
				seisuGokei += num;
			}
			sisu++;
		}
		return String.valueOf(seisuGokei);
	}

	private String calcShosu(String shosu) {
		int sisu = 0;
		int shosuGokei = 0;

		char[] ch = shosu.toCharArray();
		for (int i = ch.length -1; i >= 0; i--) {
			System.out.print("入力: " + ch[i]);
			int a = Integer.parseInt(String.valueOf(ch[i]));
			int num = (int)Math.pow(a, -1 * sisu);
			System.out.println(" => num: " + num);
			shosuGokei += num;
			sisu++;
		}
		return  String.valueOf(shosuGokei);
	}

	private String convertDecimalSeisu(String target) {
		int keta = target.length();
		int gokei = 0;
		for (int i = target.length()-1; i >= 0; i--) {
			char c = target.charAt(i);
			if ('1' == c) {
				int tmp = (int) Math.pow(2, keta - 1 - i);
				gokei += tmp;
			}
		}
		return String.valueOf(gokei);

	}

	private String convertDecimalShosu(String target) {
		BigDecimal retVal = new BigDecimal(0);
		int dotIndex = target.indexOf('.');
		for (int i = 0; i < dotIndex; i++) {
			char targetChar = target.charAt(i);
			if (targetChar == '0') {
				continue;
			}
			retVal = retVal.add(new BigDecimal(2).pow(dotIndex - 1 - i));
		}
		for (int i = dotIndex + 1; i < target.length(); i++) {
			char targetChar = target.charAt(i);
			if (targetChar == '0') {
				continue;
			}
			retVal = retVal.add(BigDecimal.ONE.divide(new BigDecimal(2).pow(i
					- dotIndex)));
		}
		retVal = retVal.setScale(3, RoundingMode.HALF_UP);

		return retVal.toString();
	}
}
