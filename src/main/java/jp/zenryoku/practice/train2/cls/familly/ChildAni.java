package jp.zenryoku.practice.train2.cls.familly;

import java.util.List;

import jp.zenryoku.practice.train2.cls.game.util.CommandData;
import jp.zenryoku.practice.train2.cls.game.util.CommandIF;

public class ChildAni extends Parent implements CommandIF {
    /** 年齢 */
    private int age;
    /** 名前 */
    private String name;

    public ChildAni() {
        name = "taro";
        age = 12;
        createCommandList();
    }

    @Override
    public void say() {
        System.out.println(lastName + "です。");
        System.out.println("兄です。名前は" + name + "です。年齢は" + age + "です。");
        System.out.println("特技は、「" + favorit + "」です。");
    }

    @Override
    public List<CommandData> getCommandList() {
    	addCommand(createCommandData("すかしっぺ", 15));
    	addCommand(createCommandData("漢の一撃", 20));
    	return super.getCommandList();
    }

    /**
     * コマンド実行する
     * @param index
     */
    public int exeCommand(String index) {
    	CommandData data = super.getCommandList().get(Integer.parseInt(index));
    	System.out.println(this.name + "の" + data.getCommandName());
    	return data.getCommandValue();
    }
}