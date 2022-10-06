# プロジェクトの内容
Javaの学習ように、作成したクラスなどのサンプルいコードとテキストRPGで実際に動くものとコードの関係をみる。
## Folder list
**フォルダー(パッケージ)の構成**

|　on The work | Category | pakage name |
| ------------ | -------- | ----------- |
| - | Root Directory(Package) | jp.zenryoku |
| - | for Main classes | jp.zenryoku.main |
| - | for Practice sources | jp.zenryoku.practice |
| - | for Procon server | jp.zenryoku.procon |
| ☆ | <b>for Text RPG</b> | jp.zenryoku.rpg |
| - | for Janken game | jp.zenryoku.tutorial |

# About Text RPG
1. Target sources is **src/main/java/jp.zenryoku.rpg**
2. Main method is in GameMain
3. use ["exp4j"](https://lallafa.objecthunter.net/exp4j/) for FORMULAS defined in Story.txt
4. Text RPG Package

| Category | pakage name |
| -------- | ----------- |
| Root Directory(Package) | jp.zenryoku.rpg |
| Main class | jp.zenryoku.rpg.GameMain |
| for bat(check Story.txt etc ...) | jp.zenryoku.rpg.bat |
| for Charactors like player and monsters | jp.zenryoku.rpg.charactors |
| for Constraints like message and status and the others ...  | jp.zenryoku.rpg.charactors |
| for Data classes about status, job, category etc ... | jp.zenryoku.rpg.data |
| for Scene system like StoryScene, BattleScene, EffectScene | jp.zenryoku.rpg.scene |

5. [テキストRPGの書き方](./テキストRPG書き方.md)

## Progress of TextRpg in Console.
1. Load text file created by users.  
(ユーザーが作成したテキストファイルをロード(読込)する)  
※ those files are putting in folder named "game" or your defineded.  
(これらのファイルは、「gameフォルダ」もしくはあなたが決めた名前のフォルダ内に配置してください。)

| File name | about ... |
| --------- | --------- |
| Commands.xml | Defined Command for Player or Monsters(プレーヤー、モンスターのコマンドを定義) |
| conf.txt | Defined configurations for this Text RPG |
| Job.xml | Defined players job. like Braver(プレーヤーの職業を定義する、例えば「勇者」のような) |
| MonseterType.xml | Defined MonsterType that like job for monsters(モンスタータイプの定義、つまりは、モンスター向けの職業) |
| Monsters.xml | Defined Monster(モンスターを定義する) |
| STM.xml | Defined Skill, Tech, Magic for player or monsters(プレーヤー、モンスターが使用できる特殊コマンドを定義する) |
| Story.txt | Defined Story like game book(ゲームブックのようなストーリーを定義する) |
| title.txt | Defined title view (use only text)(タイトル部分の定義、ただし文字のみ使用可能) |

3. Create RpgConfig and load story like Game Book  
(RpgConfigオブジェクトを生成し、日本のゲームブックのようなストーリーを読込む)
4. Start your game written in Story.txt  
(Story.txtに定義した、ゲームを開始する)

### User defined(ユーザー定義)
About Game book, you can write in Story.txt. And Configrations write in conf.txt, monster defined in Monster.xml ...  
You can defined your setting in this game using default setting files in "game" folder.  
(ゲームブックの内容に関しては「Story.txt」に定義します。ほか設定やモンスターなどの定義も行うことができます。
もともと定義しているファイルがgameフォルダ内あるので、それをカスタムして使用する形で遊んでもらえると嬉しいです。)

#### In conf.txt
* PARAM_CONFIG: you can defined categories use in game book. like wepons(WEP), items(ITM), magic(MAG) ..  
(作成するゲームブック内の、武器やアイテム、魔法などのカテゴリを定義することができます。)
* STATUS: you can defind statuses in game book like power(POW), intelligence(INT) ...  
(同様にステータスも定義できます。POW, INTなど)

#### configuration of job and monster type
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
### User created story
**this program load "*_story.txt". this text file written story.** And users are able to create original story in this story text file.
<EX>
1:A
one day, you are on the bed. and you find ...
1. chocorate 2
2. ice 3
END_SCENE

2:A
you find a chocorate.
END_SCENE

3:B
you find a ice.
END_SCENE

## UML Diagrams
**/src/main/resources/TextRpgClasses.mdj (using [StarUML](https://staruml.io/))**
[Diagram HTML is here](https://zenryokuservice.github.io/ObjectOrientedPrograming/html-docs/index.html)

 **[Good sample of UML](https://www.tutorialspoint.com/uml/index.htm)**

## 学習スタイル(学習ステップ・フロー)
1. 実際に使用するケースを見る -> サンプルアプリより一部抜粋　```ハローワールドの実用例を見る ```
2. 学習ポイントに沿った、内容の説明を読む　：　```ハローワールド、コンソール(標準出力)に文字を表示するということ```
3. 実際にコードを書いて、動かしてみる　：　```自分で作成したコードで「Hello world!」を表示する
4. 書いたプログラムの内容を理解する　：　```実行したプログラム、メインメソッドと「System.out.println(文字列)」で標準出力に文字を表示できることを理解```

## 学習する事一覧
* メインメソッド: プログラム引数、(※後半にビルドパス、VM引数)
* 演算子：四則演算で使用する演算子、剰余残、論理演算(AND OR)、三項演算子
* 基本文法
  - 条件分岐(if, switch)
  - 繰り返し(for, while)
  - 例外処理
* 配列
* クラスの構成要素
* クラスの使い方(呼び出して使用する)
* 各種JavaAPIの使い方(Date、コレクション、FileIO)
* クラスとクラスの関係を作る
  - 親子関係を作ることでできること(JavaFXで画面表示)
  - イベントリスナーの実装とインターフェース
* デザインパターン
  - シングルトン・パターン
  - デコレーター・パターン
  - コマンド・パターン

