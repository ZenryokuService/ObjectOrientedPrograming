package jp.zenryoku.train2;


import org.junit.jupiter.api.Test;

import jp.zenryoku.practice.train2.cls.familly.ChildAni;
import jp.zenryoku.practice.train2.cls.familly.ChildOtoto;

public class Lv2Mondai {
	@Test
	public void lv1Traing() {
		ChildAni ani = new ChildAni();
		ani.say();
		ani.buy(100);
	}

	@Test
	public void lv2Traing() {
		ChildOtoto ototo = new ChildOtoto();
		ototo.say();
		ototo.funny();
		ototo.buy(50);
	}

}
