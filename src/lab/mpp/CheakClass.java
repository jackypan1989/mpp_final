package lab.mpp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CheakClass {
	int checkin_id;

	String location_name;
	double latitude;
	double longitude;
	String status;
	String tag;
	String description;
	With[] with_friends;
	String create_time;
	String update_time;
	String name;

	CheakClass(JSONObject j) {
		try {
			checkin_id = j.getInt("checkin_id");
			name = j.getString("name");
			location_name = j.getString("location_name");
			latitude = j.getDouble("latitude");
			longitude = j.getDouble("longitude");
			status = j.getString("status");
			tag = j.getString("tag");
			description = j.getString("description");

			create_time = j.getString("create_time");
			update_time = j.getString("update_time");

			JSONArray a = j.getJSONArray("with_friends");
			with_friends = new With[a.length()];
			for (int i = 0; i < with_friends.length; i++) {
				if (a.getJSONObject(i) != null) {
					with_friends[i] = new With(a.getJSONObject(i));
				}
			}
		} catch (JSONException e) {
			Log.e("error","null");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class With {
	int id;
	String name;

	With(JSONObject j) throws JSONException {
		id = j.getInt("id");
		name = j.getString("name");
	}
}