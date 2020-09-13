package jp.zenryoku.rpg.charactors;

public class NonPlayer extends Player {
	/** 話すときに表示するメッセージ */
	private String message;
	/** パーティに参加するフラグ */
	private boolean beParty;
	/** 話すかどうかのフラグ */
	private boolean isTalk;

	/**
	 * コンストラクタ。
	 * @param name
	 */
	public NonPlayer(String name) {
		super(name);
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message セットする message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return beParty
	 */
	public boolean isBeParty() {
		return beParty;
	}
	/**
	 * @param beParty セットする beParty
	 */
	public void setBeParty(boolean beParty) {
		this.beParty = beParty;
	}
	/**
	 * @return isTalk
	 */
	public boolean isTalk() {
		return isTalk;
	}
	/**
	 * @param isTalk セットする isTalk
	 */
	public void setTalk(boolean isTalk) {
		this.isTalk = isTalk;
	}


}
