package gssm.k12.sc.us;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class runs the main program for the Traveling Sales Person problem. 
 * It uses cities to find the shortest path to visit each city
 * 
 * @author Tia Curry & Mary DuBard
 * @version October 2013
 *
 */

public class TSP {
	private static int numCities;
	private static ArrayList<City> CityName;	//list of City objects
	private static Scanner kboard;
	private static double[][] distancesBetween;	//array of distances between all points
	private static int[] verticesIncluded;		//list the Cities in the order they were added
	private static int numOfVerticesIncluded;
	private static double[] minPathLengths;
	private static double[][] MST;
	
	@SuppressWarnings("null")
	public static void main(String [] args){
		
		//getting cities and vertices 
		
		String unsplitCities;	//all of the cities with commas separating them
		String unsplitCoordinates;	//all of the coordinates w/ x & y separated w/ comma & separate with a space
		
		kboard = new Scanner(System.in);
		System.out.println("Enter in the number of cities: ");
		numCities = kboard.nextInt();
		kboard.nextLine();
		System.out.println("List out the name of the cities with only a comma separating cities: ");
		unsplitCities = kboard.nextLine();
		System.out.println("List out the x & y coordinates for the cities in the order they were listed before.\nSeparate x & y for one city with only a comma. Separate x & y coordinates for different cities with a space");
		unsplitCoordinates = kboard.nextLine();
		
		CityName = new ArrayList<City>();
		String[] cities = unsplitCities.split(",");
		String [] coords = unsplitCoordinates.split(" ");
		if(cities.length == numCities && cities.length == coords.length){ // checking for correct length and correct number of cities 
			for(int ii = 0; ii < coords.length; ii++){
				String[] splitCoords = coords[ii].split(",");
				City acity = new City(cities[ii], (double) Integer.parseInt(splitCoords[0]),(double) Integer.parseInt(splitCoords[1]));
				CityName.add(acity);	
			}
		}
		else{
			System.out.println("The number of citys and number of coordinates did not match. Reload and try again.");
			System.exit(0);
		}
		
		//putting distances between cities in the 2-D array
		
		distancesBetween = new double[numCities][numCities];
		for(int ii = 0; ii < CityName.size(); ii++){
			for (int jj = 0; jj < CityName.size(); jj++){
				distancesBetween[ii][jj] = getDistance(CityName.get(ii), CityName.get(jj));
				if(ii == jj)
					distancesBetween[ii][jj] = 1000000;
			}
		}
		
		MST = new double[numCities][numCities];
		verticesIncluded = new int[numCities];	//final order of vertices
		numOfVerticesIncluded = 0;
		minPathLengths = new double[numCities];
		verticesIncluded[0] = 0;	//first index
		minPathLengths[0] = 0;
		numOfVerticesIncluded++;
		MST[0][0]=0;
		for(int ii = 1; ii < numCities; ii++){
			int index = verticesIncluded[ii-1];
			for(int jj = 0; jj < numCities; jj++){
			distancesBetween[jj][index] = 1000;	//set all paths to previous vertex to be null
			distancesBetween[index][index] = 1000;
			}
			verticesIncluded[ii] = findMinVertex(CityName.get(verticesIncluded[ii-1]));
			minPathLengths[ii] = findMinPath(CityName.get(verticesIncluded[ii-1]));
			numOfVerticesIncluded++;
			MST[index][verticesIncluded[ii]] = minPathLengths[ii];
			MST[verticesIncluded[ii]][index] = minPathLengths[ii];
		}
		for(int jj = 0; jj < numCities; jj++){
			System.out.print(CityName.get(verticesIncluded[jj]).getName());
		}
		
	}
	
	/**
	 * 
	 * @return the distance between two locations
	 */
	public static double getDistance(City A, City B){
		double distancebetweenX = Math.abs(A.getX() - B.getX());
		double distancebetweenY = Math.abs(A.getY() - B.getY());
		double distance = Math.sqrt(Math.abs((distancebetweenX*distancebetweenX) + (distancebetweenY*distancebetweenY)));
		return distance;
	}
	
	public static double findMinPath(City C)
	{
		int index = 0;
		double minimum = 10000000;
		index = CityName.indexOf(C);

		for(int ii = 0; ii < numCities; ii++){
			
			if(distancesBetween[index][ii]<minimum)
				minimum = distancesBetween[index][ii];
		}
		return minimum;
	}
	
	public static int findMinVertex(City C){
		int index = 0;
		double minimum = 10000000;
		City minCity = new City("min", 0, 0);
		    	index = CityName.indexOf(C);

		for(int ii = 0; ii < numCities; ii++){
			if(distancesBetween[index][ii]<minimum){
				minimum = distancesBetween[index][ii];
				minCity = CityName.get(ii);
			}
		}
		return CityName.indexOf(minCity);
	}
	

}
