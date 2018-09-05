package app.bit.com.myapp2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        class Calc{
            int num,res;
            String op="";
            public void exe(){
                switch(op){
                    case "+":res+=num;break;
                    case "-":res-=num;break;
                    case "*":res*=num;break;
                    case "/":res/=num;break;
                    default:res=num;break;
                }
            }
            public int getNum(){return num;}
            public void setNum(int num){this.num=num;}
            public String getOp(){return op;}
            public void setOp(String op){this.op=op;}
            public void setRes(int res){this.res=res;}
            public String getRes(){return String.valueOf(res);}
        }
        final Calc c = new Calc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx = MainActivity.this;
        final EditText io = findViewById(R.id.io);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setNum(Integer.parseInt(io.getText().toString()));
                c.exe();
                c.setOp("+");
                io.setText("");
                //io.setText(io.getText()+"+");

            }
        });
        findViewById(R.id.minus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setNum(Integer.parseInt(io.getText().toString()));
                c.exe();
                c.setOp("-");
                io.setText("");
                //io.setText(io.getText()+"-");
            }
        });
        findViewById(R.id.mul_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setNum(Integer.parseInt(io.getText().toString()));
                c.exe();
                c.setOp("*");
                io.setText("");
                //io.setText(io.getText()+"*");
            }
        });
        findViewById(R.id.div_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setNum(Integer.parseInt(io.getText().toString()));
                c.exe();
                c.setOp("/");
                io.setText("");
                //io.setText(io.getText()+"/");
            }
        });
        findViewById(R.id.equal_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"이전res :"+c.getRes(),Toast.LENGTH_LONG).show();
                Toast.makeText(ctx,"op :"+c.getOp(),Toast.LENGTH_LONG).show();
                c.setNum(Integer.parseInt(io.getText().toString()));
                Toast.makeText(ctx,"num :"+c.getNum(),Toast.LENGTH_LONG).show();
                c.exe();
                Toast.makeText(ctx,"res :"+c.getRes(),Toast.LENGTH_LONG).show();
                io.setText(c.getRes());
                c.setRes(0);
                c.setOp("");


                /*if(io.getText().toString().contains("+")){
                    int num1 = Integer.parseInt(io.getText().toString().split("\\+")[0]);
                    int num2 = Integer.parseInt(io.getText().toString().split("\\+")[1]);
                    int res = num1 + num2;
                    io.setText(String.valueOf(res));
                }else if(io.getText().toString().contains("-")){
                    int num1 = Integer.parseInt(io.getText().toString().split("\\-")[0]);
                    int num2 = Integer.parseInt(io.getText().toString().split("\\-")[1]);
                    int res = num1 - num2;
                    io.setText(String.valueOf(res));
                }else if(io.getText().toString().contains("*")){
                    int num1 = Integer.parseInt(io.getText().toString().split("\\*")[0]);
                    int num2 = Integer.parseInt(io.getText().toString().split("\\*")[1]);
                    int res = num1 * num2;
                    io.setText(String.valueOf(res));
                }else{
                    int num1 = Integer.parseInt(io.getText().toString().split("\\/")[0]);
                    int num2 = Integer.parseInt(io.getText().toString().split("\\/")[1]);
                    int res = num1 / num2;
                    io.setText(String.valueOf(res));
                }*/


            }
        });
    }
}
