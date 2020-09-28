package jp.zenryoku.practice.train;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Test;

import jp.zenryoku.practice.train.cls.Training1;

public class Lv1Mondai {
	/**
	 * テストの実行が終わったら標準入力のストリームを閉じる
	 */
	@AfterClass
	public static void terminated() {
		try {
			System.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void lv1Traing() {
		int num = 0;
		System.out.printf("数字の値は%dです", num);
	}

	@Test
	public void lv2Traing() {
		// 問題にある実行プログラム
		System.out.println("************");
		System.out.println("* Welcome! *");
		System.out.println("************");

		// 改行コードを取得する
		String separator = System.lineSeparator();
		System.out.println("************" + separator + "* Welcome! *" + separator + "************");
	}

	@Test
	public void lv3Traing() {
		// int型の宣言
		int num;
		num = 0;
		// String型の初期化
		String moji = "test";
		System.out.printf(moji + num);
	}

	@Test
	public void lv4Traing() {
		Training1 train = new Training1();
		System.out.println(train.getClass().getName());
	}

	@Test
	public void lv5Traing() throws IOException {
		// 標準入力の読み取り
		int in = System.in.read();
		char input =  (char) in;
		System.out.println("Input: " + input);
	}

	@Test
	public void lv5Traing2() throws IOException {
		// 標準入力の読み取り
		int in;
		System.out.println("終了するときはbを入力して下さい");
		while ((in = System.in.read()) != -1) {
			System.out.write(in);
			if ('b' == in) {
				break;
			}
		}
	}

	@Test
	public void lv6Traing() {
		Scanner scan = new Scanner(System.in);
		String in = scan.nextLine();

		if (in.matches("[a-z]{2,4}")) {
			System.out.println(in);
		} else {
			System.out.println("数字を入力してください");
		}
	}

	@Test
	public void lv7Traing() {
		Scanner scan = new Scanner(System.in);
		System.out.println("整数を入力してください");
		String in = scan.nextLine();
		int num = 0;
		try {
			num = Integer.parseInt(in);
			String a = in.substring(in.length() - 1, in.length());
			System.out.printf("最も下の桁は%sです", a);
		} catch (ClassCastException e) {
			System.out.println("入力値は整数ではありません: " + in);
		} catch (Exception e) {
			System.out.println("想定外のエラーです");
			e.printStackTrace();
		}
	}

	@Test
	public void lv8Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("2つの整数を入力してください");

		String in = scan.nextLine();
		System.out.print("入力値A:" + in + SEP);
		String in2 = scan.nextLine();
		System.out.print("入力値B:" + in2 + SEP);

		double num = 0;
		double num2 = 0;
		try {
			num = Double.parseDouble(in);
			num2 = Double.parseDouble(in2);
			double d = (num / num2) * 100;
			int answer = (int) d;
			System.out.println("Aの値は、Bの" + (answer) + "%です");
		} catch (ClassCastException e) {
			System.out.println("入力値は整数ではありません: " + in);
		} catch (Exception e) {
			System.out.println("想定外のエラーです");
			e.printStackTrace();
		}
	}

	@Test
	public void lv9Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("2つの整数を入力してください");

		String in = scan.nextLine();
		System.out.print("入力値A:" + in + SEP);
		String in2 = scan.nextLine();
		System.out.print("入力値B:" + in2 + SEP);

		double num = 0;
		double num2 = 0;
		try {
			num = Double.parseDouble(in);
			num2 = Double.parseDouble(in2);
			double sho = num / num2;
			double joyo = (num % num2);
			System.out.println("AとBの商は" + sho + "です");
			System.out.println("AとBの剰余は" + joyo + "です");
		} catch (ClassCastException e) {
			System.out.println("入力値は整数ではありません: " + in);
		} catch (Exception e) {
			System.out.println("想定外のエラーです");
			e.printStackTrace();
		}
	}

	@Test
	public void lv10Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("2つの整数を入力してください");

		String in = scan.nextLine();
		System.out.print("入力値A:" + in + SEP);
		String in2 = scan.nextLine();
		System.out.print("入力値B:" + in2 + SEP);

