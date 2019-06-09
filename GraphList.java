
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

	class GraphList {

		List<Vertex> vertices;
		Vertex solution;

		public class Vertex implements Comparable<Vertex> {
			public Vertex father;
			public char color;
			public Puzzle p;
			public int distance;
			public List<Vertex> end;

			Vertex(Puzzle p){
				this.color = 'b';
				this.father = null;
				this.p = p;
				this.distance = 999999;
				this.end = new ArrayList<Vertex>();
			}

			void addEnd(Vertex v){
				end.add(v);
			}

			@Override
			public int compareTo (Vertex v){
				return Integer.valueOf(this.p.puzzle).compareTo(Integer.valueOf(v.p.puzzle));
			}
		}
		/**
		  * Standard Constructor of Class GraphList
		  * Sets isDigraph and isComplete as false
		  */

		public GraphList( ){
			this.vertices = new ArrayList<Vertex>();
		}

		Vertex addVertex(Puzzle p){
			if (this.hasPuzzleOnGraph(p)){
				for ( Vertex v : vertices){
					if (v.p.puzzle.equals(p.puzzle)){
						return v;
					}
				}
			}else{	
				Vertex v = new Vertex(p);
				vertices.add(v);
				return v;
			}
			return null;
		}

		void addEdge(Vertex origin, Vertex destiny){
			if(this.hasPuzzleOnGraph(origin.p)){
				if(this.hasPuzzleOnGraph(destiny.p)){
					// add edge for both
					for(Vertex v : vertices){
						if(v.p.puzzle.equals(origin.p.puzzle)) v.end.add(destiny);
						if(v.p.puzzle.equals(destiny.p.puzzle)) v.end.add(origin);
					}
				}else{
					vertices.add(destiny);
					//continue adding edge
						for(Vertex v : vertices){
						if( v.p.puzzle.equals(origin.p.puzzle)) v.end.add(destiny);
						if(v.p.puzzle.equals(destiny.p.puzzle)) v.end.add(origin);
					}
				}
			}else{
				vertices.add(origin);
				//continue adding edge
						for(Vertex v : vertices){
						if( v.p.puzzle.equals(origin.p.puzzle)) v.end.add(destiny);
						if(v.p.puzzle.equals(destiny.p.puzzle)) v.end.add(origin);
					}				
			}
		}

		public String toString(){
			String answer = "";
			for (Vertex v : vertices){
				answer += "|Father|" + "\n" + v.p;
				for (Vertex e : v.end)
					answer += "|Son|" + "\n" + e.p;
			}
			return answer;
		}


		public int getVertexDegree(Vertex v){
			return v.end.size();
		}

		// public int getVertexDegree(int v){
		// 	int answer = 0;
		// 	for (Vertex a : vertices){
		// 		if (a.id == v){
		// 			answer = a.end.size();
		// 		}
		// 	}
		// 	return answer;
		// }

		public Boolean hasEdge (Puzzle a, Puzzle b){
			for (Vertex v : vertices){
				if(v.p.puzzle.equals(a.puzzle))
				 for (Vertex e : v.end)
				 	if(e.p.puzzle.equals(b.puzzle))
					return true;
				}
			return false;
		}

		public Boolean hasVertex (Puzzle p){
			for (Vertex v : vertices){
				if (v.p.puzzle.equals(p.puzzle)){
					return true;
				}
			}
			return false;
		}
/** not used functions /
		public Boolean isComplete(){
			for (Vertex e : vertices){
				if ( (e.end.size()) != (vertices.size()-1)){
					this.isComplete = false;
					return false;
				}
			}
			return true;
		}
		public void printEdges(){
			for (Vertex v : vertices){
				for( Edge e : v.end ){
					System.out.print(v.id + " ");
					System.out.println(e);
				}
			}
		}

		public void getComplementGraph(){
			for (int i = 0; i < vertices.size(); i++){
				for (int j = i+1; j < vertices.size(); j++){
					if (!hasEdge(i,j))
						System.out.println(i+","+j+","+1);
				}
				
			}
		}

/**/

		public void BFS(Vertex s){
			Vertex v = null;
			for ( Vertex u : vertices){
				u.color = 'b';
				u.distance = 999999;
				u.father = null;
			}
			s.color = 'c';
			s.distance = 0;
			s.father = null;
			Queue<Vertex> q = new LinkedList<>();
			q.add(s);
			while(!q.isEmpty()){
				Vertex u = q.remove();
				Puzzle[] states = u.p.getPuzzleStates(u.p);
				for ( Puzzle p : states ){
					if (!this.hasPuzzleOnGraph(p) && p.isValidPuzzle){
						v = this.addVertex(p);
						System.out.println(v.p);
						this.addEdge(u, v);
						q.add(v);
					}
				}

				for(Vertex tmp : u.end){
					if (tmp.color == 'b'){
						tmp.color = 'c';
						tmp.distance = u.distance+1;
						tmp.father = u;
						if (tmp.p.puzzle.equals("123456780")){
						 System.out.println(tmp); 
						 q.clear();	
						}
					 }
					}
				u.color = 'p';
			}
		}

		public boolean hasPuzzleOnGraph(Puzzle p){
			for (Vertex v : vertices){
				if(v.p.puzzle.equals(p.puzzle))
					return true;
			}
			return false;
		}

		// public void printProprieties(){
		// 	for (Vertex v : vertices){
		// 		for (Vertex tmp : v.end){
		// 			System.out.println("id: " + tmp.id + " idFather: " + tmp.idFather + " color " + tmp.color + " distance: " + tmp.distance);
		// 		}
		// 	}
		// }

		public void buildGraph(Vertex v){
			Puzzle[] states = v.p.getPuzzleStates(v.p);
			for (Puzzle tmp : states){
				if(tmp.isValidPuzzle){
					Vertex temp = this.addVertex(tmp);
					this.addEdge(v,temp);
				}
			}
		}

		public static void main(String[] args) {
			try{
				//Read Object
				BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
				//Variables
				GraphList graph = new GraphList();
				String str = io.readLine();
				Puzzle p = new Puzzle(str);
				Vertex v = graph.addVertex(p);
				//graph.buildGraph(v);
				//System.out.println(graph);
				graph.BFS(v);
				//graph.printProprieties();
				System.out.println(graph.solution);
					
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	} // end of GraphList class