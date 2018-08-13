import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TravellingSalesmanProblem 
{
	
	public static void main(String[] args) 
	{
		boolean isTsp = false, isAtsp = false;
		boolean isMatrix = false, isGeo = false;
		File file;
		
			JFileChooser fileChooser = new JFileChooser();

			int response = fileChooser.showOpenDialog(null);
			if(response != JFileChooser.APPROVE_OPTION)
			{
				return;
			}

			file = fileChooser.getSelectedFile();
		

		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(IOException e) 
		{
			

			JOptionPane.showMessageDialog(null, "Error loading file " + e);
			System.exit(1);
		}

		int dimension = 0;
		try 
		{
			String line;
			while(!(line = reader.readLine()).equals("EOF")) 
			{
				
				String[] entry = line.split(": ", 2);
				if (entry[0].trim().equals("NODE_COORD_SECTION") || entry[0].trim().equals("EDGE_WEIGHT_SECTION"))
				{
					break;
				}
				switch(entry[0].trim()) {
					case "TYPE":
						if(entry[1].trim().equals("TSP"))
						{
							isTsp = true;
						}
						else if (entry[1].trim().equals("ATSP"))
						{
							isAtsp = true;
						}
						else
						{
							throw new Exception("File not in TSP/ATSP format");
						}
						break;
					case "EDGE_WEIGHT_FORMAT":
						if (entry[1].trim().equals("FULL_MATRIX"))
						{
							isMatrix = true;
						}
						break;
					case "EDGE_WEIGHT_TYPE":
						if (entry[1].trim().equals("GEO"))
						{
							isGeo = true;
						}
						break;
					case "DIMENSION":
						dimension = Integer.parseInt(entry[1].trim());
						break;
				}
			}
		}
		catch(Exception e) 
		{

			JOptionPane.showMessageDialog(null, "Error parsing header " + e);
			System.exit(1);
		}
		if(isGeo)
		{
		ArrayList<City> cities = new ArrayList<City>(dimension);

		try 
		{
			String line;
			while((line = reader.readLine()) != null && !line.equals("EOF")) 
			{
				String[] entry = line.split(" ");
				cities.add(new City(entry[0], Double.parseDouble(entry[1].trim()), Double.parseDouble(entry[2].trim())));
			}

			reader.close();
		}
		catch(Exception e) 
		{
			

			JOptionPane.showMessageDialog(null, "Error parsing data " + e);
			System.exit(1);
		}

		Timer timer = new Timer();
		Solver solver = new Solver(cities);
		timer.start();
		int[] path = solver.calculateShortestPath();
		timer.stop();		
		DecimalFormat costFormat = new DecimalFormat("#.##");

		TabuSearch ts = new TabuSearch();
		System.out.println(ts.proceed(cities));


		String summary = "Path: "+ cities.get(path[0]).getName();
		for(int i = 1; i < path.length; i++) 
		{
			summary += " -> " + cities.get(path[path.length - i]).getName();
		}
		summary += " -> " + cities.get(path[0]).getName()
		 + "\nTime: " + timer.formatTime()
		 + "\nCost: " + costFormat.format(solver.getBestCost());

				JOptionPane.showMessageDialog(null, summary);
		}
		if (isMatrix)
		{

			
			double[][] distances = new double [dimension][dimension];
			try 
			{
				String line;
				int i = 0;
				int k;
				boolean dataLine;
				while((line = reader.readLine()) != null && !line.equals("EOF")) 
				{
					dataLine = false;
					String[] entry = line.split(" ");
				
					k = 0;
						for (int j = 0; j < distances.length; j++)
						{
							for (; k < entry.length; k++)
								if (!entry[k].trim().isEmpty()) 
								{
									distances[i][j] = Double.parseDouble(entry[k].trim());
									dataLine = true;
									k++;
									break;
								}

						}
					
					if (dataLine) i++;
				}

				reader.close();
			}
			catch(Exception e) 
			{
				

				JOptionPane.showMessageDialog(null, "Error parsing data " + e);
				System.exit(1);
			}
			
			
			
		
			Timer timer = new Timer();
			Solver solver = new Solver(distances);
			timer.start();
			int[] path = solver.calculateShortestPath();
			timer.stop();

			TabuSearch ts = new TabuSearch();
			System.out.println(ts.proceedForTab(distances));
		}
		
		
	}
	
	
}
