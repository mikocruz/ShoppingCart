package com.shoppingcart.tests;

import static org.junit.Assert.*;

import java.awt.ItemSelectable;
import java.util.List;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

import com.shoppingcart.classes.Item;
import com.shoppingcart.classes.PricingRulesImpl;
import com.shoppingcart.classes.ShoppingCart;
import com.shoppingcart.constants.Constants;
import com.shoppingcart.interfaces.Cart;
import com.shoppingcart.interfaces.PricingRules;
import com.shoppingcart.persistence.ItemDAO;

public class ShoppingCartTest {

	@Test
	public void testTotalScenario1() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
				
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
		
		assertEquals(cart.total(), 94.70, 0D);
	}
	
	@Test
	public void testTotalScenario2() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
				
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
			cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		
		for (int i = 0; i < 4; i++)
			cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
		
		assertEquals(cart.total(), 209.40, 0D);
	}
	
	@Test
	public void testTotalScenario3() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
				
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		
		assertEquals(cart.total(), 84.70, 0D);
	}


	@Test
	public void testTotalScenario4() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
				
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_1GB), "I<3AMAYSIM");
		
		assertEquals(cart.total(), 31.32, 0D);
	}

	@Test
	public void testGetItemsAddedScenario1() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
			
		List<String> itemsAdded = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(itemsAdded, contains("3 x Unlimited 1GB", "1 x Unlimited 5GB"));
	}

	
	@Test
	public void testGetItemsAddedScenario2() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
	
		for (int i = 0; i < 4; i++)
			cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
		
		List<String> itemsAdded = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(itemsAdded, contains("2 x Unlimited 1GB", "4 x Unlimited 5GB"));
	}

	
	@Test
	public void testGetItemsAddedScenario3() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		
		List<String> itemsAdded = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(itemsAdded, contains("1 x Unlimited 1GB", "2 x Unlimited 2GB"));
	}

	@Test
	public void testGetItemsAddedScenario4() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_1GB), "I<3AMAYSIM");
		
		List<String> itemsAdded = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(itemsAdded, contains("1 x Unlimited 1GB", "1 x 1GB Data-pack"));
	}

	@Test
	public void testGetItemsExpectedScenario1() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
			
		List<String> expectedItems = cart.getItems().get(Constants.EXPECTED_CART_ITEMS);
		assertThat(expectedItems, contains("3 x Unlimited 1GB", "1 x Unlimited 5GB"));
	}

	
	@Test
	public void testGetItemsExpectedScenario2() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
	
		for (int i = 0; i < 4; i++)
			cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_LARGE));
		
		List<String> expectedItems = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(expectedItems, contains("2 x Unlimited 1GB", "4 x Unlimited 5GB"));
	}

	
	@Test
	public void testGetItemsExpectedScenario3() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_MEDIUM));
		
		List<String> expectedItems = cart.getItems().get(Constants.ITEMS_ADDED);
		assertThat(expectedItems, contains("1 x Unlimited 1GB", "2 x Unlimited 2GB"));
	}


	
	@Test
	public void testGetItemsExpectedScenario4() {
		PricingRules rules = new PricingRulesImpl();
		Cart cart = new ShoppingCart(rules);
		
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_ULT_SMALL));
		cart.add(ItemDAO.getItem(Constants.ITEM_CODE_1GB), "I<3AMAYSIM");
		
		List<String> expectedItems = cart.getItems().get(Constants.EXPECTED_CART_ITEMS);
		assertThat(expectedItems, contains("1 x Unlimited 1GB", "1 x 1GB Data-pack", 
				"'I<3AMAYSIM' Promo Applied"));
	}

}
