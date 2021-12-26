package jp.zenryoku;

import jp.zenryoku.rpg.Games;
import jp.zenryoku.rpg.RpgScene;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.scene.BattleScene;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class RpgLogic implements Games {
    /** 改行コード */
    protected String SEP;
    /** 標準入力 */
    protected Scanner scan;
    /** タイトル画面デザイン */
    protected BufferedReader reader;
    /** 終了ステータス */
    protected RpgConst status;
    /** 現在のシーン */
    protected RpgScene scene;
    /** 使用するシーンのリスト */
    List<RpgScene> sceneList;

    public RpgLogic() {
        // 改行コード
        SEP = System.lineSeparator();
        scan = new Scanner(System.in);
        status = RpgConst.CLEAR;
        sceneList = new ArrayList<>();
        sceneList.add(new BattleScene());
        // TODO-[複数シーンに対応するための実装を追加予定]
        scene = sceneList.get(0);

        // title.txtの読み込み
        try {
            reader = Files.newBufferedReader(Paths.get("src/main/resources", "title.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    /**
     * 入力受付処理。
     * ※JavaAPIを呼び出すだけなので、テスト不要。
     */
    @Override
    public String acceptInput() {
        // 入力受付を返却する(一行分)
        return scan.nextLine();
    }

    protected abstract void executeScene();

}
