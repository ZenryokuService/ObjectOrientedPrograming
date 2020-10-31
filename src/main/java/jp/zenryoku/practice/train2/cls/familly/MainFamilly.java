package jp.zenryoku.practice.train2.cls.familly;

import java.util.Scanner;

public class MainFamilly {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("**** Game Start ****");
		Parent parent = new Parent();
		ChildAni ani = new ChildAni();
		ChildOtoto ototo = new ChildOtoto();
		while (true) {
			String input = scan.nextLine();
			if ("parent".equals(input)) {
				parent.say();
			} else if ("ani".equals(input)) {
				ani.say();
			} else if ("ototo".equals(input)) {
				ototo.say();
			} else {
				System.out.println("処理を終了します。");
				break;
			}
		}
	}
}
