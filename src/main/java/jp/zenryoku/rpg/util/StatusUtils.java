package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.params.PlayerStatus;

/**
 * ステータス生成、そのほかステータス関連の処理の実装クラス。
 *
 * @author 作成者の名前
 */
public class StatusUtils {
	/** エラーのフラグ */
	public static final int ERROR = 0;
	/** ケテル */
	public static final String KETER = "1";
	/** コクマー */
	public static final String CHOKHMAH = "2";
	/** ビナー */
	public static final String BINAH = "3";
	/** ケセド */
	public static final String CHESED = "4";
	/** ゲプラ */
	public static final String GEVURAH = "5";
	/** ティファレト */
	public static final String TIPHERETH = "6";
	/** ネツァク */
	public static final String NETZACH = "7";
	/** ボド */
	public static final String HOD = "8";
	/** イェソド */
	public static final String YESOD = "9";
	/** マルクト */
	public static final String MALKUTH = "10";

	private enum StatusValue {

		POW("1"),
		BIN("2"),
		TAI("3"),
		KI("4"),
		GAK("5"),
		MEI("6"),
		SEI("7"),
		KAN("8"),
		SIN("8"),
		REI("9");

		/** 値 */
		private int value;

		private StatusValue(String value) {
			this.value = Integer.parseInt(value);
		}

		public int getValue() {
			return this.value;
		}
	}

