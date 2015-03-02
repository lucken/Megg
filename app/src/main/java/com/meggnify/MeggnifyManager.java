package com.meggnify;

import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.meggnify.helper.Constants;
import com.meggnify.lib.Utils;
import com.meggnify.model.Venue;
import com.meggnify.model.Mission;
import com.meggnify.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MeggnifyManager {

    private static MeggnifyManager instance;

    private MeggnifyAPI meggAPI;

    public JSONObject summary;
    public JSONObject profile;

    public List<Mission> myJobs;
    public List<Mission> newJobs;
    public List<Venue> newJobVenues;
    public List<Venue> venues;

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

        myJobs = new ArrayList<Mission>();
        newJobs = new ArrayList<Mission>();
        newJobVenues = new ArrayList<Venue>();
        venues = new ArrayList<Venue>();

        tasks = new ArrayList<Task>();
    }

    public String login(String parameters)
    {
        String token = null;
        if (parameters != null) {
            InputStream stream = meggAPI.postParams(Constants.BASE_URL + "/sessions", parameters);
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
            InputStream stream = meggAPI.postParams(Constants.BASE_URL + "/registrations", parameters);
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

    public Boolean refreshSummary() {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/summary");
        String response = Utils.readStream(stream);
        Log.i("Manager", "Summary: " + response);
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

    public Boolean refreshProfile() {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/profile");
        String response = Utils.readStream(stream);
        Log.i("Manager", "Profile: " + response);
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

        return false;
    }

    public Boolean refreshMyJob(boolean cache) {
        if (cache) {
            return false;
        } else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/my_jobs");
            String response = Utils.readStream(stream);
            Log.i("Manager", "My Jobs: " + response);
            if (response != null) {
                myJobs.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("assignments");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assignment = data.getJSONObject(i);
                        Mission mission = new Mission(assignment);
                        myJobs.add(mission);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    public Boolean refreshNewJob(boolean cache) {
        if (cache) {
            return false;
        } else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/assignments");
            String response = Utils.readStream(stream);
            Log.i("Manager", "New Jobs: " + response);
            if (response != null) {
                newJobs.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("assignments");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject assignment = data.getJSONObject(i);
                        Mission mission = new Mission(assignment);
                        newJobs.add(mission);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    public Boolean refreshJobVenue(int assignmentId) {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/assignments/" + assignmentId + "/locations");
        String response = Utils.readStream(stream);
        Log.i("Manager", "New Job Locations: " + response);
        if (response != null) {
            newJobVenues.clear();
            try {
                JSONObject result = new JSONObject(response);
                JSONArray data = result.getJSONArray("locations");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject location = data.getJSONObject(i);
                    Venue venue = new Venue(location);
                    newJobVenues.add(venue);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    public Boolean refreshVenue(Boolean cache) {
        if (cache) {
            return false;
        } else {
            InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/assignment_locations");
            String response = Utils.readStream(stream);
            Log.i("Manager", "Venues: " + response);
            if (response != null) {
                venues.clear();
                try {
                    JSONObject result = new JSONObject(response);
                    JSONArray data = result.getJSONArray("locations");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject location = data.getJSONObject(i);
                        Venue venue = new Venue(location);
                        venues.add(venue);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    public Boolean refreshMission(int missionId) {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/assignments/" + missionId + "/questions");
        String response = Utils.readStream(stream);
        Log.i("Manager", "Mission: " + response);
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

    public Boolean refreshTask(int missionId) {
        InputStream stream = meggAPI.getFromUrl(Constants.BASE_URL + "/assignments/" + missionId + "/questions");
        String response = Utils.readStream(stream);
        Log.i("Manager", "Mission: " + response);
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

    public String applyJob(String parameters) {
        return null;
    }

    public String answerTask(String parameters) {
        return null;
    }

    public Boolean refreshSetting() {
        return false;
    }

}
