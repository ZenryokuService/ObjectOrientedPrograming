# テキストRPGの書き方
**【前提】**  
テキストRPGゲームの圧縮ファイルをダウンロードしていること。
ファイルは**[textRpg.zip](https://zenryokuservice.com/served/textRpg.zip)という名前です。リンクをクリックするとダウンロードが始まります。


### 編集可能なファイルの一覧
textRpg.zipを展開すると下のようなファイル(フォルダ含む)が入っています。
* docs: JavaDoc, UMLドキュメントがHTML形式で入っています。
* game: サンプルのストーリーが入っています。
* test: テキストRPGのテストを行うためのストーリーと設定ファイルが入っています。
* ObjectOrientedPrograming.jar: テキストRPGの実行ファイルです。
* README.txt: ドキュメントファイルのある場所が書いてあります。

### game,testフォルダ内のファイル
1. Commands.xml：コマンドの定義ファイル、各職業の使用できるコマンドを定義する
2. conf.txt：設定ファイル、各種パラメータの定義を行う
3. Job.xml: 各種職業を定義する、使用するコマンドの定義するのでCommands.xmlに依存する
4. MonseterType.xml: モンスタータイプ(プレーヤーの職業に相当)を定義する
5. Monsters.xml: モンスターを定義する、これも同様にMonsterType.xmlに依存する
6. STM.xml：コマンドの「まほう」「わざ」のような子供要素を持つものに対して、子要素の部分を定義する
7. Story.txt: このゲームのストーリーを定義する。文字表示、選択肢、バトル、イベントフラグ、アイテムショップなど1-6までのファイルすべてに依存する
8. title.txt: タイトル部分を表示する、おしゃれなタイトル表示ができるとよい
※(注) title.txtに関して、サンプルはあてにしないでください。

## 各シーンの定義
このプログラムは、シーンの表示内容を変えながらゲームを進めていくようにできています。
処理の順序を示す。シーケンス図などは、**textRpg/docs/html-docs/index.html**を参照ください。

単純にシーンを表示(文字列を表示する)だけであれば、下のように定義します。

＜例1：**単純にストーリーを表示する**＞
```
0:A
# 第一章：ごぜんじあいに出る
むかしむかし、あるところに、おじいさんとおばあさんがいました。
ある日おじいさんは、山へしばかりに、おばあさんは川へせんたくに行きました。
おや？おじいさんか、おばあさんが、何かを見つけたようです。みつけたのはどちら？
<1:2>
1. おじいさん 1
2. おばあさん 2
END_SCENE 1

1:A
おじいさんが、しばかりをしていると、竹やぶに光る竹を見つけました。
ふしぎに思い、ちかずいてみると。。。
なんと、竹が光だし、そこに女の子があらわれました。
びっくりしたおじいさんは、こしをぬかしてギックリゴシになってしまいました。
END_SCENE 9

```
"0:A"の行は「シーン番号0のシーンタイプは文字表示のみ」という意味似なります。
書き方は以下の通りです。

1. シーン番号(インデックス):0-999までの数字を設定することができる。次画面のシーンインデックスに「C」を指定することでゲーム終了する。

2. シーンタイプ: 各シーンの型を決める。シーンタイプは以下のようになっている。

|　シーンタイプ | 意味 |
| ------------ | -------- |
| A | 単純にストーリーを表示する　|
| B | ショップシーンを表示する　|
| C | スタータス変化のシーンを表示する　|
| D | アイテム取得シーンを表示する　|
| E | 終了シーンを表示する　|
| F | バトルシーンを表示する　|
| G | プレーヤー生成シーンを表示する　|

### 1.単純なシーン定義
```
シーン番号:シーンタイプ
ストーリー内容
END_SCENE XX
```
「シーン番号:シーンタイプ」の行と「END_SCENE XX」の行で挟み込んでやる形です。

#### シーン番号1のストーリー表示シーンの場合
**シーン番号1, ストーリー表示のみ, 次のシーン番号は2とする場合**

```
1:A
シーン番号1のストーリー表示シーン
このシーンは文字を表示するだけです
END_SCENE 2
```

### 2.シーンの中に選択肢を入れる
```
2:A
選択肢のあるシーンを定義しています
<1:3>
1. そのとおり 2
2. いやちがうね 3
3. これでいいのだ 4
END_SCENE 2
```
この状態で、１を選択すればシーン番号２のシーンへ移動、どうように２を選択すればシーン番号３へ移動
というような形で処理が動きます。

実際に使用するときは、下のような形になります。
```
0:A
むかしむかし、あるところに、おじいさんとおばあさんがいました。
ある日おじいさんは、山へしばかりに、おばあさんは川へせんたくに行きました。
おや？おじいさんか、おばあさんが、何かを見つけたようです。みつけたのはどちら？
<1:2>
1. おじいさん 1
2. おばあさん 2
END_SCENE 1
```

### 3.プレーヤー生成シーン
これは、決まった形なので、初めに文言を入れるかどうかというところです。
書き方は次の通りです。シーンタイプは「G]を指定します。
```
シーン番号:G
文章 例：プレーヤーの作成を行います。ダイスコード、使用するステータス(以下サンプル)、の書き方
<1D6>
ちから: 攻撃力、武器・防具の持てる合計重量を示す。:POW
すばやさ: 行動の速さ、相手と５以上の差があるとき２回攻撃。:AGI
かしこさ: 魔法・術などの効果量を示す。:INT
きようさ: 使える武器、防具、魔法・術などの種類が増える。:DEX
カリスマ: 人やモンスターに好かれる度合、統率力を示す。:KSM
END_SCENE 1
```

