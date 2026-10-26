package ca.gbc.comp3074.paycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {
    private EditText hoursInput;
    private EditText rateInput;
    private EditText taxInput;
    private View results;
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.CANADA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenInsets.apply(this);
        hoursInput = findViewById(R.id.hours_input);
        rateInput = findViewById(R.id.rate_input);
        taxInput = findViewById(R.id.tax_input);
        results = findViewById(R.id.results);
        findViewById(R.id.calculate_button).setOnClickListener(view -> calculatePay());
        findViewById(R.id.about_button).setOnClickListener(view ->
                startActivity(new Intent(this, AboutActivity.class)));

        if (savedInstanceState != null && savedInstanceState.containsKey("regularPay")) {
            showResults(savedInstanceState.getDouble("regularPay"),
                    savedInstanceState.getDouble("overtimePay"),
                    savedInstanceState.getDouble("totalPay"),
                    savedInstanceState.getDouble("tax"));
        }
    }

    private void calculatePay() {
        Double hours = readNumber(hoursInput);
        Double rate = readNumber(rateInput);
        Double taxPercent = readNumber(taxInput);
        results.setVisibility(View.GONE);
        if (hours == null || rate == null || taxPercent == null) {
            return;
        }
        if (taxPercent > 100) {
            taxInput.setError(getString(R.string.tax_error));
            taxInput.requestFocus();
            return;
        }
        try {
            PayCalculator.Result pay = PayCalculator.calculate(hours, rate, taxPercent);
            showResults(pay.regularPay, pay.overtimePay, pay.totalPay, pay.tax);
        } catch (IllegalArgumentException exception) {
            Toast.makeText(this, R.string.large_error, Toast.LENGTH_LONG).show();
        }
    }

    private Double readNumber(EditText input) {
        input.setError(null);
        String text = input.getText().toString().trim();
        if (text.isEmpty()) {
            input.setError(getString(R.string.required_error));
            return null;
        }
        try {
            // Decimal keyboard input is written as digits with an optional decimal point.
            if (!text.matches("[0-9]+(\\.[0-9]*)?|\\.[0-9]+")) {
                throw new NumberFormatException();
            }
            double value = Double.parseDouble(text);
            if (!Double.isFinite(value) || value < 0) {
                throw new NumberFormatException();
            }
            return value;
        } catch (NumberFormatException exception) {
            input.setError(getString(R.string.number_error));
            return null;
        }
    }

    private void showResults(double regular, double overtime, double total, double tax) {
        setAmount(R.id.regular_pay, R.string.regular_result, regular);
        setAmount(R.id.overtime_pay, R.string.overtime_result, overtime);
        setAmount(R.id.total_pay, R.string.total_result, total);
        setAmount(R.id.tax_amount, R.string.tax_result, tax);
        results.setVisibility(View.VISIBLE);
        results.setTag(new double[] {regular, overtime, total, tax});
    }

    private void setAmount(int viewId, int labelId, double amount) {
        TextView output = findViewById(viewId);
        output.setText(getString(labelId, currency.format(amount)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (results.getVisibility() == View.VISIBLE) {
            double[] amounts = (double[]) results.getTag();
            outState.putDouble("regularPay", amounts[0]);
            outState.putDouble("overtimePay", amounts[1]);
            outState.putDouble("totalPay", amounts[2]);
            outState.putDouble("tax", amounts[3]);
        }
    }
}
