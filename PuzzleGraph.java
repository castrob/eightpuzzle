
/**
  * Graph Algorithms - 8-Puzzle solution with LargeSearch
  * @author: Joao Castro
  * @date: 10-09-2017 (DD/MM/YYYY)
  *
  */

// Imports
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


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
			} else {
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

	public List<Puzzle> DFS(Puzzle p, Set<Puzzle> alreadyVisited) throws Exception {
        ArrayList<Puzzle> list = new ArrayList<Puzzle>();

        if (alreadyVisited.contains(p)) {
	        return list; // lista vazia significa que não encontrou
        }

        alreadyVisited.add(p);

        if (p.puzzle.equals("123456780")) {
            list.add(p);
            return list;
        }
        Puzzle[] states = p.getPuzzleStates(p);
        for(Puzzle puzzle : states){
            if(puzzle.isValidPuzzle){
                List<Puzzle> returned = DFS(puzzle, alreadyVisited);
                if (!returned.isEmpty()) {
                    list.add(p);
                    list.addAll(returned);
                    return list;
                }
            } else {
                throw new Exception("aaa?");
            }
        }

        return list;
    }

	public List<Puzzle> DFS(Puzzle p) throws Exception {
		return DFS(p, new HashSet<>());
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

	public static void main(String[] args) throws Exception {
		try{
			BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
//			int control = Integer.parseInt(io.readLine());
			String str;
//			while ( control > 0){
			str = "182043765"; //io.readLine();
			Puzzle p = new Puzzle(str);
			PuzzleGraph graph = new PuzzleGraph();
			if (p.isValidPuzzle){
				Vertex v = graph.addVertex(p);
				List<Puzzle> puzzles = graph.DFS(v.p);
				for (Puzzle puzzle : puzzles) {
					System.out.println(puzzle.puzzle);
				}
				graph.getSolucionDistance(graph.solution);
				System.out.println(graph.solutionDistance);
				graph.printSolutionPath(graph.solution);

				graph.solutionDistance = 0;
//				control--;
			}else {
				System.out.println("Grafo Impossivel !");
			}
//			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}