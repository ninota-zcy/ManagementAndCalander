package com.example.managementandcalander;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MyWordsTag";
    private ContentResolver resolver;
    public static List<Activity> activityList = new LinkedList();
    private DBHelper helper;
    private Button add;
    private Button fevers;
    private Button all;
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.caidan,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch(item.getItemId()){
//            case R.id.all:{
//                refreshWordsList();
//                break;
//            }
//            case R.id.search:{
//                SearchDialog();
//                break;
//            }
//            case R.id.add:{
//                InsertDialog();
//                break;
//            }
//            case R.id.help:{
//                Intent intent = new Intent(MainActivity.this,Help.class);
//                startActivity(intent);
//                break;
//            }
//            case R.id.exit:{
//                exit();
//            }
//        }
//        return false;
//    }
//    public void exit(){
//
//        for(Activity act:activityList){
//
//            act.finish();
//
//        }
//
//        System.exit(0);
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityList.add(this);
        resolver = this.getContentResolver();

        ListView list = (ListView) findViewById(R.id.list);
        registerForContextMenu(list);

        OperationDB operationDB = OperationDB.getOperations();
        setWordsListView(operationDB.getAllMessage());

        helper = new DBHelper(this);
        helper.getReadableDatabase();
        //SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase("mydb",null);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertDialog();
            }
        });

        fevers = findViewById(R.id.fevers);
        fevers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshListFever();
            }
        });
        all = findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAllList();
            }
        });
        helper.close();
    }



    private void setWordsListView(ArrayList<Map<String, String>> items){
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Messages.Message._ID,Messages.Message.COLUMN_NAME_TIME,Messages.Message.COLUMN_NAME_FEVER,Messages.Message.COLUMN_NAME_ELSEES},
                new int[]{R.id.textId,R.id.textTime,R.id.textFever, R.id.textElsees});

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }







    //新增对话框
    public void InsertDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this)
                .setTitle("新增记录")//标题
                .setView(tableLayout)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strTem = ((EditText) tableLayout.findViewById(R.id.txtTem)).getText().toString();
                        String strElsees = ((EditText) tableLayout.findViewById(R.id.txtElsees)).getText().toString();
                        String strFever = "";
                        double temperature = Double.parseDouble(strTem);
                        if(temperature > 37.1){
                            strFever = "发烧";
                        }else
                            strFever = "未发烧";

                        OperationDB wordsDB=OperationDB.getOperations();
                        wordsDB.Insert(strTem, strFever, strElsees);

                        //单词已经插入到数据库，更新显示列表
                        refreshList();
                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {           }
                })
                .create()//创建对话框
                .show();//显示对话框
    }




    //更新列表
    public void refreshList(){
        OperationDB db=OperationDB.getOperations();
        ArrayList<Map<String,String>> items = db.getAllMessage();
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Messages.Message._ID,Messages.Message.COLUMN_NAME_TIME, Messages.Message.COLUMN_NAME_FEVER},
                new int[]{R.id.textId,R.id.textTime, R.id.textFever});

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    //更新列表
    public void refreshAllList(){
        OperationDB db=OperationDB.getOperations();
        ArrayList<Map<String,String>> items = db.getAllMessage();
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.detail,
                new String[]{Messages.Message._ID,Messages.Message.COLUMN_NAME_TIME, Messages.Message.COLUMN_NAME_TEM,Messages.Message.COLUMN_NAME_FEVER, Messages.Message.COLUMN_NAME_ELSEES},
                new int[]{R.id.textId,R.id.textTime,R.id.textTem, R.id.textFever, R.id.textElsees});

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

    public void refreshListFever() {
        OperationDB db=OperationDB.getOperations();
        ArrayList<Map<String, String>> items = db.SearchUseSql();
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{Messages.Message._ID,Messages.Message.COLUMN_NAME_TIME, Messages.Message.COLUMN_NAME_FEVER, Messages.Message.COLUMN_NAME_ELSEES},
                new int[]{R.id.textId,R.id.textTime, R.id.textFever, R.id.textElsees});

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
    }

}
