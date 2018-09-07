
import java.util.*;
import java.io.*;


class Edge{

	private String endCity;
	private int distance;


	public Edge(String endCity, int distance){
	   this.endCity = endCity;
	   this.distance = distance;
	}

	public String getEndCity(){
	   return endCity;
	}

	public int getDistance(){
	   return distance;
	}
}



class Node{

        private String city_name;
        private ArrayList<Edge> adjacent;

        public Node(String city_name){
           this.city_name = city_name;
           adjacent = new ArrayList<Edge>();
        }

	public String getCityName(){
          return city_name;
        }

	public ArrayList<Edge> getAdjacent(){
           return adjacent;

        }

	public void addAdjacent(String endCity, int distance){
           adjacent.add(new Edge(endCity,distance));
        }

}


class State{

   Node node;
   ArrayList<Edge> path_steps;
   int path_cost = 0;
   ArrayList<String> start_cities;

   public State (Node node_name, int distance){
       this.node = node_name;
       this.path_cost = distance;
       start_cities = new ArrayList<String>();
       path_steps = new ArrayList<Edge>();
   }

   public void addEdge(Edge e){

	path_steps.add(e);
   }
   
   public void addStartCity(String s){

	start_cities.add(s);

   }


   public Node getNode(){
	return node;
   }

   public ArrayList<Edge> getSteps(){
        return path_steps;
   }

   public int getCost(){
	return path_cost;
   }

   public void setPathCost(int cost){
    this.path_cost = cost;
    }   

}

class CustomComparator implements Comparator<State>{
     @Override
      public int compare(State s1, State s2) {
	  if(s2.path_cost < s2.path_cost)
		{
 		  return 1;
		}
	  else if(s1.path_cost > s2.path_cost)
		return -1;
			return 0;
	}

}


public class find_route{

public static HashMap<String, Node> graph = new HashMap<String, Node>();

	public static boolean containsNode(Collection<State> s, Node node){
		for(State o: s){
	           if(o != null && o.getNode() == node)
			return true;
		}
	  return false;

	}

        public static State returnState(Collection<State> s, Node node){
                for(State o: s){
                   if(o != null && o.getNode() == node)
                        return o;
                }
	  return null;
        }

	private static Node getNode(String city_name){
	   if(graph.containsKey(city_name))
	     return graph.get(city_name);
	   else{
	     Node node = new Node(city_name);
	     graph.put(city_name, node);
	    //System.out.println(city_name + " ");
             return node;
	   }
	}


	public static void add(String city1, String city2, String distance){
	//add the edge to the hash map
		int weight = Integer.parseInt(distance);

		Node source = getNode(city1);
		Node destination = getNode(city2);
		
		source.addAdjacent(city2, weight);
		destination.addAdjacent(city1, weight);

	}


	public static void generate_graph(File input){
	  try{
	   Scanner sc = new Scanner(input);
	   int num_edges = 0;
	   String start, end, distance;
	 

	   while(sc.hasNextLine()){
	      String string = sc.nextLine();
	      if(string.equals("END OF INPUT")){
	       return;
              }
	      else{
		String line[];
		line = string.split(" ");
		start = line[0];
		end = line[1];
		distance = line[2];

		add(start, end, distance);
	      }	
	    }	
	  }
          catch(Exception e){
	    System.out.println("Could not read file and generate graph");
	  }
        

	}


	public static void perform_backtrace(State s, String start, String end){
	       String path_cost = Integer.toString(s.getCost());
               System.out.println("distance: " + path_cost + " km");
               String start_city = start;
               String distance = null;
               String end_city = null;
               int total = 0;
		Edge e;
                  for(int i = 0; i < s.path_steps.size(); i++){
                    e = s.path_steps.get(i); 
		    end_city = s.start_cities.get(i);
                     distance = Integer.toString(e.getDistance());
                     System.out.println(start_city + " to " + end_city + ", " + distance + "km");
		     start_city = end_city;
                     total += e.getDistance();
                  }
               distance = Integer.toString(s.getCost() - total);
               System.out.println(start_city + " to " + end + ", " + distance + "km");
       }


