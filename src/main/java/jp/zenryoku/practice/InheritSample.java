package jp.zenryoku.practice;

public class InheritSample {
	public static void main(String[] args) {
		Parent1 parent = new Parent1();
		Child1 child = new Child1();

		System.out.println("親クラス：");
		parent.say();
		System.out.println("子クラス：");
		child.say();
	}
}
