package jp.zenryoku.practice;

/** 子クラス */
public class Child1 extends Parent1 {
	private int age;

	/** コンストラクタ */
	public Child1() {
		age = 10;
		name = "Taro";
	}

	@Override
	public void say() {
		System.out.println("私の年齢は" + age + "です。");
	}
}
