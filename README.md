# ObjectOrientedPrograming
オブジェクト指向プログラミングのサンプルコード

#### 使用する外部アプリケーション
1. [StarUML](http://staruml.io/)

## フローチャートを作る
1. 必要な処理をリストアップする
2. 処理の順序を矢印で作成する
3. 条件分岐なども作成する
4. 事前準備する変数を作成
5. 事前準備するメソッド(通常のメソッド)を作成する

## フローチャート通りにメインメソッドを作成する
1. mainメソッドを作成する
2. フローチャートで作成した処理(process, predefined process)を作成する
  ※メソッドの枠のみを作成する
3. 作成したコードを見直す

## テストケースを作成する
参考サイト
* java.lang.refrectionの使い方に関して、[JavaDocAPI](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Class.html#getDeclaredMethod-java.lang.String-java.lang.Class...-)を参照
* [Functionalインターフェースの使い方に関して１](https://codechacha.com/ja/java8-functional-interface/)を参照
* [Gitブランチ操作](https://git-scm.com/book/ja/v2/Git-%E3%81%AE%E3%83%96%E3%83%A9%E3%83%B3%E3%83%81%E6%A9%9F%E8%83%BD-%E3%83%AA%E3%83%99%E3%83%BC%E3%82%B9)

# 手順メモ
## Part1
#### Chapter1: オブジェクト指向ではない方法でじゃんけんゲームを作ってみよう
1. FirstJankenMain.java
  * クラスを作成して、フローチャートにある処理をメソッドを作成する。※処理の中身は作成しない。

2. FirstJankenMainTest.java
  * フィールド変数にテスト対象クラスを宣言する(FirstJankenMain)
  * テストを実行するのに、privateメソッド、フィールド変数は外側から呼び出せないのでjava.lang.refrectionを使用する
  * Field変数を取得するのにはClass.
  * コンソール出力した値を確認する方法はここの[サイトを参考](https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println)にする
  * 改行コードの取得はこのサイトを[参照](https://techacademy.jp/magazine/19071)
  * リフレクションを使用するので、テストクラスの起動確認コードを実装
  * テストクラスの実装後に、実態クラスの実装を行う
  * JUnitでログ出力するときに[Supplierインターフェースを使用するのに参照](https://stackabuse.com/unit-testing-in-java-with-junit-5/)

3. 頭の中で用意したテストケースを実行した後に、テストケースの不足がないか確認、修正・追加する
  * [カバレッジ](https://zenryokuservice.com/wp/2020/09/01/%e9%96%8b%e7%99%ba%e7%92%b0%e5%a2%83%e6%a7%8b%e7%af%89%ef%bd%9ewindows%e7%89%88eclipse%e3%81%ae%e8%a8%ad%e5%ae%9a%ef%bd%9e/)を起動して、テストケースが網羅できているか確認する。

4. テストケースを作り終わって、実際にじゃんけんゲームを起動してみると、ユーザーの手とCUPの手が何を出しているのかわからないという結果になったのでわかるように修正する必要がある。＝メインメソッドの修正
  * ユーザーの手とCPUの手がわからない(表示されない) -> 表示するように修正
  * 「じゃんけん」と「あいこ」のハンドルができていなかった
  * じゃんけんの各手と入力値の表を表示する(手Mapとテストケースの追加)
  * 入力チェックの処理を追加実装

#### Chapter2: オブジェクト指向プログラミングでじゃんけんゲームを作ってみよう
1. Part1.Chapter2.Section1で作成したフローチャートのプロセスを参考にクラスの役割分担を考え、処理の塊一覧を作成(この一覧はメモのため省略(未コミット)
2. 上のフローチャートと処理の塊一覧よりクラス図を作成する
3. 列挙型の書き方を参考にした[サイト](https://techacademy.jp/magazine/9244)

## Part2
#### Chapter1: テキストRPG(戦闘シーンのみ)を作ってみよう
1. Part2.Chapter1.Section1は、ユーズケース図でやりたいことを可視化する([参考](https://www.uml-diagrams.org/use-case-subject.html))
    ユースケース図(イメージ図)の作成としているが、クラス図(イメージ)、フローチャートも一緒に作成する。
2. [STAR UMLの使い方](https://docs.staruml.io/user-guide/formatting-diagram)を参照
    実際の処理を行うことを考え、どのようにアプリを起動するか？、メインメソッドの実装イメージを作る。
3. まずは、ユーズケース、フローチャート、クラス図を作成する。(ざっくりでよい)
4. 次は、プログラムレベルで、実装する処理を考えながら(コメントをソースに書きながら)上記のドキュメントを**追加修正**する。
  主なクラスとしては以下のクラスを使用して起動するようにして、マルチスレッド対応にする。
    * GameMain: メインメソッドを実装
    * TextRpgGameEngine: Threadクラスを継承してマルチスレッド対応にする
    * TextRpgLogic: Gamesインターフェースを実装したクラスで、GameEngineクラスで使用するクラスを変更できるようにする。
    * ConsoleUtils: コンソール出力する処理を実装する、ステータス、メッセージ、行動選択肢などを表示する。
    * CheclerUtils: 各種チェック処理を実装する。コンストラクタを起動する必要がないので、静的(static)メソッドにする。
5. データクラスのテストケースは実装しない
6. 作成する順序は、以下の通り。
    1. 初期表示を行うConsoleUtils#printBattleStatus()のテストケース
    2. TextRpgLogicのテストケース

7. ビルドパスにresourcesを追加する(src/mainとsrc/testの両方追加)：resources読み込みの[参考サイト](https://qiita.com/ka2kama/items/9e16cc6d1019838770cc)
8. [アノテーションの実装方法](https://www.techscore.com/tech/Java/JavaSE/JavaLanguage/7-3/)を参考にする
9. アノテーションのついたメソッドの取得方法。特殊なのでサンプルコードを以下に示す。
    <pre>
    public void printCommandList() {<br/>
        Player player = new Player();<br/>
        Method[] mess = player.getClass().getDeclaredMethods();<br/>
        for(Method mes : mess) {<br/>
            Command ano = mes.getAnnotation(Command.class);<br/>
            if (ano != null) {<br/>
                LOG.info(() -> mes.getName());<br/>
            }<br/>
        }<br/>
    }
    </pre>

#### テキストRPG(戦闘シーンのみ)の仕様
1. 戦闘審開始時に「XXXが現れた！」を表示する。
2. プレーヤーのステータスを表示する ※テストプログラムの作成をするのも良い。
    * プレーヤーの名前は全角4文字まで、半角で8文字までとする。
    * 1人分の表示はプレーヤーの名前が半角で奇数の場合はアスタリスク15個分、偶数の場合は14個分とする。
    * ステータスに表示する項目はレベル、HP、MPとする。
3. モンスターのトークフラグ(isTalk)がtrueの場合は、メッセージを出力する。
4. プレーヤーの行動選択肢は行動を表すメソッドのに**@Command**アノテーションを付与する。
5. プレーヤーの行動選択肢(コマンド)は、indexの番号を入力することで実行する。
    * public @insterface Command()にindexとcommandNameを保持。
6. 行動選択肢(コマンド)は数字で、１から始まる数字とする。
7. メインメソッドの処理は、以下のようにGameMainクラスから、TextRpgGameEngineを使用して実行する。
    * TextRpgGameEngineはThreadクラスを継承し、Tread#start()で実行、マルチスレッド対応とする。
    * 作成するXXXLogicクラスはGamesインターフェースを実装する。
    * ゲームループで行う処理はTextRpgLogicクラス以下のメソッドにまとめる。
        1. 「初期表示(init())」
        2. 「入力受付(acceptInput())」
        3. 「データ更新(updateData())」
        4. 「画面更新(render()」
8. 攻撃力コマンドは武器の攻撃力を使用し、ダメージを受けるときは、防御力を使用する。
9. 防御コマンドを使用したときは、防御力を1.5倍にして攻撃はしない。
10. **updateData()**の返却値がfalseの時は、ユーザーエラーのハンドル処理を行う。
11. **render()**の返却値がtrueの場合ループ処理を抜け、戦闘シーンを終了する。、