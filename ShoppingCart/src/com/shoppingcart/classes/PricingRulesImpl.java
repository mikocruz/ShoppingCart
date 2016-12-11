package com.shoppingcart.classes;

import java.util.ArrayList;
import java.util.List;

import com.shoppingcart.constants.Constants;
import com.shoppingcart.interfaces.PricingRules;
import com.shoppingcart.persistence.ItemDAO;

public class PricingRulesImpl implements PricingRules{
	
	@Override
	public List<Item> executeRules(List<Item> items) {
		int ultSmallCtr = 0;
		int ultLargeCtr = 0;
		List<Item> freeItems = new ArrayList<Item>();
		for (Item item : items) {
			//rule one
			if (item.getCode().equals(Constants.ITEM_CODE_ULT_SMALL)) {
				ultSmallCtr += 1;
				
				if (ultSmallCtr == 3) {
					item.setPrice(0);
					ultSmallCtr = 0;
				}
			}
				
			//rule two
			if (item.getCode().equals(Constants.ITEM_CODE_ULT_LARGE)) {
				ultLargeCtr += 1;
				if (ultLargeCtr == 3) {
					for (Item item2 : items) {
						if (item2.getCode().equals(Constants.ITEM_CODE_ULT_LARGE))
							item2.setPrice(39.90);
					}
					break;
				}				
			}
								
			//rule three
			if (item.getCode().equals(Constants.ITEM_CODE_ULT_MEDIUM)) {
				Item freeItem = ItemDAO.getItem(Constants.FREEBIE_ITEM);
				freeItem.setPrice(0);
				freeItems.add(freeItem);
			}
		}
		
		freeItems.addAll(0, items);
		return freeItems;
	}
}
