package jp.zenryoku.rpg.bat;

import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class StoryTextCheckerTest {
    private static StoryTextChecker target;

    @BeforeAll
    public static void init() {
        try {
            target = new StoryTextChecker();
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testExecute() {
    }
}
