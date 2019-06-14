
//Algoritmos de Busca
// Ana Leticia Viana
// Augusto Noronha
// Cora Silberschneider
// Joao Castro

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

		throw new Exception("Puzzle sem solucao");
	}

	public List<Puzzle> DFS(Puzzle p) throws Exception {
		Set<Puzzle> alreadyVisited = new HashSet<>();
		List<Puzzle> queue = new ArrayList<>();
		List<Puzzle> visitationorder = new ArrayList<>();
		queue.add(p);
		visitationorder.add(p);
		alreadyVisited.add(p);

		while (!queue.isEmpty()) {
			Puzzle puzzle = queue.remove(0);
			visitationorder.add(puzzle);
			if (puzzle.puzzle.equals("123456780")) {
				return visitationorder;
			}
			for (Puzzle newPuzzle : puzzle.getPuzzleStates(puzzle)) {
				if (!alreadyVisited.contains(newPuzzle)) {
					// insere na posição 0 da lista, para ser o primeiro a ser retirado
					queue.add(0, newPuzzle);
					alreadyVisited.add(newPuzzle);
				}
			}
		}

		throw new Exception("Puzzle sem solucao");
	}


	public List<Puzzle> AStar(Puzzle p) throws Exception {
		// a ordenação dos puzzles dentro do priority queue é dado pela função compareTo
		// implementada dentro da classe puzzle
		Queue<Puzzle> open = new PriorityQueue<>();
		List<Puzzle> closed = new ArrayList<>();
		Set<Puzzle> alreadyVisited = new HashSet<>();

		open.add(p);
		alreadyVisited.add(p);
		while (!open.isEmpty()) {
			Puzzle puzzle = open.remove();
			closed.add(puzzle);
			if (puzzle.heuristic() == 0) { // Encontramos a Solução do Puzzle
				break;
			}
			Puzzle[] states = puzzle.getPuzzleStates(puzzle);
			for (Puzzle state : states) {
				if (!alreadyVisited.contains(state)) {
					open.add(state);
					alreadyVisited.add(state);
				}
			}
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
					double start = System.nanoTime() / 1_000_000_000;
					List<Puzzle> puzzles = solver.BFS(p);
					double elapsedTime = System.nanoTime() / 1_000_000_000.0 - start;
					System.out.println("Algoritmo levou: " + elapsedTime + " segundos");
					System.out.println("Algoritmo visitou: " + puzzles.size() + " estados");
					System.out.println("Resposta final tem: " + puzzles.get(puzzles.size() - 1).level + " estados");

				} else {
					System.out.println("Puzzle Impossivel !");
				}
				break;
			case 2: // busca em profundidade
				System.out.println(" Digite o estado inicial do puzzle: ");
				puzzleString = io.readLine();
				p = new Puzzle(puzzleString);
				if (p.isValidPuzzle) {
					double start = System.nanoTime() / 1_000_000_000;
					List<Puzzle> puzzles = solver.DFS(p);
					double elapsedTime = System.nanoTime() / 1_000_000_000.0 - start;
					System.out.println("Algoritmo levou: " + elapsedTime + " segundos");
					System.out.println("Algoritmo visitou: " + puzzles.size() + " estados");
					System.out.println("Resposta final tem: " + puzzles.get(puzzles.size() - 1).level + " estados");
				} else {
					System.out.println("Puzzle Impossivel !");
				}
				break;
			case 3: // busca com A*
				System.out.println(" Digite o estado inicial do puzzle: ");
				puzzleString = io.readLine();
				p = new Puzzle(puzzleString);
				if (p.isValidPuzzle) {
					double start = System.nanoTime() / 1_000_000_000;
					List<Puzzle> puzzles = solver.AStar(p);
					double elapsedTime = System.nanoTime() / 1_000_000_000.0 - start;
					System.out.println("Algoritmo levou: " + elapsedTime + " segundos");
					System.out.println("Algoritmo visitou: " + puzzles.size() + " estados");
					System.out.println("Resposta final tem: " + puzzles.get(puzzles.size() - 1).level + " estados");
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