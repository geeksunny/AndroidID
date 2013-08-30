package com.radicalninja.androidid;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String androidID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView id_box = (TextView) findViewById(R.id.android_id);
		androidID = getAndroidID();
		id_box.setText(androidID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle menu item selection
		switch (item.getItemId()) {
		case R.id.copy_clipboard:
			copyToClipboard();
			return true;
		case R.id.action_app_info:
			// Launch APP INFO activity?
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private String getAndroidID() {
		return  Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}

	@SuppressLint("NewApi") private void copyToClipboard() {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(androidID);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("Android ID", androidID);
			clipboard.setPrimaryClip(clip);
		}
		Toast.makeText(getApplicationContext(), "Android ID copied to clipboard!", Toast.LENGTH_SHORT).show();
	}
}
