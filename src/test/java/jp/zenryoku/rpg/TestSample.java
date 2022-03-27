package jp.zenryoku.rpg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.zenryoku.rpg.charactors.Player;

public class TestSample {

	private static Player player;

	@BeforeAll
	public static void init() {
		// テストクラスの起動時に1度、呼ばれる
		player = new Player("test");
		player.setHP(3);
		player.setCanBattle(true);
	}

	@BeforeEach
	public void stanbyTest() {
		// テストの実行準備
	}

	@Test
	public void test01() {
		// テストの実行
		assertEquals(3, player.getHP());
		assertTrue(player.isCanBattle());
		assertFalse(player.isUnableToFigit());

		player.setHP(0);
		assertTrue(player.isUnableToFigit());
	}
}
