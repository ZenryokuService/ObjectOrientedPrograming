package jp.zenryoku.practice.train2.cls.familly.cls.monster;

import jp.zenryoku.practice.train2.cls.familly.charctor.Monster;
import jp.zenryoku.practice.train2.cls.game.util.CommandIF;

/**
 * スライムのクラス
 */
public class Slime extends Monster implements CommandIF {
	public Slime() {
		this.setName("スライム");
		this.setCanBattle(true);
		this.setHP(10);
		this.setMP(5);
		// 攻撃力
		this.setAttack(5);
		// 防御力
		this.setDiffence(5);
	}

	/* (non-Javadoc)
	 * @see jp.zenryoku.practice.train2.cls.game.util.CommandIF#exeCommand(java.lang.String)
	 */
	@Override
	public int exeCommand(String index) {
		int val = 0;
		String name = getName();
		if ("0".equals(index)) {
			System.out.println(name + "の攻撃");
			val = 3;
		} else if ("1".equals(index)) {
			System.out.println(name + "のスラスラ");
			val = 5;
		}
		return val;
	}
}
