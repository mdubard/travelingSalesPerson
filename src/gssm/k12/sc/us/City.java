package gssm.k12.sc.us;

public class City {
	private double x;
	private double y;
	private String cityName;
	
	public City(String name, double xcoor, double ycoor){
		cityName = name;
		x = xcoor;
		y = ycoor;
	}
	
	public String getName(){
		return cityName;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

}
