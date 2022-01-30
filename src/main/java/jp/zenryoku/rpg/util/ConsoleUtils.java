package jp.zenryoku.rpg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import jp.zenryoku.rpg.charactors.Command;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.RpgItem;
import jp.zenryoku.rpg.data.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.item.Items;
import jp.zenryoku.rpg.item.equip.Armor;
import jp.zenryoku.rpg.item.equip.MainWepon;

/**
 * 標準入出力のユーティリティ
 */
public class ConsoleUtils {
	/** デバック */
	private static final boolean isDebug = false;
	/** 自分のクラスのインスタンス */
	private static ConsoleUtils instance;
	/** 枠線上部、下部(一人分の長さ(半角８文字、全角４文字)) */
	private final int MAX_LEN = 14;
	/** 改行文字 */
	private final String SEPARATOR = System.lineSeparator();
	/** OS名 */
	private final String OS_NAME = System.getProperty("os.name");
	/** 標準出力の切り替え先 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();
	/** 標準入力の受付 */
	private static Scanner scan;


	public static ConsoleUtils getInstance() {
		if (instance == null) {
			instance = new ConsoleUtils(false);
		}
		return instance;
	}

	/**
	 * コンストラクタ。
	 * Propertyファイルを読み込みステータス表示のフォーマットを取得する。
	 *
	 */
	public ConsoleUtils(boolean isTest) {
		scan = new Scanner(System.in);
		if (isTest) {
			// テストのときはPrintStreamクラスをし標準出力先に変更する
			System.setOut(new PrintStream(console));
		}
	}

	/**
	 * メッセージ表示をして、未入力チェックを行い入力を受け付ける。
	 * @param message
	 * @return
	 */
	public String acceptInput(String message) {
		System.out.println(message);
		boolean isOK = false;
		String input = null;
		while (isOK == false) {
			input = scan.nextLine();
			isOK = input == null || "".equals(input) ? false : true;
			if (isOK == false) {
				System.out.println(MessageConst.ERR_NO_INPUT.toString());
			}
		}
		return input;
	}
	/**
	 * メッセージ表示をして、未入力チェックを行うか判定し、入力を受け付ける。
	 * @param message 表示するメッセージ
	 * @param doCheck 未入力入力チェックをするかしないか
	 * @return
	 */
	public String acceptInput(String message, boolean doCheck) {
		System.out.println(message);
		boolean isOK = false;
		String input = null;
		if (doCheck == false) {
			input = scan.nextLine();
			return input;
		}
		while (isOK == false) {
			input = scan.nextLine();
			isOK = input == null || "".equals(input) ? false : true;
			if (isOK == false) {
				System.out.println(MessageConst.ERR_NO_INPUT.toString());
			}
		}
		return input;
	}
	/**
	 * メッセージ表示をして、未入力チェックを行い入力を受け付ける。
	 * @param message
	 * @return
	 */
	public String acceptInput(String message, String regrex) {
		System.out.println(message);
		boolean isOK = false;
		String input = null;
		while (isOK == false) {
			input = scan.nextLine();
			isOK = input == null || "".equals(input) ? false : true;
			if (isOK == false) {
				System.out.println(MessageConst.ERR_NO_INPUT.toString());
				continue;
			}
			if (input.matches(regrex) == false) {
				System.out.println(MessageConst.ERR_OUT_OF_INPUT);
				continue;
			}
			isOK = true;
		}
		return input;
	}

	/**
	 * メッセージをコンソールに表示する。
	 * @param message メッセージ
	 */
	public void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * プレーヤーのステータスをコンソールに表示する。
	 * @param player
	 */
	public void printBattleStatus(Player player) {
		StringBuilder build = new StringBuilder();
		String name = player.getName();
		// 必要は情報を取得する
		boolean isMultiByte = CheckerUtils.isMultiByteStr(name);
		// 外枠を作成
		boolean isGusu = appendLine(build, name, isMultiByte);
		// 改行追加
		 build.append(SEPARATOR);
		 // ステータスの表示部分(頭にスペース２つは固定)=6文字分

		 // Lvを表示する[12 - (ステータス部分(6文字) + 値(最大３桁) + 1(アスタリスク)]
		 String lv = String.valueOf(player.getLevel());
		 build.append("* LV: " + lv + appendSpace(lv, isGusu) + "*" + SEPARATOR);
		 // HPを表示する
		 String hp = String.valueOf(player.getHP());
		 build.append("* HP: " + hp + appendSpace(hp, isGusu) + "*" + SEPARATOR);
		 // MPを表示する
		 String mp = String.valueOf(player.getMP());
		 build.append("* MP: " + mp + appendSpace(mp, isGusu) + "*" + SEPARATOR);
		// 外枠を作成
		appendLastLine(build, name.length(), isGusu);
		System.out.println(build.toString());
	}

