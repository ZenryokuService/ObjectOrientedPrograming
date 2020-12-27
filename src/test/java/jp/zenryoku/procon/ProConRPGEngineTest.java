package jp.zenryoku.procon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProConRPGEngineTest {
	/** テスト対象 */
	private static ProConRPGEngine target;

	@BeforeAll
	public static void init() throws Exception {
		ProConRPGLogic logic = new ProConRPGLogic();
		target = new ProConRPGEngine(logic);
		target.start();
	}

	@Test
	public void test01() {

	}
}
