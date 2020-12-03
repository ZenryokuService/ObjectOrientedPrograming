package jp.zenryoku.practice.train2.cls.familly;

import java.util.Scanner;

public class MainFamilly {
	public static void main(String[] args) {
		// 標準入力の受付
		Scanner scan = new Scanner(System.in);
		System.out.println("**** Game Start ****");
		// 親クラスのインスタンス
		Parent parent = new Parent();
		// HP
		parent.setHP(50);
		//戦闘可能フラグ
		parent.setCanBattle(true);
		//攻撃力
		parent.setAttack(20);
		//防御力
		parent.setDiffence(15);
		
		// 子クラス(あに)のインスタンス
		ChildAni ani = new ChildAni();
		// HP
		parent.setHP(40);
		//戦闘可能フラグ
		parent.setCanBattle(true);
		//攻撃力
		parent.setAttack(20);
		//防御力
		parent.setDiffence(15);
		// 子クラス(弟)のインスタンス
		ChildOtoto ototo = new ChildOtoto();
		// HP
		parent.setHP(30);
		//戦闘可能フラグ
		parent.setCanBattle(true);
		//攻撃力
		parent.setAttack(20);
		//防御力
		parent.setDiffence(15);
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
