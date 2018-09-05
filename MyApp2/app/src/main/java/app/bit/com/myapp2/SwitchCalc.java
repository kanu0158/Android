package app.bit.com.myapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SwitchCalc extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_calc);
        Button addBtn = findViewById(R.id.add_btn);
        Button minusBtn = findViewById(R.id.minus_btn);
        Button mulBtn = findViewById(R.id.mul_btn);
        Button divBtn = findViewById(R.id.div_btn);
        Button equalBtn = findViewById(R.id.equal_btn);

        addBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        mulBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
        equalBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        final EditText num = findViewById(R.id.io);
        switch (v.getId()){
            case R.id.add_btn:break;
            case R.id.minus_btn:break;
            case R.id.mul_btn:break;
            case R.id.div_btn:break;
            case R.id.equal_btn:break;
        }
    }
}
