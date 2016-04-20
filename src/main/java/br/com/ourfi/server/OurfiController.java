package br.com.ourfi.server;

import java.util.ArrayList;
import java.util.List;

public class OurfiController {
	
	private static final int MAX_DISTANCE = 300;

	public static class Authentication {
		public String User;
		public String Token;
		public String Mod;
	}
	
	public static class Request {
		public Authentication Authentication = new Authentication();
	}
	
	public static class RequestWifi extends Request {
		public WifiInfo Wifi = new WifiInfo();
	}
	
	public static class RequestWifiList extends Request {
		public Location Location = new Location();
	}
	
	public static class Location {
		public Double Lat;
		public Double Lon;
		public Double Alt;
	}
	
	public static class WifiInfo {
		public String SSID;
		public String Password;
		public Location Location = new Location();
	}
	
	public static class Response {
		public Boolean Success = true;
		public String Message = "OK";
	}
	
	public static class ResponseWifiList extends Response {
		public List<WifiInfo> Wifis = new ArrayList<WifiInfo>();
	}
	
	private static IWifiInfoRepo repository = new WifiMemoryRepository();
	
	public static ResponseWifiList listWifis(RequestWifiList requestWifiList) {
		ResponseWifiList r = new ResponseWifiList();
		try {
			r.Wifis.addAll(repository.list(requestWifiList.Location, MAX_DISTANCE));
			return r;
		} catch (Exception e) {
			r.Success = false;
			String message = e.getMessage();
			if (message != null) {
				r.Message = message;
			} else {
				r.Message = e.getClass().getName();
			}
			return r;
		}
	}

	public static Response addWifi(RequestWifi requestWifi) {
		Response r = new Response();
		try {
			repository.add(requestWifi.Wifi, MAX_DISTANCE);
		} catch (Exception e) {
			r.Success = false;
			String message = e.getMessage();
			if (message != null) {
				r.Message = message;
			} else {
				r.Message = e.getClass().getName();
			}
		}
		return r;
	}

	public static Response removeWifi(RequestWifi requestWifi) {
		Response r = new Response();
		try {
			repository.remove(requestWifi.Wifi, MAX_DISTANCE);
		} catch (Exception e) {
			r.Success = false;
			String message = e.getMessage();
			if (message != null) {
				r.Message = message;
			} else {
				r.Message = e.getClass().getName();
			}
		}
		return r;
	}

}
