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
   State parent;
   int path_cost = 0;

   public State (Node node_name, int distance){
       this.node = node_name;
       this.path_cost = distance;
   }


   public Node getNode(){
        return node;
   }

   public State getParent(){
        return parent;
   }

   public int getCost(){
        return path_cost;
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

 public static void perform_uninformed_search(String start, String goal, File input_file){


        //generate a Hash Map Adjacency Matrix of all the connected edges in the given graph
        generate_graph(input_file);

        //place open as a priority queue (Custom based off of path_cost)
        CustomComparator path_compare = new CustomComparator();
        PriorityQueue<State> open_queue = new PriorityQueue<State>(1,path_compare);
        ArrayList<State> open = new ArrayList<State>();
        ArrayList<State> closed = new ArrayList<State>();

        //initialize the frontier with the start state
        Node start_node = getNode(start);
        State start_state = new State(start_node,0);
        //open.add(start_state);
        open_queue.add(start_state);



                while(!open_queue.isEmpty()){
                 //select minimum cost state from open ArrayList and save it in closed; then remove from the frontier
                     closed.add(open_queue.peek());
                     open_queue.poll();
                 //check to see if that node is the goal state
                     if(closed != null && !closed.isEmpty()){
                            State item = closed.get(closed.size()-1);
                            Node node = item.getNode();
                            String city_name = node.getCityName();
                               //case that it is the goal state
                                if(city_name.equals(goal)){
                                 String path_cost = Integer.toString(item.getCost());
                                 System.out.println("Path found. Success. Path cost is: " + path_cost);
                                 System.exit(0);
                                }
                               //case that it isn't the goal state: generate the successors of that node
                             else{
                                   ArrayList<Edge> connected_to = new ArrayList<Edge>();
                                   connected_to = node.getAdjacent();
                                //check to see whether successors are part of closed or open; update the cost if they aren't and insert into the open queue
                                //if successor is part of open or closed, find minimum cost of the two. If min cost has decreased and it's a member of closed, pop off closed andmove it to open_queue
                                      for(Edge edge: connected_to){
                                        String end_City;
                                        end_City = edge.getEndCity();
                                        Node city_node = getNode(end_City);
                                          if(containsNode(closed, city_node) || containsNode(open_queue, city_node)){
                                                int minimum;



                                                //if in closed, compare costs and pop if lower and move to open; if not lower, leave the same
                                         /*	 if(containsNode(closed, city_node)){
                                                State state_in_closed_set = returnState(closed, city_node);
                                                    if(state_in_closed_set.getCost() < (item.getCost() + edge.getDistance())){
                                                      //update the minimum cost, move to the open_queue, and pop from the closed list
                                                      minimum = item.getCost() + edge.getDistance();
                                                           int location = 0;
                                                           for(State s: closed){
                                                              if((s.getNode().getCityName()).equals(state_in_closed_set.getNode().getCityName())){
                                                                location = closed.lastIndexOf(s);
                                                                System.out.println(location);
                                                              }
                                                           }
                                                            closed.get(location).path_cost = minimum;
                                                            open_queue.add(closed.get(location));
                                                            closed.remove(location);

                                                    }
                                                }
                                              else{
                                                //node found in open
                                                 State state_in_open_set = returnState(open_queue, city_node);
                                                    if(state_in_open_set.getCost() < (item.getCost() + edge.getDistance())){
                                                      minimum = item.getCost() + edge.getDistance();
                                                       State updated_state = new State(city_node, minimum);
                                                       open_queue.remove(state_in_open_set);
                                                       open_queue.add(updated_state);

                                                    }

                                                }
                                        */

                                          }
                                          else{
                                           int cost;
                                           cost = edge.getDistance() + item.getCost();
                                           State new_state = new State(city_node, cost);
                                           open_queue.add(new_state);

                                          }

                                       }
                               }
                        }

                }
                System.out.println("No route found between " + start + " and " + goal + ".");


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




