package jp.zenryoku.practice.train2.cls.familly.charctor;

import jp.zenryoku.practice.train2.cls.game.util.CommandIF;

public class Monster extends Player{
	/** 話をするかどうか */
	private boolean isTalk;

	/**
	 * @return the isTalk
	 */
	public boolean isTalk() {
		return isTalk;
	}

	/**
	 * @param isTalk the isTalk to set
	 */
	public void setTalk(boolean isTalk) {
		this.isTalk = isTalk;
	}
}
