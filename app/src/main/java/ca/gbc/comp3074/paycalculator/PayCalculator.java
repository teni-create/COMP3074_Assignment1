package ca.gbc.comp3074.paycalculator;

/** The assignment's pay rules, kept separate from the screen code. */
public final class PayCalculator {
    private PayCalculator() { }

    public static Result calculate(double hours, double hourlyRate, double taxPercent) {
        if (!Double.isFinite(hours) || !Double.isFinite(hourlyRate)
                || !Double.isFinite(taxPercent) || hours < 0 || hourlyRate < 0
                || taxPercent < 0 || taxPercent > 100) {
            throw new IllegalArgumentException("Invalid hours, hourly rate, or tax percentage.");
        }

        double regularPay;
        double overtimePay;
        if (hours <= 40) {
            regularPay = hours * hourlyRate;
            overtimePay = 0;
        } else {
            regularPay = 40 * hourlyRate;
            overtimePay = (hours - 40) * hourlyRate * 1.5;
        }
        double totalPay = regularPay + overtimePay;
        // The handout uses regular pay for tax, not total pay.
        double tax = regularPay * (taxPercent / 100);
        if (!Double.isFinite(totalPay) || !Double.isFinite(tax)) {
            throw new IllegalArgumentException("The calculated amount is too large.");
        }
        return new Result(regularPay, overtimePay, totalPay, tax);
    }

    public static final class Result {
        public final double regularPay;
        public final double overtimePay;
        public final double totalPay;
        public final double tax;

        private Result(double regularPay, double overtimePay, double totalPay, double tax) {
            this.regularPay = regularPay;
            this.overtimePay = overtimePay;
            this.totalPay = totalPay;
            this.tax = tax;
        }
    }
}
