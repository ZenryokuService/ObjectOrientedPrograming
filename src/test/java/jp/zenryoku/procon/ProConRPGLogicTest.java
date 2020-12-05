package jp.zenryoku.procon;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class ProConRPGLogicTest extends ServerTestHelper {
	/** テスト対象 */
	private ProConRPGLogic logic;

	@Test
	public void testMultiAccess() {

		try {
			logic = new ProConRPGLogic();

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			fail("サーバーエラー");
		}
		clientSocket(0, "LogicTest");
		clientSocket(0, "Logic123");
		clientSocket(0, "LogicAAA");
		clientSocket(0, "LogicLGOS");
	}
}
