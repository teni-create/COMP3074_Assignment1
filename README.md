# Pay Calculator

COMP3074 — Assignment 1  
Teni Ogun — 101568492

## About the app

This Android app calculates regular pay, overtime pay, total pay, and tax using the hours worked, hourly rate, and tax percentage entered by the user. It is written in Java and uses XML layouts.

The main screen contains the inputs and pay summary. The About screen displays my name and student ID. Its back arrow returns to the calculator with the entered values and results still available.

## Running the project

1. Open the project folder in Android Studio.
2. Wait for Gradle to sync. Install any required SDK components if prompted.
3. Select an emulator or connected device running Android 7.0 (API 24) or newer.
4. Select the `app` configuration and click Run.

The project uses compile and target SDK 37. Use Android Studio's bundled JDK. The local SDK path is stored in `local.properties`, which should remain outside version control.

## Pay calculations

- Up to 40 hours: regular pay is hours worked multiplied by the hourly rate, with no overtime.
- Above 40 hours: regular pay covers the first 40 hours. Extra hours are paid at 1.5 times the hourly rate.
- Total pay is regular pay plus overtime pay.
- Tax is calculated on regular pay, following the assignment formula.

Enter the tax rate as a percentage: `15` means 15%. Amounts are displayed in Canadian dollars to two decimal places.

For example, 45 hours at $20 per hour with 15% tax produces:

| Item | Amount |
| --- | ---: |
| Regular pay | $800.00 |
| Overtime pay | $150.00 |
| Total pay | $950.00 |
| Tax | $120.00 |

## Input checks

Hours and hourly rate must be non-negative numbers. The tax percentage must be between 0 and 100. Empty fields and invalid numbers display an error instead of a new calculation.

## Testing

All eight automated calculation tests passed. To run them in Android Studio, right-click `PayCalculatorTest` and select Run.

The following checks were also completed manually:

- Calculating pay for 45 hours and exactly 40 hours.
- Checking the name and student ID on the About screen.
- Returning from About with the inputs and results preserved.
- Submitting a blank hours field and a tax rate above 100.
- Rotating the screen and checking that the inputs and results remain available.

## Main files

- `MainActivity.java` handles the form, validation, results, and opening the About screen.
- `PayCalculator.java` contains the pay and tax calculations.
- `AboutActivity.java` handles the About screen and its back arrow.
- `ScreenInsets.java` handles spacing around system bars on newer Android versions.
- `res/layout/` contains the screen layouts.
- `res/values/strings.xml` contains the app's text and student details.
- `PayCalculatorTest.java` contains the calculation tests.