	/**
	 * プレーヤーのステータスをコンソールに表示する。
	 * @param player
	 */
	public void printStatus(PlayerCharactor player) {
		StringBuilder build = new StringBuilder();
		String name = player.getName();
		// 必要は情報を取得する
		boolean isMultiByte = CheckerUtils.isMultiByteStr(name);
		// 装備のスペースを追加するためのスペースサイズ
		int sobisize = maxSobiSize(player);
		int sotowaku = MAX_LEN * 2 + sobisize;
		// 外枠を作成
		boolean isGusu = appendLine(build, name, isMultiByte, sotowaku);
		// 改行追加
		build.append(SEPARATOR);
		// 装備の類

		// ステータスの表示部分(頭にスペース２つは固定)=6文字分

		String addAstah = isMultiByte ? " *" : "*";
		// Lvを表示する[12 - (ステータス部分(6文字) + 値(最大３桁) + 1(アスタリスク)]
		String lv = String.valueOf(player.getLevel());
		build.append("* LV: " + lv + appendSpace(lv, isGusu) + addAstah);
		// 武器
		String wep = getSobiName((Items) player.getMainWepon());
		boolean wepIsGusu = wep.length() % 2 == 0;
		int sobiSpace = sotowaku - sobisize;
		if (isMultiByte) {
			build.append(" " + RpgConst.BUKI + ":" + wep + appendSpace(wep, wepIsGusu, sobiSpace) + "*" + SEPARATOR);
		} else {
			build.append(" " + RpgConst.BUKI + ":" + wep + appendSpace(wep, wepIsGusu, 8) + "*" + SEPARATOR);
		}
		// HPを表示する
		String hp = String.valueOf(player.getHP());
		build.append("* HP: " + hp + appendSpace(hp, isGusu) + addAstah);
		// 防具
		String arm = getSobiName((Items) player.getArmor());
		boolean armIsGusu = arm.length() % 2 == 0;
		if (isMultiByte) {
			build.append(" " + RpgConst.BOG + ":" + arm + appendSpace(arm, armIsGusu, sobiSpace - 2) + "*" + SEPARATOR);
		} else {
			build.append(" " + RpgConst.BOG + ":" + arm + appendSpace(arm, armIsGusu, 6) + "*" + SEPARATOR);
		}
		// MPを表示する
		String mp = String.valueOf(player.getMP());
		build.append("* MP: " + mp + appendSpace(mp, isGusu) + addAstah);
		if (isMultiByte) {
			build.append(appendSpace("", true, sobisize + 11)  + "*" + SEPARATOR);
		} else {
			build.append(appendSpace("", true, 17)  + "*" + SEPARATOR);
		}
		List<RpgStatus> statusList = player.getStatusList();
		int max = 0;
		for (RpgStatus s : statusList) {
			String n = s.getName();
			boolean isMulti = CheckerUtils.isMultiByteStr(n);
			int len = n.length();
			if (isMulti) {
				len = len * 2;
			}
			if (max < len) {
				max = len;
			}
		}
		RpgConfig conf = RpgConfig.getInstance();
		RpgStatus atk = (RpgStatus) conf.getStatusMap().get(RpgConst.ATK.toString());
		RpgStatus def = (RpgStatus) conf.getStatusMap().get(RpgConst.DEF.toString());
		int counter = 1;
		for (RpgStatus data : statusList) {
			if (data.getKigo().equals("ATK") || data.getKigo().equals("DEF")) {
				continue;
			}
			String n = data.getName();
			String v = String.valueOf(data.getValue());

			boolean valueIsGusu = n.length() % 2 == 0 ? true : false;
			build.append("* " + n + ": " + v + appendSpace(n, valueIsGusu, max - 6) + "*");

			if (counter == 1) {
				//build.append(atk.getName());
				build.append(printRightSideStatus(atk.getName(),atk.getValue().toString(), isMultiByte, sobisize, max));
			}else if (counter == 2) {
				//build.append(def.getName());
				build.append(printRightSideStatus(def.getName(), def.getValue().toString(), isMultiByte, sobisize, max));
			} else {
				printRightSideStatus(build, "", isMultiByte, sobisize, max);
			}
			counter++;
		}
		// 外枠を作成
		appendLastLine(build, isGusu, sotowaku - 14);
		System.out.println(build.toString());
	}

