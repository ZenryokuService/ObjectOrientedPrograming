package jp.zenryoku.rpg.scene;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.scene.Scene;
import jp.zenryoku.rpg.Games;
import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.CheckerUtils;
import jp.zenryoku.rpg.util.ConsoleUtils;
import lombok.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
public class BattleScene extends RpgScene {
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
	 * 初期表示を行う。
	 * 1. バトルステータスを表示
	 * 2. 初期表示: 「XXXXが現れた」
	 * 3. コマンドの入力を促す
	 * 4. コマンドの一覧を表示する
	 *
	 */
	@Override
	public void initScene() throws RpgException {
		// プレーヤーの取得
		player = PlayerParty.getInstance().getPlayer();
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
		boolean isFinish = false;
		initScene();
		initBattle();
		List<RpgCommand> list = player.getJob().getCommandList();
		int listSize = list.size();
		while(true) {
            // バトルステータスを表示
            console.printBattleStatus(player);
            // コマンドの入力を促す
            // コマンドの一覧を表示する
            List<RpgCommand> commandList = console.printCommandList(player);
			// プレーヤーターン、コマンド取得
			String selectCommand = console.acceptInput("こうどうを、せんたくしてください。", "[1-" + (listSize - 1) + "]");
			int select = Integer.parseInt(selectCommand) - 1;
			RpgCommand pCommand = commandList.get(select);
			if (isDebug) System.out.println("Command; " + pCommand.getName());
			// プレーヤー攻撃
			isFinish = printAttackAndCalc(player, monster, pCommand);
			if (isFinish) {
				break;
			}

			// モンスターの攻撃
			if (isDebug) System.out.println("monster.job: " + monster.getType());
			List<RpgCommand> cmdList = monster.getType().getCommandList();
			int monRnd = CalcUtils.getInstance().generateRandom(0,1);
			RpgCommand mCommand = cmdList.get(monRnd);
			isFinish = printAttackAndCalc(monster, player, mCommand);
			if (isFinish) {
				break;
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
	private boolean printAttackAndCalc(PlayerCharactor pla, PlayerCharactor mon, RpgCommand cmd) {
		Map<String, RpgData> paramMap = RpgConfig.getInstance().getParamMap();
		RpgFormula pFormula = new RpgFormula(cmd.getFormulaStr());
		if (isDebug) System.out.println("Formula: " + pFormula.getFormulaStr());
		int pValue = pFormula.formula(pla);
		console.printMessage(pla.getName() + cmd.getExeMessage() + "!");
		mon.setHP(mon.getHP() - pValue);
		console.printMessage(mon.getName() + "に" + pValue + "のダメージ");
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
			}
			return true;
		}
		return false;
	}
}
