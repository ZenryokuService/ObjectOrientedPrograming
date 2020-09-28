package jp.zenryoku.practice;

public class InheritSample {
	public static void main(String[] args) {
		Child1 child = new Child1();
		Child2 child2 = new Child2();

		System.out.println("子クラス１：");
		child.say();
		// child.hello();
		System.out.println("子クラス２：");
		child2.say();
		child2.hello();
	}

	private void sample1() {
		Parent1 parent = new Parent1();
		Child1 child = new Child1();

		System.out.println("親クラス：");
		parent.say();
		System.out.println("子クラス：");
		child.say();
	}

	private void sample2() {
		Child1 child = new Child1();
		Child2 child2 = new Child2();

		System.out.println("子クラス１：");
		child.say();
		// child.hello();
		System.out.println("子クラス２：");
		child2.say();
		child2.hello();
	}
}