	/** ステータス表示の右側をStringBuilderに追加 */
	private String printRightSideStatus(String name,String value, boolean isMultiByte, int sobisize, int max) {
		String ret = null;
		if ("".equals(name)) {
			if (isMultiByte) {
				ret = appendSpace("", true, sobisize + 11)  + "*" + SEPARATOR;
			} else {
				ret = appendSpace("", true, 17)  + "*" + SEPARATOR;
			}
			return ret;
		}
		if (isMultiByte) {
			ret = " "  + name + ": " + value + appendSpace(" " + name, true, sobisize + 6) + "*" + SEPARATOR;
		} else {
			ret = " "  + name + ": " + value + appendSpace(" " + name, true, max - 2) + "*" + SEPARATOR;
		}
		return ret;
	}


	/** ステータス表示の右側をStringBuilderに追加 */
	private void printRightSideStatus(StringBuilder build, String name, boolean isMultiByte, int sobisize, int max) {
		if (name.length() == 0) {
			if (isMultiByte) {
				build.append(appendSpace("", true, sobisize + 11)  + "*" + SEPARATOR);
			} else {
				build.append(appendSpace("", true, 17)  + "*" + SEPARATOR);
			}
			return;
		}
		if (isMultiByte) {
			build.append(appendSpace(name, true, sobisize + 10) + "*" + SEPARATOR);
		} else {
			build.append(appendSpace(name, true, max + 9) + "*" + SEPARATOR);
		}
	}

	/** 装備品の文字列のサイズを算出する */
	private int maxSobiSize(PlayerCharactor player) {
		Items wepItem = (Items) player.getMainWepon();
		Items armItem = (Items) player.getArmor();
		String wep = getSobiName(wepItem);
		String arm = getSobiName(armItem);

		boolean isWepMulti = CheckerUtils.isMultiByteStr(wep);
		int wepSize = getSobiNameLen(wepItem, isWepMulti);

		boolean isArmMulti = CheckerUtils.isMultiByteStr(arm);
		int armSize = getSobiNameLen(armItem, isArmMulti);


		if (isArmMulti) {
			armSize = arm.length() * 2;
		} else {
			armSize = arm.length();
		}
		// 追加するスペースのサイズ
		int appendSize = wepSize <  armSize ? armSize : wepSize;
		return appendSize;
	}

	/** 装備品の名前を返却する、ただし何も装備していないときは「なし」を返す */
	private String getSobiName(Items item) {
		String res = null;
		if (item == null) {
			res = "なし";
		} else {
			String n = item.getName();
			String v = "";
			if (item instanceof MainWepon) {
				v = "(" + String.valueOf(((MainWepon) item).getOffence()) + ")";
			} else if (item instanceof Armor) {
				v = "(" + String.valueOf(((Armor) item).getDiffence()) + ")";
			}
			res = n == null ? "なし" : n + v;
		}
		return res;
	}

	/** 装備品の名前を返却する、ただし何も装備していないときは「なし」を返す */
	private int getSobiNameLen(Items item, boolean isMulti) {
		int res = 0;
		int v = 0;
		int nameLen = 0;

		if (item == null) {
			res = 4;
		} else {
			nameLen = item.getName().length();
			if (item instanceof MainWepon) {
				String val = "(" + String.valueOf(((MainWepon) item).getOffence()) + ")";
				v = val.length();
			} else if (item instanceof Armor) {
				String val = "(" + String.valueOf(((Armor) item).getDiffence()) + ")";
				v = val.length();
			}
		}

		if (isMulti) {
			res = nameLen * 2 + v;
		} else {
			res = nameLen + v;
		}
		return res;
	}

