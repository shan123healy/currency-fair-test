package com.currencyfair.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * Java Application used to test the list average rates by month functionality.
 * 
 * 
 *
 */
public class GetRateAvgByCurrency {

	// http://localhost:8081/rest/message/getRatesAvg
	public static void main(String[] args) {

		try {
			
			String urlParameters = "currencyFrom=EUR&currencyTo=GBP";

			String request = 
				"http://localhost:8080/Backend/rest/message/getRatesAvg";

			// Service URL
			URL url = new URL(request + "?" + urlParameters);
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}