package jp.zenryoku.practice.lv2renshu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mondai4 {
	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "red");
		map.put(1, "blue");
		map.put(2, "yellow");

		Scanner scan = new Scanner(System.in);
		System.out.println("色コードを入力してください");
		String input = scan.nextLine();
		int num = Integer.parseInt(input);
		System.out.println(map.get(num));
	}
}
