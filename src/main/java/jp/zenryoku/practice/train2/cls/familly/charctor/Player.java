package jp.zenryoku.practice.train2.cls.familly.charctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.zenryoku.practice.train2.cls.game.util.CommandData;

public class Player {
	/** 名前 */
	private String name;
	/** レベル */
	private int level;
	/** 生命力 */
	private int HP;
	/** 特殊能力(技能)の使用時に消費 */
	private int MP;
	/** 攻撃力 */
	private int attack;
	/** 防御力 */
	private int diffence;
	/** 戦闘可能フラグ */
	private boolean canBattle;
	/** 行動のリスト */
	private List<CommandData> commandList;

	/**
	 * コンストラクタ。
	 */
	public Player() {
		this.name = "桃太郎";
		// レベル1の設定
		setLevel(1);
		setHP(20);
		setMP(10);
		// コマンドリスト作成
		commandList = new ArrayList<CommandData>();
	}

	/**
	 * コンストラクタ。
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		// レベル1の設定
		setLevel(1);
		setHP(20);
		setMP(10);
	}

	protected void createCommandList() {
		if (commandList == null) {
			commandList = new ArrayList<CommandData>();
			commandList.add(createCommandData("たたかう", 10));
			commandList.add(createCommandData("ぼうぎょ", 5));
		}
	}

	protected void addCommand(CommandData data) {
		if (commandList == null) {
			createCommandList();
		}
		commandList.add(data);
	}

	protected CommandData createCommandData(String commandName, Integer value) {
		if (commandList == null) {
			createCommandList();
		}
		CommandData command = new CommandData();
		command.setIndex(commandList.size());
		command.setCommandName(commandName);
		command.setCommandValue(value);
		
		return command;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the hP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * @param hP the hP to set
	 */
	public void setHP(int hP) {
		HP = hP;
	}

	/**
	 * @return the mP
	 */
	public int getMP() {
		return MP;
	}

	/**
	 * @param mP the mP to set
	 */
	public void setMP(int mP) {
		MP = mP;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the diffence
	 */
	public int getDiffence() {
		return diffence;
	}

	/**
	 * @param diffence the diffence to set
	 */
	public void setDiffence(int diffence) {
		this.diffence = diffence;
	}

	/**
	 * @return the canBattle
	 */
	public boolean isCanBattle() {
		return canBattle;
	}

	/**
	 * @param canBattle the canBattle to set
	 */
	public void setCanBattle(boolean canBattle) {
		this.canBattle = canBattle;
	}

	/**
	 * @return the commandList
	 */
	public List<CommandData> getCommandList() {
		return commandList;
	}

	/**
	 * @param commandList the commandList to set
	 */
	public void setCommandList(List<CommandData> commandList) {
		this.commandList = commandList;
	}
	
}