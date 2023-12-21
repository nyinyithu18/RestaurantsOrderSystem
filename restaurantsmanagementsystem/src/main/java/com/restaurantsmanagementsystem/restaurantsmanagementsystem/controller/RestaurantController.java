package com.restaurantsmanagementsystem.restaurantsmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.restaurantsmanagementsystem.restaurantsmanagementsystem.model.RestaurantsModel;
import com.restaurantsmanagementsystem.restaurantsmanagementsystem.service.serviceimpl.RestaurantsServiceImpl;

@Controller
public class RestaurantController {

	@Autowired
	private RestaurantsServiceImpl restaurantsServiceimpl;
	
	@RequestMapping(value = "addMenu/{table_id}", method = RequestMethod.GET)
	public ModelAndView showMenu(@PathVariable ("table_id")String table_id) {
		
		int tableId = Integer.parseInt(table_id);		
		List<RestaurantsModel> menuList = restaurantsServiceimpl.selectAllMenuItem();
		List<RestaurantsModel> showOrder = restaurantsServiceimpl.showOrder(tableId);
		
		ModelAndView model = new ModelAndView();
		model.addObject("tableId", tableId);
		model.addObject("menuList", menuList);
		model.addObject("showOrder", showOrder);
		model.setViewName("orderByMenu");
		return model;
	}
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.GET)
	public ModelAndView addOrder(@ModelAttribute RestaurantsModel restaurantsModel) {
		
		boolean checkout = false;
		restaurantsModel.set_checkout(checkout);
		int result = restaurantsServiceimpl.addOrder(restaurantsModel);
		
		int table_id = restaurantsModel.getTable_id();
		List<RestaurantsModel> menuList = restaurantsServiceimpl.selectAllMenuItem();
		List<RestaurantsModel> showOrder = restaurantsServiceimpl.showOrder(table_id);
		
		ModelAndView model = new ModelAndView();
		model.addObject("insertResult", result);
		model.addObject("tableId", table_id);
		model.addObject("menuList", menuList);
		model.addObject("showOrder", showOrder);
		model.setViewName("orderByMenu");
		return model;
	}
/*	
	@RequestMapping(value = "cancelOrder/{food_name}",method = RequestMethod.GET)
	public ModelAndView deleteOrder(@PathVariable ("food_name")String food_name) {
		
		
		int result = restaurantsServiceimpl.deleteOrder(food_name);
		
		
		List<RestaurantsModel> showOrder = restaurantsServiceimpl.showOrder(tableId);
		ModelAndView model = new ModelAndView();
		model.setViewName("orderByMenu");
		model.addObject("deleteResult", result);
		model.addObject("showOrder", showOrder);
		return model;
	}
*/
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public ModelAndView payment(@RequestParam (name="table_id")String table_id) {
		
		int tableId = Integer.parseInt(table_id);
		List<RestaurantsModel> showOrder = restaurantsServiceimpl.showOrder(tableId);
		RestaurantsModel totalPrice = restaurantsServiceimpl.totalPrice(tableId);
		
		ModelAndView model = new ModelAndView();
		model.addObject("tableId", tableId);
		model.addObject("showOrder", showOrder);
		model.addObject("totalPrice", totalPrice);
		model.setViewName("payment");
		return model;
	}
	
	@RequestMapping(value = "/finish/{tableId}", method = RequestMethod.GET)
	public ModelAndView finish(@PathVariable (name="tableId")String tableId) {
		
		int table_id = Integer.parseInt(tableId);
		int result = restaurantsServiceimpl.deleteOrderFinish(table_id);
		if(result != 0) {
			restaurantsServiceimpl.checkoutFinish(table_id);
		}
		ModelAndView model = new ModelAndView();
		model.addObject("finish", result);
		model.setViewName("payment");
		return model;
	}
}
