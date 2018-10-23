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
 * Main Servlet, it loads the basic web to manage graphics
 * 
 */
@WebServlet("/ViewerServlet")
public class ViewerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		request.setAttribute("currencyFromList", getCurrencyFromList());
		request.setAttribute("currencyToList", getCurrencyToList());	
				
	    request.getRequestDispatcher("viewer.jsp").forward(request, response);
	    
	}
	
	private String[] getCurrencyFromList() throws IOException{
		
		// RESTful API - Service - GetCurrencyFromList
		URL url = new URL(
				"http://localhost:8080:/Backend/rest/message/getCurrencyFromList");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String currencyFromOutput = br.readLine();		

		currencyFromOutput = currencyFromOutput
								.replace("[", "")
								.replace("]", "")
								.replace("\"", "");
		
		conn.disconnect();
		
		String[] currencyFromList = currencyFromOutput.split(",");		
		
		return currencyFromList;
		
	}
	
	private String[] getCurrencyToList() throws IOException{
		
		// RESTful API - Service - GetCurrencyToList
		URL url = new URL(
				"http://localhost:8080/API/rest/message/getCurrencyToList");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String currencyToOutput = br.readLine();		

		currencyToOutput = currencyToOutput
								.replace("[", "")
								.replace("]", "")
								.replace("\"", "");
		
		conn.disconnect();
		
		String[] currencyToList = currencyToOutput.split(",");		
		
		return currencyToList;
		
	}

}
