package com.restaurantsmanagementsystem.restaurantsmanagementsystem.model;

import lombok.Getter;
import lombok.Setter;

public class RestaurantsModel {

	@Getter
	@Setter
	private int food_id;
	
	@Getter
	@Setter
	private String food_name;
	
	@Getter
	@Setter
	private int price;
	@Getter
	@Setter
	
	private int table_id;
	
	@Getter
	@Setter
	private int quantity;
	
	@Getter
	@Setter
	public boolean is_checkout;
	
	@Getter
	@Setter
	private int order_id;
	
	@Getter
	@Setter
	private int total_price;
}
