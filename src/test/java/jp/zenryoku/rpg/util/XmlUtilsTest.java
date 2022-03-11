package jp.zenryoku.rpg.util;

import jp.zenryoku.rpg.TestUtils;
import jp.zenryoku.rpg.charactors.monsters.Monster;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.job.RpgCommand;
import jp.zenryoku.rpg.data.job.RpgJob;
import jp.zenryoku.rpg.data.job.RpgMonsterType;
import jp.zenryoku.rpg.data.status.RpgStatus;
import jp.zenryoku.rpg.exception.RpgException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

public class XmlUtilsTest {

    @Test
    public void testCreateMonster() {
        TestUtils.initRpgConfig();
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
                Map<String, RpgStatus> stMap = mon.getStatusMap();
                Set<String> keys = stMap.keySet();
                for (String key : keys) {
                    RpgStatus status = stMap.get(key);
                    System.out.println(key + " : "  + status.getValue());
                }
            }
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateJob() {
        try {
            Map<String, RpgCommand> cmdMap = XmlUtils.loadCommands();
            RpgConfig.getInstance().setCommandMap(cmdMap);
            Map<String, RpgJob> map = XmlUtils.loadJobs();
            Set<String> set = map.keySet();
            for (String key : set) {
                RpgJob job = map.get(key);
                System.out.println("ID: " + job.getJobId());
                System.out.println("Name: " + job.getName());
                System.out.println("HP: " + job.getDiscription());
                List<RpgCommand> cmdList = job.getCommandList();
                System.out.print("CMD: ");
                for (RpgCommand cmd : cmdList) {
                    System.out.print(cmd.getCommandId() + ", ");
                }
                System.out.println();
            }
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateMonsterType() {
        try {
            Map<String, RpgCommand> cmdMap = XmlUtils.loadCommands();
            RpgConfig.getInstance().setCommandMap(cmdMap);
            Map<String, RpgMonsterType> map = XmlUtils.loadMonterType();
            Set<String> set = map.keySet();
            for (String key : set) {
                RpgMonsterType job = map.get(key);
                System.out.println("ID: " + job.getJobId());
                System.out.println("Name: " + job.getName());
                System.out.println("Disc: " + job.getDiscription());
                List<RpgCommand> cmdList = job.getCommandList();
                System.out.print("CMD: ");
                for (RpgCommand cmd : cmdList) {
                    System.out.print(cmd.getCommandId() + ", ");
                }
                System.out.println();
            }
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testCreateCommand() {
        try {
            Map<String, RpgCommand> map = XmlUtils.loadCommands();
            Set<String> set = map.keySet();
            for (String st : set) {
                RpgCommand cmd = map.get(st);
                System.out.println("ID: " + cmd.getCommandId());
                System.out.println("Name: " + cmd.getName());
                System.out.println("Formula: " + cmd.getFormula());
            }
        } catch (RpgException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
