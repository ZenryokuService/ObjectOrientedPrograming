package jp.zenryoku.practice;

/** 親クラス */
public class Parent1 {
	private int age;
	protected String name;
	public final String GROUP_A = "groupA";

	/** コンストラクタ */
	public Parent1() {
		age = 40;
		name = "Gonbe";
	}
	public void say() {
		System.out.println("私の年齢は" + age + "です。");
	}
}