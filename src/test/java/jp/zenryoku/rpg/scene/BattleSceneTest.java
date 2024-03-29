package jp.zenryoku.rpg.scene;

import static org.junit.jupiter.api.Assertions.*;

import jp.zenryoku.rpg.exception.RpgException;
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
		target = new BattleScene("0", "F");
	}

	/**
	 * 初期表示を行うテスト。
	 * 表示したい内容はすぐに変更する可能性が高いので、エラーがないことを確認する。
	 */
	@Test
	public void testInit() {
		try {
			target.initScene();
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * データを更新するテスト。
	 * @throws RpgException 想定外のエラー
	 */
	@Test
	public void testUpateData() throws RpgException {
		// マップを作成する必要があるため、初期表示を行う
//		target.initScene();
//		assertTrue(target.updateData("1"));
//		assertTrue(target.updateData("2"));
//		assertTrue(target.updateData("3"));
//		assertFalse(target.updateData("4"));
	}

	/**
	 * 画面更新のテスト。
	 * @throws RpgException 想定外のエラー
	 */
	@Test
	public void testRender() throws RpgException {
//		target.initScene();
//		assertTrue(target.updateData("1"));
//		target.render();
	}
}
