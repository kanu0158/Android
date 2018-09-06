package app.bit.com.contactsapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;


public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context _this = Main.this;
        findViewById(R.id.moveLogin).setOnClickListener(
                (View v)->{
                    SQLiteHelper helper = new SQLiteHelper(_this);
                    this.startActivity(new Intent(_this,Login.class));
                }
        );
    }
    static class Member{int seq;String name, pw, email, phone, addr, photo;}
    static interface StatusService{public void perform();}
    static interface ListService{public List<?> perform();}
    static interface RetrieveService{public Object perform();}
    static String DBNAME = "sh.db";
    static String MEMTAB = "MEMBER";
    static String MEMSEQ = "SEQ";
    static String MEMNAME = "NAME";
    static String MEMPW = "PW";
    static String MEMEMAIL = "EMAIL";
    static String MEMPHONE = "PHONE";
    static String MEMADDR = "ADDR";
    static String MEMPHOTO = "PHOTO";
    static abstract class QueryFactory{
        Context _this;

        public QueryFactory(Context _this) {
            this._this = _this;
        }
        public abstract SQLiteDatabase getDatabase();
    }
    static class SQLiteHelper extends SQLiteOpenHelper{

        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1);
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    "CREATE TABLE IF NOT EXISTS %s " +
                            " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT, " +
                            " %s TEXT " +
                            " ) ",MEMTAB, MEMSEQ, MEMNAME, MEMPW, MEMEMAIL, MEMPHONE, MEMADDR, MEMPHOTO
            );
            Log.d("실행할 쿼리 :: ",sql);
            db.execSQL(sql);
            Log.d("===========================","create쿼리실행완료");
            Log.d("실행할 쿼리 :: ",sql);
            String[] names = {"에릭감마","로드존슨","앤디루빈","존레식","라이언달"};
            String[] emails = {"gof","spring","android","jqueryAjax","nodeJS"};
            for(int i=1;i<=5;i++){
                db.execSQL(String.format(
                        "INSERT INTO %s " +
                                " (%s , %s , %s , %s , %s ,  %s ) " +
                                " VALUES ( '%s' , '%s' , '%s' , '%s' , '%s' , '%s' )"
                        ,MEMTAB, MEMNAME, MEMPW, MEMEMAIL, MEMPHONE, MEMADDR, MEMPHOTO,
                        names[i-1], "1", emails[i-1]+"@google.com", "010-1234-567"+i, "신촌"+i+"길", "profile_"+i
                ));
            }
            Log.d("===========================","insert쿼리실행완료");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTAB);
            onCreate(db);
        }
    }
}
