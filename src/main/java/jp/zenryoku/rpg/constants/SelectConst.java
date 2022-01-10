package jp.zenryoku.rpg.constants;

import lombok.Data;

/** 選択時に使用する定数 */
public enum SelectConst {
    ////////////////////////////////
    // 選択時に使用する判定文字(3)       //
    ////////////////////////////////
    SELECT_YES("1", "yes"),
    SELECT_NO("2", "no"),
    ;

    /** 選択時に使用する値 */
    private String value;
    /** 表示する値 */
    private String printValue;

    private SelectConst(String value, String printValue) {
        this.value = value;
        this.printValue = printValue;
    }

    public String getValue() {
        return value;
    }
    public String getPrintValue() {
        return printValue;
    }
    public String getSelectMessage() {
        return value + ": " + printValue;
    }
}
