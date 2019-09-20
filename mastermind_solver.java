import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* 
Color Key:
1 - Bu
2 - Bk
3 - Y
4 - R
5 - G
6 - W
7 - O
*/

public class mastermind_solver {

	static int n_guesses = 5;
	static ArrayList<Integer> white_coords = new ArrayList<Integer>(); 
		
	public static void main(String[] args) throws FileNotFoundException {
		Set<Integer> combins = new HashSet<Integer>();
		for (int i = 1111; i <= 7777; i++) 
			combins.add(i);
				
		Iterator<Integer> it = combins.iterator();
		ArrayList<Integer> errors = new ArrayList<Integer>();
		while(it.hasNext()) {
			int next = it.next();
			int [] num = numToArr(next);
			for (int k = 0; k < num.length; k++) {
				if (num[k] > 7 || num[k] == 0)
					errors.add(next);
			}
		}
		for (int i = 0; i < errors.size(); i++)
			combins.remove(errors.get(i));
		
		Scanner input = new Scanner(new File(args[0]));
		int[][] guesses = new int[n_guesses][4];	
		int[] scores = new int[n_guesses];
		for (int i = 0; i < n_guesses; i++) {
			String guess = input.nextLine();
			String[] guessArr = guess.split(" ");
			int score = Integer.parseInt(guessArr[4]);
			scores[i] = score;
			for (int j = 0; j < 4; j++) {
				switch(guessArr[j]) {
					case "Bu":
						guesses[i][j] = 1;
						break;
					case "Bk":
						guesses[i][j] = 2;
						break;
					case "Y":
						guesses[i][j] = 3;
						break;
					case "R":
						guesses[i][j] = 4;
						break;
					case "G":
						guesses[i][j] = 5;
						break;
					case "W":
						guesses[i][j] = 6;
						break;
					case "O":
						guesses[i][j] = 7;
						break;
				}
			}	
		}
		for (int i : combins) {
			if (isValid(i, scores, guesses))
				System.out.println(i);
		}
	}
	public static boolean isValid(int combin, int[] scores, int[][] guesses) {
		int[] combination = numToArr(combin);
		
		for (int i = 0; i < n_guesses; i++) {
			int white = scores[i] / 10;
			int black = scores[i] % 10;
			int count_black = 0;
			int count_white = 0;
			ArrayList<Integer> black_coords = new ArrayList<Integer>();
			ArrayList<Integer> white_coords = new ArrayList<Integer>();
			for (int j = 0; j < 4; j++) {
				if (guesses[i][j] == combination[j]) {
					count_black++;
					black_coords.add(j);
				}
			}
			for (int j = 0; j < 4; j++) {
				if (hasWhite(combination[j], guesses[i], black_coords, white_coords))
					count_white++;

			}
			if (count_black != black || count_white != white) {
				return false;
			}
		}
		white_coords.clear();
		return true;
	}
	public static boolean hasWhite(int combination, int[] guess, ArrayList<Integer> black_coords, ArrayList<Integer> white_coords) {
		for (int i = 0; i < guess.length; i++) {
			if (combination == guess[i] && !black_coords.contains(i) && !white_coords.contains(i)) {
				white_coords.add(i);
				return true;
			}
		}
		return false;
	}
	public static int[] numToArr(int i) {
		int[] arr  = new int[4];
		arr[0] = i / 1000;
		i = i % 1000;
		arr[1] = i / 100;
		i = i % 100;
		arr[2] = i / 10;
		i = i % 10;
		arr[3] = i;
	
		return arr;
	}
}
