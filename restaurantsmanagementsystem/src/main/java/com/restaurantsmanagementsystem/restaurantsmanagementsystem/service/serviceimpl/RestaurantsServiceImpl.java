package com.restaurantsmanagementsystem.restaurantsmanagementsystem.service.serviceimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import com.restaurantsmanagementsystem.restaurantsmanagementsystem.model.RestaurantsModel;
import com.restaurantsmanagementsystem.restaurantsmanagementsystem.service.RestaurantsService;

@Service
public class RestaurantsServiceImpl implements RestaurantsService{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static final String selectAllMenuQuery = "SELECT food_id, food_name, price FROM food";
	public static final String selectShowOrderQuery = "SELECT food.food_name,food.price, quantity, table_id FROM food, orders WHERE food.food_id = orders.food_id AND table_id=?;";
	public static final String insertOrderQuery = "INSERT INTO orders (food_id, table_id, quantity, is_checkout) VALUES (?,?,?,?)";
	public static final String selectTotalPriceQuery = "SELECT sum(orders.quantity * food.price) as total_price FROM food, orders WHERE food.food_id = orders.food_id AND table_id=?";
	public static final String deleteOrderQuery = "DELETE FROM orders WHERE table_id=?";
	public static final String checkoutFinishQuery = "UPDATE orders SET is_checkout = true WHERE table_id=?";
	
	@Override
	public List<RestaurantsModel> selectAllMenuItem() {
		List<RestaurantsModel> model = jdbcTemplate.query(selectAllMenuQuery, new RowMapper<RestaurantsModel>() {

			@Override
			public RestaurantsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				RestaurantsModel modelList = new RestaurantsModel();
				modelList.setFood_id(rs.getInt("food_id"));
				modelList.setFood_name(rs.getString("food_name"));
				modelList.setPrice(rs.getInt("price"));
				return modelList;
			}
		});
		return model;
	}

	@Override
	public int addOrder(RestaurantsModel model) {
		int result = jdbcTemplate.update(insertOrderQuery, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, model.getFood_id());
				ps.setInt(2, model.getTable_id());
				ps.setInt(3, model.getQuantity());
				ps.setBoolean(4, model.is_checkout);
			}
		});
		return result;
	}

	@Override
	public List<RestaurantsModel> showOrder(int table_id) {
		List<RestaurantsModel> modelList = jdbcTemplate.query(selectShowOrderQuery, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, table_id);
				
			}
		}, new RowMapper<RestaurantsModel>() {

			@Override
			public RestaurantsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
				RestaurantsModel modelList = new RestaurantsModel();
				modelList.setFood_name(rs.getString("food_name"));
				modelList.setPrice(rs.getInt("price"));
				modelList.setQuantity(rs.getInt("quantity"));
				modelList.setTable_id(rs.getInt("table_id"));
				return modelList;
			}
		});
		return modelList;
	}

	@Override
	public RestaurantsModel totalPrice(int table_id) {
		RestaurantsModel restaurantsModel = jdbcTemplate.query(selectTotalPriceQuery, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, table_id);
			}
		}, new ResultSetExtractor<RestaurantsModel>() {

			@Override
			public RestaurantsModel extractData(ResultSet rs) throws SQLException, DataAccessException {
				RestaurantsModel model = new RestaurantsModel();
				while (rs.next()) {
					model.setTotal_price(rs.getInt("total_price"));
				}
				return model;
			}
		});
		return restaurantsModel;
	}

	@Override
	public int deleteOrderFinish(int table_id) {
		int result = jdbcTemplate.update(deleteOrderQuery, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, table_id);
			}
		});
		return result;
	}

	@Override
	public int checkoutFinish(int table_id) {
		int result = jdbcTemplate.update(checkoutFinishQuery, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, table_id);		
			}
		});
		return result;
	}
		
}
