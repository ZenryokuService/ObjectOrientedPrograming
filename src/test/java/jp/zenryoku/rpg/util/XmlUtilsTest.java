package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class XmlUtilsTest {

    @Test
    public void testCreateMonster() {
        try {
            List<Monster> list = XmlUtils.loadMonsters();
            for (Monster mon : list) {
                System.out.println("Name: " + mon.getName());
                System.out.println("LV: " + mon.getLevel());
                System.out.println("HP: " + mon.getHP());
                System.out.println("MP: " + mon.getMP());
                System.out.println("ATK: " + mon.getAttack());
                System.out.println("DEF: " + mon.getDiffence());
                System.out.println("isTalk: " + mon.isTalk());
                System.out.println("Message: " + mon.getMessage());
            }
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
