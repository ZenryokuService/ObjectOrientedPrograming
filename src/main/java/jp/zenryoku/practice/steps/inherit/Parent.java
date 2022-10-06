package jp.zenryoku.practice.steps.inherit;

public class Parent {
    protected String lastName;
    private String firstName;

    /** コンストラクタ */
    public Parent() {
        lastName = "Minamotono";
        firstName = "Yoritomo";
    }

    public void greet() {
        System.out.println("My name is " + lastName + " " + firstName);
    }
}
