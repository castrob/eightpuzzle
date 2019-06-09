
/**
  * Graph Algorithms - 8-Puzzle solution with LargeSearch
  * @author: Joao Castro
  * @date: 10-09-2017 (DD/MM/YYYY)
  *
  */

// Imports
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;



/**
  * Class to Build a Eigth-Puzzle with a determined String input
  * @author: Joao Castro
  * @date: 09/06/2017
  */

class Puzzle {
	String puzzle;
	Boolean isValidPuzzle;

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
	  * This function is called to test if this puzzle is possible
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
				break;
				case 1: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(1,0);
				 states[1] = p.swapElements(1,2);
				 states[2] = p.swapElements(1,4);
				break;
				case 2: 
				 states = new Puzzle[2];
				 states[0] = p.swapElements(2,1);
				 states[1] = p.swapElements(2,5);
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
				break;
				case 5: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(5,2);
				 states[1] = p.swapElements(5,4);
				 states[2] = p.swapElements(5,8);
				break;
				case 6:
				 states = new Puzzle[2];
				 states[0] = p.swapElements(6,3);
				 states[1] = p.swapElements(6,7);
				break;
				case 7: 
				 states = new Puzzle[3];
				 states[0] = p.swapElements(7,4);
				 states[1] = p.swapElements(7,6);
				 states[2] = p.swapElements(7,8);				
				break;
				case 8: 
				 states = new Puzzle[2];
				 states[0] = p.swapElements(8,7);
				 states[1] = p.swapElements(8,5);
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
}


class PuzzleGraph{
	List<Vertex> vertices;
	Vertex solution;
	int solutionDistance;
	//Boolean dictionary[];
	

	public class Vertex {
		public Vertex father;
		public char color;
		public Puzzle p;
		public List<Vertex> end;

		Vertex(Puzzle p){
			this.color = 'b';
			this.father = null;
			this.p = p;
			this.end = new ArrayList<Vertex>();
		}

		void addEnd(Vertex v){
			end.add(v);
		}
	} // Vertex v

	/**
	  * Standard Constructor for class PuzzleGraph
	  */
	public PuzzleGraph(){
		this.vertices = new ArrayList<Vertex>();
		this.solutionDistance = -1;
		//this.dictionary = new Boolean[600011];
	}

	/**
	  * This function is called to create a new Vertex
	  * @param Puzzle p
	  * @return Vertex - Created or existed vertex
	  */

	Vertex addVertex(Puzzle p){
		if(p != null){
			if(this.hasVertex(p)){
				return this.getVertex(p);
			}else{
				Vertex v = new Vertex(p);
				vertices.add(v);
				//this.dictionary[Integer.parseInt(p.puzzle) % 600011] = true;
				return v;
			}
		}
		return null;
	}
	/**
	  * This function is called to test if a Vertex Exists
	  * @param Puzzle p 
	  * @return Boolean true or false
	  */
/**/
	Boolean hasVertex(Puzzle p){
		for( Vertex v : vertices ){
			if ( v.p.puzzle.equals(p.puzzle))
				return true;
		}
		return false;
	}
/** /
	Boolean hasVertex(Puzzle p){
		// System.out.println(p.puzzle);
		int pos = Integer.parseInt(p.puzzle) % 600011;
			if ( dictionary[pos] == null ){
				return false;
			}
		return true;
	}
/**/

	/**
	  * This function is called to return an existed vertex
	  * @param Puzzle p
	  * @return Vertex or Null
	  */

	Vertex getVertex(Puzzle p){
		for(Vertex v : vertices){
			if ( v.p.puzzle.equals(p.puzzle))
				return v;
		}
		return null;
	}

	/**
	  * this function is called to create a new Edge 
	  * @param Vertex a - Origin vertex
	  * @param Vertex b - Destiny vertex
	  */

	void addEdge(Vertex a, Vertex b){
		if(this.hasVertex(a.p)){
			if(this.hasVertex(b.p)){
				// if exists both vertex
				 Vertex v = this.getVertex(a.p);
				 v.addEnd(b);
				 v = this.getVertex(b.p);
				 v.addEnd(a);
				}else{
					// if vertex b doesnt exist
					Vertex v = this.getVertex(a.p);
					Vertex btmp = this.addVertex(b.p);
					v.addEnd(btmp);
					btmp.addEnd(v);
				}
			}else{
				Vertex atmp = this.addVertex(a.p);
				Vertex btmp = this.addVertex(b.p);
				atmp.addEnd(btmp);
				btmp.addEnd(atmp);
			}
	}

	/**
	  * toString method of class GraphList
	  */

	public String toString(){
		String answer = "";
		for( Vertex v : vertices){
			answer += "|Father|" + "\n" + v.p;
			for(Vertex e : v.end)
				answer += "|Son|" + "\n" + e.p;
		}
		return answer;
	}

	/**
	  * This function is called to large search on graph 
	  * @param Vertex v - initial vertex
	  */
/**/
	public void BFS(Vertex s){
		Vertex v = null;
		for(Vertex u : vertices){
			u.color = 'b';
			u.father = null;
		}
		s.color = 'c';
		s.father = null;
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add(s);
		if( s != null)
		while(!q.isEmpty()){
			Vertex u = q.remove();
			if (u.p.puzzle.equals("123456780")){
				this.solution = u;
				q.clear();
			}else {
			Puzzle[] states = u.p.getPuzzleStates(u.p);
			for(Puzzle p : states){
				if(p.isValidPuzzle){
					v = this.addVertex(p);
					this.addEdge(u,v);
					q.add(v);
				}
			}

			for(Vertex tmp : u.end ){
				if(tmp.color == 'b'){
					tmp.color = 'c';
					tmp.father = u;
					if(tmp.p.puzzle.equals("123456780")){
						this.solution = tmp;
						q.clear();
					}
				}
			}
			u.color = 'p';
			}
		}
	}
/**/

	public Vertex printSolutionPath(Vertex v){
		Vertex resp;
		if (v != null){
			resp = printSolutionPath(v.father);
			System.out.println(v.p);
		}
		else{
			resp = null;
		}
		return resp;
	}

	public void getSolucionDistance(Vertex v){
		Vertex tmp = v;
		while (tmp != null){
			tmp = tmp.father;
			this.solutionDistance++;
		}
	}

	public static void main(String[] args) {
		try{
			BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
			int control = Integer.parseInt(io.readLine());
			String str;
			while ( control > 0){
			str = io.readLine();
			Puzzle p = new Puzzle(str);
			PuzzleGraph graph = new PuzzleGraph();
			if ( p.isValidPuzzle ){
				Vertex v = graph.addVertex(p);
				graph.BFS(v);
				graph.getSolucionDistance(graph.solution);
				System.out.println(graph.solutionDistance);
				graph.printSolutionPath(graph.solution);

				graph.solutionDistance = 0;
				control--;
			}else {
				System.out.println("Grafo Impossivel !");
			}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}