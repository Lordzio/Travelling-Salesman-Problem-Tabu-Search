import java.util.ArrayList;

public class TabuSearchMethods {
	
	public static double distances[][];
	
	
	public TabuSearchMethods(double[][] distances)
	{
		this.distances = distances;
	}
	
	public TabuSearchMethods(ArrayList<City> cities)
	{
		distances = new double[cities.size()][cities.size()];
		for(int i = 0; i < cities.size(); i++) 
		{
			for(int j = 0; j < cities.size(); j++)
			{
				distances[i][j] = cities.get(i).distance(cities.get(j));
			}
		}
	}
	
	public double getPathCost(int solution[], double distances[][]){      
        double cost = 0;
   
        for(int i = 0 ; i < solution.length-1; i++){
            cost+= distances[solution[i]][solution[i+1]];
        }
   
        return cost;
    }
	
	public String prepareSoultion(int[] solution)
	{
		String solutionString = "";
		for (int i = 0; i < solution.length-1; i++)
		{
			solutionString += solution[i]+1 +" ->";
		}
		solutionString+=solution[solution.length-1]+1;
		return solutionString;
	}
}
