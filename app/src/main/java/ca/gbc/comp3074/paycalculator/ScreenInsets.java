package ca.gbc.comp3074.paycalculator;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

/** Keeps the native ActionBar and system bars clear of the form on newer Android. */
final class ScreenInsets {
    private ScreenInsets() { }

    static void apply(Activity activity) {
        if (Build.VERSION.SDK_INT < 35) {
            return;
        }
        ViewGroup content = activity.findViewById(android.R.id.content);
        View screen = content.getChildAt(0);
        screen.setOnApplyWindowInsetsListener((view, windowInsets) -> {
            Insets bars = windowInsets.getInsets(WindowInsets.Type.systemBars()
                    | WindowInsets.Type.displayCutout());
            // Native decor includes the ActionBar in the top inset it passes down.
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return windowInsets;
        });
        screen.requestApplyInsets();
    }
}
