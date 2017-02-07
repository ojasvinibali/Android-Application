package inclassassignment.inclass2b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WeightEstimator extends AppCompatActivity {
    private RadioGroup rgbmi;
    private int feet;
    private int inches;
    private EditText feetText;
    private EditText inchesText;
    double bmi;
    double lowerRangePounds;
    double upperRangePounds;
    double totalHeight;
    TextView weightOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_estimator);

        rgbmi = (RadioGroup)findViewById(R.id.radioGroupBMI);
        weightOutput = (TextView) findViewById(R.id.weightOutput);

        findViewById(R.id.calculateWeight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feetText = (EditText)findViewById(R.id.editTextFeet);
                final String feetString = feetText.getText().toString().trim();
                inchesText = (EditText)findViewById(R.id.editTextInches);
                final String inchesString = inchesText.getText().toString().trim();

                if(rgbmi.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select a BMI Range!", Toast.LENGTH_LONG).show();
                }

                else if(feetString.equals("") || inchesString.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter height!", Toast.LENGTH_LONG).show();
                }

                else if(rgbmi.getCheckedRadioButtonId() == R.id.radioButton){
                    totalHeight = (Double.parseDouble(feetString)*12) + Double.parseDouble(inchesString);
                    lowerRangePounds = (18.5*totalHeight*totalHeight)/703;
                    double rounded = (double) Math.round(lowerRangePounds * 100) / 100;
                    weightOutput.setText("Your weight should be less than "+String.valueOf(rounded)+" lbs");
                }
                else if(rgbmi.getCheckedRadioButtonId() == R.id.radioButton2){
                    totalHeight = (Double.parseDouble(feetString)*12) + Double.parseDouble(inchesString);
                    lowerRangePounds = (18.5*totalHeight*totalHeight)/703;
                    upperRangePounds = (24.9*totalHeight*totalHeight)/703;
                    double rounded = (double) Math.round(lowerRangePounds * 100) / 100;
                    double rounded2 = (double) Math.round(upperRangePounds * 100) / 100;
                    weightOutput.setText("Your weight should be between "+String.valueOf(rounded)+" to "+String.valueOf(rounded2)+" lbs");

                }
                else if(rgbmi.getCheckedRadioButtonId() == R.id.radioButton3){
                    totalHeight = (Double.parseDouble(feetString)*12) + Double.parseDouble(inchesString);
                    lowerRangePounds = (25*totalHeight*totalHeight)/703;
                    upperRangePounds = (29.9*totalHeight*totalHeight)/703;
                    double rounded = (double) Math.round(lowerRangePounds * 100) / 100;
                    double rounded2 = (double) Math.round(upperRangePounds * 100) / 100;
                    weightOutput.setText("Your weight should be between "+String.valueOf(rounded)+" to "+String.valueOf(rounded2)+" lbs");
                }
                else if(rgbmi.getCheckedRadioButtonId() == R.id.radioButton4){
                    totalHeight = (Double.parseDouble(feetString)*12) + Double.parseDouble(inchesString);
                    upperRangePounds = (29.9*totalHeight*totalHeight)/703;
                    double rounded = (double) Math.round(upperRangePounds * 100) / 100;
                    weightOutput.setText("Your weight should be greater than "+String.valueOf(rounded)+" lbs");

                }
            }
        });
    }
}
