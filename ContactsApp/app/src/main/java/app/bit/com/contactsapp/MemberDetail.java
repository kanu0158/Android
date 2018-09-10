package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.bit.com.contactsapp.util.PhoneUtil;
import app.bit.com.contactsapp.util.SMSUtil;

import static app.bit.com.contactsapp.Main.*;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context _this = MemberDetail.this;
        //Intent intent = this.getIntent();
        String seq = this.getIntent().getExtras().getString("seq");
        Log.d("넘어온 seq 값 ::",seq);
        String seq2 = this.getIntent().getStringExtra("seq");
        Log.d("넘어온 seq2 값 ::",seq2);
        ItemRetrieve query = new ItemRetrieve(_this);
        query.seq = seq;
        Member m = (Member) new RetrieveService(){
            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();

        Log.d("================================","====================");
        Log.d("디테일 MEMBER : ",m.seq+"|"+m.name+"|"+m.email+"|"+m.phone+"|"+m.photo+"|"+m.addr);
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        ImageView profile = findViewById(R.id.profile);
        //profile.setImageResource(R.drawable.profile_1);
        profile.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(this.getPackageName()+":drawable/"+m.photo,null,null),_this.getTheme()));
        TextView phone = findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
        TextView addr = findViewById(R.id.addr);
        addr.setText(m.addr);

        findViewById(R.id.updateBtn).setOnClickListener(
                (View v)->{
                    Intent moveUpdate = new Intent(_this,MemberUpdate.class);
                    moveUpdate.putExtra("seq",seq);
                    moveUpdate.putExtra("name",m.name);
                    moveUpdate.putExtra("email",m.email);
                    moveUpdate.putExtra("phone",m.phone);
                    moveUpdate.putExtra("addr",m.addr);
                    moveUpdate.putExtra("profile",m.photo);
                    startActivity(moveUpdate);
                }
        );

        findViewById(R.id.callBtn).setOnClickListener(
                (View v)->{
                    PhoneUtil util = new PhoneUtil(_this,this);
                    Log.d("===============================1","=======================");
                    util.setPhoneNum(phone.getText().toString());
                    Log.d("===============================2","=======================");
                    util.call();
                    Log.d("===============================3","=======================");
                }
        );
        findViewById(R.id.dialBtn).setOnClickListener(
                (View v)->{
                    PhoneUtil util = new PhoneUtil(_this,this);
                    Toast.makeText(_this,"전화번호:"+phone.getText().toString(),Toast.LENGTH_LONG).show();
                    util.setPhoneNum(phone.getText().toString());
                    util.dial();
                }
        );
        findViewById(R.id.smsBtn).setOnClickListener(
                (View v)->{
                    SMSUtil util = new SMSUtil(_this,this);
                }
        );
        findViewById(R.id.emailBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.albumBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.movieBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.mapBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.musicBtn).setOnClickListener(
                (View v)->{}
        );
        findViewById(R.id.listBtn).setOnClickListener(
                (View v)->{}
        );

    }

    private class RetrieveQuery extends QueryFactory {
        SQLiteOpenHelper helper;
        public RetrieveQuery(Context _this) {
            super(_this);
            helper=new SQLiteHelper(_this);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemRetrieve extends RetrieveQuery{
        String seq;
        public ItemRetrieve(Context _this) {
            super(_this);
        }
        public Member execute(){
            Member m = null;
            Cursor cursor = this.getDatabase().rawQuery(String.format(
                    " SELECT * FROM %s WHERE %s LIKE '%s' ", MEMTAB,MEMSEQ,seq),null);
            if(cursor!=null){
                while (cursor.moveToNext()){
                    m = new Member();
                    m.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    m.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    m.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    m.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    m.email = cursor.getString(cursor.getColumnIndex(MEMEMAIL));
                    m.addr = cursor.getString(cursor.getColumnIndex(MEMADDR));
                }
                Log.d("================================","====================");
                Log.d("디테일 if true : ",m.seq+"|"+m.name+"|"+m.email+"|"+m.phone+"|"+m.photo+"|"+m.addr);
            }else{
                Log.d("================================","====================");
                Log.d("디테일 if false ","입니다.");
            }
            return m;
        }
    }

}
