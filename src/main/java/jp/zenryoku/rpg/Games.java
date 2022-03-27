package jp.zenryoku.rpg;

import jp.zenryoku.rpg.constants.RpgConst;

/**
 * Gameクラスに実装するインターフェース。
 *
 * @author 実装者の名前
 */
public interface Games {
	/**
	 * 初期表示処理
	 * @param title ゲームのタイトル
	 */
	public void init(String title);

	/**
	 * 入力受付処理
	 * @return 入力した文字列
	 */
	public String acceptInput();

	/**
	 * データの更新処理
	 * @param input 入力値
	 * @return 成功したか
	 */
	public boolean updateData(String input);

	/**
	 * 画面の更新
	 * @return 成功したかどうか
	 */
	public boolean render();

	/**
	 * 終了ステータス
	 * @return RpgConstで定義
	 */
	public RpgConst getEndStatus();
}
