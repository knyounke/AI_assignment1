import java.io.*;
import java.util.*;



public class find_route{
	public static void main(String[] args){

	String start_state = args[1];
	String goal_state = args[2];

	// read the input into a 2D array and path costs into a 1D array
	ArrayList<String> paths = new ArrayList<String>();
	int num_lines = 0;

	try{
	  Scanner scanner = new Scanner(new File(args[0]));
 
	  String oneLine = oneLine = scanner.nextLine();
	 	 while(oneLine != "END OF INPUT"){
		  String[] parts = oneLine.split(" ");
		  paths.add(parts[0]);
		 if(scanner.hasNextLine()){
		  oneLine = scanner.nextLine();		
		  num_lines++;}
		else break;
	      	}
	}
	catch(FileNotFoundException not_found)
	{
	  System.out.println("File was not found!");
	}

	


	}



}

