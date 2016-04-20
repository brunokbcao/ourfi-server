package br.com.ourfi.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.ourfi.server.OurfiController.RequestWifi;
import br.com.ourfi.server.OurfiController.RequestWifiList;
import br.com.ourfi.server.OurfiController.Response;
import br.com.ourfi.server.OurfiController.ResponseWifiList;

public class JSONDispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		if ("GET".equals(method)) return;

		GsonBuilder gb = new GsonBuilder().serializeNulls();
		Gson json = gb.create();
		String jsonStr = null;

		ServletInputStream in = req.getInputStream();
		ServletOutputStream out = resp.getOutputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(in, baos);
		jsonStr = new String(baos.toByteArray());

		resp.setHeader("Content-Type", "application/json");

		String pathInfo = req.getPathInfo() == null ? "" : req.getPathInfo();
		if (pathInfo.startsWith("/list")) {
			RequestWifiList requestWifiList = json.fromJson(jsonStr, RequestWifiList.class);
			ResponseWifiList responseWifiList = OurfiController.listWifis(requestWifiList);
			jsonStr = json.toJson(responseWifiList);
		} else if (pathInfo.startsWith("/add")) {
			RequestWifi requestWifi = json.fromJson(jsonStr, RequestWifi.class);
			Response responseJson = OurfiController.addWifi(requestWifi);
			jsonStr = json.toJson(responseJson);
		} else if (pathInfo.startsWith("/remove")) {
			RequestWifi requestWifiList = json.fromJson(jsonStr, RequestWifi.class);
			Response responseJson = OurfiController.removeWifi(requestWifiList);
			jsonStr = json.toJson(responseJson);
		} else {
			Response res = new Response();
			res.Success = false;
			res.Message = "Unknown method: " + pathInfo;
			jsonStr = json.toJson(res);
		}

		out.println(jsonStr);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		process(arg0, arg1);
	}
}
