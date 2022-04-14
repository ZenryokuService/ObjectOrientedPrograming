package jp.zenryoku.rpg.bat;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.CheckerUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * コンストラクタ。固定のチェック対象ファイルのディレクトリを指定する。
     * @throws RpgException
     */
    public StoryTextChecker() throws RpgException {
        path = "src/main/resources/" + TXT_FILE;
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
            Path p = Paths.get(path, TXT_FILE);
            buf = Files.newBufferedReader(p);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new RpgException(MessageConst.ERR_FILE_READ.toString());
        }
        return buf;
    }

    /**
     * Story.txtの内容をチェックします。ただしコメント行はチェックしません。
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
            if (CheckerUtils.likeSceneDef(line)) {
                // シーン開始行のチェック
                doCheck(line, "シーン定義が不適切です。" + line, li -> CheckerUtils.isStartSceneLine(li));
                // END_CENE XXで終わる
                doInnerCheck(buf, "シーンが閉じられていません。");
            }

        }
    }

    /**
     * 関数型を使用したメソッド、このクラスでは、チェック処理とエラーメッセージを渡してチェックする。
     * @param line チェック対象行
     * @param message エラーメッセージ
     * @param fun　チェック用のメソッド(ラムダ)
     * @throws RpgException チェックでエラーになったとき
     */
    private void doCheck(String line , String message, Predicate<String> fun) throws RpgException {
        if (fun.test(line) == false) {
            throw new RpgException(message);
        }
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
