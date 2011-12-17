package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @Author Wyatt Pillmore
 */
public class PathFinder {
	Queue<Node> queue;
	Node goal;
	

	public PathFinder(String infile, String outfile) {
			try {
				
				/** Create new Char array with the given params from maze */
				
				BufferedReader input = new BufferedReader(new FileReader(infile));
				
				infile = input.readLine();
				String op[] = infile.split(" ");
				int left = Integer.parseInt(op[0]);
				int right = Integer.parseInt(op[1]);
				char[][] maze = new char[left][right];
				
				boolean[][] isAvailable = new boolean[left][right];
				boolean SOLVED = false;
				queue = new LinkedList<Node>();//instantiate queue as a java Linked List

				int row = 0;
				while (input.ready()) {
					infile = input.readLine();

					for (int col = 0; col < infile.length(); col++) {
						maze[row][col] = infile.charAt(col);
					}
					row++;
				}
				input.close();
			
				for (int i = 0; i < maze.length ; i ++){
					for(int j = 0; j < maze[i].length; j++)
					{
						switch (maze[i][j]) {
						case ('X'):
							isAvailable[i][j] = false;
						break;
						case (' '):
							isAvailable[i][j] = true;
						break;
						case ('S'):
							queue.add(new Node(i , j));
							isAvailable[i][j] = false;
							break;
						case ('G'):
							isAvailable[i][j] = true;
						break;
						}
					}
				}
		
				Node current = null;
			//BreadthFirstSearch algorithm.
				while (!queue.isEmpty()) {
					/*
					 * Check all surrounding x and y values to see if 
					 * our cases are met. If ' ', keep searching.
					 */
					current = queue.remove();
					if (isAvailable[current.x][current.y + 1] == true) {
						current.next = new Node(current.x, current.y + 1);
						current.next.previous = current;
						queue.add(current.next);
						isAvailable[current.x][current.y + 1] = false;
					}
					if(isAvailable[current.x][current.y - 1] == true){
						current.next = new Node(current.x, current.y - 1);
						current.next.previous = current;
						queue.add(current.next);
						isAvailable[current.x][current.y - 1] = false;
					}
					if(isAvailable[current.x + 1][current.y] == true){
						current.next = new Node(current.x + 1, current.y);
						current.next.previous = current;						
						queue.add(current.next);
						isAvailable[current.x + 1][current.y] = false;
						}
					if(isAvailable[current.x - 1][current.y] == true){
						current.next = new Node(current.x - 1, current.y);
						current.next.previous = current;
						queue.add(current.next);
						isAvailable[current.x - 1][current.y] = false;
					}
					if(maze[current.x][current.y] == 'G'){
						goal = current;
						SOLVED = true;
						break;
					}
					
				}
				/*
				 * check to see if boolean value isOut is true. If we have not broken
				 * out of the search, and no output has been written, we can proceed
				 * in backtracking to the start from the Goal, replacing the fastest
				 * route with '.' all the way to 'S'.
				 */
				if(SOLVED == false){
					printMaze(maze, outfile);
					return;
				}
				
				while(maze[goal.previous.x][goal.previous.y] != 'S' && SOLVED == true){
		
				maze[goal.previous.x][goal.previous.y] = '.';
				goal = goal.previous;
				
				if(maze[goal.previous.x][goal.previous.y] == 'S')
				{
					printMaze(maze, outfile);
					}
				}
				}catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	/**
	 * Basic Node Constructor
	 */
	public void printMaze(char[][] m, String outfile){	
		try {
			PrintWriter output = new PrintWriter(new FileWriter(outfile));
			for(int i = 0; i < m.length; i++){
				for(int j = 0; j < m[i].length; j++){
					output.print(m[i][j]);
				}
					output.print("\n");
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	class Node {

		int x, y;
		Node next;
		Node previous;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Node(Node next, Node prev) {
			this.next = next;
			this.previous = prev;
		}
	}
	

	/*
	 * Tester method. I have tested every maze, including a few custom
	 * unsolvables (not included in the .zip) and timed my efforts.
	 */

	public static void main(String[] args) {

		long startTime, stopTime;
		startTime = System.nanoTime();

		while (System.nanoTime() - startTime < 1000000000) {
		}

		startTime = System.nanoTime();
		PathFinder maze = new PathFinder(args[0],args[1]);
		System.out.println(maze);
		stopTime = System.nanoTime();
		double averageTime = stopTime - startTime;

		System.out.println(averageTime / 10000000);
	}
}