	public static void generate_successors(PriorityQueue<State> open_queue, ArrayList<State> closed, State item, Node node){
          ArrayList<Edge> connected_to = new ArrayList<Edge>(); //looks at n's connected nodes m's
                                   connected_to = node.getAdjacent();
                                //check to see whether successors are part of closed or open; update the cost if they aren't and insert into the open queue
                                //if successor is part of open or closed, find minimum cost of the two. If min cost has decreased and it's a member of closed, pop off closed andmove it to open_queue
                  for(Edge edge: connected_to){
                                       String end_City;
                                        end_City = edge.getEndCity();
                                        Node m_node = getNode(end_City);
                           if(containsNode(closed, m_node) ||  containsNode(open_queue, m_node)){
                                                int minimum;

                                     //if in closed, compare costs and pop if lower and move to open; if not lower, leave the same
                                    if(containsNode(closed, m_node)){
                                                State state_in_closed_set = returnState(closed, m_node);

                                               if(state_in_closed_set.getCost() > (item.getCost() + edge.getDistance())){
						System.out.println("executed");    
                                                  //update the minimum cost, move to the open_queue, and pop from the closed list
                                                      minimum = item.getCost() + edge.getDistance();
                                                           int location = 0;
                                                           for(State s: closed){
							         if((s.getNode().getCityName()).equals(state_in_closed_set.getNode().getCityName())){
                                                                  location = closed.lastIndexOf(s);
                                                                 }
                                                            }
                                                            closed.get(location).setPathCost(minimum);
							    closed.get(location).addEdge(edge);
							    closed.get(location).addStartCity(node.getCityName());
                                                            open_queue.add(closed.get(location));
                                                            closed.remove(location);
                                               }
                                      } 
                                      else{
                                                //node found in open
                                                 State state_in_open_set = returnState(open_queue, m_node);
					     //node in already open has greater cost than new calculation, update the minimum cost and add new edge to the state
                                              if(state_in_open_set.getCost() > (item.getCost() + edge.getDistance())){
                                                      minimum = item.getCost() + edge.getDistance();
                                                       State updated_state = new State(m_node, minimum);
							updated_state.addEdge(edge);
							updated_state.addStartCity(node.getCityName());
                                                       open_queue.remove(state_in_open_set);
                                                       open_queue.add(updated_state);

                                               }

                                      }

                           }
                           else{ //successor is not part of either closed or open
                                     int cost;
                                      cost = edge.getDistance() + item.getCost();
                                      State new_state = new State(m_node, cost);
                                      new_state.addEdge(edge);
				      new_state.addStartCity(node.getCityName());
                                      open_queue.add(new_state);
                            }

                }

                                



	}




	public static void perform_uninformed_search(String start, String goal, File input_file){

        
	//generate a Hash Map Adjacency Matrix of all the connected edges in the given graph
	generate_graph(input_file);

	//place open as a priority queue (based off of path cost)
	CustomComparator path_compare = new CustomComparator();	
	PriorityQueue<State> open_queue = new PriorityQueue<State>(1,path_compare);
	ArrayList<State> closed = new ArrayList<State>();

	//initialize the frontier with the start state
	Node start_node = getNode(start);
	State start_state = new State(start_node,0);
	//open.add(start_state);
	open_queue.add(start_state);


        	while(!open_queue.isEmpty()){
                 //select minimum cost state from priority queue and save it in closed; then remove it from the queue
                     closed.add(open_queue.peek());
		     open_queue.poll();
                 //check to see if that node is the goal state
                     if(closed != null && !closed.isEmpty()){
                            State item = closed.get(closed.size()-1);
                            Node node = item.getNode();
                            String city_name = node.getCityName();
                               //case that it is the goal state
				if(city_name.equals(goal)){
				perform_backtrace(item, start, goal);		
                                 System.exit(0);
				} 
			       //case that it isn't the goal state: generate the successors of that node
				else{
				   generate_successors(open_queue, closed, item, node); 	
					
				} 
			}  

 		}	
		//if went through and found no connection

		System.out.println("distance: infinity");
		System.out.println("route: ");
		System.out.println("none");
	
	}



	public static void main(String[] args){

	File input_file = new File(args[1]);
	String start = args[2];
	String goal = args[3];


        //check to see type of search to perform
	   if(args[0].equals("uninf")){
	      perform_uninformed_search(start,goal,input_file);
	   }

	   else if(args[0].equals("inf")){

	   }
	   else{
	    System.out.println("Invalid selction of search method.");
	   }		


	}


}