	/**
	 * 生年月日から、「月」「日」「月と日の合計」「西暦の下2桁」を算出する。
	 * ヨガ数秘の表を作るための要素となる数を算出し配列に格納。
	 *
	 * ＜1999年12月4日生まれの場合＞
	 * <table border="1">
	 * <tr><td>算出する順番</td><td>計算方法</td><td>例</td></tr>
	 * <tr><td>No1</td><td>誕生月</td><td>12 => 1 + 2 = 3</td></tr>
	 * <tr><td>No2</td><td>誕生日</td><td>04 => 4</td></tr>
	 * <tr><td>No3</td><td>No1 + No2</td><td>3 + 4 = 7</td></tr>
	 * <tr><td>No4</td><td>西暦の下2桁</td><td>99 => 9 + 9 = 18 => 1 + 8 = 9</td></tr>
	 * <tr><td>No5</td><td>西暦の4桁</td><td>1 + 9 + 9 + 9 = 28 => 2 + 8 = 10</td></tr>
	 * <tr><td>No6</td><td>No4 + No5</td><td>9 + 10 = 19 = 1 + 9 = 10</td></tr>
	 * <tr><td>No7</td><td>No2 + No4</td><td>4 + 9 = 13 => 1 + 3 = 4</td></tr>
	 * <tr><td>No8</td><td>No1 + No5</td><td>3 + 10 => 13 => 1 + 3 = 4</td></tr>
	 * <tr><td>No9</td><td>生年月日</td><td>1 + 9 + 9 + 9 + 9 + 1 + 2 + 4 = 44 => 8</td></tr>
	 * </table>
	 *
	 * @param birthDate 生年月日(yyyyMMdd)
	 * @return ヨガ数秘の配列：JavaDoc参照
	 */
	public static String[] createYogaSuhi(String birthDate) {
		String[] result = new String[9];
		// 月を算出
		result[0] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(4,6)));
		// 日を算出
		result[1] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(6,8)));
		// 月と日の合計を算出
		String v1 = convertIntToString(Integer.parseInt(result[0]) + Integer.parseInt(result[1]));
		result[2] = String.valueOf(StatusUtils.convertStringToInt(v1));
		// 西暦の下2桁を算出
		result[3] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(2, 4)));
		// 西暦4桁
		result[4] = String.valueOf(StatusUtils.convertStringToInt(birthDate.substring(0, 4).toCharArray()));
		// 4 + 5
		int gokei = Integer.parseInt(result[3]) + Integer.parseInt(result[4]);
		result [5] = String.valueOf(StatusUtils.convertStringToInt(String.format("%02d",gokei)));
		// 2 + 4
		int gokei2 = Integer.parseInt(result[1]) + Integer.parseInt(result[3]);
		result [6] = String.valueOf(StatusUtils.convertStringToInt(String.format("%02d",gokei2)));
		// 1 + 5
		int gokei3 = Integer.parseInt(result[0]) + Integer.parseInt(result[4]);
		result [7] = String.valueOf(StatusUtils.convertStringToInt(String.format("%02d", gokei3)));
		// 生年月日
		result[8] = String.valueOf(StatusUtils.convertStringToInt(birthDate.toCharArray()));

		return result;
	}

	/**
	 * 西暦4桁、または、生年月日8桁を合計して数秘的合算を行う。
	 * @param seireki 西暦4桁
	 * @return 合算値
	 */
	public static int convertStringToInt(char[] seireki) {
		if (4 != seireki.length && 8 != seireki.length) {
			return ERROR;
		}
		int num = 0;

		for (char ch : seireki) {
			String str = String.valueOf(ch);
			num += Integer.parseInt(str);
		}

		while (num > 10) {
			num = convertStringToInt(String.valueOf(num));
		}
		return num;
	}

	/**
	 * 数秘術演算、２桁の数字を１桁にする
	 * @param val 2桁の数字
	 * @return 1桁の数、エラーは0を返す
	 */
	public static int convertStringToInt(String val) {
		int check = checkReturnNum(val);
		while (check > 10) {
			check = convertStringToInt(String.valueOf(check));
		}
		// 値が１０以下になっている
		return check;
	}

	/**
	 * 数秘術演算、２桁の数字を１桁にする
	 * @param val 2桁の数字
	 * @return 1桁の数、エラーは0を返す
	 */
	public static String convertStringToInt(String val, String val2) {
		int num1 = convertStringToInt(val);
		int num2 = convertStringToInt(val2);
		int gokei = num1 + num2;

		while (gokei > 10) {
			gokei = convertStringToInt(String.valueOf(gokei));
		}
		return String.valueOf(gokei);
	}

	/**
	 * 二桁の数字文字列を、分割し合計する。
	 * 例；12 => 1 + 2 = 3
	 *
	 * @param val
	 * @return 数秘的な合計値
	 */
	public static int checkReturnNum(String val) {
		// 2桁である確認
		if (val != null && val.length() != 2) {
			return ERROR;
		}
		String strLeft = val.substring(0,1);
		String strRight = val.substring(1,2);

		int left = Integer.parseInt(strLeft);
		int right = Integer.parseInt(strRight);

		return (left + right);
	}

	/**
	 * PlayerStatusクラスを生成して返却する。
	 * ※近(0マス)・中(1マス)・長(2マス)分の距離
	 * <table border="1">
	 * <tr><td>ステータス名</td><td>説明</td><td>効力範囲</td></tr>
	 * <tr><td>No1</td><td>力</td><td>近・中・長距離の物理攻撃</td></tr>
	 * <tr><td>No2</td><td>敏</td><td>攻撃と回避に影響する</td></tr>
	 * <tr><td>No3</td><td>体</td><td>体力・体の能力</td></tr>
	 * <tr><td>No4</td><td>器</td><td>器用さ・察しの良さ</td></tr>
	 * <tr><td>No5</td><td>学</td><td>学習能力・成長力</td></tr>
	 * <tr><td>No6</td><td>命</td><td>命中力・的中力</td></tr>
	 * <tr><td>No7</td><td>精</td><td>精神力・忍耐力・集中力</td></tr>
	 * <tr><td>No8</td><td>感</td><td>感受性・直観力</td></tr>
	 * <tr><td>No9</td><td>信</td><td>誠実力・信仰力</td></tr>
	 * <tr><td>No10</td><td>霊</td><td>霊感力・霊力・精霊との交信力</td></tr>
	 * </table>
	 *
	 * @param suhi ヨガ数秘の配列
	 * @return PlayerStatus
	 * @throws Exception
	 */
	public static PlayerStatus createStatus(String[] suhi) throws Exception {
		if (suhi == null || suhi.length != 9) {
			return null;
		}

		return createStatusValue(suhi);
	}

	/**
	 *
	 * @param suhi チェック済みの数秘算出値を渡す
	 * @return ステータス値
	 */
	private static PlayerStatus createStatusValue(String[] suhi) throws Exception {
		int[] stat = new int[10];

		for (String su : suhi) {
			switch(su) {
				case PlayerStatus.POW:
					stat[0]++;
					break;
				case PlayerStatus.BIN:
					stat[1]++;
					break;
				case PlayerStatus.TAI:
					stat[2]++;
					break;
				case PlayerStatus.KI:
					stat[3]++;
					break;
				case PlayerStatus.GAK:
					stat[4]++;
					break;
				case PlayerStatus.MEI:
					stat[5]++;
					break;
				case PlayerStatus.SEI:
					stat[6]++;
					break;
				case PlayerStatus.KAN:
					stat[7]++;
					break;
				case PlayerStatus.SIN:
					stat[8]++;
					break;
				case PlayerStatus.REI:
					stat[9]++;
					break;
				default :
					throw new Exception("引数の値が不正です。" + su);
			}
		}
		PlayerStatus status = new PlayerStatus(stat);

		return status;
	}

	private static String convertIntToString(int val) {
		return String.format("%02d", val);
	}

	public static void addPlayerStatus(PlayerStatus status, String[] suhi) {
		// 2, 3, 7, 8の値を取得
		String no2 = suhi[1];
		String no3 = suhi[2];
		String no7 = suhi[6];
		String no8 = suhi[7];
		try {
			status.add(selectTarot(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 大アルカナに対応するステータスを生成する。
	 * @param tarotKey セフィロトのパスを表すキー
	 * @throws Exception
	 */
	public static PlayerStatus selectTarot(String tarotKey) throws Exception {
		PlayerStatus status = null;
		switch(tarotKey) {
		case PlayerStatus.THE_FOOL:
			status = new PlayerStatus(new int[] {0, 0, 1, 1, 0, 0, 0, 0, 1, 0});
			break;
		case PlayerStatus.THE_MAGICIAN:
			status = new PlayerStatus(new int[] {1, 0, 1, 0, 0, 0, 0, 1, 0, 0});
			break;
		case PlayerStatus.THE_PRIESTESS:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 1, 0, 0, 1, 1, 0});
			break;
		case PlayerStatus.THE_EMPRESS:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_EMPEROR:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_HIEROPHANT:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_LOVERS:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_CHARIOT:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.STRENGTH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_HERMIT:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.WHEEL_OF_FORTUNE:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.JUSTICE:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_HANGED_MAN:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.DEATH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.TEMPERANCE:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_DEVIL:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_TOWER:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_STAR:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_MOON:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_SUN:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.JUDGEMENT:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case PlayerStatus.THE_WORLD:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		default:
			throw new Exception("タロットキーが不適当です。" + tarotKey);
		}
		return status;
	}

	/**
	 * 大アルカナに対応するステータスを生成する。
	 * @param sephirothNo セフィロトの番号(1-10)
	 * @throws Exception
	 */
	public static PlayerStatus selectSephira(String sephirothNo) throws Exception {
		PlayerStatus status = null;
		switch(sephirothNo) {
		case KETER:
			status = new PlayerStatus(new int[] {0, 0, 1, 1, 0, 0, 0, 0, 1, 0});
			break;
		case CHOKHMAH:
			status = new PlayerStatus(new int[] {1, 0, 1, 0, 0, 0, 0, 1, 0, 0});
			break;
		case BINAH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 1, 0, 0, 1, 1, 0});
			break;
		case CHESED:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case GEVURAH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case TIPHERETH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case NETZACH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case HOD:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case YESOD:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		case MALKUTH:
			status = new PlayerStatus(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
			break;
		default:
			throw new Exception("セフィロトNoが不適当です。" + sephirothNo);
		}
		return status;
	}
}
