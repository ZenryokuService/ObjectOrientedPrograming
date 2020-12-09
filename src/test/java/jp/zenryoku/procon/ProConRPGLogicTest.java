package jp.zenryoku.procon;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
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
		clientSocket(0, "LogicTest");
		clientSocket(0, "Logic123");
		clientSocket(0, "LogicAAA");
		clientSocket(0, "LogicLGOS");
	}

	@Test
	public void testTitleView() {
		logic.init("TestFX");
	}

}
