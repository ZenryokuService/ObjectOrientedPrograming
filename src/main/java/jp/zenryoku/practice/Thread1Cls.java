package jp.zenryoku.practice;

import java.util.Scanner;

/**
 * 起動スレッド１、入力受付、表示、３秒待機を繰り返す。
 * ただし「bye」と入力されたら処理を終了する
 * 
 * @author 作成者の名前
 * 2020/10/04
 */
public class Thread1Cls extends Thread {

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Thread1Cls: 処理を開始します");
		
		while(true) {
			System.out.println("＜Thread1Cls＞ 入力してください：");
			String input = scan.nextLine();
			if ("bye".equals(input)) {
				System.out.println("Thread1Cls: 終了します");
				break;
			}
			System.out.println("Thread1: [" + input + "] 5秒待機します");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread1: 待機終了");
		}
	}
}
