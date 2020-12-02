package jp.zenryoku.practice.sample.jdbc;

/** 子クラス１: test1テーブル用クラス */
public class ChildTable1 extends Parent2{
	/** コンストラクタ */
	public ChildTable1() {
		super();
		super.tableName = "test1Table";
	}

	/** SQLの実行 */
	public void execute() {
		// 取得
		select(1);
		// 登録
		insert(2);
		// 更新
		update(3);
		// 削除
		delete(4);
	}
}
