
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


class OutOfTimeException extends Exception {

}
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
			double time = System.nanoTime() / 1_000_000_000;
			if (start + 5 < time) {
				throw new OutOfTimeException();
			}
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

	public List<Puzzle> DFS2(Puzzle p) throws Exception {
		Set<Puzzle> alreadyVisited = new HashSet<>();
		List<Puzzle> queue = new ArrayList<>();
		List<Puzzle> visitationorder = new ArrayList<>();
		queue.add(p);
		visitationorder.add(p);
		alreadyVisited.add(p);

		while (!queue.isEmpty()) {
			double time = System.nanoTime() / 1_000_000_000;
			if (start + 5 < time) {
				throw new OutOfTimeException();
			}

			Puzzle puzzle = queue.remove(0);
			visitationorder.add(puzzle);
			if (puzzle.puzzle.equals("123456780")) {
				return visitationorder;
			}
			int counter = 0;
			for (Puzzle newPuzzle : puzzle.getPuzzleStates(puzzle)) {
				if (!alreadyVisited.contains(newPuzzle)) {
					queue.add(counter++, newPuzzle);
					alreadyVisited.add(newPuzzle);
				}
			}
		}

		throw new Exception("aaa?");
	}


	public List<Puzzle> AStar(Puzzle p) throws Exception {
		Queue<Puzzle> open = new PriorityQueue<>();
		List<Puzzle> closed = new ArrayList<>();
		Set<Puzzle> alreadyVisited = new HashSet<>();

		open.add(p);
		alreadyVisited.add(p);
		while (!open.isEmpty()) {
			double time = System.nanoTime() / 1_000_000_000;
			if (start + 5 < time) {
				throw new OutOfTimeException();
			}

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

	static double start;
	public static void main(String[] args) throws Exception {
		int counter = 100;
		List<Puzzle> puzzles = new ArrayList<>();
		while (counter > 0) {
			List<Integer> numbers = new ArrayList<>();
			for (int i = 0; i < 9; i++) {
				numbers.add(i);
			}

			Random rand = new Random();
			String s = "";
			while (numbers.size() > 0) {
				int next = rand.nextInt((numbers.size()));
				s += numbers.get(next);
				numbers.remove(next);
			}

			Puzzle p = new Puzzle(s);
			if (p.isValidPuzzle) {
				counter--;
				puzzles.add(p);
			}
		}

		List<Integer> tamanhosDFS = new ArrayList<>();
		List<Integer> tamanhosBFS = new ArrayList<>();
		List<Integer> tamanhoAEstrela = new ArrayList<>();

		List<Double> tempoDFS = new ArrayList<>();
		List<Double> temposBFS = new ArrayList<>();
		List<Double> tempoAEstrela = new ArrayList<>();

		PuzzleSolver solver = new PuzzleSolver();

		double elapsedTime;
		for (Puzzle puzzle : puzzles) {
			try {
				System.out.println("Comecando puzzle: " + puzzle.puzzle);
				start = System.nanoTime() / 1_000_000_000;
				List<Puzzle> pDFS = solver.DFS2(puzzle);
				elapsedTime = System.nanoTime() - start;
				tamanhosDFS.add(pDFS.size());
				tempoDFS.add(elapsedTime);
				System.out.println("tamanho DFS: " + pDFS.size());
				System.out.println("tempo DFS: " + elapsedTime);
			} catch (OutOfTimeException e) {
				System.out.println("Algoritmo não conseguiu terminar");
			}

			try {
				start = System.nanoTime() / 1_000_000_000;
				List<Puzzle> pBFS = solver.BFS(puzzle);
				elapsedTime = System.nanoTime() - start;
				tamanhosBFS.add(pBFS.size());
				temposBFS.add(elapsedTime);
				System.out.println("tamanho BFS: " + pBFS.size());
				System.out.println("tempo BFS: " + elapsedTime);
			} catch (OutOfTimeException e) {
				System.out.println("Algoritmo não conseguiu terminar");
			}
			try {
				start = System.nanoTime() / 1_000_000_000;
				List<Puzzle> aStar = solver.AStar(puzzle);
				elapsedTime = System.nanoTime() - start;
				tamanhoAEstrela.add(aStar.size());
				tempoAEstrela.add(elapsedTime);
				System.out.println("tamanho A estrela: " + aStar.size());
				System.out.println("tempo A estrela: " + elapsedTime);
			} catch (OutOfTimeException e) {
				System.out.println("Algoritmo não conseguiu terminar");
			}

		}
	}

//	public static void main(String[] args) throws Exception {
//		try {
//			BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
//			String puzzleString = "";
//			PuzzleSolver solver = new PuzzleSolver();
//			Puzzle p = null;
//			System.out.println("\t Ciência da Computação - PUC Minas");
//			System.out.println("\t Inteligência Artificial - 7o Periodo");
//			System.out.println("\t Algoritmos de Busca - 8-Puzzle");
//			System.out.println("\t Augusto Noronha - Ana Letícia - Cora Silberschneider - João Castro");
//			System.out.println("\t 0 - Sair");
//			System.out.println("\t 1 - Busca em Largura");
//			System.out.println("\t 2 - Busca em Profundidade");
//			System.out.println("\t 3 - Busca A*");
//			int option = Integer.parseInt(io.readLine());
//			switch (option) {
//			case 0:
//				break;
//			case 1: // busca em largura
//				System.out.println(" Digite o estado inicial do puzzle: ");
//				puzzleString = io.readLine();
//				p = new Puzzle(puzzleString);
//				if (p.isValidPuzzle) {
//					List<Puzzle> puzzles = solver.BFS(p);
//					System.out.println(puzzles.size());
//
////					for (Puzzle puzzle : puzzles) {
////						System.out.println(puzzle);
////					}
//				} else {
//					System.out.println("Puzzle Impossivel !");
//				}
//				break;
//			case 2: // busca em profundidade
//				System.out.println(" Digite o estado inicial do puzzle: ");
//				puzzleString = io.readLine();
//				p = new Puzzle(puzzleString);
//				if (p.isValidPuzzle) {
//					List<Puzzle> puzzles = solver.DFS(p);
//					System.out.println(puzzles.size());
//
////					for (Puzzle puzzle : puzzles) {
////						System.out.println(puzzle);
////					}
//				} else {
//					System.out.println("Puzzle Impossivel !");
//				}
//				break;
//			case 3: // busca com A*
//				System.out.println(" Digite o estado inicial do puzzle: ");
//				puzzleString = io.readLine();
//				p = new Puzzle(puzzleString);
//				if (p.isValidPuzzle) {
//					List<Puzzle> puzzles = solver.AStar(p);
//					System.out.println(puzzles.size());
////					for (Puzzle puzzle : puzzles) {
////						System.out.println(puzzle);
////					}
//				} else {
//					System.out.println("Puzzle Impossivel !");
//				}
//				break;
//			default:
//				System.out.println("\n\t\t Opção inválida");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}