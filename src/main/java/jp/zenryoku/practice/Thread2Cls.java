package jp.zenryoku.practice;

import java.util.Scanner;

/**
 * 起動スレッド２、３秒待機、入力受付、表示を繰り返す。
 * 
 * @author 作成者の名前
 * 2020/10/04
 */
public class Thread2Cls extends Thread {
	@Override
	public synchronized void run() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Thread2Cls: 処理を開始します");
		
		while(true) {
			System.out.println("Thread2: 5秒待機します");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread2: 待機終了");
			System.out.print("＜Thread2Cls＞ 入力してください：");
			String input = scan.nextLine();
			System.out.println("Thread2Cls: " + input);
			if ("bye".equals(input)) {
				System.out.println("Thread2Cls: 終了します");
				break;
			}
		}
	}
}
