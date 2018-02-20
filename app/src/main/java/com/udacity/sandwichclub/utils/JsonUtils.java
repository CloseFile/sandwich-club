package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject name;
        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        try {
            JSONObject root = new JSONObject(json);
            name = root.getJSONObject("name");

            mainName = name.getString("mainName");
            placeOfOrigin = root.getString("placeOfOrigin");
            description = root.getString("description");
            image = root.getString("image");

            JSONArray alsoKnownAsJA = name.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();
            if (alsoKnownAsJA.length() != 0) {
                int length = alsoKnownAsJA.length();
                for (int i = 0; i < length; i++) {
                    alsoKnownAs.add(alsoKnownAsJA.getString(i));
                }
            }
            JSONArray ingredientsJA = root.getJSONArray("ingredients");
            ingredients = new ArrayList<>();
            int length = ingredientsJA.length();
            for (int i = 0; i < length; i++) {
                ingredients.add(ingredientsJA.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
//        JSONObject sandwichJson = new JSONObject(json);
//        return new Sandwich(
//                sandwichJson.getString("mainName"),
//                (List<String>) sandwichJson.getJSONArray("alsoKnownAs"),
//                sandwichJson.getString("placeOfOrigin"),
//                sandwichJson.getString("description"),
//                sandwichJson.getString("image"),
//                (List<String>) sandwichJson.getJSONArray("ingredients")
//        );


