package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.charactors.PlayerParty;
import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.status.RpgFormula;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.XmlUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RpgFormulaTest {
    private static RpgFormula target;
    private static PlayerCharactor player;
    @BeforeAll
    public static void init() throws RpgException {
        TestUtils.initRpgConfig(true);
        player = PlayerParty.getInstance().getPlayer();
    }

    @Test
    public void testFormula() {
        TestUtils.initRpgConfig();
        target = new RpgFormula("(POW + WEV) * (1 + (0.1 * JLV))");
        int res = target.formula(player);
        assertEquals(1, res);
        target = new RpgFormula("(POW + AGI+DEX) / 3 + ARV");
        int res1 = target.formula(player);
        assertEquals(3, res1);
        target = new RpgFormula(" (INT + DEX+KSM) / 3  + MPW");
        int res2 = target.formula(player);
        assertEquals(4, res2);
        target = new RpgFormula("(INT + DEX + AGI) / 3 + TSM ");
        int res3 = target.formula(player);
        assertEquals(4, res3);
        target = new RpgFormula("(((INT + DEX) / 2 ) * 0.1) * ITV");
        int res4 = target.formula(player);
        assertEquals(0, res4);
        target = new RpgFormula("( 1 + KSM * 0.1 )");
        int res5 = target.formula(player);
        assertEquals(1, res5);
        target = new RpgFormula("(AGI + INT + DEX) / 100");
        double res6 = target.formula(player, true);
        assertEquals(0.15, res6);
        target = new RpgFormula("(POW + DEX) / 2 + BPK");
        int res7 = target.formula(player);
        assertEquals(3, res7);
        target = new RpgFormula("ATK");
        //target.setDebug(true);
        int res8 = target.formula(player);
        assertEquals(1, res8);
    }


}
