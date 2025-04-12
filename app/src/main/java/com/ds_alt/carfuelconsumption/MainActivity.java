package com.ds_alt.carfuelconsumption;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText distanceEditText, fuelPriceEditText;
    private RadioGroup fuelConsumptionGroup;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI components
        distanceEditText = findViewById(R.id.distanceEditText);
        fuelPriceEditText = findViewById(R.id.fuelPriceEditText);
        fuelConsumptionGroup = findViewById(R.id.fuelConsumptionGroup);
        Button calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Setting up button listener
        calculateButton.setOnClickListener(v -> calculateFuelConsumptionAndCost());
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateFuelConsumptionAndCost() {
        String distanceStr = distanceEditText.getText().toString();
        String fuelPriceStr = fuelPriceEditText.getText().toString();

        // Change font color and underline color to indicate fields are not in focus
        distanceEditText.setTextColor(Color.GRAY); // Change text color
        fuelPriceEditText.setTextColor(Color.GRAY);

        distanceEditText.getBackground().mutate().setTint(Color.GRAY); // Change underline color
        fuelPriceEditText.getBackground().mutate().setTint(Color.GRAY);

        if (distanceStr.isEmpty() || fuelPriceStr.isEmpty()) {
            resultTextView.setText("Please enter distance and fuel price.");
            return;
        }

        try {
            double distance = Double.parseDouble(distanceStr);
            double fuelPrice = Double.parseDouble(fuelPriceStr);

            if (distance <= 0 || fuelPrice <= 0) {
                resultTextView.setText("Distance and fuel price must be greater than zero.");
                return;
            }

            // Get selected fuel consumption rate
            int selectedId = fuelConsumptionGroup.getCheckedRadioButtonId();
            double consumptionRate;

            if (selectedId == R.id.consumption5RadioButton) {
                consumptionRate = 5;
            } else if (selectedId == R.id.consumption6RadioButton) {
                consumptionRate = 6;
            } else if (selectedId == R.id.consumption7RadioButton) {
                consumptionRate = 7;
            } else if (selectedId == R.id.consumption8RadioButton) {
                consumptionRate = 8;
            } else if (selectedId == R.id.consumption9RadioButton) {
                consumptionRate = 9;
            } else if (selectedId == R.id.consumption10RadioButton) {
                consumptionRate = 10;
            } else {
                resultTextView.setText("Please select a fuel consumption rate.");
                return;
            }

            // Calculate total fuel consumption and cost
            double totalFuel = (consumptionRate / 100) * distance;
            double totalCost = totalFuel * fuelPrice;

            // Display results
            resultTextView.setText(String.format(
                    "Total Fuel Consumption: %.2f liters\nTotal Cost: %.2f currency units",
                    totalFuel, totalCost
            ));

            // Clear focus from input fields
            distanceEditText.clearFocus();
            fuelPriceEditText.clearFocus();

            // Ensure consistent text style (optional, if needed)
            distanceEditText.setTypeface(null);
            fuelPriceEditText.setTypeface(null);

        } catch (NumberFormatException e) {
            resultTextView.setText("Please enter valid numeric values.");
        }
    }
}
