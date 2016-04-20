package br.com.ourfi.server;

import java.util.List;

import br.com.ourfi.server.OurfiController.Location;
import br.com.ourfi.server.OurfiController.WifiInfo;

public interface IWifiInfoRepo {
	
	List<WifiInfo> list(Location location, double distance);
	void add(WifiInfo wifi, double distance);
	void remove(WifiInfo wifi, double distance);
	
}
