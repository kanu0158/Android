package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context _this = MemberList.this;
        findViewById(R.id.moveAdd).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(_this,MemberAdd.class));
                }
        );
        findViewById(R.id.delete_btn).setOnClickListener(
                (View v)->{
                    Toast.makeText(_this,"delete합니다!",Toast.LENGTH_SHORT).show();
                }
        );
        findViewById(R.id.moveDetail).setOnClickListener(
                (View v)->{
                    this.startActivity(new Intent(_this,MemberDetail.class));
                }
        );
    }
}
