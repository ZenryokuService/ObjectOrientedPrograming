package jp.zenryoku.rpg.charactors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 行動を表すアノテーション
 * 。
 * @author 実装者の名前
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	/**
	 * 表示順
	 * @return 表示する番号
	 */
	public int index();

	/**
	 * 実行選択肢
	 * @return コマンド名
	 */
	public String commandName();
}
