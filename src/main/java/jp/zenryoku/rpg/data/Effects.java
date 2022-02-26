package jp.zenryoku.rpg.data;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

import java.util.List;

@Data
public class Effects extends RpgData {
    /** 記号 */
    private String kigo;
    /** 表示文字  */
    private String disp;
    /** 効果式　*/
    private String siki;
    /** メッセージ */
    private String message;
    /** 効果式リスト */
    private List<Effects> effList;

    public Effects() {
    }
    public Effects(String kigo, String disp, String siki, String message) {
        this.kigo = kigo;
        this.disp = disp;
        this.siki = siki;
        this.message = message;
    }
//    public void exeEffect(PlayerCharactor player) throws RpgException;
}
