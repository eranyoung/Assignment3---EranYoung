import java.util.*;
import java.lang.*;

public class TowersOfFour{

	private List<Stack<Integer>> towers;
	private int height;
	private int size;

	public TowersOfFour(int size, int balls, int tubesFilled){
		height = balls;
		this.size = size;
		towers = new ArrayList<Stack<Integer>>();
		for(int i = 0; i < size; i++){
			towers.add(new Stack<Integer>());
		}
		int [] randNumCounter = new int[tubesFilled];
		for(int i = 0; i < tubesFilled; i ++ ){
			int randIndex = 0;
			do{
			    randIndex = (int)Math.floor((Math.random() * size));
			}while(!towers.get(randIndex).empty());
			//int [] randNumCounter = new int[tubesFilled];
			for(int c = 0; c < balls; c++){
				int num;
				do{
					num = (int)Math.floor((Math.random() * tubesFilled));
				}while(!(randNumCounter[num] < balls));
				
				towers.get(randIndex).push(num);
				randNumCounter[num]++;
				
			}
		}	
	}

	public void printBoard(){

		String output = "";
		for(int c = 0; c < this.height; c++){
			output = output + (height - c) + "  |";
			for(int i = 0; i < this.size; i++){
				if(towers.get(i).empty()){
					output = output + "   |";
				} else{
					output = output + " " + towers.get(i).pop() + " |";
				}
			}
			output = output + "\n";
		}
		for(int i = 0; i < this.size; i++){
			output = output + "____";
		}
		output = output + "\n   |";
		for(int i = 0; i < this.size; i ++){
			output = output + " " + i + " |";
		}
		System.out.println(output);
	}


	public static void main(String [] args){
		int size = Integer.parseInt(args[0]);
		int balls = Integer.parseInt(args[1]);
		int tubesFilled = Integer.parseInt(args[2]);
		TowersOfFour t = new TowersOfFour(size, balls, tubesFilled);
		t.printBoard();
	}
}