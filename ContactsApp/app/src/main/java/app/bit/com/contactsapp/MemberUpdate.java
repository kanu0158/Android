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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static app.bit.com.contactsapp.Main.*;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context _this = MemberUpdate.this;
        String n = getIntent().getExtras().getString("name");
        String seq = getIntent().getExtras().getString("seq");
        String e = getIntent().getExtras().getString("email");
        String p = getIntent().getExtras().getString("phone");
        String a = getIntent().getExtras().getString("addr");
        String i = getIntent().getExtras().getString("profile");
        EditText name = findViewById(R.id.textName);
        name.setText(n);
        ImageView profile = findViewById(R.id.profile);
        profile.setImageDrawable(
                getResources().
                        getDrawable(
                                getResources().
                                        getIdentifier(this.getPackageName()
                                                        +":drawable/"
                                                        +i,
                                                        null,null
                                        )
                                        ,_this.getTheme()));
        EditText email = findViewById(R.id.changeEmail);
        email.setText(e);
        EditText phone = findViewById(R.id.changePhone);
        phone.setText(p);
        EditText addr = findViewById(R.id.changeAddress);
        addr.setText(a);
        findViewById(R.id.confirmBtn).setOnClickListener(
                (View v)->{
                    ItemUpdate query = new ItemUpdate(_this);
                    query.m.seq = Integer.parseInt(seq);
                    query.m.name = (name.getText().toString().equals(""))?n:name.getText().toString();
                    query.m.email = (email.getText().toString().equals(""))?e:email.getText().toString();
                    query.m.phone = (phone.getText().toString().equals(""))?p:phone.getText().toString();
                    query.m.addr = (addr.getText().toString().equals(""))?a:addr.getText().toString();

                    new StatusService(){
                        @Override
                        public void perform() {
                            query.execute();
                        }
                    }.perform();
                    Intent moveDetail = new Intent(_this,MemberDetail.class);
                    moveDetail.putExtra("seq",seq);
                    startActivity(moveDetail);
                }
        );
        findViewById(R.id.cancelBtn).setOnClickListener(
                (View v)->{
                    Intent moveDetail = new Intent(_this,MemberDetail.class);
                    moveDetail.putExtra("seq",seq);
                    startActivity(moveDetail);
                }
        );

    }
    private class UpdateQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public UpdateQuery(Context _this) {
            super(_this);
            helper=new SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemUpdate extends UpdateQuery{
        Member m;
        public ItemUpdate(Context _this) {
            super(_this);
            m = new Member();
        }
        public void execute(){
            String q = String.format(
                    " UPDATE %s " +
                    " SET %s = '%s' , " +
                    " %s = '%s' , " +
                    " %s = '%s' , " +
                    " %s = '%s' " +
                    " WHERE %s LIKE '%s' ",MEMTAB,
                                            MEMNAME,m.name,
                                            MEMEMAIL,m.email,
                                            MEMPHONE,m.phone,
                                            MEMADDR,m.addr,
                                            MEMSEQ,m.seq);

            getDatabase().execSQL(q);
        }
    }

}
