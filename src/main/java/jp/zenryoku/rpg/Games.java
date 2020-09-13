package jp.zenryoku.rpg;

/**
 * Gameクラスに実装するインターフェース。
 *
 * @author 実装者の名前
 */
public interface Games {
	/** 初期表示処理 */
	public void init();
	/** 入力受付処理 */
	public String acceptInput();
	/** データの更新処理 */
	public boolean updateData(String input);
	/** 画面の更新 */
	public boolean render();
}
