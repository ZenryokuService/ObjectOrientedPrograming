package jp.zenryoku.rpg.bat;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CalcUtils;
import jp.zenryoku.rpg.util.CheckerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ストーリーテキスト(Story.txt)のチェック処理を行う。
 * <h3>Story.txtの書き方ルール</h3>
 *
 */
public class StoryTextChecker {
    /** ファイルまでのパス */
    private String path;
    /** チェック対象フォルダ */
    private String directory;
    /** ストーリーテキストのファイル名 */
    private final String TXT_FILE = "Story.txt";
    /** 合致したときの割合 */
    private final double CHK_OK = 100.0;

    /**
     * コンストラクタ。固定のチェック対象ファイルのディレクトリを指定する。
     * @throws RpgException
     */
    public StoryTextChecker() throws RpgException {
        path = "src/main/resources/story/" + TXT_FILE;
        BufferedReader buf = getBufferedReader();
        // チェック開始
        try {
            execute(buf);
        }  catch (RpgException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException ie) {
            ie.printStackTrace();
            System.out.println(TXT_FILE + "の読み込みに失敗しました。");
        }
    }

    /**
     * コンストラクタ。固定のチェック対象ファイルのディレクトリを指定する。
     * @throws RpgException
     */
    public StoryTextChecker(String path) throws RpgException {
        this.path = path;
        BufferedReader buf = getBufferedReader();
        // チェック開始
        try {
            execute(buf);
        }  catch (RpgException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException ie) {
            ie.printStackTrace();
            System.out.println(TXT_FILE + "の読み込みに失敗しました。");
        }
    }

    /**
     * BufferedReaderを取得する。
     * @throws RpgException
     */
    public BufferedReader getBufferedReader() throws RpgException {
        BufferedReader buf = null;
        try {
            Path p = Paths.get(path);
            buf = Files.newBufferedReader(p);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new RpgException(MessageConst.ERR_FILE_READ.toString());
        }
        return buf;
    }

    /**
     * Story.txtの内容をチェックします。ただしコメント行はチェックしません。<br/>
     * 必ず、シーン定義の中に、以下の定義がある。※アイテム取得は未実装。
     * <ol>
     *     <li>選択肢の定義</li>
     *     <li>モンスター定義</li>
     *     <li>店舗の定義</li>
     *     <li>アイテム・お金の取得、ステータス変化の定義</li>
     *     <li>イベントフラグ(シーンの分岐に使用)の定義</li>
     *     <li>イベントフラグ取得シーン(対象のシーンにたどり着くとフラグを取得する)の定義</li>
     * </ol>
     * <dl>
     *     <dt>シーン定義のチェック</dt>
     *     <dd>
     *         <ol>
     *             <li>シーン番号:シーンタイプのように定義できているかチェック</li>
     *             <li>シーン定義の最後が「END_SCENE 次のシーン番号」になっているかチェック</li>
     *         </ol>
     *     </dd>
     * </dl>
     * @param buf Story.txt
     */
    public void execute(BufferedReader buf) throws RpgException, IOException {
        // 読み込んだ１行分の文字列
        String line = null;
        while ((line = buf.readLine()) != null) {
            // # ,##で始まる行はコメントとして扱うので飛ばす
            if (CheckerUtils.isComment(line)) {
                continue;
            }
            // シーン定義の場合
            if (CalcUtils.calcSceneDefProbability(line) == CHK_OK) {
                String chkLine = buf.readLine();
                // シーンの内容が終わることをチェック
                doCheck(chkLine, "シーン定義の最終行が不適切です。"
                        , chk -> {return CalcUtils.calcEndSceneProbability(chk);});
            } else {
                throw createErMessage(line);
            }

        }
    }

    /**
     * 対象行が不適切であることを例外として作成する
     * @param line 検査対象行
     * @return 対象行が不適切であることの例外
     */
    private RpgException createErMessage(String line) {
        return new RpgException("「" + line + "」が不適切です。");
    }

    /**
     * 関数型を使用したメソッド、このクラスでは、チェック処理とエラーメッセージを渡してチェックする。
     * @param line チェック対象行
     * @param message エラーメッセージ
     * @param fun　チェック用のメソッド(ラムダ)
     * @throws RpgException チェックでエラーになったとき
     */
    private void doCheck(String line , String message, Function<String, Double> fun) throws RpgException {
        if (fun.apply(line) != CHK_OK) {
            throw new RpgException(message + "「" + line + "」");
        }
        System.out.println(line + " is" + fun.apply(line));
    }

    /**
     * シーンが始まったら、その内容をチェックする。
     * @param buf ストーリーテキスト
     * @param message エラーメッセージ
     * @throws RpgException　設定エラー
     */
    private void doInnerCheck(BufferedReader buf, String message) throws RpgException {
        String line = null;
        try {
            while ((line = buf.readLine()) != null) {
                if (CheckerUtils.likeEND_SCENE(line)) {
                    if (CheckerUtils.isEndScene(line) == false) {
                        throw new RpgException("END_SCENEの定義が不適切です。" + line);
                    }
                    // END_SCENEがTRUEの場合は処理を抜ける
                    break;
                }
            }
        } catch (IOException ie) {
            throw new RpgException(MessageConst.ERR_FILE_READ.toString() + line);
        }
    }
}
