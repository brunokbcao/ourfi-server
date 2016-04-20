package br.com.ourfi.server;

import br.com.ourfi.server.OurfiController.Location;

public class DistanceUtils {

	public enum Bearing {
		NORTH(0),
		EAST(90),
		SOUTH(180),
		WEST(270);
		
		private double direction;

		private Bearing(double direction) {
			this.direction = direction;
		}

		public double toRadians() {
			return Math.toRadians(direction);
		}
	}
	
	public static double getBound(Location location, Bearing bearing, double distance) {
		double R = 6378000.1; //radius of Earth

		double bearingRad = bearing.toRadians();    //Direction
		double lat1 = Math.toRadians(location.Lat); //Latitude in radians
		double lon1 = Math.toRadians(location.Lon); //Longitude in radians

		double lat2 = Math.asin( Math.sin(lat1)* Math.cos(distance/R) +
				Math.cos(lat1)*Math.sin(distance/R)*Math.cos(bearingRad));

		double lon2 = lon1 + Math.atan2(Math.sin(bearingRad)*Math.sin(distance/R)*Math.cos(lat1),
				Math.cos(distance/R)- Math.sin(lat1)* Math.sin(lat2));

		lat2 = Math.toDegrees(lat2);
		lon2 = Math.toDegrees(lon2);
		
		switch (bearing) {
		case NORTH:
		case SOUTH:
			return lat2;
		case EAST:
		case WEST:
		default: 
			return lon2;
		}
	}
	
}