		double num = 0;
		double num2 = 0;
		try {
			num = Double.parseDouble(in);
			num2 = Double.parseDouble(in2);
			double sho = num / num2;
			double joyo = (num % num2);
			System.out.printf("AとBの商は%f、剰余は%fです。", sho, joyo);
		} catch (ClassCastException e) {
			System.out.println("入力値は整数ではありません: " + in);
		} catch (Exception e) {
			System.out.println("想定外のエラーです");
			e.printStackTrace();
		}
	}

	@Test
	public void lv11Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("2つの整数を入力してください");

		String in = scan.nextLine();
		System.out.print("入力値A:" + in + SEP);
		String in2 = scan.nextLine();
		System.out.print("入力値B:" + in2 + SEP);

		double num = 0;
		double num2 = 0;
		try {
			num = Double.parseDouble(in);
			num2 = Double.parseDouble(in2);
			double sho = num / num2;
			double joyo = (num % num2);
			System.out.printf("少数表示で、AとBの商は%f、剰余は%fです。" + SEP, sho, joyo);
			int seiSho = (int) sho;
			int seiJoyo = (int) joyo;
			System.out.printf("整数表示で、AとBの商は%d、剰余は%dです。" + SEP, seiSho, seiJoyo);
		} catch (ClassCastException e) {
			System.out.println("入力値は整数ではありません: " + in + ", " + in2);
		} catch (Exception e) {
			System.out.println("想定外のエラーです");
			e.printStackTrace();
		}
	}

	@Test
	public void lv12Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("入力してください");

		String in = scan.nextLine();
		System.out.print("入力値:" + in + SEP);

		if (in.matches(".*[0-9].*")) {
			System.out.println("Has Number!");
		} else {
			System.out.println("No Number!");
		}

	}

	@Test
	public void lv13Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("入力してください");

		String in = scan.nextLine();
		System.out.print("入力値:" + in + SEP);

		if (in.contains("a" ) && in.contains("z")) {
			System.out.println("Hello AZ!");
		} else {
			System.out.println("No Number!");
		}

	}

	@Test
	public void lv14Traing() {
		// 改行コード
		final String SEP = System.lineSeparator();
		Scanner scan = new Scanner(System.in);
		System.out.println("入力してください");

		String in = scan.nextLine();
		System.out.print("入力値:" + in + SEP);

		if (in.contains("a" ) || in.contains("z")) {
			System.out.println("Hello AZ!");
		} else {
			System.out.println("No Number!");
		}

	}

	@Test
	public void lv15Traing() {
		Scanner scan = new Scanner(System.in);
		System.out.print("入力値：");
		String in = scan.nextLine();
		System.out.println(in.contains("a") ?  "Hello A!" : "Hello World!");
	}

	@Test
	public void lv16Traing() {
		int[] numnum = new int[3];
		numnum[0] = 1;
		numnum[1] = 2;
		numnum[2] = 3;
		System.out.println("配列の2番目：" + numnum[2]);
	}

	@Test
	public void lv17Traoing() {
		Scanner scan = new Scanner(System.in);
		String[] seito = new String[] {"A君", "B君", "C君", "D君", "E君"};

		int gokei = 0;
		try {
			int[] tensu = new int[5];
			for (int i = 0; i < seito.length; i++) {
				System.out.print(seito[i] + ": ");
				String in = scan.nextLine();
				gokei += Integer.parseInt(in);
			}
			System.out.println("平均点数は：" + (gokei / 5) + "点");
		} catch (ClassCastException | NumberFormatException e) {
			System.out.println("整数を入力してください");
		} catch (Exception e) {
			System.out.println("想定外のエラーが起きました");
			e.printStackTrace();
		}
	}

	@Test
	public void lv18Traing() throws IOException {
		// ファイルの読みこみ
		BufferedReader buf = Files.newBufferedReader(Paths.get("src/main/resources/Wepons.csv"));
		// データの取得
		String line = null;

		while((line = buf.readLine()) != null) {
			System.out.println(line);
		}
	}

	@Test
	public void lv19Traing() throws IOException {
		// ファイルの読みこみ
		BufferedReader buf = Files.newBufferedReader(Paths.get("src/main/resources/Wepons.csv"));
		// データの取得
		String line = null;
		List<String[]> list = new ArrayList<String[]>();

		// リストへデータを配列に変換して追加
		while((line = buf.readLine()) != null) {
			list.add(line.split(","));
		}

		// listのサイズ
		System.out.println("CSVののデータ数は" + (list.size() - 1));
		// 配列を表示する
		String[] header = list.get(0);
		String[] data = list.get(2);

		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i] + ": " + data[i]);
		}
	}

	@Test
	public void lv20Traing() throws IOException {
		// ファイルの読みこみ
		BufferedReader buf = Files.newBufferedReader(Paths.get("src/main/resources/Wepons.csv"));
		// データの取得
		String line = null;
		List<String[]> list = new LinkedList<String[]>();

		// リストへデータを配列に変換して追加
		while((line = buf.readLine()) != null) {
			list.add(line.split(","));
		}

		// listのサイズ
		System.out.println("CSVののデータ数は" + (list.size() - 1));
		// 配列を表示する
		String[] header = list.get(0);
		String[] data = list.get(2);

		for (int i = 0; i < header.length; i++) {
			System.out.println(header[i] + ": " + data[i]);
		}
	}

}
