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

## 学習フロー
* プログラムの動かし方
  - メインメソッドを動かす(Hello World)
  - 「*(アスタリスク)」で図形を描こう。※自分で考えてプログラムを組む
* 演算子の使い方
  - 文字列の連結「+」演算子
  - 四則演算、剰余、2条など
  - 「=」演算子
  - int型とString型
  - 論理演算、booleanの計算をしてみる
  - 三項演算子の使い方
* 基本文法
  - 条件分岐(if, switch)
  - 繰り返し(for, while)
  - 例外処理
  - ファイル読み込み、書き込み、業追加アプリ
* じゃんけんゲームの作成
  - プログラムの設計をする(フローチャート作成)
  - フローチャートをコードに落とす(コメント)
  - コメントをコードにする
  - メソッドを作ってコメント部分を実装する
