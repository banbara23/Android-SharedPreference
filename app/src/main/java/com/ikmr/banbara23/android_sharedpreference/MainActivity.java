package com.ikmr.banbara23.android_sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSharedPreference();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveListSharedPreference();
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGsonSharedPreference();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGsonSharedPreference2();
            }
        });
    }

    /**
     * 保存テスト
     */
    private void saveSharedPreference() {
//        GetSharedPreferences("設定データの名前", ファイル操作のモード)
//        ファイル操作のモードは二つあります。
//        ・Context.MODE_PRIVATE
//        ・Context.MODE_MULTI_PROCESS

        SharedPreferences data = getSharedPreferences("dataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("levelSave", "111222333");
        editor.apply();

        //取り出し
        Toast.makeText(this, data.getString("levelSave", "empty"), Toast.LENGTH_SHORT).show();
    }

    /**
     * 配列
     */
    private void saveListSharedPreference() {
        final String key = "hashKey";
        //設定データファイルの名前や操作モードを自動的に設定
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = data.edit();

        HashSet<String> stringHashSet = new HashSet<>();
        stringHashSet.add("1");
        stringHashSet.add("2");
        stringHashSet.add("3");
        stringHashSet.add("4");
        stringHashSet.add("5");

        stringHashSet.remove("5");  //削除

        editor.putStringSet(key, stringHashSet);

        editor.apply(); // これを忘れるな！！！！

        //editor.commit()というメソッドでも保存できるが、非同期であるapplyを使おう

        HashSet<String> hash = (HashSet<String>) data.getStringSet(key, null);
        if (hash != null) {
            //取り出し
            Toast.makeText(this, hash.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gson使用版
     */
    private void saveGsonSharedPreference() {
        String prefKey = "jsonKey";
        SampleEntity entity = new SampleEntity();

        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = data.edit();

        Gson gson = new Gson();
        String jsonString = gson.toJson(entity);
        editor.putString(prefKey, jsonString);
        editor.apply();

        if (data.getString(prefKey, "empty") != null) {
            Toast.makeText(this, data.getString(prefKey, "empty").toString(), Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", data.getString(prefKey, "empty").toString());
        }
    }

    /**
     * Gson使用版 リストに自作クラス
     */
    private void saveGsonSharedPreference2() {
        String prefKey = "jsonKey";
        SampleEntity entity = new SampleEntity();
        List<SampleEntity> etityList = new ArrayList<>();

        etityList.add(entity);
        etityList.add(entity);
        etityList.add(entity);
        etityList.add(entity);


        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = data.edit();

        Gson gson = new Gson();
        String jsonString = gson.toJson(etityList);
        editor.putString(prefKey, jsonString);
        editor.apply();

        if (data.getString(prefKey, null) == null) {
            return;
        }
        Type type = new TypeToken<List<SampleEntity>>() {
        }.getType();
        List<SampleEntity> sampleEntities = gson.fromJson(data.getString(prefKey, null), type);
        Toast.makeText(this, sampleEntities.toString(), Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", sampleEntities.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SampleEntity {
        String valueString = "stringやで";
        int i = 1234;
        boolean isWake = true;
        String[] list = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};

        @Override
        public String toString() {
            return "SampleEntity{" +
                    "valueString='" + valueString + '\'' +
                    ", i=" + i +
                    ", isWake=" + isWake +
                    ", list=" + Arrays.toString(list) +
                    '}';
        }
    }
}
