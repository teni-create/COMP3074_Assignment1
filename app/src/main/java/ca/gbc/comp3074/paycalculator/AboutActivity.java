package ca.gbc.comp3074.paycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ScreenInsets.apply(this);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Close this screen to reveal the existing calculator underneath it.
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
