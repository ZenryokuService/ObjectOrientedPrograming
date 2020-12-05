package jp.zenryoku.rpg.scene;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@Disabled
@TestInstance(Lifecycle.PER_CLASS)
public class BattleSceneTest {
	/** 戦闘シーン */
	private BattleScene target;

	@BeforeAll
	public void init() {
		target = new BattleScene();
	}

	/**
	 * 初期表示を行うテスト。
	 * 表示したい内容はすぐに変更する可能性が高いので、エラーがないことを確認する。
	 */
	@Test
	public void testInit() {
		try {
			target.init("title");
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * データを更新するテスト。
	 */
	@Test
	public void testUpateData() {
		// マップを作成する必要があるため、初期表示を行う
		target.init("title");
		assertTrue(target.updateData("1"));
		assertTrue(target.updateData("2"));
		assertTrue(target.updateData("3"));
		assertFalse(target.updateData("4"));
	}

	/**
	 * 画面更新のテスト。
	 */
	@Test
	public void testRender() {
		target.init("trial");
		assertTrue(target.updateData("1"));
		target.render();
	}
}
