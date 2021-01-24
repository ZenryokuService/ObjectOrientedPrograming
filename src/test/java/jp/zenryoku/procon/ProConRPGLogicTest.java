package jp.zenryoku.procon;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//@Disabled
public class ProConRPGLogicTest extends ServerTestHelper {
	/** テスト対象 */
	private static ProConRPGLogic logic;

	@BeforeAll
	public static void initTest() {
		try {
			logic = new ProConRPGLogic();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			fail("サーバーエラー");
		}
	}
	@Test
	public void testMultiAccess() {
		System.out.println("Start");
		try {
			logic.exeServer();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			fail("サーバーサーバー起動失敗");
		}

		try {

			clientSocket(0, "LogicTest");
			clientSocket(1, "Logic123");
			clientSocket(2, "LogicAAA");
			clientSocket(3, "LogicLGOS");
		} catch (Exception e) {
			e.printStackTrace();
			fail("MainServerでエラー");
		}
	}

	@Test
	public void testTitleView() {
		//logic.init("TestFX");
	}

}
