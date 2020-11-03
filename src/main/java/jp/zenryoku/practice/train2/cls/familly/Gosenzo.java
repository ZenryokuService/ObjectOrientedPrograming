package jp.zenryoku.practice.train2.cls.familly;

public abstract class Gosenzo {
	/** 秘伝 */
	protected static String hiden;

	protected Gosenzo() {
		hiden = "秘伝のタレ";
	}

	/**
	 *  血系限界
	 * @return 何かしらの文字列
	 */
	protected abstract String kekkeiGenkai();
}
