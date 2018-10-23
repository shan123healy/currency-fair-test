package com.currencyfair.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Java Application used to test the list all Messages functionality.
 * 

 *
 */
public class GetAllMessagesClient {

	// http://localhost:8081/Backend/rest/message/getAll
	public static void main(String[] args) {

		try {

			// Service URL
			URL url = new URL(
					"http://localhost:8080/Backend/rest/message/getAll");
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