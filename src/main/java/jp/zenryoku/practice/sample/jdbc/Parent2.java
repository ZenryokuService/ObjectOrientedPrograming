package jp.zenryoku.practice.sample.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** 親クラス */
public abstract class Parent2 {
	/** テーブル名 */
	protected String tableName;
	/** DBコネクション */
	protected Connection con;

	public Parent2() {
		try {
			// DBコネクションの取得
			con = DriverManager.getConnection("DBへのURL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** SQLの実行メソッド */
	protected void execute(String sql) {
		try {
			Statement stm = con.createStatement();
			// SQLの実行
			stm.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** データの取得メソッド */
	public void select(int id) {
		// DBからデータを取得する処理
		String sql = "select * from " + this.tableName + " where " + this.tableName + ".id";
		execute(sql);
	}

	/** データの登録メソッド */
	public void insert(int id) {
		// DBからデータを登録する処理
	}

	/** データの更新メソッド */
	public void update(int id) {
		// DBからデータを更新する処理
	}

	/** データの削除メソッド */
	public void delete(int id) {
		// DBからデータを削除する処理
	}

}