「&lt;1D6&gt;」はダイスコードと呼んでいます。これはプレーヤー生成時にちからなど「conf.txt」で定義している
値を家呈するときに使用するバーチャル・サイコロ(?)を決定するものです。
上の場合は「**６面ダイスを１回振る**」という意味になります。
また「&lt;2D12&gt;」であれば、「**１２面ダイスを２回振る**」という意味になります。

#### プログラムの動き
プレーヤー生成シーンを起動するプログラムは**CreatePlayerScene**というクラスに定義しています。
プログラムでは、PlayerCharactorクラスのインスタンスを生成します。

##### PlayerCharactorクラス
**Player**クラスを親に持ちますので**Player**クラスのプロパティ(フィールド変数)にもアクセスできます。
一部コードを抜粋します。クラス定義とフィールド変数のみを抜粋しました。
「@Data」とあるアノテーションは**lombok**というjarファイル(ライブラリ)を使用しています。
これを付けることにより「Getter」と「Setter」を実装しなくても、実装したことになります。
具体的には、したのようなコードを書かなくても書いたことにできます。
例として次のフィールド変数に関しては、GetterとSetterを書かなくてよくなります。

そして、以下の点に注意です。
1. 「private」とついているフィールド、メソッドは**PlayerCharactorクラスから参照できません**。
2. 「protected」とついているフィールド、メソッドは**PlayerCharactorクラスから参照できます**。
3. 「public」とついているフィールド、メソッドは**どのクラスからでも参照できます**。

**フィールド変数**  
```
/** 生年月日(これは定義済み) */
private String birthDay;
```
**lombokで書いたことにできるコード**
```
/** Getter */
public String getBirthDay() {
	return this.birthDay;
}

/** Getter */
public void setBirthDay(String birthDay) {
	this.birthDay = birthDay;
}

```

＜lombokを使用したサンプルコード＞
```java
@Data
public class PlayerCharactor extends Player {
    private static final boolean isDebug = false;
    /** 生年月日 */
    private String birthDay;
    /** ステータスリスト */
    protected Map<String, RpgStatus> statusMap;
    /** オプショナルステータスリスト */
    protected Map<String, RpgStatus> optionalMap;
    /** ステータス異常(変化)リスト */
    protected List<Effects> effectList;
    /** 動けるフラグ */
    protected boolean canMove;
    /** 職業 */
    protected RpgJob job;
    /** 経験値 */
    protected int exp;
    /** STMマップ */
    protected List<RpgStm> stmList;

}
```

