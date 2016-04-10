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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class ProductsReader.
 * @author Yasith Lokuge
 */
public class ProductsReader {

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public ArrayList<JsonObject> getProducts() {

		ArrayList<JsonObject> jsonLines = new ArrayList<JsonObject>();
		FileInputStream fstream = null;

		try {
			String strLine;
			fstream = new FileInputStream("resources/products.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			while ((strLine = br.readLine()) != null) {

				JsonObject jsonObject = new JsonParser().parse(strLine).getAsJsonObject();
				jsonLines.add(jsonObject);

			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonLines;
	}
}
