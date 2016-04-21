/*******************************************************************************
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.sortable.challenge;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * The Class Main.
 * @author Yasith Lokuge
 */
public class Main {

	/** The output json. */
	private static ArrayList<JsonObject> outputJson = new ArrayList<JsonObject>();

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(Main.class);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		logger.info("Application Started");
		logger.info("Please wait while processing Data");
		OutputWriter outputWriter = new OutputWriter();
		
		ArrayList<JsonObject> listings = new ListingsReader().getListings();
		ArrayList<JsonObject> products = new ProductsReader().getProducts();
		
		for(JsonObject productJsonObject :  products){
			String [] productName = productJsonObject.get("product_name").getAsString().split("\\s|_|-");			
			ArrayList <JsonObject> productListings = new ArrayList<JsonObject>();			
			
			for(JsonObject listingJsonObject : listings){
				String [] listTitle = listingJsonObject.get("title").getAsString().split("\\s|_|-");					
				if(checkProductInListing(productName,listTitle)){
					productListings.add(listingJsonObject);								
				}					
			}						
			createEntry(productJsonObject,productListings);
		}		
		outputWriter.writeToFile(outputJson);	
		logger.info("Application Stopped");
		logger.info("Output.txt file is created at the resources folder");
	}

	/**
	 * Creates the entry.
	 *
	 * @param productJsonObject the product json object
	 * @param productListings the product listings
	 */
	private static void createEntry(JsonObject productJsonObject, ArrayList<JsonObject> productListings) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("product_name", productJsonObject.get("product_name"));
		
		JsonArray jsonArray = new JsonArray();
		
		for (JsonObject list : productListings) {						
			jsonArray.add(list);
		}
		
		jsonObject.add("listings", jsonArray);
		outputJson.add(jsonObject);
	}

	/**
	 * Check product in listing.
	 *
	 * @param productName the product name
	 * @param listTitle the list title
	 * @return true, if successful
	 */
	private static boolean checkProductInListing(String[] productName, String[] listTitle) {
				
		ArrayList <String> lists = new ArrayList<String>(Arrays.asList(listTitle));
		
		for(String name : productName){
			if(!lists.contains(name))
				return false;
		}
		return true;
	}
}
