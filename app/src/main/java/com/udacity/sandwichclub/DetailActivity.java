package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView placeOfOrigin, alsoKnownAs, ingredients, description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        placeOfOrigin = findViewById(R.id.origin_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        description = findViewById(R.id.description_tv);
        image = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(image);
        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOrigin.setText(getString(R.string.no_data));
        } else {
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().size() != 0) {
            int length = sandwich.getAlsoKnownAs().size();
            for (int i = 0; i < length; i++) {
                alsoKnownAs.append(sandwich.getAlsoKnownAs().get(i));
                if (i + 1 < length)
                    alsoKnownAs.append(", ");
                else
                    alsoKnownAs.append(".");
            }
        } else {
            alsoKnownAs.setText(getString(R.string.no_data));
        }

        if (sandwich.getIngredients().size() != 0) {
            int length = sandwich.getIngredients().size();
            for (int i = 0; i < length; i++) {
                ingredients.append(sandwich.getIngredients().get(i));
                if (i + 1 < length)
                    ingredients.append(", ");
                else
                    ingredients.append(".");
            }
        } else {
            ingredients.setText(R.string.no_data);
        }

        description.setText(sandwich.getDescription());
    }

}