	/**
	 * プレーヤーの行動選択肢を一覧表示する。
	 *
	 * @param player
	 * @return コマンドマップ(indexでソート済み)
	 */
	public Map<Integer, String> printCommandList(Player player) {
		Method[] mess = player.getClass().getDeclaredMethods();
		// 並び替え機能付きのMAP
		Map<Integer, String> commandMap = new TreeMap<>();
		for(Method mes : mess) {
			Command ano = mes.getAnnotation(Command.class);
			if (ano != null) {
				// マップにインデックス(順番)とコマンド名を登録
				commandMap.put(ano.index(), ano.commandName());
			}
		}
		// 並び替え後に表示
		commandMap.forEach((Integer index, String value) -> {
			System.out.println(index + ": " + value);
		});
		return commandMap;
	}

	private void appendIsGusuIsMultiThen(StringBuilder build, boolean isGusu, boolean isMulti) {

	}

	/**
	 * 名前の(マルチバイトは2倍にするの)長さ分「*」をStringBuilderに追加する。
	 * 
	 *
	 * @param build StringBuilder
	 * @param name 名前
	 * @return isGusu 出力するアスタの数が true: 偶数(全部で14文字) / false: 奇数(全部で15文字)
	 */
	private boolean appendLine(StringBuilder build, String name, boolean isMultiByte) {
		// 12 - 名前の文字数
		int astahAll = 0;
		if (isMultiByte) {
			// 全角の場合は２倍にする
			astahAll = MAX_LEN - (name.length() * 2);
		} else {
			astahAll = MAX_LEN - name.length();
		}
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(astahAll);
		if (isGusu) {
			astah = astahAll / 2;
		} else {
			astah = astahAll / 2 + 1;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String space = " ";
		if (isGusu == false || isMultiByte) {
			build.append(space + name + space + " ");
		} else {
			build.append(space + name + space);
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		if (astah <= 2) {
			build.append(" ");
		}
		return isGusu;
	}

	/**
	 * 名前の(マルチバイトは2倍にするの)長さ分「*」をStringBuilderに追加する。
	 * this#printStatusで使用する
	 *
	 * @param build StringBuilder
	 * @param name 名前
	 * @param isMultiByte マルチバイト文字かどうか
	 * @param max 最大幅
	 * @return isGusu 出力するアスタの数が true: 偶数(全部で14文字) / false: 奇数(全部で15文字)
	 */
	private boolean appendLine(StringBuilder build, String name, boolean isMultiByte, int max) {
		// 12 - 名前の文字数
		int astahAll = 0;
		if (isMultiByte) {
			// 全角の場合は２倍にする
			astahAll = max - (name.length() * 2);
		} else {
			astahAll = max - name.length();
		}
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(astahAll);
		if (isGusu) {
			astah = astahAll / 2;
		} else {
			astah = astahAll / 2 + 1;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String space = " ";
		if (isGusu == false) {
			build.append(space + " " + name + space + " ");
		} else if (isMultiByte) {
			build.append(space + name + space + " ");
		} else {
			build.append(space + name + space);
		}

		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		if (astah <= 2) {
			build.append(" ");
		}
		return isGusu;
	}

	/**
	 * 名前の(バイトは配列の)長さ分「*」をStringBuilderに追加する。
	 *
	 * @param build StringBuilder
	 * @param nameLen 名前の長さ
	 */
	private void appendLastLine(StringBuilder build, int nameLen, boolean isGusu) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		if (isGusu) {
			astah = MAX_LEN + 3;
		} else {
			astah = MAX_LEN + 4;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		build.append(SEPARATOR);
	}

	private void appendLastLine(StringBuilder build, boolean isGusu) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		if (isGusu) {
			astah = MAX_LEN + 2;
		} else {
			astah = MAX_LEN + 3;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		build.append(SEPARATOR);
	}

	/** this#printStatusで使用する */
	private void appendLastLine(StringBuilder build, boolean isGusu, int addtional) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		if (isGusu) {
			astah = MAX_LEN + 2;
		} else {
			astah = MAX_LEN + 4;
		}
		int max = astah + addtional;
		for (int i = 0; i <= max; i++) {
			build.append("*");
		}
		build.append(SEPARATOR);
	}

	private void appendEmptyLine(StringBuilder build, boolean isGusu, int addtional) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		if (isGusu) {
			astah = MAX_LEN + 2;
		} else {
			astah = MAX_LEN + 3;
		}
		int max = astah + addtional;
		build.append("*");
		for (int i = 0; i < max - 2; i++) {
			build.append(" ");
		}
		build.append("*");
		build.append(SEPARATOR);
	}

	/**
	 * ステータス表示のために、引数の後ろにスペースを追加する。
	 *
	 * @param statusValue 最終的にスペースが追加された文字列
	 */
	private String appendSpace(String statusValue, boolean isGusu, boolean isMulti) {
		StringBuilder space = new StringBuilder();
		int len = 0;
		/** 表示するステータスの文字数 */
		int STATUS_LEN = 27;
		if (isGusu) {
			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
			len = (STATUS_LEN + 4) - statusValue.length();
		} else {
			// 奇数の場合、最大文字数 + 3(スペース2個分)
			len = (STATUS_LEN + 5) - statusValue.length();
		}
		for (int i = 0; i < len; i++) {
			space.append(" ");
		}
		return space.toString();
	}

	/**
	 * ステータス表示のために、引数の後ろにスペースを追加する。
	 *
	 * @param statusValue 最終的にスペースが追加された文字列
	 */
	private String appendSpace(String statusValue, boolean isGusu) {
		StringBuilder space = new StringBuilder();
		int len = 0;
		/** 表示するステータスの文字数 */
		int STATUS_LEN = 6;
		if (isGusu) {
			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
			len = (STATUS_LEN + 4) - statusValue.length();
		} else {
			// 奇数の場合、最大文字数 + 3(スペース2個分)
			len = (STATUS_LEN + 5) - statusValue.length();
		}
		for (int i = 0; i < len; i++) {
			space.append(" ");
		}
		return space.toString();
	}

	/**
	 * ステータス表示のために、引数の後ろにスペースを追加する。
	 *
	 * @param statusValue 最終的にスペースが追加された文字列
	 * @param isGusu 偶数かどうか
	 * @param max 最大桁数
	 */
	private String appendSpace(String statusValue, boolean isGusu, int max) {
		StringBuilder space = new StringBuilder();
		boolean isMulti = CheckerUtils.isMultiByteStr(statusValue);
		int len = 0;

		int strLen = isMulti ? statusValue.length() * 2 - 3: statusValue.length();
		if (isGusu) {
			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
			len = (max + 1) - strLen;
		} else {
			// 奇数の場合、最大文字数 + 3(スペース2個分)
			len = (max + 1) - strLen;
		}
		for (int i = 0; i < len; i++) {
			space.append(" ");
		}
		return space.toString();
	}

	/**
	 * 補助メソッド：ステータス表示のために、引数の後ろにスペースを追加する。
	 * 引数の文字列が偶数柿数かにより、スペースの文字数を決定する。
	 * @param str 入力文字列
	 * @param isGusu 偶数柿数か
	 * @param  max 最大スペースのサイズ
	 * @return スペースの数
	 */
	private int isGusuSpaceLen(String str, boolean isGusu, int max) {
		int len = 0;

		return len;
	}
	/**
	 * コンソールをクリアする。
	 */
	public void clearConsole() {
		try {
			if (OS_NAME.contains("Windows")) {
//				Runtime r = Runtime.getRuntime();
//				r.exec(new String[] {"cmd", "/c ", "cls"});
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				// Windows以外はこれでいけるらしい TODO-[後で確認]
				Runtime.getRuntime().exec("clear");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 設定情報を読み込んだ後の情報を表示する。
	 * 例:
	 * help -> 全体を表示する
	 * help param -> パラメータのみを表示する
	 * help status -> ステータスのみを表示する
	 *
	 */
	public void printConfig(String command) {
		RpgConfig conf = RpgConfig.getInstance();
		String[] sep = command.split(" ");
		String target = null;
		if (sep.length == 2) {
			target = sep[1];
		} else {
			// 全部表示
			System.out.println("パラメータ");
			printMap(conf.getParamMap());
			System.out.println("ステータス");
			printMap(conf.getStatusMap());
			System.out.println("アイテムタイプ");
			printMap(conf.getItemTypeMap());
			System.out.println("アイテム一覧");
			printMap(conf.getItemMap());
			System.out.println("計算式");
			printMap(conf.getFormulaMap());
			return;
		}

		// パラメータ用
		if ("param".equals(target)) {
			System.out.println("パラメータ");
			printMap(conf.getParamMap());
		}
		// ステータス
		if ("status".equals(target)) {
			System.out.println("ステータス");
			printMap(conf.getStatusMap());
		}
		// アイテムタイプ
		if ("itemType".equals(target)) {
			System.out.println("アイテムタイプ");
			printMap(conf.getItemTypeMap());
		}
		// アイテム
		if ("item".equals(target)) {
			System.out.println("アイテム一覧");
			printMap(conf.getItemMap());
		}
		// 計算式
		if ("formula".equals(target)) {
			System.out.println("計算式");
			printMap(conf.getFormulaMap());
		}
	}

	public void printMenu() throws RpgException {
		RpgConfig conf = RpgConfig.getInstance();
		PlayerParty party = conf.getParty();
		PlayerCharactor player = party.getPlayer();
		ConsoleUtils console = ConsoleUtils.getInstance();
		List<RpgItem> itemList = player.getItemBag();
		System.out.println(MessageConst.MENU);
		//System.out.println(MessageConst.IS_MENU);

		boolean isMenu = true;


		while (isMenu) {
			if (isDebug) System.out.println("アイテムサイズ: " + itemList.size());
			String input = console.acceptInput(MessageConst.MENU_DO_SELECT.toString(), "[1-3]");
			if ("1".equals(input)) {
				System.out.println("＜アイテム＞");
				// 所持アイテムの表示
				for (int i = 0; i < itemList.size(); i++) {
					RpgItem item = itemList.get(i);
					System.out.println((i + 1) + ". " + item.getName());
				}
				// 装備の変更をおこなう
				String selectSobi = console.acceptInput(MessageConst.EQUIP_SELECT.toString());
				RpgItem sobi = itemList.get(Integer.parseInt(selectSobi) - 1);
				System.out.println(sobi.getName() + " : " + sobi.getItemType() + " : " + sobi.getItemValueKigo());
				if (isDebug) {
					Map<String, RpgData> testMap = conf.getItemMap();
					testMap.forEach((key, val) -> {
						RpgItem item = (RpgItem) val;
						System.out.println("Key: " + key + " : " + "Val: " + item.getItemType());
					});
				}
				RpgItem type = (RpgItem) conf.getItemMap().get(sobi.getName());
				if (RpgConst.WEP.equals(type.getItemType()) == false) {
					throw new RpgException(MessageConst.ERR_SETTING_OBJECT.toString() + " : " + type.getName());
				}
				player.setMainWepon(new MainWepon(type));
				printStatus(player);
			} else if ("2".equals(input)) {
				// アイテムの使用をおこなう
			} else if ("3".equals(input)) {
				// まほうの使用をおこなう

			} else if ("exit".equals(input)) {
				break;
			}
		}
	}

	/**
	 * そうびの変更を行う。
	 */
	private void selectEquipMent(Player player, RpgConfig conf) {


	}

	/**
	 * アイテムの使用
	 */
	private void useItems() {

	}

	/**
	 * まほうの使用
	 */
	private void useMagics() {

	}

	/**
	 * 引数のマップ内容をすべて表示する。
	 * @param map
	 */
	private void printMap(Map<String, RpgData> map) {
		map.forEach((key, val) -> {
			String disc = val.getDiscription();
			String res = disc != null ? disc : val.getName();
			System.out.println(key + " : " + res + "<" + val.getKigo() + ">");
		});
		// 表示の区切り
		acceptInput("", false);
	}

	private void printList(List<RpgItem> list) {

	}
}
