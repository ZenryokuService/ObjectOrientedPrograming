package jp.zenryoku.rpg;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RpgLogicTest {
    private static RpgLogic target;

    /**
     * 指定したファイルを読み込む、BufferedReaderを取得する。
     *
     * @param path ディレクトリのパス
     * @param fileName ファイル名
     * @return 対象のファイルリーダー
     */
    private BufferedReader getBufferedReader(String path, String fileName) {
        BufferedReader buf = null;
        try {
            buf = Files.newBufferedReader(Paths.get(path, fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return buf;
    }

    /**
     * 以下の内容をチェックする
     * 1. シーンタイプが"A"になっているか
     * 2. シーンインデックスが数字になっているか
     * 3. 追加したテキストが想定内か(目視確認)
     * @param scene
     */
    private void assertScene(RpgScene scene) {
        assertEquals("A", scene.sceneType);
        assertTrue(scene.getSceneIndex().matches("[0-9]"));
        scene.getTextList().forEach(System.out::println);
        System.out.println("*** Scene end ***");

    }

    /**
     * シーンタイプを指定して、上のメソッドと同じ処理をする。
     * ※オーバーロード
     * @param scene シーンオブジェクト
     * @param sceneType シーンタイプ
     */
    private void assertScene(RpgScene scene, String sceneType) {
        assertEquals(sceneType, scene.sceneType);
        assertTrue(scene.getSceneIndex().matches("[0-9]"));
        scene.getTextList().forEach(System.out::println);
        System.out.println("*** Scene end ***");

    }

    /** テスト対象クラスをインスタンス化 */
    @BeforeAll
    public static void init() {
        target = (RpgLogic)new TextRpgLogic();
    }

    /**
     * コンストラクタのテスト。
     * SampleRpg_story.txtを読み込みシーンオブジェクトの生成を確認する。
     */
    @Test
    public void testCreateSceneObject() {
        try {
            // シーンオブジェクトのリスト
            List<RpgScene> sceneList = target.getSceneList();
            // コメントのリスト
            List<String> commentList = target.getCommentList();
            // コメントのリストの要素数チェック
            assertNotEquals(0, commentList.size());
            assertEquals(4, commentList.size());
            // シーンリストの要素数チェック
            assertEquals(4, sceneList.size());

            // シーンオブジェクトのチェック
            RpgScene scene = sceneList.get(0);
            assertScene(scene);
            assertEquals("1", scene.nextIndex);
            assertScene(sceneList.get(Integer.parseInt(scene.nextIndex)));

            RpgScene scene1 = sceneList.get(1);
            assertScene(scene1);
            assertEquals("2", scene1.nextIndex);

            RpgScene scene2 = sceneList.get(2);
            assertScene(scene2, "F");
            assertEquals("3", scene2.nextIndex);

            RpgScene scene3 = sceneList.get(3);
            assertScene(scene3);
            assertEquals(null, scene3.nextIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
