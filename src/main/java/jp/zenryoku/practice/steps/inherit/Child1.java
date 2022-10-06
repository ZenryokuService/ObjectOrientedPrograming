package jp.zenryoku.practice.steps.inherit;

public class Child1 extends Parent {
    private String firstName;
    /** オーバーライド */
    public void greet() {
        System.out.println("My name is " + lastName + " " + firstName);
    }
}

