package jp.zenryoku.rpg;

import jp.zenryoku.rpg.bat.StoryTextChecker;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class StoryTextCheckerTest {
    @Test
    public void testStoryCheck() {
        try {
            StoryTextChecker check = new StoryTextChecker("src/test/resources/story/");
            BufferedReader buf = check.getBufferedReader();
            check.execute(buf);
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException ie) {
            ie.printStackTrace();
            fail(ie.getMessage());
        }
    }
}
