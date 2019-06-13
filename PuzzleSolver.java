
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

class PuzzleSolver {

	/**
	 * Standard Constructor for class PuzzleGraph
	 */
	public PuzzleSolver() {
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
		for (Puzzle puzzle : states) {
			if (puzzle.isValidPuzzle) {
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

	public List<Puzzle> BFS(Puzzle p) throws Exception {
		Set<Puzzle> alreadyVisited = new HashSet<>();
		Queue<Puzzle> queue = new LinkedList<Puzzle>();
		List<Puzzle> visitationorder = new ArrayList<>();
		queue.add(p);
		visitationorder.add(p);
		alreadyVisited.add(p);

		while (!queue.isEmpty()) {
			Puzzle puzzle = queue.remove();
			visitationorder.add(puzzle);
			if (puzzle.puzzle.equals("123456780")) {
				return visitationorder;
			}
			for (Puzzle newPuzzle : puzzle.getPuzzleStates(puzzle)) {
				if (!alreadyVisited.contains(newPuzzle)) {
					queue.add(newPuzzle);
					alreadyVisited.add(newPuzzle);
				}
			}
		}

		throw new Exception("aaa?");
	}

	public List<Puzzle> AStar(Puzzle p) throws Exception {
		List<Puzzle> open = new ArrayList<>();
		List<Puzzle> closed = new ArrayList<>();

		open.add(p);
		while (!open.isEmpty()) {
			Puzzle puzzle = open.remove(0);
			closed.add(puzzle);
			if (puzzle.heuristic() == 0) { // Encontramos a Solução do Puzzle
				break;
			}
			Puzzle[] states = puzzle.getPuzzleStates(puzzle);
			for (Puzzle state : states) {
				open.add(state);
			}
			Collections.sort(open);
			System.out.println(open);
		}
		return closed;
	}

	public static void main(String[] args) throws Exception {
		try {
			BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
			String puzzleString = "";
			PuzzleSolver solver = new PuzzleSolver();
			Puzzle p = null;
			System.out.println("\t Ciência da Computação - PUC Minas");
			System.out.println("\t Inteligência Artificial - 7o Periodo");
			System.out.println("\t Algoritmos de Busca - 8-Puzzle");
			System.out.println("\t Augusto Noronha - Ana Letícia - Cora Silberschneider - João Castro");
			System.out.println("\t 0 - Sair");
			System.out.println("\t 1 - Busca em Largura");
			System.out.println("\t 2 - Busca em Profundidade");
			System.out.println("\t 3 - Busca A*");
			int option = Integer.parseInt(io.readLine());
			switch (option) {
			case 0:
				break;
			case 1: // busca em largura
				System.out.println(" Digite o estado inicial do puzzle: ");
				puzzleString = io.readLine();
				p = new Puzzle(puzzleString);
				if (p.isValidPuzzle) {
					List<Puzzle> puzzles = solver.BFS(p);
					for (Puzzle puzzle : puzzles) {
						System.out.println(puzzle);
					}
				} else {
					System.out.println("Puzzle Impossivel !");
				}
				break;
			case 2: // busca em profundidade
				System.out.println(" Digite o estado inicial do puzzle: ");
				puzzleString = io.readLine();
				p = new Puzzle(puzzleString);
				if (p.isValidPuzzle) {
					List<Puzzle> puzzles = solver.DFS(p);
					for (Puzzle puzzle : puzzles) {
						System.out.println(puzzle);
					}
				} else {
					System.out.println("Puzzle Impossivel !");
				}
				break;
			case 3: // busca com A*
				System.out.println(" Digite o estado inicial do puzzle: ");
				puzzleString = io.readLine();
				p = new Puzzle(puzzleString);
				if (p.isValidPuzzle) {
					List<Puzzle> puzzles = solver.AStar(p);
					for (Puzzle puzzle : puzzles) {
						System.out.println(puzzle);
					}
				} else {
					System.out.println("Puzzle Impossivel !");
				}
				break;
			default:
				System.out.println("\n\t\t Opção inválida");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}