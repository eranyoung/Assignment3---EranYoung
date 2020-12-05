import java.util.*;
import java.lang.*;

public class TowersOfFour{

	private List<Stack<Integer>> towers;
	private List<Stack<Integer>> towersClone;//only used for printing the board
	private List<Stack<Integer>> compStacks;
	private int height;
	private int size;
	private int tubesFilled;

	public TowersOfFour(int size, int balls, int tubesFilled){
		height = balls;
		this.size = size;
		this.tubesFilled=tubesFilled;
		towers = new ArrayList<Stack<Integer>>();
		towersClone = new ArrayList<Stack<Integer>>();
		for(int i = 0; i < size; i++){
			towers.add(new Stack<Integer>());
			towersClone.add(new Stack<Integer>());
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
				towersClone.get(randIndex).push(num);
				randNumCounter[num]++;
				
			}
		}
		compStacks = new ArrayList<Stack<Integer>>();
		for(int i = 0; i < tubesFilled; i ++){
			compStacks.add(new Stack<Integer>());
			for(int c = 0; c < height; c++){
				compStacks.get(i).push(i);
			}
		}
	}

	public Queue<String> solveBoard(){
		Queue<String> moves = new LinkedList<String>();
		solve(moves);
		return moves;
	}

	private void solve(Queue<String> q){
		int i = 0;
		do{
			
			//printBoard();
			if(this.towers.get(i).empty()){
				i++;
				continue;
			}

			int nextInd = nextValidIndex(towers.get(i).peek(), i);
			this.towers.get(nextInd).push(this.towers.get(i).pop());
			q.add("t" + i + " - " + " t" + nextInd);

			if(this.towers.get(i).empty()){
				i++;
			}
			
			if(i >= this.size){
				i = 0;
			}
		}while(this.hasUnsolvedCols());

	}

	private int nextValidIndex(int val, int curIndex){
		for(int i =  0; i < this.size; i ++){
			if(this.towers.get(i).empty())
				return i;
			if(i != curIndex){
				int compVal = this.towers.get(i).peek();
				if(compVal == val && (this.towers.get(i).size() < height))
					return i;
			}
		}
		return -1;
	}

	private boolean hasUnsolvedCols(){
		boolean returnVal = true;
		boolean [] equalFlags = new boolean[this.tubesFilled];
		

		for(int i = 0; i < this.size; i++){
			if(!(this.towers.get(i).empty() || this.towers.get(i).size() < this.height)){
				for(int c = 0; c < this.compStacks.size(); c++){
					if(this.equalStacks(this.towers.get(i), this.compStacks.get(c))){
						equalFlags[c] = true;
						c = this.compStacks.size();
						continue;
					}
				}
			}

		}

		for(int i = 0; i < equalFlags.length; i ++){
			if(!equalFlags[i])
				return true;
		}
		return false;
	}

	private boolean equalStacks(Stack<Integer> s1, Stack<Integer> s2)
	{
		boolean equal = true;

		Stack<Integer> temp1 = new Stack<Integer>(), temp2 = new Stack<Integer>();
		int x,y;
		while(!(s1.empty()) && !(s2.empty())){
			x = s1.pop();
			y = s2.pop();

			temp1.push(x);
			temp2.push(y);

			if(x != y){
				equal = false;
				break;
			}
		}

		if(!(s1.empty()) || !(s2.empty()))
			equal = false;

		while(!(temp1.empty())){
			x = temp1.pop();
			y = temp2.pop();
			s1.push(x);
			s2.push(y);
		}

		return equal;
	}

	public void printBoard(int mode){
		List<Stack<Integer>> towers;
		if(mode == 0){
			System.out.println("\nEnding Board: ");
			towers = this.towers;
		} else{
			System.out.println("\nStarting Board: ");
			towers = this.towersClone;
		}

		String output = "\n";
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

	public void printQueue(Queue<String> q){
		int i = 1;
		while(q.size() > 0){
			System.out.println(i + ". " + q.poll() + "\n");
		}
	}


	public static void main(String [] args){
		int size = Integer.parseInt(args[0]);
		int balls = Integer.parseInt(args[1]);
		int tubesFilled = Integer.parseInt(args[2]);
		TowersOfFour t = new TowersOfFour(size, balls, tubesFilled);
		t.printBoard(1);
		Queue<String> q = t.solveBoard();
		t.printBoard(0);
		System.out.println("\nGame Moves: \n");
		t.printQueue(q);

	}
}