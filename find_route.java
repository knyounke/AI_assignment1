import java.io.*;
import java.util.*;


public class find_route{
	
	//public node pop(queue)
	class Neighbor{
		int vertexNumber;
		Neighbor next;
		public Neighbor(int vnum,Neighbor neighbor){
			this.vertexNumber = vnum;
			next = neighbor; 
		}
	}
	class Vertex{
		String name;
		Neighbor adjacency_list;
		Vertex(String name, Neighbor neighbors){
		this.name = name;
		this.adjacency_list = neighbors;
		}
	}



	public static void main(String[] args){
	//READ THE FILE INTO AN ADJACENCY LIST
		ArrayList<String> closed_set = new ArrayList<String>();
		//PriorityQueue<Node> fringe; //use a priority queue to sort while inserting
		String start, goal;
		Vertex[] states; //collection of names and all of the adjacency linked lists of all vertices

	
		

	//PERFORM PROBLEM SOLVING AGENT USING UNIFORM COST SEARCH & GRAPH SEARCH
		/*
		node; <--a node with a state
		frontier; <--queue
		explored; 

		do(){
		if(frontier == NULL){ return 0;}
		node = pop(frontier);
		}*/

	
	}

}

//step 1: read open the file
//step 2: separate values based on spaces and organize each city name as a new state
//step 3: close the file
//step 4: initialize arg[1] as initial state in the frontier and arg[2] as goal state and explored set as empty
//step 5: loop through

