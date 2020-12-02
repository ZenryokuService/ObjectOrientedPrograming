package jp.zenryoku.practice.sample.jdbc;

/** 子クラス2: test1テーブル用クラス */
public class ChildTable2 extends Parent2{
	/** コンストラクタ */
	public ChildTable2() {
		super();
		super.tableName = "test2Table";
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
