package jp.zenryoku.practice.train.cls;

import jp.zenryoku.practice.Child1;
import jp.zenryoku.practice.Parent1;

public class ClsSampleMain {
	public static void main(String[] args) {
		System.out.println("*** Parent1とChild1 ***");
		Parent1 parent = new Parent1();
		parent.say();

		Child1 child = new Child1();
		child.say();
	}
}
