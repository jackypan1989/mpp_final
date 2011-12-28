package lab.mpp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.LocalData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityPage extends Activity {
	private String[] friendNameList = { "jacky", "wang" };
	private long[] selectId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = LayoutInflater.from(this.getParent()).inflate(
				R.layout.act, null);
		// setContentView(R.layout.search_activity);
		setContentView(contentView);

		ArrayList<String> nameArrayList = LocalData.getFbFriendNameList();
		final String[] nameList = (String[]) nameArrayList
				.toArray(new String[nameArrayList.size()]);

		final Activity mppFinal = this.getParent().getParent();

		// set button
		Button button = (Button) findViewById(R.id.recommendButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new Dialog(mppFinal);
				AlertDialog.Builder builder = new AlertDialog.Builder(mppFinal);
				builder.setTitle("加入一個朋友");

				final ListView modeList = new ListView(mppFinal);

				ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
						mppFinal,
						android.R.layout.simple_list_item_multiple_choice,
						android.R.id.text1, nameList);
				modeList.setAdapter(modeAdapter);
				modeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

				builder.setView(modeList);
				builder.setPositiveButton("確定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d, int i) {
								selectId = modeList.getCheckItemIds();
								// if(selectId.is)
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d, int i) {

							}
						});

				dialog = builder.create();
				dialog.show();
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MPPFinalActivity.goTo(2);
			}
		});

		String placeName[] = null;
		try {
			Bundle params = new Bundle();
			params.putString("type", "place");

			params.putString("center", LocalData.latitude + ","
					+ LocalData.longitude);

			params.putString("distance", "1000");

			JSONObject o = new JSONObject(LoginActivity.mFacebook.request(
					"search", params));
			JSONArray j = o.getJSONArray("data");
			placeName = new String[j.length()];
			// Log.e("gps",j+"");
			for (int i = 0; i < j.length(); i++) {
				placeName[i] = j.getJSONObject(i).getString("name");
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 地標的spinner
		ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(
				getParent().getParent(), android.R.layout.simple_spinner_item,
				placeName);
		placeAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinnerPlcae = (Spinner) findViewById(R.id.spinner3);
		spinnerPlcae.setAdapter(placeAdapter);
		spinnerPlcae.setPrompt("請選擇地點");

		// set spinner for tag
		Spinner spinnerActivityTag = (Spinner) findViewById(R.id.spinnerActivityTag);
		// create array to set adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.tagList);
		// set dropdown view
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerActivityTag.setAdapter(adapter);
		spinnerActivityTag.setPrompt("請選擇一個標籤");
		// set onclick
		spinnerActivityTag
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

					public void onNothingSelected(AdapterView arg0) {

					}
				});

		// set spinner for tag
		Spinner spinnerActivityStatus = (Spinner) findViewById(R.id.spinnerActivityStatus);
		// create array to set adapter
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.statusList);
		// set dropdown view
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerActivityStatus.setAdapter(adapter2);
		spinnerActivityStatus.setPrompt("請選擇目前狀態");
		// set onclick
		spinnerActivityStatus
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

					public void onNothingSelected(AdapterView arg0) {

					}
				});

	}

	@Override
	public void onResume() {
		super.onResume();
		this.getParent().getParent().setTitle("ActivityPage");
	}

}
