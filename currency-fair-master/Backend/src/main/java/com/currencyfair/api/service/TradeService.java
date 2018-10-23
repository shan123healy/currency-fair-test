package com.currencyfair.api.service;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.currencyfair.config.ConnectionFactory;
import com.currencyfair.config.DBUtil;
import com.currencyfair.domain.AmountSumDTO;
import com.currencyfair.domain.CountrySumDTO;
import com.currencyfair.domain.MessageDTO;
import com.currencyfair.domain.RateAvgDTO;


@Path("/message")
public class TradeService {

	/**
	 * Method used to add a new message to the database.
	 * 
	 * @param message
	 *            the message to be added
	 * 
	 * @return the obtained result by adding a new message
	 */
	@POST
	@Path("/add")
	@Consumes("application/json")
	public Response addMessage(MessageDTO message) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updateCount = 0;
		
		//24-JAN-15 10:27:44
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy HH:mm:ss");
		try {
			 
			Date date = formatter.parse(message.getTimePlaced());
			message.setTimePlacedF(new java.sql.Date(date.getTime()));
	 
		} catch (ParseException e) {
			
			System.out.println("Invalid date format - " + e.getMessage());
			e.printStackTrace();
			
		}		
		
		String sql = "INSERT INTO message "
				+ "(user_id, currency_from, currency_to, amount_sell, amount_buy, rate, time_placed, originating_country, time_placed_f) VALUES"
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, message.getUserId());
			preparedStatement.setString(2, message.getCurrencyFrom());
			preparedStatement.setString(3, message.getCurrencyTo());
			preparedStatement.setDouble(4, message.getAmountSell());
			preparedStatement.setDouble(5, message.getAmountBuy());
			preparedStatement.setDouble(6, message.getRate());
			preparedStatement.setString(7, message.getTimePlaced());
			preparedStatement.setString(8, message.getOriginatingCountry());
			preparedStatement.setDate(9, message.getTimePlacedF());
			
			preparedStatement.executeUpdate();

			updateCount = preparedStatement.getUpdateCount();

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		String result;

		if (updateCount > 0) {

			result = "Message created...";
			System.out.println(result);
			return Response.status(HttpURLConnection.HTTP_CREATED)
					.entity(result).build();

		} else {
			
			result = "Message NOT created...";
			System.out.println(result);
			return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR)
					.entity(result).build();

		}

	}
	
	/**
	 * Method used to find all the messages stored in the database.
	 * 
	 * @return An ArrayList of messages
	 * 
	 */
	@GET
	@Path("/getAll")
	@Produces("application/json")
	public ArrayList<MessageDTO> getAllMessages() {

		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();
		MessageDTO m;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT user_id, currency_from, currency_to, amount_sell, amount_buy, "
				+ "rate, time_placed, originating_country, time_placed_f "
				+ " FROM message";
				//+ " ORDER BY id ASC";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				m = new MessageDTO();
				m.setUserId(rs.getLong("user_id"));
				m.setCurrencyFrom(rs.getString("currency_from"));
				m.setCurrencyTo(rs.getString("currency_to"));
				m.setAmountSell(rs.getDouble("amount_sell"));
				m.setAmountBuy(rs.getDouble("amount_buy"));
				m.setRate(rs.getDouble("rate"));
				m.setTimePlaced(rs.getString("time_placed"));
				m.setOriginatingCountry(rs.getString("originating_country"));
				m.setTimePlacedF(rs.getDate("time_placed_f"));
				
				list.add(m);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
	/**
	 * Method used to find all the currency from options stored in the database 
	 * 
	 * @return An ArrayList of String
	 */
	@GET
	@Path("/getCurrencyFromList")
	@Produces("application/json")
	public ArrayList<String> getCurrencyFromList() {

		ArrayList<String> list = new ArrayList<String>();
		String c;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT DISTINCT(currency_from) FROM message"				
					+ " ORDER BY currency_from ASC";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				c = rs.getString("currency_from");				
				
				list.add(c);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
	/**
	 * Method used to find all the currency to options stored in the database 
	 * 
	 * @return An ArrayList of String
	 */
	@GET
	@Path("/getCurrencyToList")
	@Produces("application/json")
	public ArrayList<String> getCurrencyToList() {

		ArrayList<String> list = new ArrayList<String>();
		String c;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT DISTINCT(currency_to) FROM message"				
					+ " ORDER BY currency_to ASC";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				c = rs.getString("currency_to");				
				
				list.add(c);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
	/**
	 * Method used to find the rates stored in the database sorted by 
	 * date.
	 * 
	 * @param currencyFrom
	 *            the parameter by which the information will be filtered
	 * 
	 * @param currencyTo
	 *            the parameter by which the information will be filtered
	 * 
	 * @return An ArrayList of RateAvgDTO
	 */
	@GET
	@Path("/getRatesAvg")
	@Produces("application/json")
	public ArrayList<RateAvgDTO> getRatesAvg(
			@QueryParam("currencyFrom") String currencyFrom,
			@QueryParam("currencyTo") String currencyTo
			) {
		
		ArrayList<RateAvgDTO> list = new ArrayList<RateAvgDTO>();
		RateAvgDTO r;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT avg(rate) AS rate_avg, to_char(time_placed_f,'YYYY-MM-DD') AS year_month "
				+ "FROM message WHERE "
				+ "currency_from = ? AND "
				+ "currency_to = ? "
				+ "GROUP BY year_month ORDER BY year_month ";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, currencyFrom);
			preparedStatement.setString(2, currencyTo);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				r = new RateAvgDTO();
				r.setRateAvg(rs.getDouble("rate_avg"));
				r.setYearMonth(rs.getString("year_month"));
				
				list.add(r);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
	/**
	 * Method used to find the amounts stored in the database sorted by 
	 * date.
	 * 
	 * @param currencyFrom
	 *            the parameter by which the information will be filtered
	 * 
	 * @param currencyTo
	 *            the parameter by which the information will be filtered
	 * 
	 * @return An ArrayList of AmountSumDTO
	 */
	@GET
	@Path("/getAmountsSum")
	@Produces("application/json")
	public ArrayList<AmountSumDTO> getAmountsSum(
			@QueryParam("currencyFrom") String currencyFrom,
			@QueryParam("currencyTo") String currencyTo
			) {
		
		ArrayList<AmountSumDTO> list = new ArrayList<AmountSumDTO>();
		AmountSumDTO a;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT sum(amount_sell) AS sell_sum, "
				+ "sum(amount_buy) AS buy_sum, "
				+ "to_char(time_placed_f,'YYYY-MM') AS year_month "
				+ "FROM message WHERE "
				+ "currency_from = ? AND "
				+ "currency_to = ? "
				+ "GROUP BY year_month ORDER BY year_month ";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, currencyFrom);
			preparedStatement.setString(2, currencyTo);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				a = new AmountSumDTO();
				a.setSellSum(rs.getDouble("sell_sum"));
				a.setBuySum(rs.getDouble("buy_sum"));
				a.setYearMonth(rs.getString("year_month"));
				
				list.add(a);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
	/**
	 * Method used to find the countries and amounts stored in the database sorted by 
	 * date.
	 * 
	 * @param currencyFrom
	 *            the parameter by which the information will be filtered
	 * 
	 * @param currencyTo
	 *            the parameter by which the information will be filtered
	 * 
	 * @return An ArrayList of CountrySumDTO
	 */
	@GET
	@Path("/getCountriesSum")
	@Produces("application/json")
	public ArrayList<CountrySumDTO> getCountriesSum(
			@QueryParam("currencyFrom") String currencyFrom,
			@QueryParam("currencyTo") String currencyTo
			) {
		
		ArrayList<CountrySumDTO> list = new ArrayList<CountrySumDTO>();
		CountrySumDTO c;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String sql = "SELECT sum(amount_sell) AS sell_sum, "
				+ "sum(amount_buy) AS buy_sum, "
				+ "originating_country "				
				+ "FROM message WHERE "
				+ "currency_from = ? AND "
				+ "currency_to = ? "
				+ "GROUP BY originating_country ORDER BY originating_country ";

		try {

			connection = ConnectionFactory.getConnection();

			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, currencyFrom);
			preparedStatement.setString(2, currencyTo);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				c = new CountrySumDTO();
				c.setSellSum(rs.getDouble("sell_sum"));
				c.setBuySum(rs.getDouble("buy_sum"));
				c.setOriginatingCountry(rs.getString("originating_country"));
				
				list.add(c);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				DBUtil.close(preparedStatement);

			}
			if (connection != null) {

				DBUtil.close(connection);

			}
		}

		return list;

	}
	
}
