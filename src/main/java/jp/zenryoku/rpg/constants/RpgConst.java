package jp.zenryoku.rpg.constants;

public enum RpgConst {
    SAVE(0), CLEAR(1), NEXT(2);

    private int status;

    private RpgConst(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}