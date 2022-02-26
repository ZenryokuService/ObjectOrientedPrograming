package jp.zenryoku.rpg.data.status;

import jp.zenryoku.rpg.charactors.players.PlayerCharactor;
import jp.zenryoku.rpg.constants.MessageConst;
import jp.zenryoku.rpg.constants.RpgConst;
import jp.zenryoku.rpg.data.Effects;
import jp.zenryoku.rpg.data.RpgConfig;
import jp.zenryoku.rpg.data.RpgData;
import jp.zenryoku.rpg.exception.RpgException;
import jp.zenryoku.rpg.util.StringUtils;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 一時的なステータス変化を表す。ステータス異常、強化など。
 * ストーリーテキストから生成される。
 */
@Data
public class StEffect extends  Effects {
    /** 記号 */
    private String kigo;
    /** 増減 */
    private String ope;
    /** 対象差数・値 */
    private String kokaValue;
    /**  表示文字 */
    private String disp;
    /** 効果式(複数区化に対応) */
    private List<StEffect> apperList;

    public StEffect(String kigo, String ope, String value) throws RpgException {
        this.kigo = kigo;
        this.ope = ope;
        this.kokaValue = value;
        //apperList = StringUtils.createEffectApeer(line);
    }

    public void exeEffect(PlayerCharactor player) throws RpgException {
        // 対象のカテゴリを取得する
        Map<String, RpgData> paramMap = RpgConfig.getInstance().getParamMap();
        Map<String, RpgStatus> statusMap = RpgConfig.getInstance().getStatusMap();
        RpgData category = paramMap.get(this.kigo);

        RpgStatus stat = null;
        if (category == null) {
            stat = statusMap.get(this.kigo);
        }

        if (category == null && stat == null) {
            throw new RpgException(MessageConst.ERR_NO_APPEAR_SIKI.toString());
        }
        // 増減はセット済み
        // 対象になる項目に値を追加
        if (kigo.equals(RpgConst.ZHP)) {
            player.setHP(exeCalc(player.getHP(), ope, Integer.parseInt(kokaValue)));
        } else if(kigo.equals(RpgConst.ZMP)) {
            player.setMP(exeCalc(player.getMP(), ope, Integer.parseInt(kokaValue)));
        }
    }

    private int exeCalc(int before, String ope, int kokaValue) throws RpgException {
        int result = 0;
        if (ope.equals(RpgConst.PLUS)) {
            result = before + kokaValue;
        } else if (ope.equals(RpgConst.MINUS)) {
            result = before + kokaValue;
        } else {
            throw new RpgException(MessageConst.ERR_OPE.toString());
        }
        return result;
    }
}
