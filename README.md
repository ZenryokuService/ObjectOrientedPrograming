## Folder list

| Category | pakage name |
| -------- | ----------- |
| Root Directory | jp.zenryoku |
| for Main classes | jp.zenryoku.main |
| for Practice sources | jp.zenryoku.practice |
| for Procon server | jp.zenryoku.procon |
| for Text RPG | jp.zenryoku.rpg |
| for Janken game | jp.zenryoku.tutorial |

# About Text RPG
1. Target sources is **src/main/java/jp.zenryoku.rpg**
2. Main method is in GameMain
3. use ["exp4j"](https://lallafa.objecthunter.net/exp4j/) for FORMULAS defined in *_story.txt

## TextRpg in Console.
1. Load text file created by users.
2. create RpgConfig and load story like Game Book(日本のゲームブック)

### User defined
* PARAM_CONFIG: you can defined categories use in game book. like wepons(WEP), items(ITM), magic(MAG) .. 
* STATUS: you can defind statuses in game book like power(POW), intelligence(INT) ...

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

## Diagrams
**/src/main/resources/TextRpgClasses.mdj (StarUML)**
[PDF is here](./resources/TextRpgDiagram.pdf)

## 学習スタイル(学習ステップ・フロー)
1. 実際に使用するケースを見る -> サンプルアプリより一部抜粋　```ハローワールドの実用例を見る ```
2. 学習ポイントに沿った、内容の説明を読む　：　```ハローワールド、コンソール(標準出力)に文字を表示するということ```
3. 実際にコードを書いて、動かしてみる　：　```自分で作成したコードで「Hello world!」を表示する
4. 書いたプログラムの内容を理解する　：　```実行したプログラム、メインメソッドと「System.out.println(文字列)」で標準出力に文字を表示できることを理解```
 
## 学習する事一覧
* メインメソッド: プログラム引数、ビルドパス、VM引数
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
 