そして、プレーヤー生成処理に関しては**CreatePLayerScene**クラスが処理を行っています。  
注意として、設定ファイル(Story.txtなど)の読込は**RpgLogic**クラスで行っております。

＜処理内容＞
1. Story.txtで定義した文章の表示
2. 設定ファイルCONFIG_PARAMにダイスコードが定義されているかチェック
3. 名前入力の受付と値のセット(PlayerCharactor#name)※Playerクラスに名前が定義してあります。
4. 同様に誕生日
5. 設定ファイル、CONFIG_STATUSで定義したパラメータ(ちからなど)にダイスコードで指定した範囲の生成した乱数値をセット
6. CONFIG_STATUSには定義していないが、CONFIG_PARAM内にある、「PLY」がついている行に関して、これを**オプショナルステータスマップ**に登録、プレーヤーのオプショナルステータスとして使用する

### 4.バトルシーンの定義
簡単です。シーンタイプは「F]を指定します。
```
<monster:モンスター番号\>
戦闘後のメッセージ。
</monster>
```
ちなみに、「戦闘後のメッセージ」は、工事中のため表示されません。。。すいません。。。

実際に使用するときは、こんな形になります。
```
18:F
まおうじょうのモンスターでした。
<monster:11>
平和が訪れた。
</monster>
END_SCENE 19
```

ここで「&lt;monster:数字&gt;」の中にある数字に関しては、「Monsters.xml」に依存します。
Story.txtのあるディレクトリ(フォルダ)にMonsters.xmlには、好きなモンスターを定義することができます。
&lt;monster&gt;タグで囲った中身がモンスターの定義になります。
具体的には下のように書きます。XMLの書き方になります。
```
<?xml version="1.0"?>
<class>
    <!-- 1. 選抜試合１人目 -->
    <monster>
        <id>1</id>
        <name>ならずもの</name>
        <lv>1</lv>
        <type>OTH</type>
        <hp>5</hp>
        <mp>0</mp>
        <pow>1</pow>
        <agi>1</agi>
        <int>1</int>
        <dex>1</dex>
        <ksm>1</ksm>
        <atk>1</atk>
        <def>1</def>
        <isTalk>true</isTalk>
        <message>しょうきんは、おれが、もらう！</message>
        <commandList>ATK</commandList>
        <exp>1</exp>
        <money>1</money>
    </monster>
</class>
```
今回使用しているXMLのタグ(monsterなど)は、自作のものでこのプログラムでしか使用できないものになるので注意してください。
このXMLは「Extensible Markup Language」の略で、日本語では「拡張可能なマークアップ言語」と訳されます。
早い話が、「**色々なプロパティ(属性値)、をプログラムの変更なしで追加編集できるということ**」です。
今回のテキストRPGでいえば、モンスターの定義(Monsters.xml)などはプログラムの中身を書き換えなくても、変更できる「**拡張可能な部分**」になるわけです。

さて、Monsters.xmlの中身を見ていきます。
プログラムの中では、Monsterクラスがあります。Eclipseプロジェクト(IntteliJプロジェクト)内の次のファイルを見てください。これはモンスターを表すためのクラスMonsterクラスです。

#### Monsterクラス
「**jp.zenryoku.rpg.charactors.monsters.Monster**」

このようなクラスの書き方を**完全修飾名**といいます。
javaプログラムの中に書くのであれば、下のようになります。
```
public class A {
	public static void main(String[] args) {
		java.util.List<String> list = new java.util.ArrayList<>();
		list.add("aaa");
	}
}
```
全部のコードをこのように書くととても面倒です。
なので**import文**を使用します。
```
import java.util.List;
import java.util.ArrayList;

public class A {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("aaa");
	}
}
```
**import文**を使うとコードがスッキリしました。

