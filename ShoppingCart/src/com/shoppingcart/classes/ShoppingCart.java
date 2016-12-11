package com.shoppingcart.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shoppingcart.constants.Constants;
import com.shoppingcart.interfaces.Cart;
import com.shoppingcart.interfaces.PricingRules;
import com.shoppingcart.persistence.ItemDAO;
import com.shoppingcart.util.Utilities;

public class ShoppingCart implements Cart {

	List<Item> items = new ArrayList<Item>();
	String promoCode = "";
	PricingRules pricingRules;
	private List<Item> expectedCartItems;
	
	public ShoppingCart() {
		super();
	}

	public ShoppingCart(PricingRules pricingRules) {
		this.pricingRules = pricingRules; 
	}

	@Override
	public void add(Item item) {
		items.add(item);
	}

	@Override
	public void add(Item item, String promoCode) {
		items.add(item);
		this.promoCode = promoCode;
	}

	@Override
	public double total() {
		double totalPrice = 0;		
		expectedCartItems = pricingRules.executeRules(this.items);
		
		for (Item item : expectedCartItems) {
			totalPrice += item.getPrice();
		}
		
		if (promoCode.equalsIgnoreCase(Constants.PROMO_CODE)) {
			totalPrice -= (Constants.ITEM_DISCOUNT_10 * totalPrice);
		}
		
		return Utilities.roundOff(totalPrice);
	}

	@Override
	public Map<String, List<String>> getItems() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> itemsAdded = getCartItems(items);
		List<String> expectedItems = getCartItems(pricingRules.executeRules(this.items));
		
		if (promoCode.equalsIgnoreCase(Constants.PROMO_CODE))
			expectedItems.add("'" + promoCode.toUpperCase() + "' " + Constants.PROMO_APPLIED);
		
		map.put(Constants.ITEMS_ADDED, itemsAdded);
		map.put(Constants.EXPECTED_CART_ITEMS, expectedItems);
		
		return map;
	}
		
	private List<String> getCartItems(List<Item> items) {
		List<String> itemList = new ArrayList<String>();
		StringBuffer sb = null;
		int ultSmallCtr = 0;
		int ultMediumCtr = 0;
		int ultLargeCtr = 0;
		int oneGb = 0;
		for (Item item : items) {
			if (item.getCode().equals(Constants.ITEM_CODE_ULT_SMALL)) {
				ultSmallCtr += 1;
			} else if (item.getCode().equals(Constants.ITEM_CODE_ULT_MEDIUM)) {
				ultMediumCtr += 1;
			} else if (item.getCode().equals(Constants.ITEM_CODE_ULT_LARGE)) {
				ultLargeCtr += 1;
			} else if (item.getCode().equals(Constants.ITEM_CODE_1GB)) {
				oneGb += 1;
			}
		}
		
		if (ultSmallCtr > 0) {
			sb = new StringBuffer();
			sb.append(ultSmallCtr).append(" x ")
				.append(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL).getName());
			
			itemList.add(sb.toString());
		}
		
		if (ultMediumCtr > 0) {
			sb = new StringBuffer(); 
			sb.append(ultMediumCtr).append(" x ")
				.append(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM).getName());
			
			itemList.add(sb.toString());
		}
		
		if (ultLargeCtr > 0) {
			sb = new StringBuffer(); 
			sb.append(ultLargeCtr).append(" x ")
				.append(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE).getName());
			
			itemList.add(sb.toString());
		}
		
		if (oneGb > 0) {
			sb = new StringBuffer();
			sb.append(oneGb).append(" x ")
				.append(ItemDAO.getItem(Constants.ITEM_CODE_1GB).getName());
				
			itemList.add(sb.toString());
		}
				
		return itemList;
	}
}
