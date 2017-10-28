package com.bruce.simple;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.bruce.touch.Touch3DView;
import com.bruce.touch.shadowLayout.ZDepth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyRecyclerView recyclerView = (MyRecyclerView) findViewById(R.id.recycler_view);
        ItemAdapter itemAdapter = new ItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);
        List<String> stringList = new ArrayList<>();
        stringList.add("ONE");
        stringList.add("TWO");
        stringList.add("THREE");
        stringList.add("FOUR");
        stringList.add("FIVE");
        itemAdapter.setItemList(stringList);
    }
}
