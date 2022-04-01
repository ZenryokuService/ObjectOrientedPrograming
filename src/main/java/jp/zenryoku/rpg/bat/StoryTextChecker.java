package jp.zenryoku.rpg.bat;

import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.exception.RpgException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private final String TXT_FILE = "Story.txt";

    /**
     * コンストラクタ。固定のチェック対象ファイルのディレクトリを指定する。
     * @throws RpgException
     */
    public StoryTextChecker() throws RpgException {
        path = "src/main/resources/" + TXT_FILE;
        BufferedReader buf = getBufferedReader();
        // チェック開始
        execute(buf);
    }

    /**
     * コンストラクタ。固定のチェック対象ファイルのディレクトリを指定する。
     * @throws RpgException
     */
    public StoryTextChecker(String path) throws RpgException {
        this.path = path + TXT_FILE;
        BufferedReader buf = getBufferedReader();
        // チェック開始
        execute(buf);
    }

    /**
     * BufferedReaderを取得する。
     * @throws RpgException
     */
    private BufferedReader getBufferedReader() throws RpgException {
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

    public void execute(BufferedReader buf) {

    }
}
