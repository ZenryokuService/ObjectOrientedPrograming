package jp.zenryoku.procon.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jp.zenryoku.procon.ProConRPGLogic;
import jp.zenryoku.procon.ServerTestHelper;
import jp.zenryoku.procon.client.ClientSuperCls;


public class ProConServerTest extends ServerTestHelper {
	/** ルート・サーバー */
	private static ProConRPGLogic server;
	/** テスト対象 */
	private static ProConServer target;

	private ClientSuperCls cls;
	/**
	 * プロコンサーバーを起動する
	 */
	@BeforeAll
	public static void init() {
		try {
			server = new ProConRPGLogic();
			server.exeServer();
			server.init("Test");
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

	/**
	 * 初回リクエストを受け取るテスト
	 */
	@Test
	public void testConstructor() {
		try {
			cls = new ClientSuperCls("localhost");
			cls.firstRequest(true);
		} catch (IOException e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}

	@Test
	public void testMapCommand() {
		try {
			cls = new ClientSuperCls("localhost");
			cls.firstRequest(false);
			cls.commandRequest("move");
			cls.commandRequest("bye");
		} catch (IOException e) {
			e.printStackTrace();
			fail("テスト失敗；" + e.getMessage());
		}
	}
}
