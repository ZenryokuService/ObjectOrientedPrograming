package jp.zenryoku.practice.lv2renshu;

import java.util.Scanner;

public class Mondai7 {
	public static void main(String[] args) {
		// ループ文の中で「new」をするのはメモリを大量に消費する！
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("文字列を入力してください");
			String input = scan.nextLine();
			if ("bye".equals(input)) {
				System.out.println("Good bye!");
				break;
			} else {
				System.out.println(input);
			}
		}
	}
}
