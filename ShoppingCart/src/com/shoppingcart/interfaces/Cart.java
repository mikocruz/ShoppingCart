package com.shoppingcart.interfaces;

import java.util.List;
import java.util.Map;

import com.shoppingcart.classes.Item;

public interface Cart {
	
	public void add(Item item);
	
	public void add(Item item, String promoCode);
	
	public double total();
	
	public Map<String, List<String>> getItems();

}
