package jp.zenryoku.practice.train2.cls.game;

import java.util.Random;
import java.util.Scanner;

import jp.zenryoku.practice.train2.cls.familly.ChildAni;
import jp.zenryoku.practice.train2.cls.familly.cls.monster.Slime;

public class BattleMain {
	public static void main(String[] args) {
		System.out.println("*** あに vs スライム ***");
		ChildAni ani = new ChildAni();
		Slime slime = new Slime();
		Random rd = new Random();
		Scanner scan = new Scanner(System.in);
		while (true) {
			// 行動選択
			System.out.println("鼓動一覧: ");
			ani.getCommandList().forEach((data) -> {
				System.out.println(data.getIndex() + ": " + data.getCommandName());
			});
			System.out.println("bye: 終了");
			System.out.println("****************");
			System.out.print("鼓動選択: ");

			String input = scan.nextLine();
			System.out.println(input);
			
			if ("bye".equals(input)) {
				break;
			}
			int val = ani.exeCommand(input);
			int res = val - slime.getDiffence();
			System.out.println(slime.getName() + "は" + res + "のダメージを受けた");
			ani.setHP(ani.getHP() - res);

			if (slime.getHP() < 1) {
				System.out.println(slime.getName() + "は倒れた。。。");
				break;
			}
			// スライムのターン
			int index = rd.nextInt(1);
			System.out.println("*** Testing " + index + " ***");
			int val2 = slime.exeCommand(String.valueOf(index));
			System.out.println(ani.getName() + "は" + (val2 - ani.getDiffence()) + "のダメージを受けた");

			int res2 = ani.getHP() - val2;
			ani.setHP(res2);
			if (ani.getHP() < 1) {
				System.out.println(ani.getName() + "は倒れた。。。");
				break;
			}
		}
	}
}
