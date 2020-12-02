package jp.zenryoku.practice.train2.cls.game.util;

public class CommandData {
	/** コマンド名(たたかうなど) */
	private String commandName;
	/** 各コマンドの効果(値) */
	private int commandValue;
	/** インデックス */
	private int index;
	
	/**
	 * @return the commandName
	 */
	public String getCommandName() {
		return commandName;
	}
	/**
	 * @param commandName the commandName to set
	 */
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	/**
	 * @return the commandValue
	 */
	public int getCommandValue() {
		return commandValue;
	}
	/**
	 * @param commandValue the commandValue to set
	 */
	public void setCommandValue(int commandValue) {
		this.commandValue = commandValue;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
}
