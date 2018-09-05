package app.bit.com.myappbmi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Bmi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        final Context this__ = Bmi.this;
        final TextView bmi = findViewById(R.id.bmi);
        final TextView grade = findViewById(R.id.grade);
        final EditText height = findViewById(R.id.height);
        final EditText weight = findViewById(R.id.weight);

        final class Calc{
            double height,weight,bmi;
            String result;
            public void excute() {
                bmi = weight / (height * height);
                Toast.makeText(this__,"bmi : ",Toast.LENGTH_LONG).show();
                if (bmi >= 35) {
                    result = "3단계비만";
                } else if (bmi >= 30 && bmi < 35) {
                    result = "2단계비만";
                } else if (bmi >= 25 && bmi < 30) {
                    result = "1단계비만";
                } else if (bmi >= 23 && bmi < 25) {
                    result = "비만전단계";
                } else if (bmi >= 18.5 && bmi < 23) {
                    result = "정상";
                } else {
                    result = "저체중";
                }
            }
        }


        findViewById(R.id.bmi_btn).setOnClickListener(
                (View v)->{
                    Toast.makeText(this__,"AAA",Toast.LENGTH_LONG).show();
                    Calc c = new Calc();
                    c.height = (Double.parseDouble(height.getText().toString())/100);
                    c.weight = Double.parseDouble(weight.getText().toString());
                    Log.d("입력받은 키",""+c.height);
                    Log.d("입력받은 몸무게",""+c.weight);
                    c.excute();
                    Log.d("bmi",""+c.bmi);
                    bmi.setText(String.valueOf(c.bmi));
                    grade.setText(c.result);
                }
        );
    }
}
