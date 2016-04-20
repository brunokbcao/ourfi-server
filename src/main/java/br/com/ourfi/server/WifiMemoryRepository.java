package br.com.ourfi.server;

import java.util.ArrayList;
import java.util.List;

import br.com.ourfi.server.OurfiController.Location;
import br.com.ourfi.server.OurfiController.WifiInfo;
import br.com.ourfi.server.DistanceUtils.Bearing;

public class WifiMemoryRepository implements IWifiInfoRepo {

	private List<WifiInfo> repository = new ArrayList<WifiInfo>();

	public List<WifiInfo> list(Location location, double distance) {
		double north = DistanceUtils.getBound(location, Bearing.NORTH, distance);
		double south = DistanceUtils.getBound(location, Bearing.SOUTH, distance);
		double west = DistanceUtils.getBound(location, Bearing.WEST, distance);
		double east = DistanceUtils.getBound(location, Bearing.EAST, distance);
		
		List<WifiInfo> array = new ArrayList<WifiInfo>();
		for (WifiInfo wifiInfo : repository) {
			if (wifiInfo.Location.Lat < north
					&& wifiInfo.Location.Lat > south
					&& wifiInfo.Location.Lon < east
					&& wifiInfo.Location.Lon > west) {
				array.add(wifiInfo);
			}
		}
		return array;
	}

	public void add(WifiInfo wifi, double distance) {
		remove(wifi, distance);
		repository.add(wifi);
	}

	public void remove(WifiInfo wifi, double distance) {
		double north = DistanceUtils.getBound(wifi.Location, Bearing.NORTH, distance);
		double south = DistanceUtils.getBound(wifi.Location, Bearing.SOUTH, distance);
		double west = DistanceUtils.getBound(wifi.Location, Bearing.WEST, distance);
		double east = DistanceUtils.getBound(wifi.Location, Bearing.EAST, distance);
		
		WifiInfo remove = null;
		for (WifiInfo wifiInfo : repository) {
			if (wifiInfo.Location.Lat < north
					&& wifiInfo.Location.Lat > south
					&& wifiInfo.Location.Lon < east
					&& wifiInfo.Location.Lon > west) {
				if (wifiInfo.SSID.equals(wifi.SSID)) {
					remove = wifiInfo;
					break;
				}
			}
		}
		
		if (remove != null) {
			repository.remove(remove);
		}
	}

}
