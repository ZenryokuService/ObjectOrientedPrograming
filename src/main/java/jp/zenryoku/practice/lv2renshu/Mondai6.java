package jp.zenryoku.practice.lv2renshu;

import java.util.Scanner;

public class Mondai6 {
	public static void main(String[] args) {
		// 標準入力を取得
		Scanner scan = new Scanner(System.in);
		String param = scan.nextLine();
		if ("Hello".equals(param)) {
			System.out.println("Hello World");
		} else if ("Test".equals(param)) {
			System.out.println("Testing now!");
		} else {
			System.out.println("Please input \"Hello\" or \"Test\"");
		}
	}
}
