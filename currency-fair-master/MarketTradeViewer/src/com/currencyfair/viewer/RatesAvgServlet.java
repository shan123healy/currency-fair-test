package com.currencyfair.viewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet used to load rates information from RESTful API and refresh graphics via AJAX
 * 
 */
@WebServlet("/RatesAvgServlet")
public class RatesAvgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatesAvgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currencyFrom = request.getParameter("currencyFrom");
		String currencyTo = request.getParameter("currencyTo");
		
		response.setContentType("text/html");
		response.getWriter().write(generateJSONData(currencyFrom, currencyTo));
		
	}
	
	public String generateJSONData(String currencyFrom, String currencyTo) throws IOException {
		
		// RESTful API - Service - GetRatesAvgList
		String urlParameters = "currencyFrom=" + currencyFrom + "&currencyTo=" + currencyTo;

		String request = 
			"http://localhost:8080/Backend/rest/message/getRatesAvg";
		
		URL url = new URL(request + "?" + urlParameters);
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String JSONoutput = br.readLine();	

		conn.disconnect();

		JSONoutput = "{\"rates\":" + JSONoutput + "}";
		System.out.println(JSONoutput);
		return JSONoutput;
		
	}

}
