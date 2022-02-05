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
	private final int MAX_LEN = 16;
	/** 外枠の余裕部分 */
	private final int APPEND_LEN = 4;
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
		int sotowaku = appendLine(build, name, isMultiByte);

		// 改行追加
		 build.append(SEPARATOR);
		 // ステータスの表示部分(頭にスペース２つは固定)=6文字分

		 // Lvを表示する[12 - (ステータス部分(6文字) + 値(最大３桁) + 1(アスタリスク)]
		 String lv = String.valueOf(player.getLevel());
		 String lvStr = "* LV: " + lv;
		 build.append(lvStr + appendSpace(lvStr, sotowaku - 1) + "*" + SEPARATOR);
		 // HPを表示する
		 String hp = String.valueOf(player.getHP());
		 String hpStr = "* HP: " + hp;
		 build.append(hpStr + appendSpace(hpStr, sotowaku - 1) + "*" + SEPARATOR);
		 // MPを表示する
		 String mp = String.valueOf(player.getMP());
		 String mpStr = "* MP: " + mp;
		 build.append( mpStr + appendSpace(mpStr, sotowaku - 1) + "*" + SEPARATOR);
		// 外枠を作成
		appendLastLine(build, sotowaku);
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
		boolean isGusu = name.length() % 2 == 0;
		// 装備のスペースを追加するためのスペースサイズ
		int sobisize = maxSobiSize(player);
		// 外枠を作成
		int sotowaku = appendLine(build, name, isMultiByte, sobisize) / 2;
		// 改行追加
		build.append(SEPARATOR);
		// 装備の類

		// ステータスの表示部分(頭にスペース２つは固定)=6文字分

		String addAstah = isMultiByte ? " *" : "*";
		// Lvを表示する[12 - (ステータス部分(6文字) + 値(最大３桁) + 1(アスタリスク)]
		String lv = String.valueOf(player.getLevel());
		String lvStr = "* LV: " + lv;
		build.append( lvStr + appendSpace(lvStr, sotowaku) + "*");
		// 武器
		String wep = getSobiName((Items) player.getMainWepon());
		boolean isValue = "なし".equals(wep);
		boolean wepIsGusu = wep.length() % 2 == 0;
		int sobiSpace = sotowaku - sobisize;
		String wepStr = " " + RpgConst.BUKI + ":" + wep;

		if (isMultiByte && isGusu) {
			//System.out.println("*** AAA ***");
			build.append(wepStr + appendSpace(wepStr, sobiSpace + 6) + "*" + SEPARATOR);
		} else if (isMultiByte && sotowaku < 20) {
			//System.out.println("*** Testing *** : " + sotowaku);
			build.append(wepStr + appendSpace(wepStr, sobiSpace) + "*" + SEPARATOR);
		} else if (isMultiByte) {
			//System.out.println("*** BBB ***" + sotowaku);
			build.append(wepStr + appendSpace(wepStr, sobiSpace + 9) + "*" + SEPARATOR);
		} else {
			build.append(wepStr  + appendSpace(wepStr, sobiSpace - 1) + "*" + SEPARATOR);
		}
		// HPを表示する
		String hp = String.valueOf(player.getHP());
		String hpStr = "* HP: " + hp;
		build.append(hpStr + appendSpace(hpStr, sotowaku) + "*");
		// 防具
		String arm = getSobiName((Items) player.getArmor());
		boolean armIsGusu = arm.length() % 2 == 0;
		String armStr = " " + RpgConst.BOG + ":" + arm;
		if (isMultiByte && isGusu) {
			build.append(armStr + appendSpace(armStr, sobiSpace + 6) + "*" + SEPARATOR);
		} else if (isMultiByte && sotowaku < 20) {
			build.append(armStr + appendSpace(armStr, sobiSpace) + "*" + SEPARATOR);
		} else if (isMultiByte) {
			build.append(armStr + appendSpace(armStr, sobiSpace + 7) + "*" + SEPARATOR);
		}else {
			build.append(armStr + appendSpace(armStr, sobiSpace - 1) + "*" + SEPARATOR);
		}
		// MPを表示する
		String mp = String.valueOf(player.getMP());
		String mpStr = "* MP: " + mp;
		build.append(mpStr + appendSpace(mpStr, sotowaku) + "*");
		if (isMultiByte) {
			build.append(appendSpace("", sotowaku - 1)  + "*" + SEPARATOR);
		} else {
			build.append(appendSpace("", sotowaku - 2)  + "*" + SEPARATOR);
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
		RpgData atk = conf.getParamMap().get(RpgConst.ATK.toString());
		RpgData def = conf.getParamMap().get(RpgConst.DEF.toString());
		int counter = 1;
		if (isDebug) System.out.println("sobisize: " + sobisize);
		for (RpgStatus data : statusList) {
			if (data.getKigo().equals("ATK") || data.getKigo().equals("DEF")) {
				continue;
			}
			String n = data.getName();
			String v = String.valueOf(data.getValue());
			boolean isMulti = CheckerUtils.isMultiByteStr(n);

			boolean valueIsGusu = n.length() % 2 == 0 ? true : false;
			String nStr = "* " + n + ": " + v;
			build.append(nStr + appendSpace(nStr, sotowaku) + "*");

			if (counter == 1) {
				String val = atk.getValue() != null ? atk.getValue().toString() : null;
				build.append(printRightSideStatus(atk.getName(), val, isMulti, sotowaku, sotowaku));
			}else if (counter == 2) {
				String val = def.getValue() != null ? def.getValue().toString() : null;
				if (isMultiByte && isGusu) {
					build.append(printRightSideStatus(def.getName(), val, isMulti, sobisize, sotowaku));
				} else if (isMultiByte && sotowaku < 20) {
					build.append(printRightSideStatus(def.getName(), val, isMulti, sobisize, sotowaku + 1));
				} else if (isMultiByte) {
					build.append(printRightSideStatus(def.getName(), val, isMulti, sobisize, sotowaku));
				} else {
					build.append(printRightSideStatus(def.getName(), val, isMulti, sobisize, sotowaku));
				}
			} else {
				printRightSideStatus(build, "", isMultiByte, sobisize, sotowaku);
			}
			counter++;
		}
		// 外枠を作成
		int lastSize = sotowaku * 2;
		if(isMultiByte && isGusu) {
			lastSize = lastSize + 1;
		} else if(isMultiByte) {
			lastSize = lastSize + 1;
		}
		appendLastLine(build, lastSize);
		System.out.println(build.toString());
	}

	/** ステータス表示の右側をStringBuilderに追加 */
	private String printRightSideStatus(String name,String value, boolean isMultiByte, int sobisize, int max) {
		String ret = null;
		if ("".equals(name)) {
			if (isMultiByte) {
				ret = appendSpace("", max + 1)  + "*" + SEPARATOR;
			} else {
				ret = appendSpace("", max)  + "*" + SEPARATOR;
			}
			return ret;
		}
        boolean isGusu = name.length() % 2 == 0 ? true : false;
		value = value == null ? "0" : value;
		if (isDebug) System.out.println("Name; " + name + "  Value: " + value + "Sobi: " + sobisize + " isMultiByte: " + isMultiByte);
		String valStr = " " + name + ": " + value;
		if (isMultiByte && "0".equals(value) == false) {
			ret = valStr + appendSpace(valStr, max - 2) + "*" + SEPARATOR;
		} else if (isMultiByte && "0".equals(value)) {
			ret = valStr + appendSpace(valStr, max - 3) + "*" + SEPARATOR;
		} else {
			ret = valStr + appendSpace(valStr, max - 3) + "*" + SEPARATOR;
		}
		return ret;
	}


	/** ステータス表示の右側をStringBuilderに追加 */
	private void printRightSideStatus(StringBuilder build, String name, boolean isMultiByte, int sobisize, int max) {
		if (name.length() == 0) {
			if (isMultiByte) {
				build.append(appendSpace("", max - 1)  + "*" + SEPARATOR);
			} else {
				build.append(appendSpace("", max - 2)  + "*" + SEPARATOR);
			}
			return;
		}
		if (isMultiByte) {
			build.append(appendSpace(name, max + sobisize) + "*" + SEPARATOR);
		} else {
			build.append(appendSpace(name, max + sobisize) + "*" + SEPARATOR);
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

		boolean itemNotNull = false;
		if (item == null) {
			// なしの場合
			res = 4;
		} else {
			itemNotNull = true;
			nameLen = item.getName().length();
			if (item instanceof MainWepon) {
				String val = "(" + String.valueOf(((MainWepon) item).getOffence()) + ")";
				v = val.length();
			} else if (item instanceof Armor) {
				String val = "(" + String.valueOf(((Armor) item).getDiffence()) + ")";
				v = val.length();
			}
		}

		if (isMulti && itemNotNull) {
			res = nameLen * 2 + v;
		} else if (itemNotNull) {
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
	 * @return 全体の文字数を返却する(全角部分は２倍計算)
	 */
	private int appendLine(StringBuilder build, String name, boolean isMultiByte) {
		// 12 - 名前の文字数
		int nameLen = 0;
		int astahAll = 0;
		if (isMultiByte) {
			// 全角の場合は２倍にする
			nameLen = name.length() * 2;
			astahAll = name.length() * 2 + APPEND_LEN;
		} else {
			nameLen = name.length();
			astahAll = name.length() + APPEND_LEN;
		}
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(name.length());
		if (isGusu) {
			astah = astahAll / 2 - 2;
		} else {
			astah = astahAll / 2 - 3;
		}
		if (astah < 5) {
			astah = 4;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String append = null;
		String space = " ";
		int spaceLen = 0;
		if (isGusu == false || isMultiByte) {
			spaceLen = 3;
			append = space + name + space + space;
		} else {
			spaceLen = 2;
			append = space + name + space;
		}
		build.append(append);
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		if (astah <= 2) {
			build.append(" ");
		}
		return astah * 2 + nameLen + spaceLen;
	}

	/**
	 * 名前の(マルチバイトは2倍にするの)長さ分「*」をStringBuilderに追加する。
	 * this#printStatusで使用する
	 *
	 * @param build StringBuilder
	 * @param name 名前
	 * @param isMultiByte マルチバイト文字かどうか
	 * @param sobisize 右側に追加する武器・防具の最大サイズ
	 * @return
	 */
	private int appendLine(StringBuilder build, String name, boolean isMultiByte, int sobisize) {
		// 12 - 名前の文字数
		int nameLen = 0;
		int astahAll = 0;
		if (isMultiByte) {
			// 全角の場合は２倍にする
			nameLen = name.length() * 2;
			astahAll = name.length() * 2 + APPEND_LEN * 2 + sobisize;
		} else {
			nameLen = name.length();
			astahAll = name.length() + + APPEND_LEN * 4 + sobisize;
		}
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(name.length());
		if (isGusu) {
			astah = astahAll - 2;
		} else {
			astah = astahAll - 3;
		}
		if (astah < 5) {
			astah = 4;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String append = null;
		String space = " ";
		int spaceLen = 0;
		if (isGusu == false || isMultiByte) {
			spaceLen = 3;
			append = space + name + space + space;
		} else {
			spaceLen = 2;
			append = space + name + space;
		}
		build.append(append);
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		if (astah <= 2) {
			build.append(" ");
		}
		return astah * 2 + nameLen + spaceLen;
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
	private void appendLastLine(StringBuilder build, int sotowaku) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		for (int i = 0; i < sotowaku; i++) {
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

//	/**
//	 * ステータス表示のために、引数の後ろにスペースを追加する。
//	 *
//	 * @param statusValue 最終的にスペースが追加された文字列
//	 */
//	private String appendSpace(String statusValue, boolean isGusu, boolean isMulti) {
//		StringBuilder space = new StringBuilder();
//		int len = 0;
//		/** 表示するステータスの文字数 */
//		int STATUS_LEN = 27;
//		if (isGusu) {
//			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
//			len = (STATUS_LEN + 4) - statusValue.length();
//		} else {
//			// 奇数の場合、最大文字数 + 3(スペース2個分)
//			len = (STATUS_LEN + 5) - statusValue.length();
//		}
//		for (int i = 0; i < len; i++) {
//			space.append(" ");
//		}
//		return space.toString();
//	}
//
//	/**
//	 * ステータス表示のために、引数の後ろにスペースを追加する。
//	 *
//	 * @param statusValue 最終的にスペースが追加された文字列
//	 */
//	private String appendSpace(String statusValue, boolean isGusu) {
//		StringBuilder space = new StringBuilder();
//		int len = 0;
//		/** 表示するステータスの文字数 */
//		int STATUS_LEN = 6;
//		if (isGusu) {
//			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
//			len = (STATUS_LEN + 4) - statusValue.length();
//		} else {
//			// 奇数の場合、最大文字数 + 3(スペース2個分)
//			len = (STATUS_LEN + 5) - statusValue.length();
//		}
//		for (int i = 0; i < len; i++) {
//			space.append(" ");
//		}
//		return space.toString();
//	}

	/**
	 * ステータス表示のために、引数の後ろにスペースを追加する。
	 * このメソッドは、左側のステータス表示で使用する。
	 *
	 * @param statusValue ステータスの文字列
	 * @param max 最大桁数
	 */
	private String appendSpace(String statusValue, int max) {
		StringBuilder space = new StringBuilder();
		int len = 0;
		int colonIdx = statusValue.indexOf(":");
		String st = null;
		if ("".equals(statusValue)) {
			st = "";
		} else {
			st = statusValue.substring(2, colonIdx);
		}
		//System.out.println("substr: " + st);
		boolean isMulti = CheckerUtils.isMultiByteStr(st);

		int strLen = isMulti ? (st.length() * 2) + (statusValue.length() - st.length()) : statusValue.length();

		len = max - strLen;
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
