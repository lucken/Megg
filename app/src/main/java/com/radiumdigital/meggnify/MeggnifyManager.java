package com.radiumdigital.meggnify;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.radiumdigital.meggnify.lib.Utils;
import com.radiumdigital.meggnify.model.Location;
import com.radiumdigital.meggnify.model.Mission;
import com.radiumdigital.meggnify.model.Profile;
import com.radiumdigital.meggnify.model.Task;

import android.util.Log;
import android.util.SparseArray;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MeggnifyManager {

	private static MeggnifyManager instance;
	
	private MeggnifyAPI meggAPI;

    public JSONObject summary;
    public JSONObject profile;
    public JSONObject cash;

	public List<Mission> jobs;
	public List<Mission> missions;
    public List<Location> locations;

	public List<Task> tasks;

	public static MeggnifyManager sharedInstance() {
		if (instance == null) {
			synchronized(MeggnifyManager.class) {
				if (instance == null) {
					instance = new MeggnifyManager();
				}
			}
		}
		return instance;
	}
	
	protected MeggnifyManager() {
		meggAPI = MeggnifyAPI.sharedInstace();

        summary = null;
        profile = null;
        cash = null;

        jobs = new ArrayList<Mission>();
		missions = new ArrayList<Mission>();
        locations = new ArrayList<Location>();

		tasks = new ArrayList<Task>();
	}

    public String login(String parameters)
    {
        String token = null;
        if (parameters != null) {
            InputStream stream = meggAPI.postParams(Constants.BASE_URL + "/api/v1/sessions", parameters);
            String response = Utils.readStream(stream);
            if (response!= null) {
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject data = result.getJSONObject("data");
                    if (data != null) {
                        token = data.getString("auth_token");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return token;
    }

    public String register(String parameters)
    {
        String token = null;
        if (parameters != null) {
            InputStream stream = meggAPI.postParams(Constants.BASE_URL + "/api/v1/registrations", parameters);
            String response = Utils.readStream(stream);
            if (response!= null) {
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject data = result.getJSONObject("data");
                    if (data != null) {
                        token = data.getString("auth_token");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return token;
    }

	public Boolean refreshSummary(String token) {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/summary");
        String response = Utils.readStream(stream);
        if (response != null) {
            try {
                JSONObject result = new JSONObject(response);
                JSONObject data = result.getJSONObject("summary");
                summary = data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
	}
	
	public Boolean refreshJob(boolean cache, String token) {
		if (cache) {
			return false;
		} else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/my_jobs");
            String response = Utils.readStream(stream);
            if (response != null) {
                jobs.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("assignments");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assignment = data.getJSONObject(i);
                        Mission mission = new Mission(assignment);
                        jobs.add(mission);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
		}
		
		return false;
	}
	
	public Boolean refreshMission(boolean cache, String token) {
		if (cache) {
			return false;
		} else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/assignments");
            String response = Utils.readStream(stream);
            if (response != null) {
                missions.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("assignments");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assignment = data.getJSONObject(i);
                        Mission mission = new Mission(assignment);
                        missions.add(mission);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
		}
		
		return false;
	}

    public Boolean refreshLocation(Boolean cache, String token) {
        if (cache) {
            return false;
        } else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/assignment_locations");
            String response = Utils.readStream(stream);
            if (response != null) {
                locations.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("locations");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject loc = data.getJSONObject(i);
                        Location location = new Location(loc);
                        locations.add(location);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }
	
	public Boolean refreshTask(int missionId, String token) {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/assignments/" + missionId + "/questions");
        String response = Utils.readStream(stream);
        if (response != null) {
            tasks.clear();
            try {
                JSONObject result = new JSONObject(response);
                JSONObject data = result.getJSONObject("assignment");
                JSONArray questions = data.getJSONArray("questions");
                for (int i = 0; i < questions.length(); i++) {
                    JSONObject question = questions.getJSONObject(i);
                    Task task = new Task(question);
                    tasks.add(task);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

		return false;
	}
	
	public Boolean refreshCash(boolean cache, String token) {
		return false;
	}
	
	public Boolean refreshProfile(boolean cache, String token) {
		if (cache) {
			return false;
		} else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/api/v1/profile");
            String response = Utils.readStream(stream);
            if (response != null) {
                try {
                    JSONObject result = new JSONObject(response);
                    JSONObject data = result.getJSONObject("profile");
                    profile = data;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            }
		}
		
		return false;
	}
	
	public Boolean refreshSetting() {
		return false;
	}

}
