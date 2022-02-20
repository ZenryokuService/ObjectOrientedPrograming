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

	/**
	 * SQLの実行メソッド。
	 * @param sql 実行するSQL文
	 */
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

	/**
	 * データの取得メソッド。
	 * @param id 取得する対象のID
	 */
	public void select(int id) {
		// DBからデータを取得する処理
		String sql = "select * from " + this.tableName + " where " + this.tableName + ".id";
		execute(sql);
	}

	/**
	 * データの登録メソッド
	 * @param id 登録するデータのID(アートインクリメントの場合は不要)
	 */
	public void insert(int id) {
		// DBからデータを登録する処理
	}

	/**
	 * データの更新メソッド
	 * @param id 更新するデータのID
	 */
	public void update(int id) {
		// DBからデータを更新する処理
	}

	/**
	 * データの削除メソッド
	 * @param id 削除するデータのID
	 */
	public void delete(int id) {
		// DBからデータを削除する処理
	}

}
