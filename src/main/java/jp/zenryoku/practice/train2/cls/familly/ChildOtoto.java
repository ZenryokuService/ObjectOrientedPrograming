package jp.zenryoku.practice.train2.cls.familly;

public class ChildOtoto extends Parent {
    /** 年齢 */
    private int age;
    /** 名前 */
    private String name;

    public ChildOtoto() {
        name = "jiro";
        age = 10;
        favorit = "鼻を膨らます";
    }

    @Override
    public void say() {
        System.out.println(lastName + "といいます。");
        System.out.println("弟です。名前は" + name + "です。年齢は" + age + "です。");
        System.out.println("特技は、「" + favorit + "」です。");
    }

    @Override
    public void funny() {
        super.funny();
    }
}