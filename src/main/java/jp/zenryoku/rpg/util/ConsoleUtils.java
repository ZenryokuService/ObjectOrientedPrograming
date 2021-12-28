package jp.zenryoku.rpg.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import jp.zenryoku.rpg.charactors.Command;
import jp.zenryoku.rpg.charactors.Player;

public class ConsoleUtils {
	/** 自分のクラスのインスタンス */
	private static ConsoleUtils instance;
	/** 枠線上部、下部(一人分の長さ(半角８文字、全角４文字)) */
	private final int MAX_LEN = 12;
	/** 改行文字 */
	private final String SEPARATOR = System.lineSeparator();
	/** 表示するステータスの文字数 */
	private final int STATUS_LEN = 6;
	/** OS名 */
	private final String OS_NAME = System.getProperty("os.name");
	/** 標準出力の切り替え先 */
	private static final ByteArrayOutputStream console = new ByteArrayOutputStream();

	public static ConsoleUtils getInstance() {
		if (instance == null) {
			instance = new ConsoleUtils(false);
		}
		return instance;
	}

	public static ConsoleUtils getInstanceForTest() {
		if (instance == null) {
			instance = new ConsoleUtils(true);
		}
		return instance;
	}

	/**
	 * コンストラクタ。
	 * Propertyファイルを読み込みステータス表示のフォーマットを取得する。
	 *
	 */
	public ConsoleUtils(boolean isTest) {
		if (isTest) {
			// テストのときはPrintStreamクラスをし標準出力先に変更する
			System.setOut(new PrintStream(console));
		}
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
		appendLine(build, name.length(), isGusu);
		System.out.println(build.toString());
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
			astah = (astahAll + 1) / 2;
		}
		for (int i = 0; i < astah; i++) {
			build.append("*");
		}
		build.append(" " + name + " ");
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
	private void appendLine(StringBuilder build, int nameLen, boolean isGusu) {
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

	/**
	 * ステータス表示のために、引数の後ろにスペースを追加する。
	 *
	 * @param statusValue 最終的にスペースが追加された文字列
	 */
	private String appendSpace(String statusValue, boolean isGusu) {
		StringBuilder space = new StringBuilder();
		int len = 0;
		if (isGusu) {
			// 偶数の場合、最大文字数 + 2(スペース2個分) - 1(後ろにつけるアスタ分)
			len = (STATUS_LEN + 2) - statusValue.length() - 1;
		} else {
			// 奇数の場合、最大文字数 + 3(スペース2個分)
			len = (STATUS_LEN + 3) - statusValue.length() - 1;
		}
		for (int i = 0; i < len; i++) {
			space.append(" ");
		}
		return space.toString();
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
}
