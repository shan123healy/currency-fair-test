package com.currencyfair.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Java Application used to test the add new Message functionality.
 * 
 * 
 *
 */
public class AddMessageClient {

	// http://localhost:8080/Backend/rest/message/add
	public static void main(String[] args) {

		try {

			// Service URL
			URL url = new URL(
					"http://localhost:8080/Backend//rest/message/add");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			// JSON representation of the Message to be added
			String input = "{\"userId\":\"134256\","
					+ "\"currencyFrom\":\"EUR\","
					+ "\"currencyTo\":\"GBP\","
					+ "\"amountSell\":\"1000\","
					+ "\"amountBuy\":\"747.10\","
					+ "\"rate\":\"0.7471\","
					+ "\"timePlaced\":\"24-JAN-15 10:27:44\","
					+ "\"originatingCountry\":\"FR\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

				throw new RuntimeException(
						"Contact not added! - Failed : HTTP error code : "
								+ conn.getResponseCode());

			}

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