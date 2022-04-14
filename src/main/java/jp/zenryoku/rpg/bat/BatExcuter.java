package jp.zenryoku.rpg.bat;

import jp.zenryoku.rpg.exception.RpgException;

import java.io.IOException;

/**
 * ストーリーテキスト、設定ファイルのチェック処理を実行する。
 * TODO-[各種仕様決定後(実装完了後)にチェック処理を実装する]
 */
public class BatExcuter {
    /**
     * チェック対象ファイル。
     * <ul>
     * <li>Commands.xml</li>
     * <li>conf.txt</li>
     * <li>Job.xml/li>
     * <li>MonseterType.xml/li>
     * <li>Monsters.xml/li>
     * <li>Story.txt/li>
     * <li>STM.xml/li>
     * <li>title.txt/li>
     * </ul>
     * @param directory
     */
    public static void executeChecker(String directory) throws RpgException, IOException {
        if ("".equals(directory)) {
            StoryTextChecker story = new StoryTextChecker();
            story.execute(story.getBufferedReader());
        } else {
            StoryTextChecker story = new StoryTextChecker(directory);
            story.execute(story.getBufferedReader());
        }
    }
}
