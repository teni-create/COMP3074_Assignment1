package ca.gbc.comp3074.paycalculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PayCalculatorTest {
    private void check(double hours, double rate, double percent,
                       double regular, double overtime, double total, double tax) {
        PayCalculator.Result result = PayCalculator.calculate(hours, rate, percent);
        assertEquals(regular, result.regularPay, 0.000001);
        assertEquals(overtime, result.overtimePay, 0.000001);
        assertEquals(total, result.totalPay, 0.000001);
        assertEquals(tax, result.tax, 0.000001);
    }

    @Test public void belowOvertimeThreshold() { check(30, 20, 15, 600, 0, 600, 90); }
    @Test public void exactlyFortyHours() { check(40, 20, 15, 800, 0, 800, 120); }
    @Test public void overtimeTaxUsesRegularPay() { check(45, 20, 15, 800, 150, 950, 120); }
    @Test public void fractionalHoursAndRate() { check(40.5, 18.5, 12.5, 740, 13.875, 753.875, 92.5); }
    @Test public void zeroHours() { check(0, 20, 15, 0, 0, 0, 0); }
    @Test public void percentageBoundaries() {
        check(10, 20, 0, 200, 0, 200, 0);
        check(10, 20, 100, 200, 0, 200, 200);
    }
    @Test public void rejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(-1, 20, 15));
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(40, -1, 15));
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(40, 20, 101));
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(40, 20, -1));
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(Double.NaN, 20, 15));
        assertThrows(IllegalArgumentException.class, () -> PayCalculator.calculate(40, Double.POSITIVE_INFINITY, 15));
    }
    @Test public void rejectsOverflow() {
        assertThrows(IllegalArgumentException.class,
                () -> PayCalculator.calculate(Double.MAX_VALUE, Double.MAX_VALUE, 15));
    }
}
