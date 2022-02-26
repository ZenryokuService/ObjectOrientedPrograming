package jp.zenryoku.rpg.data.items;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.status.StEffect;
import jp.zenryoku.rpg.exception.RpgException;
import lombok.Data;

import java.util.List;

@Data
public class EvEffect extends Effects {
    /** 記号 */
    private String kigo;
    /** 増減 */
    private String ope;
    /** 対象差数・値 */
    private String kokaValue;
    /** 効果式(複数区化に対応) */
    private List<StEffect> apperList;

    public EvEffect(String kigo, String ope, String value) throws RpgException {

        this.kigo = kigo;
        this.ope = ope;
        this.kokaValue = value;
    }

    public void exeEffect(PlayerCharactor player) throws RpgException {

    }
}
