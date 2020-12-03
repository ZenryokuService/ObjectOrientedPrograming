package jp.zenryoku.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProgServerTest {
	/** テスト対象 */
	private ProgServer target;

	@Test
	public void testConstractor() {
		try {
			target = new ProgServer();
		} catch(Exception e) {
			fail();
		}
	}

	@Test
	public void testRun() {
		try {
			target = new ProgServer();
		} catch(Exception e) {
			fail();
		}
	}
}
