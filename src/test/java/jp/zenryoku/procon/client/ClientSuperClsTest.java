package jp.zenryoku.procon.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jp.zenryoku.procon.ProConRPGLogic;
import jp.zenryoku.procon.ServerTestHelper;

public class ClientSuperClsTest extends ServerTestHelper {
	/** テスト対象 */
	private ClientSuperCls target;

	@Test
	public void test() {
		try {
			ProConRPGLogic server = new ProConRPGLogic();
			server.exeServer();
			target = new ClientSuperCls("localhost");
			target.exevuteRpg();
		} catch (Exception e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}
}
