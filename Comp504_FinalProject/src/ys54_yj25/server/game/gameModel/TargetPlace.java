package ys54_yj25.server.game.gameModel;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import gov.nasa.worldwind.geom.LatLon;

public class TargetPlace {

	private LatLon pos;
	private double radius = 0.001; // default
	
	public TargetPlace(LatLon pos) {
		this.pos = pos;
	}
	
	public TargetPlace(LatLon pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}

	public boolean hitTarget(LatLon latlon) {
//		double diff = Math.sqrt(pos.getLatitude().subtract(latlon.getLatitude()).getDegrees()) 
//				+ Math.sqrt(pos.getLongitude().subtract(latlon.getLongitude()).getDegrees());
		double help = 57.2958;
		double posLatitude = pos.getLatitude().getDegrees() / help;
		System.out.println(posLatitude);
		double latlonLatitude = latlon.getLatitude().getDegrees();
		System.out.println(latlonLatitude);
		double posLongitude = pos.getLongitude().getDegrees() / help;
		System.out.println(posLongitude);
		double latlinLongitude = latlon.getLongitude().getDegrees();
		System.out.println(latlinLongitude);
		double diff = Math.sqrt(Math.abs(posLatitude - latlonLatitude)) +  Math.sqrt(Math.abs(posLongitude + latlinLongitude));
		
		System.out.println(latlon + " And diff is: " + diff);
		//return diff <= Math.sqrt(radius);
		return diff <= 20;
	}
	
	public LatLon getPos() {
		return pos;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TargetPlace other = (TargetPlace) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}

	public static ArrayList<TargetPlace> getRandomTargetPlace() {
		ArrayList<TargetPlace> randomTargetPlace = new ArrayList<>();
		while (randomTargetPlace.size() < 10) {
			 double latitude = ThreadLocalRandom.current().nextDouble(-90, 90);
			 double longitude = ThreadLocalRandom.current().nextDouble(-180, 180);
			 TargetPlace newPlace = new TargetPlace(LatLon.fromRadians(latitude, longitude), 15);
			 randomTargetPlace.add(newPlace);
		}		
		return randomTargetPlace;
	}
	
}
