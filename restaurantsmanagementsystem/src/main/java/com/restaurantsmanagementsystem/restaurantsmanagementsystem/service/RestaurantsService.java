package com.restaurantsmanagementsystem.restaurantsmanagementsystem.service;

import java.util.List; 
import com.restaurantsmanagementsystem.restaurantsmanagementsystem.model.RestaurantsModel;

public interface RestaurantsService {

	public List<RestaurantsModel> selectAllMenuItem ();
	public int addOrder(RestaurantsModel model);
	public List<RestaurantsModel> showOrder(int table_id);
	public RestaurantsModel totalPrice(int table_id);
	public int deleteOrderFinish(int table_id);
	public int checkoutFinish(int table_id);
}
