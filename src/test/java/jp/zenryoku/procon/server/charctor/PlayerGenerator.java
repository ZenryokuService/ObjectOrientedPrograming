package jp.zenryoku.procon.server.charctor;

import jp.zenryoku.procon.client.ClientData;
import jp.zenryoku.rpg.charactors.Player;

/**
 * プレーヤーを生成する。
 *
 * @author 実装者の名前
 *
 */
public class PlayerGenerator {
	public static Player generatePlayer(ClientData data) {
		// 名前をセット
		Player player = new Player(data.getName());

		return player;
	}
}
