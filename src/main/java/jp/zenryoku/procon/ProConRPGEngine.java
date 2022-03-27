package jp.zenryoku.procon;

import jp.zenryoku.rpg.Games;

/**
 * プログラミングコンテストゲームのメインスレッド。
 *
 * @author 実装者の名前
 */
public class ProConRPGEngine extends Thread {
	/** GameLogicクラス */
	private Games logic;

	public ProConRPGEngine(Games logic) {
		this.logic = logic;
	}

	public void run() {
		// JavaFX起動、タイトル画面を開く
		logic.init("プロコンサーバーRPG");
	}

	public static void main(String[] args) throws Exception {
		ProConRPGLogic logic = new ProConRPGLogic();
		ProConRPGEngine target = new ProConRPGEngine(logic);
		target.start();

	}
}
