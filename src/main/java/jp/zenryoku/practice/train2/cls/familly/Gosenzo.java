package jp.zenryoku.practice.train2.cls.familly;

import jp.zenryoku.practice.train2.cls.familly.charctor.Player;

public abstract class Gosenzo extends Player {
	/** 秘伝 */
	protected static String hiden;

	protected Gosenzo() {
		super();
		hiden = "秘伝のタレ";
	}

	/**
	 *  血系限界
	 * @return 何かしらの文字列
	 */
	protected abstract String kekkeiGenkai();

}
