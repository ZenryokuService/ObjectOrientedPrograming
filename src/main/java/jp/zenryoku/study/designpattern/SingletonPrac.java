package jp.zenryoku.study.designpattern;

public class SingletonPrac {
    /** 自身のインスタンス */
    private static SingletonPrac instance;
    /** プロパティ１：名前 */
    private String name;
    /** プロパティ２：年齢 */
    private int age;
    /** プロパティ３：血液型 */
    private char bloodType;

    /**
     * 引数なしのコンストラクタ
     * シングルトン実装のため外部からの呼び出し不可。
     */
    private SingletonPrac() {
        this.name = "single";
        this.age = 12;
        this.bloodType = 'A';
    }

    /**
     * 引数ありのコンストラクタは、外部から呼び出し可能。
     * これを使用すると、シングルトンの意味がなくなる。
     *
     * @param name 名前
     * @param age 年齢
     * @param bloodType 血液型
     */
    public SingletonPrac(String name, int age, char bloodType) {
        this.name = name;
        this.age = age;
        this.bloodType = bloodType;
    }

    /**
     * 【シングルトン実装】
     * インスタンスは必ず１つしかできない。
     * インスタンスが生成されているならば、それを返却。
     * そうでなければ、新しいインスタンスを生成して返却する。
     *
     * @return このクラスのインスタンス
     */
    public static SingletonPrac getInstance() {
        if (instance == null) {
            instance = new SingletonPrac();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getBloodType() {
        return bloodType;
    }

    public void setBloodType(char bloodType) {
        this.bloodType = bloodType;
    }
}
