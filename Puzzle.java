import java.util.Objects;

/**
  * Class to Build a Eigth-Puzzle with a determined String input
  * @author: Joao Castro
  * @date: 09/06/2017
  */

class Puzzle implements Comparable<Puzzle> {
	String puzzle;
	Boolean isValidPuzzle;
	int level = 0;
	int h;

	/**
	  * Standard Constructor
	  * n = 3;
	  * Puzzle[n][n]
	  * isValidPuzzle = false
	  */
	public Puzzle(){
		this.puzzle = "000000000";	
		this.isValidPuzzle = false;
	}

	/**
	  * Alternative Constructor
	  * @param String str - Input String
	  */
	public Puzzle(String str){
		this.puzzle = str;
		this.isValidPuzzle = this.validPuzzle(str);
	}

	// Getters and Setters
	public String getPuzzle(){return this.puzzle;}
	public void setPuzzle(String str){this.puzzle = str;}

	public Puzzle clone(){
		return new Puzzle(this.puzzle);
	}


	/**
	  * This function is called to test if this puzzle is possible
	  * @param String str - puzzle string
	  * @return Boolean Answer - True or False
	  */
	public Boolean validPuzzle(String str){
		Boolean answer = true;
		int x, sum = 0;
		for(int i = 0; i < str.length(); i++){
			x = Character.getNumericValue(str.charAt(i));
			for(int j = i+1; j < str.length(); j++){
				if(Character.getNumericValue(str.charAt(j)) != 0 && Character.getNumericValue(str.charAt(j)) < x)
					sum++;
			}
		}
		if(sum % 2 != 0){
			answer = false;
		}
		return answer;
	}

	/**
	  * This function is caleld to test if this puzzle is possible
	  * @param Puzzle p - Puzzle
	  * @return Boolean Answer - True or False
	  */
	public Boolean validPuzzle(Puzzle p){
		return validPuzzle(p.puzzle);
	}

	/**
	  * Get zero position
	  */

	public int getZeroPosition(){
		int answer = 0;
		for ( int i = 0; i < this.puzzle.length(); i++)
			if(this.puzzle.charAt(i) == '0')
				answer = i;
		return answer;
	}

	/**
	  * Swap char elements inside a String
	  */
	public Puzzle swapElements(int a, int b){
		Puzzle answer;
		String tmp;
		char[] c = this.puzzle.toCharArray();
		char temp = c[a];
		c[a] = c[b];
		c[b] = temp;
		tmp = new String(c);
		answer = new Puzzle(tmp);
		return answer;
	}

	/**
	  * This Function returns an array of Puzzles with all possibles moves of a recieved state
	  * @param Puzzle - Recieved State
	  * @return Puzzle[] - Array with all 
	  */

	public Puzzle[] getPuzzleStates (Puzzle p){
		int zeroPos = 0;
		int nextLevel = p.level + 1;
		Puzzle[] states = new Puzzle[0];
		if (p.validPuzzle(p)){
			// get the position of 0 to determinate the number of states
			// if its a pair position it has 2 states (Exception of middle tha has 4)
			zeroPos = p.getZeroPosition();
			switch(zeroPos){
				case 0: 
				 states = new Puzzle[2];
				 states[0] = p.swapElements(0,1);
				 states[1] = p.swapElements(0,3);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				break;
				case 1: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(1,0);
				 states[1] = p.swapElements(1,2);
				 states[2] = p.swapElements(1,4);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				 states[2].level = nextLevel;
				break;
				case 2: 
				 states = new Puzzle[2];
				 states[0] = p.swapElements(2,1);
				 states[1] = p.swapElements(2,5);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				break;
				case 3: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(3,0);
				 states[1] = p.swapElements(3,4);
				 states[2] = p.swapElements(3,6);
				break;
				case 4: 
				 states = new Puzzle[4];
				 states[0] = p.swapElements(4,1);
				 states[1] = p.swapElements(4,3);
				 states[2] = p.swapElements(4,5);
				 states[3] = p.swapElements(4,7);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				 states[2].level = nextLevel;
				 states[3].level = nextLevel;
				break;
				case 5: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(5,2);
				 states[1] = p.swapElements(5,4);
				 states[2] = p.swapElements(5,8);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				 states[2].level = nextLevel;
				break;
				case 6:
				 states = new Puzzle[2];
				 states[0] = p.swapElements(6,3);
				 states[1] = p.swapElements(6,7);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				break;
				case 7: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(7,4);
				 states[1] = p.swapElements(7,6);
				 states[2] = p.swapElements(7,8);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				 states[2].level = nextLevel;				
				break;
				case 8: 
				 states = new Puzzle[2];
				 states[0] = p.swapElements(8,7);
				 states[1] = p.swapElements(8,5);
				 states[0].level = nextLevel;
				 states[1].level = nextLevel;
				break;
			}
		}		
		return states;
	}

	/**
	  * toString method to print Puzzle
	  */

	public String toString(){
		String answer = "";
		int breakline = 0;
		for(int i=0; i < this.puzzle.length(); i++){
			answer += ( this.puzzle.charAt(i) + " ");
			breakline++;
			if(breakline == 3){
				answer += "\n";
				breakline = 0;
			}
		}
		return answer;
	}


	/**
	 * Heuristic used to AStar implementation
	 * @return Manhattam Distance from this Puzzle to the Solution Puzzle
	 */
	public int heuristic(){
		int value = 0;
		String solution = "123456780";
		for(int i = 0; i < this.puzzle.length(); i++) {
			int index1 = this.puzzle.indexOf("" + i);
			int index2 = solution.indexOf("" + i);
			int x1 = index1 / 3;
			int y1 = index1 % 3;
			int x2 = index2 / 3;
			int y2 = index2 % 3;
			value += Math.abs(x1 - x2) + Math.abs(y1 - y2);
		}
		return value;
	}

	public int getFScore(){
		return this.level + this.heuristic();
	}
	
	@Override
	public int compareTo(Puzzle p) {
		return this.getFScore() - p.getFScore();
	}


}
