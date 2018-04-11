package com.achartenginedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.achartenginedemo.Base.BaseActivity;
import com.achartenginedemo.View.DiagramActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    private Context context = MainActivity.this;
    private ListView listView;
    private String[] titles;

    private List<String> title = new ArrayList<>();//名称
    private List<double[]> xValue = new ArrayList<>();//x轴值
    private List<double[]> yValue = new ArrayList<>();//y轴值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.title);

        //负责标题,根据标题数量形成曲线数量
        title = Arrays.asList("1", "2");

        //X轴负责线段长度
        for (int i = 0; i < title.size(); i++) {
            xValue.add(new double[]{1, 2, 3, 4, 5});
        }
        //Y轴负责每条线段的数据点
        yValue.add(new double[]{1, 2, 3, 4, 5});
        yValue.add(new double[]{5, 2, 3, 1, 4});

        initView();
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, titles));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IntentActivity(DiagramActivity.class, i);
            }
        });
    }

    /**
     * 通用跳转
     *
     * @param activity
     */
    private void IntentActivity(Class<?> activity, int i) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("i", i);
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", (Serializable) title);
        bundle.putSerializable("xValue", (Serializable) xValue);
        bundle.putSerializable("yValue", (Serializable) yValue);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
