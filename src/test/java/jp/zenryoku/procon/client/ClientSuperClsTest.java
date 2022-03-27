package jp.zenryoku.procon.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jp.zenryoku.procon.ProConRPGLogic;
import jp.zenryoku.procon.ServerTestHelper;
import jp.zenryoku.procon.server.ProConServerConst;

public class ClientSuperClsTest extends ServerTestHelper {
	/** プロコンサーバー */
	private static ProConRPGLogic server;
	/** テスト対象 */
	private static ClientSuperCls target;

	/**
	 * プロコンサーバーを起動する
	 */
	@BeforeAll
	public static void init() {
		try {
			server = new ProConRPGLogic();
			server.exeServer();
			target = new ClientSuperCls("localhost", ProConServerConst.CLIENT_1_PORT);
		} catch (Exception e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}

	/**
	 * プロコンサーバーの終了処理
	 */
	@AfterAll
	public static void terminated() {
		try {
			server.finalize();
			server = null;
		} catch (Exception e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}
	/** 初回リクエストのテスト */
	@Test
	public void testFirstRequest() {
		try {
			target.firstRequest(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}

	/**
	 * Map画面でのコマンド送受信テスト
	 */
	@Test
	public void testCommandRequest() {
		String res = null;
		try {
			target.firstRequest(false);
			target.commandRequest("1234");
		} catch (Exception e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}
}
