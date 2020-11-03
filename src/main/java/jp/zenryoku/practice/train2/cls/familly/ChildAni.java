package jp.zenryoku.practice.train2.cls.familly;

public class ChildAni extends Parent {
    /** 年齢 */
    private int age;
    /** 名前 */
    private String name;

    public ChildAni() {
        name = "taro";
        age = 12;
    }

    @Override
    public void say() {
        System.out.println(lastName + "です。");
        System.out.println("兄です。名前は" + name + "です。年齢は" + age + "です。");
        System.out.println("特技は、「" + favorit + "」です。");
    }
}