package app.bit.com.contactsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static app.bit.com.contactsapp.Main.*;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context _this = MemberList.this;
        ItemList query = new ItemList(_this);
        ListView memberList = findViewById(R.id.memberList);

        findViewById(R.id.addBtn).setOnClickListener(
                (View v)->{
                    startActivity(new Intent(_this, MemberAdd.class));
                }

        );

        memberList.setAdapter(new MemberAdapter(_this,(ArrayList<Member>)new ListService() {
            @Override
            public List<?> perform() {
                return query.execute();
            }
        }.perform()));
        memberList.setOnItemClickListener(
            (AdapterView<?> p, View v, int i, long l)->{
               Intent intent = new Intent(_this,MemberDetail.class);
               Member m = (Member)memberList.getItemAtPosition(i);
               intent.putExtra("seq",m.seq+"");
               startActivity(intent);
            }
        );

        memberList.setOnItemLongClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    //Toast.makeText(_this,"길게 눌렀다!!",Toast.LENGTH_LONG).show();
                    Log.d("======길게 눌렀다","====================");
                    Member m = (Member)memberList.getItemAtPosition(i);
                    new AlertDialog.Builder(_this)
                            .setTitle("DELETE")
                            .setMessage("정말로 삭제할까요?")
                            .setPositiveButton(
                                    android.R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(_this,"삭제실행 !",Toast.LENGTH_LONG).show();
                                            ItemDelete query = new ItemDelete(_this);
                                            query.m.seq = m.seq;
                                            new StatusService(){

                                                @Override
                                                public void perform() {
                                                    query.execute();
                                                }
                                            }.perform();
                                            startActivity(new Intent(_this,MemberList.class));
                                        }
                                    }
                            )
                            .setNegativeButton(
                                    android.R.string.no,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(_this,"삭제 취소 !",Toast.LENGTH_LONG).show();
                                            Log.d("=========삭제취소","================");
                                        }
                                    }
                            ).show();
                    return true;
                }
        );
    }
    private class DeleteQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public DeleteQuery(Context _this) {
            super(_this);
            helper = new SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemDelete extends DeleteQuery{
        Member m;
        public ItemDelete(Context _this) {
            super(_this);
            m = new Member();
        }
        public void execute(){
            getDatabase().execSQL(String.format(
                    " DELETE FROM %s " +
                    " WHERE %s LIKE '%s' ",MEMTAB,MEMSEQ,m.seq
            ));
        }
    }

    private class ListQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;
        public ListQuery(Context _this) {
            super(_this);
            helper=new SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemList extends ListQuery{

        public ItemList(Context _this) {
            super(_this);
        }
        public ArrayList<Member> execute(){
            ArrayList<Member> list = new ArrayList<>();
            Cursor cursor = this.getDatabase().rawQuery(String.format(
                    " SELECT * FROM %s ", MEMTAB),null);
            Member m = null;
            if(cursor != null){
                Log.d("=======================","if문 안");
                while (cursor.moveToNext()) {
                    m = new Member();
                    m.seq = cursor.getInt(cursor.getColumnIndex(MEMSEQ));
                    m.name = cursor.getString(cursor.getColumnIndex(MEMNAME));
                    m.phone = cursor.getString(cursor.getColumnIndex(MEMPHONE));
                    m.photo = cursor.getString(cursor.getColumnIndex(MEMPHOTO));
                    list.add(m);
                    Log.d("리스트 : ",m.seq+"|"+m.name+"|"+m.phone+"|"+m.photo);
                }
                Log.d("등록된 회원수 : ",list.size()+"");
            }else{
                Log.d("등록된 회원이","없습니다.");
            }
            Log.d("=======================","if문 밖");



            return list;
        }
    }
    private class MemberAdapter extends BaseAdapter{
        ArrayList<Member> list;
        LayoutInflater inflater;
        Context _this;

        public MemberAdapter(Context _this,ArrayList<Member> list) {
            this.list = list;
            this.inflater = LayoutInflater.from(_this);
            this._this = _this;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v=inflater.inflate(R.layout.member_item,null);
                holder = new ViewHolder();
                holder.profile = v.findViewById(R.id.profile);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }
            ItemProfile query = new ItemProfile(_this);
            query.seq = list.get(i).seq+"";
            holder.profile
                    .setImageDrawable(
                            getResources().getDrawable(
                                    getResources().getIdentifier(
                                            _this.getPackageName()+":drawable/"
                                                    + (new RetrieveService() {
                                                @Override
                                                public Object perform() {
                                                    return query.execute();
                                                }
                                            }.perform())
                                            , null, null
                                    ), _this.getTheme()
                            )
                    );
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);
            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;
    }
    private class MemberProfileQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public MemberProfileQuery(Context _this) {
            super(_this);
            helper = new SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemProfile extends MemberProfileQuery{
        String seq;
        public ItemProfile(Context _this) {
            super(_this);
        }
        public String execute(){
            Cursor c = getDatabase()
                    .rawQuery(String.format(
                            " SELECT %s FROM %s WHERE %s LIKE '%s' "
                            , MEMPHOTO, MEMTAB, MEMSEQ, seq),null);
            String result = "";
            if(c != null){
                if(c.moveToNext()){
                    result = c.getString(c.getColumnIndex(MEMPHOTO));
                }
            }
            return result;
        }
    }
}