### 5.ショップシーンの定義
実際に書くときは下のように書きます。シーンタイプは「B]を指定します。
各行の意味は次の通りです。
>「**アイテム名:値段:説明**」プログラム内では「,(カンマ)」で分割([String#split()](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/String.html#split-java.lang.String-))していて、配列に変換しています。
そして、conf.txtで読みこんだITEM_LISTを[Map](https://docs.oracle.com/javase/jp/8/docs/api/java/util/Map.html)に登録しているのでアイテム名をキーにconf.txtに定義された、詳細情報を取得しています。

```
4:B
店舗です。
<item:ShopName>
やくそう, 30, HPを10回復する
たんけん, 50, 武器
ぬののふく, 45, 防具
どくけし, 30, 使用した相手に、ステータス異常の「どく」をあたえる
</item>
END_SCENE 1
```

店舗(ショップシーン)の定義には、関連するファイルがあります。
今編集している「Story.txt」と「conf.txt」が関連します。
具体的には、「Story.txt」で定義している&lt;item\:ShopName&gt;から&lt\:/item&gt;の部分と
「conf.txt」に書いてある。「ITEM_LIST」から「END_ITEM_LIST」までの部分です。

実際に使用している、定義を以下に記載します。
各行の意味は次ん通りです。
> アイテム名:カテゴリの記号:効果式:オプション
カテゴリの記号　＝　conf.txtのCONFIG_PARAMに定義する、もしくは、定義してあります。
効果式　＝　CONFIG_PARAMで定義している記号を組み合わせてそれぞれの効果を表します。
具体的には、「HP+10」はHPを10回復という意味で、逆に「HP-10」はHPを10減らすという意味です。
詳細は、CONFIG_PARAMで定義している記号を見ながらその効果を確認してください。
ただし、**POI**などのような**ステータス変化はまだ実装できていません**。

```
ITEM_LIST
やくそう:ITM:HP+10: 10: ITN-1
どくけし: ITM:ITV=POI: 10: ITN-1
ぼくとう:WEP:WEV=3: 30:ITN-1
たんけん:WEP:WEV=4: 40:ITN-1
ぬののふく:ARM:ARV=3: 30: ITN - 2
わきざし:WEP:WEV=6: 80: ITN - 2
かわのふく:ARM:ARV=6: 80: ITN - 2
END_ITEM_LIST
```
conf.txt内のITEM_LISTで定義している「やくそう」などのアイテムの名前とStory.txt内の&lt;item\:ShopName&gt;に定義している名前が一致しないとエラーになります。
**アイテムの使用に関しては、まだ実装できていない**ですが、アイテムの使用したときの効果をITEM_LIST側に定義するためにこのような実装になっています。

その代わり、武器・防具の装備は実装できているので、アイテムの購入から武器の装備まで可能です。

もし、アイテムを増やしたい場合は、**「Story.txt」と「conf.txt」を編集してやればよい**です。

他にもありますが、詳細に関しては、動画を参考にしていただきたく思います。
文章のほうでは、実際の定義と一覧をメインに記載したいと思います。

## その他XMLファイルでの定義
1. Job.xml
   Job(職業)毎に使用できるコマンドの記号(ATKなど)を定義  
   コマンドの定義はCommand.xmlに依存する
```
<class>
    <!-- 勇者 -->
    <job>
        <id>BRV</id>
        <name>勇者</name>
        <discription>勇気ある者。バランスよく剣と魔法を使える。</discription>
        <commandList>ATK, DEF, ITM, MAG, TEC</commandList>
        <!-- CONFIG_STSTUS参照のタグ名で増加値をセット -->
        <pow>2</pow>
        <agi>2</agi>
        <int>2</int>
        <dex>2</dex>
        <ksm>2</ksm>
    </job>
</class>
```
2. Commands.xml
   Job(職業)毎に定義した、記号(ATKなど)の内容を定義する   
   「まほう」や「わざ」などの子階層を持つものはhasChildタグにtrueを設定する
   **Job.xmlのidタグとCommand.xmlに定義したcommandタグ**がリンクする
```
<class>
    <!-- 共通コマンド１：たたかう -->
    <command>
        <!-- コマンドID -->
        <id>ATK</id>
        <!-- コマンド名 -->
        <name>たたかう</name>
        <!-- コマンド効果式 -->
        <formula>ATK</formula>
        <!-- 下の階層を持つ -->
        <hasChild>false</hasChild>
        <!-- 実行時のメッセージ -->
        <exeMessage>のこうげき</exeMessage>
    </command>
    <!-- まほうコマンド -->
    <command>
        <!-- コマンドID -->
        <id>MAG</id>
        <!-- コマンド名 -->
        <name>まほう</name>
        <!-- コマンド効果式 -->
        <formula></formula>
        <!-- 下の階層を持つ -->
        <hasChild>true</hasChild>
        <!-- 実行時のメッセージ -->
        <exeMessage>はまほうをつかった</exeMessage>
    </command>
</class>
```
3. STM.xml
   使用するコマンドないのスキルやわざ、魔法を定義する。
   **Job設定にある記号とjobsタグに定義した記号**が対応する。
```
<class>
    <!-- まほう定義 -->
    <mag>
        <!-- まほうID -->
        <id>fir1</id>
        <!-- まほう名 -->
        <name>かえんじゅ</name>
        <!-- 属性 -->
        <ori>FIR</ori>
        <!-- 魔力コスト -->
        <cost>-2</cost>
        <!-- 魔法威力(パラメータ設定より -->
        <force>3</force>
        <!-- 使用可能職業(習得レベル) -->
        <jobs>BRV(2), MAG(1)</jobs>
        <!-- 説明 -->
        <discription>初級の火炎魔法</discription>
    </mag>
    <!-- 技の定義 -->
    <tec>
        <!-- わざID -->
        <id>swd1</id>
        <!-- わざ名 -->
        <name>ましょうめんぎり</name>
        <!-- 技の仕様コスト -->
        <cost>1</cost>
        <!-- 属性(剣技など) -->
        <ori>SWD</ori>
        <!-- 攻撃力 -->
        <force>WEV + POW + AGI</force>
        <!-- 使用可能職業(習得レベル) -->
        <jobs>BRV(1), WAR(1)</jobs>
        <!-- 説明 -->
        <discription>基本的な剣術の技</discription>
    </tec>
</class>
```
4. Monsters.xml
   モンスターの定義を行う  
   typeタグとMonsterType.xmlのidタグがリンクする。
```
<class>
    <!-- 1. 選抜試合１人目 -->
    <monster>
        <id>1</id>
        <name>ならずもの</name>
        <lv>1</lv>
        <type>OTH</type>
        <hp>5</hp>
        <mp>0</mp>
        <pow>1</pow>
        <agi>1</agi>
        <int>1</int>
        <dex>1</dex>
        <ksm>1</ksm>
        <atk>1</atk>
        <def>1</def>
        <isTalk>true</isTalk>
        <message>しょうきんは、おれが、もらう！</message>
        <commandList>ATK</commandList>
        <exp>1</exp>
        <money>1</money>
    </monster>
</class>
```
5. MonseterType.xml
   モンスターのタイプを定義する、プレーヤーの職業と同様なもの
```
<class>
    <!-- スライム・タイプ -->
    <monsterType>
        <id>SLM</id>
        <name>スライムタイプ</name>
        <discription>スライムタイプのモンスター。</discription>
        <commandList>ATK, DEF, BAT</commandList>
    </monsterType>
    <!-- カラス・タイプ -->
    <monsterType>
        <id>CRW</id>
        <name>カラスタイプ</name>
        <discription>カラスタイプのモンスター。</discription>
        <commandList>ATK, DEF, STC</commandList>
    </monsterType>
    <!-- 人間・タイプ -->
    <monsterType>
        <id>HUM</id>
        <name>人間タイプ</name>
        <discription>人間タイプのモンスター。</discription>
        <commandList>ATK, DEF</commandList>
    </monsterType>
</class>
```










