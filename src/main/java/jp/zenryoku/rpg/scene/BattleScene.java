package jp.zenryoku.rpg.scene;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgStm;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;
import lombok.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class BattleScene extends StoryScene {
	/** 定数：攻撃フラグ */
	private static final String ATTACK = "1";
	/** 定数：防御フラグ */
	private static final String DIFFENCE = "2";
	/** 定数：にげるフラグ */
	private static final String ESCAPE = "3";
	/** コンソール出力 */
	private ConsoleUtils console;
	/** 入力受付部品 */
	private Scanner scan;
	/** Player */
	private PlayerCharactor player;
	/** モンスター */
	private Monster monster;
	/** 戦闘終了フラグ */
	private boolean isBattleFinish;
	/** モンスター生成乱数範囲(開始) */
	private int startMonster;
	/** モンスター生成乱数範囲(終了) */
	private int endMonster;


	/**
	 * コンストラクタ。
	 * @param sceneIdex 対象のシーンインデックス
	 * @param sceneType シーンタイプ
	 */
	public BattleScene(String sceneIdex, String sceneType) {
		super(sceneIdex, sceneType);
		// コンソール出力部品
		console = ConsoleUtils.getInstance();
		// 入力受付部品
		scan = new Scanner(System.in);
		// 戦闘囚虜いうフラグの初期化
		isBattleFinish = false;
		// モンスター番号の初期化
		startMonster = -1;
		endMonster = -1;
	}

	/**
	 * コンストラクタ。
	 * @param sceneIdex 対象のシーンインデックス
	 * @param sceneType シーンタイプ
	 */
	public BattleScene(String sceneIdex, String sceneType, int monsterNo) {
		super(sceneIdex, sceneType);
		// コンソール出力部品
		console = ConsoleUtils.getInstance();
		// 入力受付部品
		scan = new Scanner(System.in);
		// 戦闘囚虜いうフラグの初期化
		isBattleFinish = false;
		// モンスターセット
		monster = RpgConfig.getInstance().getMonsterList().get(monsterNo);
	}


	/**
	 * 入力受付処理。
	 * ※JavaAPIを呼び出すだけなので、テスト不要。
	 * @return 標準入力
	 */
	public String acceptInput() {
		// 入力受付を返却する(一行分)
		return scan.nextLine();
	}

	/**
	 * テキストのロードを一時停止します。
	 * Enterキーを入力することで次の処理が流れます。
	 */
	public void stopTextLoad() {
		// 行動結果の表示後何かの入力があるまで待機
		System.out.println("<Enter>");
		acceptInput();
		console.clearConsole();
	}
	/**
	 * 更新したデータを表示する。
	 * 戦闘終了時にはTRUEを返却する。
	 * @return 戦闘が終了してればTURE, そうでなければFLASE
	 */
	public boolean render() {
		// コンソールのクリア
		console.clearConsole();
		if(isBattleFinish == false) {
			// バトルステータスを表示
			console.printBattleStatus(player);
			// コマンドの入力を促す
			console.printMessage("こうどうを、せんたくしてください。");
			// コマンドの一覧を表示する
			//commandList = console.printCommandList(player);
		}
		return isBattleFinish;
	}

	public RpgConst getEndStatus() {
		return status;
	}
	/**
	 * 選択した行動を行う処理。
	 * @param player プレーヤー
	 * @param monster モンスター
	 * @param input 入力した値
	 */
	private void action(Player player, Monster monster, String input) {
		// 1. プレーヤーの攻撃
		console.printMessage(player.getName() + "のこうげき！");
		switch(input) {
		case ATTACK:
			attackAction(player, monster);
			break;
		case DIFFENCE:
			diffenceAction(player, monster);
			break;
		case ESCAPE:
			// TODO-[逃げるは未実装]
			System.out.print("にげるのは、未実装なので、何もしない処理" + SEP);
			break;
		default:
			System.out.println("想定外の入力です。: " + input);
		}
		// 改行を入れる
		System.out.println(SEP);
	}

	/**
	 * 攻撃時のアクション。
	 * @param player プレーヤー
	 * @param monster モンスター
	 */
	private void attackAction(Player player, Monster monster) {
		// getDiffence()は通常の防御力
		int attack = player.attack() - monster.getDiffence();
		if (attack <= 0) {
			console.printMessage("ダメージが、あたえられなかった。");
		} else {
			console.printMessage(monster.getName() + "に" + attack + "のダメージ。");
		}
		// 攻撃の結果を計算する
		monster.setHP(monster.getHP() - attack);

		// モンスターを倒したか判定する
		if (player.isUnableToFigit()) {
			System.out.println( player.getName() + "は、せんとうふのうになった。");
			isBattleFinish = true;
		} else if (monster.isUnableToFigit()) {
			System.out.println(monster.getName() + "をたおした！");
			isBattleFinish = true;
		}
		if (isBattleFinish) {
			return;
		}
		// 2.モンスターの攻撃
		console.printMessage(monster.getName() + "のこうげき");
		int damage = monster.attack() - player.getDiffence();
		if (damage <= 0) {
			console.printMessage("ダメージを、うけなかった。");
		} else  {
			console.printMessage(player.getName() + "は" + damage + "のダメージをうけた。");
			player.setHP(player.getHP() - damage);
		}
	}

	/**
	 * 防御時のアクション。防御力を1.5倍にする。
	 * @param player プレーヤー
	 * @param monster モンスター
	 */
	private void diffenceAction(Player player, Monster monster) {
		console.printMessage(player.getName() +  "は、ぼうぎょのかまえをとった。");
		// 2.モンスターの攻撃
		console.printMessage(monster.getName() + "のこうげき");
		// diffence()は防御コマンド実行
		double defPower = player.diffence() * 1.5;
		// 小数点は切り捨てするので単純にint型にキャストする
		int damage = monster.attack() - (int) defPower;
		if (damage <= 0) {
			console.printMessage("ダメージをうけなかった。");
		} else {
			console.printMessage(player.getName() + "は" + damage + "のダメージをうけた。");
			player.setHP(player.getHP() - damage);
		}
		// 戦闘不能
		if (player.getHP() <= 0) {
			player.setCanBattle(false);
		}
	}

	/**
	 * コマンドの有効範囲の正規表現を取得する。
	 * コマンドは数字で、１～始まる
	 * @return コマンドの有効範囲(ex: [0-3])
	 */
//	private String commandRegrex() {
//		int size = commandList.size();
//		return "[1-" + String.valueOf(size) + "]";
//
//	}
	/**
	 * 主要武器を返却する。
	 *
	 * @return MainWepon
	 */
	private MainWepon createMainWepon() {
		RpgItem item = new RpgItem();
		item.setName("ひのきのぼう");
		item.setItemValueKigo("WEV+1");
		MainWepon wepon = null;
		try {
			wepon = new MainWepon(item);
		} catch(RpgException e) {
			return null;
		}
		return wepon;
	}

	/**
	 * 防具を返却する。
	 * @return Armor
	 */
	private Armor createArmor() {
		Armor armor = new Armor("ぬののふく");
		armor.setDiffence(4);

		return armor;
	}

	private void initBattle() {
		// プレーヤーの取得
		player = PlayerParty.getInstance().getPlayer();

		// モンスターがランダム取得の場合
		if (startMonster != -1 && endMonster != -1) {
			// 通常の定義は配列としてみていないので、0から数えるためにー１する。
			int monsterId = CalcUtils.getInstance().generateRandom(startMonster, endMonster);
			List<Monster> monsterList = RpgConfig.getInstance().getMonsterList();
			monster = monsterList.get(monsterId);
		}
		// 初期表示: 「XXXXが現れた」
		if (isDebug) System.out.println(monster);
		console.printMessage(monster.getName() + "があらわれた！" + SEP);
		if (monster.isTalk()) {
			// セリフを表示
			console.printMessage(monster.getMessage() + SEP);
		}
	}

	/**
	 * バトルシーンを起動する
	 */
	public boolean playScene() throws Exception {
		super.playScene();
		boolean isFinish = false;
		initScene();
		initBattle();
		List<RpgCommand> list = player.getJob().getCommandList();
		while(true) {
            // バトルステータスを表示
            console.printBattleStatus(player);
            // コマンドの入力を促す
            // コマンドの一覧を表示する
            List<RpgCommand> commandList = console.printCommandList(player);
			// プレーヤーターン、コマンド取得
			String selectCommand = console.acceptInput("こうどうを、せんたくしてください。", "[1-" + (commandList.size()) + "]");
			int select = Integer.parseInt(selectCommand) - 1;
			RpgCommand pCommand = commandList.get(select);

			if (isDebug) System.out.println("Command; " + pCommand.getName());

			// モンスターコマンドの取得
			if (isDebug) System.out.println("monster.job: " + monster.getType());
			List<RpgCommand> cmdList = monster.getType().getCommandList();
			int monRnd = CalcUtils.getInstance().generateRandom(0,1);
			RpgCommand mCommand = cmdList.get(monRnd);

			// 攻撃順序の判定
			int pAgi = player.getStatusMap().get("AGI").getValue();
			int mAgi = monster.getStatusMap().get("AGI").getValue();
			boolean isPlayerFirst = false;
			if (pAgi >= mAgi) {
				isPlayerFirst = true;
			}

			// 戦闘開始
			try {
				if (isPlayerFirst) {
					// プレーヤーの攻撃
					if (printAttackAndCalc(player, monster, pCommand)) {
						break;
					}
					// モンスターの攻撃
					if (printAttackAndCalc(monster, player, mCommand)) {
						break;
					}
				} else {
					// モンスターの攻撃
					if (printAttackAndCalc(monster, player, mCommand)) {
						break;
					}
					// プレーヤーの攻撃
					if (printAttackAndCalc(player, monster, pCommand)) {
						break;
					}
				}
			} catch (RpgException e) {
				// 選択肢なしの場合はやり直し
				console.printMessage(e.getMessage());
				continue;
			}

		}
		// 戦闘の決着がつくとisFinishはtrueになっているのでfalseにする
		return isFinish == false;
	}

	/**
	 * プレーヤー、モンスターから相手絵のダメージを表示。
	 * @param pla プレーヤー or モンスター
	 * @param mon プレーヤー or モンスター
	 * @param cmd コマンドオブジェクト
	 * @return ture: 第二引数のオブジェクト#HPが0以下 false: まだ生きている。
	 */
	private boolean printAttackAndCalc(PlayerCharactor pla, PlayerCharactor mon, RpgCommand cmd) throws RpgException {
		// 選択したコマンドが、子階層を持っている場合STMをセットする
		RpgStm stm = null;
		if (cmd.isChildDir()) {
			List<RpgStm> stmList = cmd.getChildList();
			int count = 1;
			for (RpgStm s : stmList) {
				if (s.getJobId().equals(pla.getJob().getJobId()) && pla.getLevel() >= s.getLeanLv()) {
					System.out.println(count + ". " + s.getName());
					count++;
				}
			}
			if (count == 1) {
				// 処理をリセットするためにthrowしている。良い子はマネしてはいけない。
				throw new RpgException(SelectConst.NO_SELECTION);
			}
			int select = -1;
			if (pla instanceof Monster) {
				select = CalcUtils.getInstance().generateRandom(1, count);
			} else {
				String tmp = console.acceptInput(SelectConst.STM_SELECT, "[1-" + count + "]");
				select = Integer.parseInt(tmp);
			}
			stm = stmList.get(select - 1);
		}
		// Command、STMからFormula(計算式)を取得
		RpgFormula pFormula = null;
		String mes = null;
		String orient = null;
		if (stm != null) {
			pFormula = new RpgFormula(stm.getFormula());
			mes = stm.getName();
			orient = stm.getOrient();
		} else {
			pFormula = new RpgFormula(cmd.getFormulaStr());
			mes = cmd.getExeMessage();
		}
		// Formula(計算式)を計算、値を反映する。
		if (isDebug) System.out.println("Formula: " + pFormula.getFormulaStr());
		int pValue = pFormula.formula(pla);
		console.printMessage(pla.getName() + mes + "!");
		if (orient != null && RpgConst.REC.toString().equals(stm.getOrient())) {
			// プラスマイナスを反転させ回復に転じる
			pla.getDamage(-pValue);
			console.printMessage(pla.getName() + "は" + pValue + "回復した。");
		} else {
			mon.getDamage(pValue);
			console.printMessage(mon.getName() + "に" + pValue + "のダメージ");
		}
		// 攻撃を受けた側のHPが０以下の場合はバトル終了
		if (mon.getHP() <= 0) {
			if (mon instanceof Monster) {
				Monster monst = (Monster) mon;
				System.out.println(mon.getName() + "をたおした。");
				System.out.println(player.getName() + "は" + monst.getMoney() + RpgConst.getMnyName() + "を手にいれた。");
				PlayerParty party = PlayerParty.getInstance();
				party.setMoney(party.getMoney() + monst.getMoney());
				System.out.println(player.getName() + "は" + monst.getExp() + "の経験値を手にいれた。");
				player.setExp(player.getExp() + monst.getExp());
				player.levelup();
			} else {
				System.out.println(mon.getName() + "はたおれた。");
				System.out.println("ゲームオーバー");
				nextIndex = RpgConst.GAME_OVER.getType();
			}
			return true;
		}
		return false;
	}
}
