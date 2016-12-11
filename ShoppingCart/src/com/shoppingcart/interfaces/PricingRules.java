package com.shoppingcart.interfaces;

import java.util.List;

import com.shoppingcart.classes.Item;

public interface PricingRules {
	
	public List<Item> executeRules(List<Item> items);
	
}
