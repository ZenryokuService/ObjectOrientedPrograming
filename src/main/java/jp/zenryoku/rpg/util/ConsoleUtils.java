package jp.zenryoku.rpg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.*;

import jp.zenryoku.rpg.charactors.Command;
import jp.zenryoku.rpg.charactors.Player;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.constants.SelectConst;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.data.categry.RpgMaster;
import jp.zenryoku.rpg.data.items.RpgItem;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.status.RpgStatus;
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
	private static final int DEL3 = 3;
	private static final int DEL2 = 2;
	private static final int DEL1 = 1;
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
	 * @param message 表示するメッセージ
	 * @return 入力文字列
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
	 * @return 入力文字列
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
	 * @param message 表示する文字列
	 * @param regrex 入力チェック用の正規表現
	 * @return 入力文字列
	 */
	public String acceptInput(String message, String regrex) {
		System.out.println(message);
		boolean isOK = false;
		String input = null;
		while (isOK == false) {
			input = scan.nextLine();
//			isOK = input == null || "".equals(input) ? false : true;
			if (input == null || "".equals(input)) {
				System.out.println(MessageConst.ERR_NO_INPUT.toString());
				continue;
			}
			if (input.matches(regrex) == false) {
				System.out.println(MessageConst.ERR_OUT_OF_INPUT + ": " + regrex + "(正規表現といいます)");
				continue;
			}
			isOK = true;
		}
		return input;
	}

	/**
	 * メッセージ表示をして、未入力チェック、入力範囲チェックを行い入力を受け付ける。
	 * @param message 表示する文字列
	 * @param max 入力範囲の最大値
	 * @return 入力文字列
	 */
	public String acceptInput(String message, int max) {
		System.out.println(message);
		boolean isOK = false;
		String input = null;
		while (isOK == false) {
			input = scan.nextLine();
//			isOK = input == null || "".equals(input) ? false : true;
			if (input == null || "".equals(input)) {
				System.out.println(MessageConst.ERR_NO_INPUT.toString());
				continue;
			}
			if (input.matches("[0-9]{1,2}") == false) {
				System.out.println(MessageConst.ERR_OUT_OF_INPUT + ": " + "[0-9]{1,2}" + "(正規表現といいます)");
				continue;
			}
			int in = Integer.parseInt(input);
			if (in < 1 || in > max) {
				System.out.println(MessageConst.ERR_OUT_OF_INPUT + ": " + "1-" + max);
				continue;
			}
			isOK = true;
		}
		return input;
	}

	/**
	 * メッセージをコンソールに表示する。
	 * @param message 表示するメッセージ
	 */
	public void printMessage(String message) {
		System.out.println(message);
	}

	/**
	 * プレーヤーのステータスをコンソールに表示する。
	 * @param player プレーヤー
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
	 * @param player プレーヤー
	 */
	public void printStatus(PlayerCharactor player) {
		StringBuilder build = new StringBuilder();
		// 所持金の表示
		RpgConfig conf = RpgConfig.getInstance();
		Map<String, RpgMaster> masterMap = conf.getMasterMap();
		Map<String, RpgData> paramMap = conf.getParamMap();

		// TODO-[現状では、固定（金額)]
		String tukaKey = masterMap.get(RpgConst.MNY).getChildList().get(0);
		RpgData tuka = paramMap.get(tukaKey);
		build.append(PlayerParty.getInstance().getMoney() + " " + tuka.getKigo() + SEPARATOR);
		String name = player.getName();
		// 必要は情報を取得する
		boolean isMultiByte = CheckerUtils.isMultiByteStr(name);
		boolean isGusu = name.length() % 2 == 0;
		// 装備のスペースを追加するためのスペースサイズ
		int sobisize = maxSobiSize(player);
		// 外枠を作成
		int underLine = appendLine(build, name, isMultiByte, sobisize);
		if (isDebug) System.out.println("underLine: " + underLine);
		int sotowaku = underLine % 2 == 0 ? underLine / 2 : underLine / 2 + 1;

		if (isDebug) System.out.println("sotowaku: " + sotowaku);
		boolean isUnder42 = underLine < 54;
		// 改行追加
		build.append(SEPARATOR);

		// 外枠サイズ調節
		int resizeSotowaku = 0;
		if (isMultiByte && isGusu) {
			// 全角　名前が偶数
			resizeSotowaku = sotowaku - DEL3;
		} else if (isMultiByte && isGusu == false) {
			// 全角　名前が奇数
			resizeSotowaku = sotowaku - DEL3;
		} else if (isMultiByte == false && isGusu) {
			// 半角　名前が偶数
			resizeSotowaku = sotowaku - DEL2;
		} else if (isMultiByte == false && isGusu == false) {
			// 半角　名前が偶数
			resizeSotowaku = sotowaku - DEL2;
		} else {
			// それ以外
			resizeSotowaku = sotowaku - DEL1;
		}
		// Lvを表示する[12 - (ステータス部分(6文字) + 値(最大３桁) + 1(アスタリスク)]
		String lv = String.valueOf(player.getLevel());
		String lvStr = "* LV: " + lv;
		build.append( lvStr + appendSpace(lvStr, sotowaku) + "*");
		// 武器
		String wep = getSobiName((Items) player.getMainWepon());
		String wepStr = " " + RpgConst.BUKI + ": " + wep;

		if (isDebug) System.out.println("sotowaku: " + sotowaku + " : sobisize: " + sobisize);
		if (isMultiByte && isGusu) {
			if (isDebug) System.out.println("*** AAA *** : " + (resizeSotowaku));
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(wepStr + appendSpace(wepStr, resizeSotowaku) + "*" + SEPARATOR);
		} else if (isMultiByte && isGusu == false) {
			if (isDebug) System.out.println("*** BBB ***" + sotowaku);
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(wepStr + appendSpace(wepStr, resizeSotowaku) + "*" + SEPARATOR);
		} else if (isMultiByte == false && isGusu == false) {
			if (isDebug) System.out.println("*** Testing *** : " + (sotowaku));
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(wepStr + appendSpace(wepStr, resizeSotowaku) + "*" + SEPARATOR);
		} else {
			if (isDebug) System.out.println("*** Other ***" + sotowaku);
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(wepStr  + appendSpace(wepStr, resizeSotowaku) + "*" + SEPARATOR);
		}
		// HPを表示する
		String hp = String.valueOf(player.getHP());
		String hpStr = "* HP: " + hp;
		build.append(hpStr + appendSpace(hpStr, sotowaku) + "*");
		// 防具
		String arm = getSobiName((Items) player.getArmor());
		boolean armIsGusu = arm.length() % 2 == 0;
		String armStr = " " + RpgConst.BOG + ": " + arm;

		if (isMultiByte && isGusu) {
			if (isDebug) System.out.println("*** ぼうぐ:A *** : " + (resizeSotowaku));
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(armStr + appendSpace(armStr, resizeSotowaku) + "*" + SEPARATOR);
		} else if (isMultiByte && isGusu == false) {
			if (isDebug) System.out.println("*** ぼうぐ:B ***" + (sotowaku - DEL2));
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(armStr + appendSpace(armStr, resizeSotowaku) + "*" + SEPARATOR);
		} else if (isMultiByte == false && isGusu == false) {
			if (isDebug) System.out.println("*** ぼうぐ:Testing *** : " + (resizeSotowaku));
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(armStr + appendSpace(armStr, resizeSotowaku) + "*" + SEPARATOR);
		} else {
			if (isDebug) System.out.println("*** ぼうぐ:Other ***" + resizeSotowaku);
			//build.append(printRightSideStatus(RpgConst.BUKI, wep, isMultiByte, isGusu, sobisize, sotowaku));
			build.append(armStr  + appendSpace(armStr, resizeSotowaku) + "*" + SEPARATOR);
		}
//		if (isMultiByte && isGusu) {
//			build.append(armStr + appendSpace(armStr, rightMax) + "*" + SEPARATOR);
//		} else if (isMultiByte && isUnder30) {
//			build.append(armStr + appendSpace(armStr, rightMax) + "*" + SEPARATOR);
//		} else if (isMultiByte) {
//			build.append(armStr + appendSpace(armStr, rightMax) + "*" + SEPARATOR);
//		}else {
//			if (isUnder30) {
//				//System.out.println("*** AA ***");
//				build.append(armStr + appendSpace(armStr, rightMax) + "*" + SEPARATOR);
//			} else {
//				//	System.out.println("*** BB ***");
//				build.append(armStr + appendSpace(armStr, rightMax) + "*" + SEPARATOR);
//			}
//		}
		// MPを表示する
		String mp = String.valueOf(player.getMP());
		String mpStr = "* MP: " + mp;
		build.append(mpStr + appendSpace(mpStr, sotowaku) + "*");

		if (isMultiByte && isGusu) {
			resizeSotowaku = sotowaku - DEL3;
			build.append(appendSpace("", resizeSotowaku)  + "*" + SEPARATOR);
		} else if (isMultiByte && isGusu == false) {
			resizeSotowaku = sotowaku - DEL3;
			build.append(appendSpace("", resizeSotowaku)  + "*" + SEPARATOR);
		} else if (isMultiByte == false && isGusu) {
			resizeSotowaku = sotowaku - DEL2;
			build.append(appendSpace("", resizeSotowaku)  + "*" + SEPARATOR);
		} else if (isMultiByte == false && isGusu == false) {
			resizeSotowaku = sotowaku - DEL2;
			build.append(appendSpace("", resizeSotowaku)  + "*" + SEPARATOR);
		} else {
			resizeSotowaku = sotowaku - DEL1;
			build.append(appendSpace("", resizeSotowaku)  + "*" + SEPARATOR);
		}
		Map<String, RpgStatus> statusMap = player.getStatusMap();
		int max = 0;
		Set<String> set = statusMap.keySet();
		for (String st : set) {
			RpgStatus s = statusMap.get(st);
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
		Map<String, RpgStatus> optMap = player.getOptionalMap();
		RpgData atk = null;
		RpgData def = null;
		atk = optMap.get(RpgConst.ATK);
		def = optMap.get(RpgConst.DEF);
		if (atk == null || def == null) {
			atk = paramMap.get(RpgConst.ATK);
			def = paramMap.get(RpgConst.DEF);
		}

		int counter = 1;
		if (isDebug) System.out.println("sobisize: " + sobisize);
		String preStr = " ";
		Set<String> keySet = statusMap.keySet();
		for (String st : keySet) {
			RpgStatus data = statusMap.get(st);
			if (data.getKigo().equals("ATK") || data.getKigo().equals("DEF")) {
				continue;
			}
			String n = data.getName();
			String v = String.valueOf(data.getValue());
			boolean isMulti = CheckerUtils.isMultiByteStr(n);

			boolean valueIsGusu = n.length() % 2 == 0 ? true : false;
			String nStr = "* " + n + ": " + v;
			build.append(nStr + appendSpace(nStr, sotowaku) + "*");

			// 攻撃力を表示する部分
			if (counter == 1) {
				String val = atk.getValue() != null ? atk.getValue().toString() : null;
				if (isMultiByte && isGusu) {
					if (isDebug) System.out.println("*** 武器:AA ***");
					build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize,resizeSotowaku));
				} else if (isMultiByte && isGusu == false) {
					if (isDebug) System.out.println("*** 武器:BB ***");
					if (isUnder42) {
						build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
					} else {
						build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
					}
				} else if (isMultiByte == false && isGusu) {
					if (isDebug) System.out.println("*** 武器:CC ***");
					build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				} else if (isMultiByte == false && isGusu == false) {
					if (isDebug) System.out.println("*** 武器:DD ***");
					build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				} else {
					if (isDebug) System.out.println("*** 武器:EE ***");
					build.append(printRightSideStatus(atk.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				}
			// 防御力を表示する部分
			}else if (counter == 2) {
				String val = def.getValue() != null ? def.getValue().toString() : null;

				if (isMultiByte && isGusu) {
					if (isDebug) System.out.println("*** AA ***");
					build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				} else if (isMultiByte && isGusu == false) {
					if (isDebug)  System.out.println("*** BB ***");
					if (isUnder42) {
						build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
					} else {
						build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
					}
				} else if (isMultiByte == false && isGusu) {
					 if (isDebug) System.out.println("*** CC ***");
					build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize,resizeSotowaku));
				} else if (isMultiByte == false && isGusu == false) {
					if (isDebug) System.out.println("*** DD ***");
					build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				} else {
					if (isDebug)  System.out.println("*** EE ***");
					build.append(printRightSideStatus(def.getName(), val, isMultiByte, isGusu, sobisize, resizeSotowaku));
				}
			} else {
				printRightSideStatus(build, "", isMultiByte, sobisize, sotowaku);
			}
			counter++;
		}
		// 外枠を作成
		appendLastLine(build, underLine);
		System.out.println(build.toString());
	}

    /**
     * 右側のステータス表示を行う。
     * @param name 項目名
     * @param value 値
     * @param isMultiByte プレーや名が全角かどうか
     * @param isGusu プレーヤー名が偶数かどうか
     * @param sobisize 装備している名前の最長バイト数
     * @param underLine 表示する一番下の「＊」の数 / 2 (奇数のときは+1)
     * @return 埋め込む文字列
     */
	private String printRightSideStatus(String name,String value, boolean isMultiByte, boolean isGusu, int sobisize, int underLine) {
		String ret = null;
		if ("".equals(name)) {
			if (isMultiByte) {
				ret = appendSpace("", underLine + 1)  + "*" + SEPARATOR;
			} else {
				ret = appendSpace("", underLine)  + "*" + SEPARATOR;
			}
			return ret;
		}

		value = value == null ? "0" : value;
		if (isDebug) System.out.println("Name; " + name + "  Value: " + value + " 偶数: " + isGusu + " isMultiByte: " + isMultiByte + " sobiSize: " + sobisize + " Max: " + underLine);
		String valStr = " " + name + ": " + value;
		if (isMultiByte && isGusu == false && sobisize == 4) {
			// 装備なしの場合
			if (isDebug) System.out.println("*** 全角：偶数：装備なし ***");
			ret = valStr + appendSpace(valStr, underLine) + "*" + SEPARATOR;
		} else if (isMultiByte && isGusu == false) {
			if (isDebug) System.out.println("*** 全角：奇数：装備あり ***");
			ret = valStr + appendSpace(valStr, underLine) + "*" + SEPARATOR;
		} else if (isMultiByte == false && isGusu == false) {
			if (isDebug) System.out.println("*** マルチ：v==0 ***");
			ret = valStr + appendSpace(valStr, underLine) + "*" + SEPARATOR;
		} else if (isMultiByte && isGusu) {
			ret = valStr + appendSpace(valStr, underLine) + "*" + SEPARATOR;
 		} else {
			if (isDebug) System.out.println("*** Other ***");
			ret = valStr + appendSpace(valStr, underLine) + "*" + SEPARATOR;
		}
		return ret;
	}


	/**
	 * ステータス表示の右側をStringBuilderに追加
	 * @param build StringBuilder
	 * @param name 名前
	 * @param isMultiByte　true: 全角 false: 半角
	 * @param sobisize　装備アイテムの最大長文字列数
	 * @param max 外枠のサイズ
	 */
	private void printRightSideStatus(StringBuilder build, String name, boolean isMultiByte, int sobisize, int max) {
		if ("".equals(name)) {
			if (isMultiByte) {
				build.append(appendSpace("", max - DEL3)  + "*" + SEPARATOR);
			} else {
				build.append(appendSpace("", max - DEL2)  + "*" + SEPARATOR);
			}
			return;
		}
		//if (isMultiByte && name.)
		if (isMultiByte) {
			build.append(appendSpace(name, max) + "*" + SEPARATOR);
		} else {
			build.append(appendSpace(name, max) + "*" + SEPARATOR);
		}
	}

	/**
	 * 装備品の文字列のサイズを算出する
	 * @param player プレーヤークラス
	 * @return 装備品の文字列のサイズ
	 */
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

	/**
	 * 装備品の名前を返却する、ただし何も装備していないときは「なし」を返す
	 * @param item アイテムオブジェクト
	 * @return アイテム名もしくは「なし」
	 */
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
				v = "(" + String.valueOf(((Armor) item).getDeffence()) + ")";
			}
			res = n == null ? "なし" : n + v;
		}
		return res;
	}

	/**
	 * 装備品の名前を返却する、ただし何も装備していないときは「なし」を返す
	 * @param item アイテム名
	 * @param isMulti 全角かどうか
	 * @return アイテム名の文字列サイズ
	 */
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
				String val = "(" + String.valueOf(((Armor) item).getDeffence()) + ")";
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
	 * listは０から数えるのが、入力値は1から数えるので「+1」する。
	 * @param player
	 * @return
	 */
	public List<RpgCommand> printCommandList(PlayerCharactor player) {
		RpgJob job = player.getJob();
		List<RpgCommand> cmdList = job.getCommandList();
		for (int i = 0; i < cmdList.size(); i++) {
			RpgCommand cmd = cmdList.get(i);
			System.out.println((i + 1) + ". " + cmd.getName());
		}
		return cmdList;
	}

	/**
	 * プレーヤーの行動選択肢を一覧表示する。
	 *
	 * @param player プレーヤークラス
	 * @return コマンドマップ(indexでソート済み)
	 */
	@Deprecated
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

	/**
	 * 名前の(マルチバイトは2倍にするの)長さ分「*」をStringBuilderに追加する。
	 * 
	 *
	 * @param build StringBuilder
	 * @param name 名前
	 * @return 全体の文字数を返却する(全角部分は２倍計算)
	 */
	private int appendLine(StringBuilder build, String name, boolean isMultiByte) {
		if (isDebug) System.out.println("name; " + name + " sobisize; " + "Multi: " + isMultiByte);
		// 12 - 名前の文字数
		int nameLen = toByteLength(name);
		int astahAll = nameLen;
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(astahAll);
		if (isGusu || isMultiByte) {
			astah = astahAll - DEL2;
		} else if (isGusu == false & isMultiByte) {
			astah = astahAll - DEL1;
		} else if (isGusu == false & isMultiByte == false) {
			astah = astahAll - DEL1;
		} else {
			astah = astahAll - DEL2;
		}
		if (isDebug) System.out.println("isGusu: " + isGusu + "isMulti: " + isMultiByte + "astahAll: " + astahAll + "astah: " + astah);
		if (astah < 5) {
			astah = 4;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String append = null;
		String space = " ";
		int spaceLen = 0;
		boolean nameIsGusu = CheckerUtils.isGusu(name.length());
		if (nameIsGusu == false || isMultiByte) {
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
	 * @return 外枠の文字列長
	 */
	private int appendLine(StringBuilder build, String name, boolean isMultiByte, int sobisize) {
		if (isDebug) System.out.println("name; " + name + " sobisize; " + sobisize + "Multi: " + isMultiByte);
		// 12 - 名前の文字数
		int nameLen = toByteLength(name);
		int astahAll = 0;
		if (isMultiByte) {
			astahAll = nameLen + APPEND_LEN * 2 + sobisize;
		} else {
			astahAll = nameLen + + APPEND_LEN * 4 + sobisize;
		}
		// 名前の両サイドに表示するアスタの数
		int astah = 0;
		// アスタの数が偶数の場合
		boolean isGusu = CheckerUtils.isGusu(astahAll);
		if (isGusu || isMultiByte) {
			astah = astahAll - DEL2;
		} else if (isGusu == false & isMultiByte) {
			astah = astahAll - DEL1;
		} else if (isGusu == false & isMultiByte == false) {
			astah = astahAll - DEL1;
		} else {
			astah = astahAll - DEL2;
		}
		if (isDebug) System.out.println("isGusu: " + isGusu + "isMulti: " + isMultiByte + "astahAll: " + astahAll + "astah: " + astah);
		if (astah < 5) {
			astah = 4;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		String append = null;
		String space = " ";
		int spaceLen = 0;
		boolean nameIsGusu = CheckerUtils.isGusu(name.length());
		if (nameIsGusu == false || isMultiByte) {
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

	/** this#printStatusで使用する */
	private void appendLastLine(StringBuilder build, int sotowaku) {
		//最大文字数にスペースと補助分を追加する
		int astah = 0;
		for (int i = 0; i < sotowaku; i++) {
			build.append("*");
		}
		build.append(SEPARATOR);
	}

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
		String right = null;
		String left = null;
		if (statusValue == null || "".equals(statusValue)) {
			//System.out.println("*** StatusValue ***");
			for (int i = 0; i < max; i++) {
				space.append(" ");
			}
			return space.toString();
		}

		boolean isAstah = false;
		if (statusValue.startsWith(" ")) {
			left = statusValue.substring(1, colonIdx);
		} else {
			isAstah = true;
			left = statusValue.substring(2, colonIdx);
		}
		right = statusValue.substring(colonIdx + 2);

		if (isDebug) System.out.println("left= " + left + " right= " + right);
		boolean isLeftMulti = CheckerUtils.isMultiByteStr(left);
		boolean isRightMulti = CheckerUtils.isMultiByteStr(right);

		int leftLen = isLeftMulti ? (left.length() * 2) : left.length();
		int rightLen = 0;
		// 右側には(NN)がつくことがある
		int kakoIdx = right.indexOf("(");
		String rightStr_1 = "";
		String rightStr_2 = "";
		if ( kakoIdx > 0) {
			rightStr_1 = right.substring(0, kakoIdx);
			rightStr_2 = right.substring(kakoIdx);
			rightLen = isRightMulti ? rightStr_1.length() * 2 + rightStr_2.length() : rightStr_1.length() + rightStr_2.length();
		} else {
			rightLen = isRightMulti ? (right.length() * 2) : right.length();
		}
		// "* "と": "の長さ=4
		int strLen = 0;
		if (isAstah) {
			strLen = leftLen + rightLen + 4;
		} else {
			strLen = leftLen + rightLen + 3;
		}

		len = max - strLen;
		if (isDebug) {
			System.out.println("kakko: " + kakoIdx);
			System.out.println("satusValue: " + statusValue);
			System.out.println("left: " + left + " : right: " + right);
			System.out.println("rightStr_1: " + rightStr_1 + " rightStr_2: " + rightStr_2);
			System.out.println(rightStr_1 + " : " + "leftLen= " + leftLen + ": rightLen= " + rightLen );
			System.out.println("isAstah: " + isAstah);
			System.out.println("strLen: " + strLen);
			System.out.println("len: " + len);
		}
		for (int i = 0; i < len; i++) {
			space.append(" ");
		}
		return space.toString();
	}

	/**
	 * 文字列のバイト単位での長さを算出する。
	 * 半角文字=1, 全角文字=2でカウントする
	 *
	 * @param str 長さを算出する対象の文字列
	 * @return 算出結果: str == nullの場合は0を返却する
	 */
	private int toByteLength(String str) {
		if (str == null) {
			return 0;
		}
		int result = 0;
		int strLen = str.length();
		// 文字数分の配列を用意
		char[] ch = str.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			String st = String.valueOf(ch[i]);
			if (st.getBytes().length <= 1) {
				result += 1;
			} else {
				result += 2;
			}
		}
		return result;
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
	 * help -\> 全体を表示する
	 * help param -\> パラメータのみを表示する
	 * help status -\> ステータスのみを表示する
	 *
	 * @param command コマンド
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

	/**
	 * 所持アイテムを表示する。
	 *
	 * @param player 対象のプレーヤー
	 */
	public void printItemList(PlayerCharactor player) {
		List<RpgItem> itemList = player.getItemBag();
		MainWepon wepon = player.getMainWepon();
		String wepStr = wepon == null ? "なし" : wepon.getName();

		Armor arm = player.getArmor();
		String armStr = arm == null ? "なし" : arm.getName();

		System.out.println("＜アイテム＞");
		// 所持アイテムの表示
		for (int i = 0; i < itemList.size(); i++) {
			RpgItem item = itemList.get(i);
			String itemStr = item.getName();
			if (wepStr.equals(itemStr) || armStr.equals(itemStr)) {
				itemStr = "E-" + itemStr;
			}
			System.out.println((i + 1) + ". " + itemStr);
		}
	}

	/**
	 * 引数のリストのサイズが0であるときにtrue;
	 *
	 * @param itemList チェックするリスト
	 * @return true: 空のリスト false: 空ではないリスト
	 */
	public boolean isZeroSizeItemList(List<?> itemList) {
		if (itemList.size() <= 0) {
			System.out.println(MessageConst.THERES_NO_ITEM.toString());
			return true;
		}
		return false;
	}


	/**
	 * まほうの使用
	 */
	private void useMagics() {

	}

	/**
	 * 引数のマップ内容をすべて表示する。
	 * @param map　パラメータマップ
	 */
	private void printMap(Map<String, ? extends RpgData> map) {
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
