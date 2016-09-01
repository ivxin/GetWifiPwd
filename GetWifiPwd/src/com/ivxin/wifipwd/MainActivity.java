package com.ivxin.wifipwd;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lv_wifi;
	private WifiManage wifiManage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() throws Exception {
		final TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText("本机保存的wifi信息(需要root权限)");
		lv_wifi = (ListView) findViewById(R.id.lv_wifi);
		wifiManage = new WifiManage();
		final List<WifiInfo> wifiInfos = wifiManage.read();
		List<String> list = new ArrayList<>();
		for (WifiInfo wifiInfo : wifiInfos) {
			list.add("Wifi:" + wifiInfo.ssid + "\n密码:" + wifiInfo.password);
		}
		lv_wifi.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		lv_wifi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WifiInfo item = wifiInfos.get(position);
				Clipboard.copy(item.password, MainActivity.this);
				Toast.makeText(MainActivity.this, "已复制密码\n" + item.password, Toast.LENGTH_SHORT).show();
			}
		});
		tv_title.setText("本机保存的wifi信息有" + list.size() + "条");
	}

}
