package com.zhumingwei.photolibexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pinjie(View view) {
        startActivity(new Intent(this,PlaygroundActivity.class));
    }

    public void lvjing(View view) {
        startActivity(new Intent(this,LvJingActivity.class));
    }

    public void jiancai(View view) {
        Toast.makeText(this,"待完成",Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this,CorpActivity.class));
    }

    public void xuzhuan(View view) {
        Toast.makeText(this,"待完成",Toast.LENGTH_LONG).show();
    }
}
