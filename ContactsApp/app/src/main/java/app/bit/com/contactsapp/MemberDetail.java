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

import java.util.ArrayList;

import static app.bit.com.contactsapp.Main.*;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        final Context _this = MemberDetail.this;
        Intent intent = this.getIntent();
        String seq = intent.getExtras().getString("seq");
        Log.d("넘어온 seq 값 ::",seq);
        String seq2 = intent.getStringExtra("seq");
        Log.d("넘어온 seq2 값 ::",seq2);
        ItemRetrieve query = new ItemRetrieve(_this,seq);
        Member m = (Member) new RetrieveService(){
            @Override
            public Object perform() {
                return query.execute();
            }
        }.perform();
        Log.d("================================","====================");
        Log.d("디테일 MEMBER : ",m.seq+"|"+m.name+"|"+m.email+"|"+m.phone+"|"+m.photo);
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        ImageView v = findViewById(R.id.profile);
        v.setImageResource(R.drawable.profile_1);
        TextView phone = findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
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
    private  class ItemRetrieve extends RetrieveQuery{
        String seq;
        public ItemRetrieve(Context _this,String seq) {
            super(_this);
            this.seq = seq;
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
                }
                Log.d("================================","====================");
                Log.d("디테일 if true : ",m.seq+"|"+m.name+"|"+m.email+"|"+m.phone+"|"+m.photo);
            }else{
                Log.d("================================","====================");
                Log.d("디테일 if false ","입니다.");
            }
            return m;
        }
    }

}
