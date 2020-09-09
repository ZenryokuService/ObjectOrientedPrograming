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

#### 手順メモ
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
