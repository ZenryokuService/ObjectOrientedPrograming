package jp.zenryoku.practice.train2.cls.familly;

public class Parent extends Gosenzo {
	/** 家計 */
	public static int kakei = 1000;
	/** 苗字 */
	protected String lastName;
	/** 名前 */
	private String name;
	/** 年齢 */
	private int age;
	/** 趣味・特技 */
	public String favorit;

	/** コンストラクタ */
	public Parent() {
		lastName = "tanaka";
		name = "takao";
		age = 50;
		favorit = "ケツで箸を割る";
	}

	/** Gosenzoクラスの抽象メソッド */
	@Override
	public String kekkeiGenkai() {
		return "白目をむく";
	}

	/** 自己紹介 */
	public void say() {
		System.out.println(lastName + "と申します。");
		System.out.println("親です。名前は" + name + "です。年齢は" + age + "です。");
		System.out.println("特技は、「" + favorit + "」です。");
	}

	/** おかしな部分 */
	protected void funny() {
		System.out.println("喜びを真逆の言葉で表現する。");
	}

	/** 買い物 */
	public void buy(int money) {
		kakei -= money;
		System.out.println("残金：" + kakei);
	}
}
