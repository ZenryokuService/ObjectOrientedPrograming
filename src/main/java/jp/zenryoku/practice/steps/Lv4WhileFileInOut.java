package jp.zenryoku.practice.steps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lv4WhileFileInOut {
    /** ファイル編集処理 */
    public static void main(String[] args) throws IOException {
        // ルートディレクトリ
        String rootDir = "src/main/resources";
        // ファイルの取得
        Path path1 = Paths.get(rootDir + "/prac/target.txt");
        // ファイルの読み込み
        BufferedReader buf = Files.newBufferedReader(path1);

        // 読込んだ文字列を格納する
        StringBuilder build = new StringBuilder();
        // 改行コード
        final String SEP = System.lineSeparator();
        // 文字列の変数
        String line = null;
        // ファイルの中身を全て読み込む
         while ((line = buf.readLine()) != null) {
             // カンマを追加
             build.append(line + "," + SEP);
         }

        //// ファイル出力　////
        // 出力するファイル
        Path path2 = Paths.get(rootDir + "/prac/target1.txt");

        // ファイルが存在するか判定
        if (!Files.exists(path2)) {
            // ファイルが存在しないならば作成
            Files.createFile(path2);
        }
        // 対象ファイルに書き込み
        BufferedWriter writer = Files.newBufferedWriter(path2, StandardCharsets.UTF_8);
        // 格納した文字列を書き込む
        writer.write(build.toString());
        // ファイルのクローズ
        writer.close();
    }

    /** サンプル：ループ処理を使わない場合 */
    public static void sample1() throws IOException {
        // ルートディレクトリ
        String rootDir = "src/main/resources";
        // ファイルの取得
        Path path1 = Paths.get(rootDir + "/prac/target.txt");
        // ファイルの読み込み
        BufferedReader buf = Files.newBufferedReader(path1);

        // 1行だけ読み込む
        String line = buf.readLine();
        // カンマを追加
        line = line + ",";

        //// ファイル出力　////
        // 出力するファイル
        Path path2 = Paths.get(rootDir + "/prac/target1.txt");

        // ファイルが存在するか判定
        if (!Files.exists(path2)) {
            // ファイルが存在しないならば作成
            Files.createFile(path2);
        }
        // 対象ファイルに書き込み
        BufferedWriter writer = Files.newBufferedWriter(path2, StandardCharsets.UTF_8);
        writer.write(line);
        // ファイルのクローズ
        writer.close();
    }
}
