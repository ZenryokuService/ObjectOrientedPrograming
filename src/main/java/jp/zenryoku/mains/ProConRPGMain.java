package jp.zenryoku.mains;

import jp.zenryoku.procon.ProConRPGEngine;
import jp.zenryoku.procon.ProConRPGLogic;
import jp.zenryoku.rpg.Games;

public class ProConRPGMain {
	public static void main(String[] args) {
		try {
			Games logic = new ProConRPGLogic();
			ProConRPGEngine engine = new ProConRPGEngine(logic);
			engine.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
