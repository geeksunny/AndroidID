package com.radicalninja.androidid;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
			LayoutInflater inflater = getLayoutInflater();
			View alertLayout = inflater.inflate(R.layout.popup_about, null);
			final TextView sourceText = (TextView) alertLayout.findViewById(R.id.about_source_string);
			sourceText.setText(Html.fromHtml(getString(R.string.about_source_string_html)));
			sourceText.setMovementMethod(LinkMovementMethod.getInstance());

			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Android ID");
			alertDialog.setView(alertLayout);
			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",  new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			alertDialog.show();
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
