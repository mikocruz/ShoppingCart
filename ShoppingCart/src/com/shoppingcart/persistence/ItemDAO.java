package com.shoppingcart.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import com.shoppingcart.classes.Item;

public class ItemDAO {
	
	public static Item getItem(String code) {
		String uri = "src\\com\\shoppingcart\\persistence\\json\\data.json";
		Item item = null;
		try {
			JsonReader reader= Json.createReader(new FileInputStream(uri));
			JsonObject jsonObject = reader.readObject();
			reader.close();
			JsonArray jsonArray = jsonObject.getJsonArray("items");
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject object = jsonArray.getJsonObject(i);
				if (object.getString("code").equals(code)) {
					item = new Item(object.getString("code"), object.getString("name"), 
							Double.parseDouble(object.getString("price")));

				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return item;
	}
}
