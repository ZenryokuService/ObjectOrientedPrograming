package jp.zenryoku.practice;

/**
 * メインスレッド(メインメソッド)のクラス。
 * 
 * @author 作成者の名前
 * 2020/10/04
 */
public class ThreadMain {
	public static void main(String[] args) {
		Thread1Cls thread1 = new Thread1Cls();
		Thread2Cls thread2 = new Thread2Cls();

		thread1.start();
		thread2.start();
	}
}
