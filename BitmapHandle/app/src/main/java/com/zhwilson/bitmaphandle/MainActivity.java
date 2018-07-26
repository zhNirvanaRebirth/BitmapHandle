package com.zhwilson.bitmaphandle;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.grid_view);
        GlideAdapter adapter = new GlideAdapter(this, getUrlList());
        gridView.setAdapter(adapter);

    }

    private List<String> getUrlList(){
        String[] urls = {"http://pic.ecook.cn/web/261231371.jpg!s1",
                "http://pic.ecook.cn/web/261236292.jpg!s7",
                "http://pic.ecook.cn/web/261220247.jpg!s7",
                "http://pic.ecook.cn/web/261227138.jpg!s7",
                "http://pic.ecook.cn/web/261225241.jpg!s7",
                "http://pic.ecook.cn/web/261218803.jpg!s7"};

        List<String> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            list.add(urls[i]);
        }
        return list;
    }
}
