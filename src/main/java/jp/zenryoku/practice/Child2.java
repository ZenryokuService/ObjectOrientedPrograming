package jp.zenryoku.practice;

/** 子クラス2 */
public class Child2 extends Parent1 {
	private int age;
	private int tall;
	private int weight;
	private String favorit;

	/** コンストラクタ */
	public Child2() {
		age = 5;
		name = "Jiro";
		tall = 160;
		weight = 50;
		favorit = "プログラミング";
	}

	@Override
	public void say() {
		System.out.println("私の名前は" + name + "です。");
		System.out.println("私の年齢は" + age + "です。");
	}

	public void hello() {
		System.out.println("こんにちは、私の名前は" + name + "です。");
		System.out.println("私の身長は" + tall + "で、体重は" + weight + "です。");
		System.out.println("趣味は" + favorit + "です。");
	}
}
