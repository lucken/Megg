package com.meggnify.helper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.meggnify.model.Assignment;
import com.meggnify.model.Result;
import com.raaf.http.rLoader;
import com.raaf.rDate;

import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

    static List<NameValuePair> params;

    public API() {
    }

    public static void ApiParser(Message message) {
        String s = message.getData().getString("message");
        if (s.contains("\"status\"")) {
            getResult(s);
            int modul = message.getData().getInt("modul");
            int mode = message.getData().getInt("mode");
            so.result.setMode(mode);
            so.result.setModul(modul);
            if (so.result.getStatus() != 200) {
                return;
            }
            try {
                JSONObject jo = new JSONObject(s);
                switch (modul) {
                    case Constants.modul_assignment:
                        switch (mode) {
                            case 1: //new job
                                JSONArray jar = jo.getJSONArray("assignments");
                                if (jar.length() > 0) {
                                    so.getAssignmentsNew().clear();
                                    for (int i = 0; i < jar.length(); i++) {
                                        jo = jar.getJSONObject(i);
                                        if (!jo.isNull("start_date") && !jo.isNull("end_date")) {
                                            //cek my job
                                            int id = jo.getInt("id");
                                            Boolean found = false;
                                            for (int j = 0; j < so.getAssignments().size(); j++) {
                                                if (so.getAssignments().get(j).getId() == id)
                                                    found = true;
                                            }
                                            if (!found) {
                                                Assignment as = new Assignment();
                                                as.setId(id);
                                                as.setName(jo.getString("name"));
                                                as.setObjective(jo.getString("objective"));
                                                as.setStart_date(jo.getString("start_date"));
                                                as.setEnd_date(jo.getString("end_date"));
                                                as.setPrice(jo.getString("price"));
                                                as.setStart_time(jo.getString("start_time"));
                                                as.setEnd_time(jo.getString("end_time"));
                                                as.setInstruction(jo.getString("instruction"));
                                                as.setHas_screeners(jo.getBoolean("has_screeners"));
                                                as.setAssignment_type(jo.getString("assignment_type"));
                                                as.setDate_start(rDate.StringToDate(jo.getString("start_date"), "yyyy-MM-dd"));
                                                as.setDate_end(rDate.StringToDate(jo.getString("end_date"), "yyyy-MM-dd"));
                                                so.getAssignmentsNew().add(as);
                                            }
                                        }
                                    }
                                }
                                break;
                            case 2:
                                break;
                        }
                        break;

                    case Constants.modul_meggnets:
                        switch (mode) {
                            case 1: // summary
                                jo = jo.getJSONObject("summary");
                                so.getUser().setName(jo.getString("name"));
                                so.getUser().setNumber_of_pending(jo.getInt("number_of_pending"));
                                so.getUser().setDate(jo.getString("date"));
                                break;
                            case 2:// my jobs
                                JSONArray jar = jo.getJSONArray("assignments");
                                if (jar.length() > 0) {
                                    so.getAssignments().clear();
                                    for (int i = 0; i < jar.length(); i++) {
                                        jo = jar.getJSONObject(i);
                                        Assignment as = new Assignment();
                                        as.setId(jo.getInt("id"));
                                        as.setName(jo.getString("name"));
                                        as.setObjective(jo.getString("objective"));
                                        as.setStart_date(jo.getString("start_date"));
                                        as.setEnd_date(jo.getString("end_date"));
                                        as.setPrice(jo.getString("price"));
                                        as.setStart_time(jo.getString("start_time"));
                                        as.setEnd_time(jo.getString("end_time"));
                                        as.setInstruction(jo.getString("instruction"));
                                        as.setHas_screeners(jo.getBoolean("has_screeners"));
                                        as.setAssignment_type(jo.getString("assignment_type"));
                                        as.setDate_start(rDate.StringToDate(jo.getString("start_date"), "MMMM d, yyyy"));
                                        as.setDate_end(rDate.StringToDate(jo.getString("end_date"), "MMMM d, yyyy"));
                                        so.getAssignments().add(as);
                                    }
                                }
                                break;
                            case 3: // profile
                                jo = jo.getJSONObject("profile");
                                so.getUser().setRank_pict(jo.getString("rank_pict"));
                                so.getUser().setRank_name(jo.getString("rank_name"));
                                so.getUser().setPoint(jo.getInt("point"));
                                so.getUser().setFull_name(jo.getString("full_name"));
                                so.getUser().setEmail(jo.getString("email"));
                                so.getUser().setNext_rank_point(jo.getInt("next_rank_point"));
                                so.getUser().setHarvest_value(jo.getDouble("harvest_value"));
                                so.getUser().setConsumed_value(jo.getDouble("consumed_value"));
                                break;
                        }
                        break;

                    case Constants.modul_rank: // '\003'

                        break;
                    case Constants.modul_registration: // '\004'

                        break;
                    case Constants.modul_session: // '\004'

                        break;
                }
            } catch (JSONException e) {
                so.result.setStatus(-1);
                so.result.setSuccess(Boolean.valueOf(false));
                so.result.setInfo(e.getMessage());
            }

        } else {
            so.result = new Result();
            so.result.setStatus(-2);
            so.result.setSuccess(false);
            so.result.setInfo("http : " + s);
        }
    }


    public static void AssNewJob(String token, Handler handler) {
        String url = Constants.BASE_URL + "/assignments";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 1);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssLocation(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/assignments/" + i + "/locations";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 2);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssStartMission(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/assignments/" + i + "/start_mission/";
        String param = "{\"auth_token\": \"" + token + "\"}";
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 3);
        rLoader rloader = new rLoader(url, handler, bundle, rLoader.Http_type.put, null, param);
        rloader.start();
    }

    public static void AssQuest(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/assignments/" + i + "/questions";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 4);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssScreenQuest(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/screening_questions?auth_token=" + token;
        url += "?id=" + i;

        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 5);
        bundle.putInt("mission_id", i);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssLocation(String token, Handler handler) {
        String url = Constants.BASE_URL + "/assignment_locations";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 6);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssMap(String token, Handler handler) {
        String url = Constants.BASE_URL + "/maps";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 7);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void AssInfo(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/assignments/" + i + "/assignment_info";
        url += "?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_assignment);
        bundle.putInt("mode", 8);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void MegSummary(String token, Handler handler) {
        String url = Constants.BASE_URL + "/summary?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_meggnets);
        bundle.putInt("mode", 1);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void MegMyJob(String token, Handler handler) {
        String url = Constants.BASE_URL + "/my_jobs?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_meggnets);
        bundle.putInt("mode", 2);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void MegProfile(String token, Handler handler) {
        String url = Constants.BASE_URL + "/profile?auth_token=" + token;
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_meggnets);
        bundle.putInt("mode", 3);
        rLoader rloader = new rLoader(url, handler, bundle);
        rloader.start();
    }

    public static void MegApplyJob(String token, int i, Handler handler) {
        String url = Constants.BASE_URL + "/apply_job";
        String param = String.format("{\"auth_token\": \"%s\",\"meggnet_assignment\": {\"assignment_id\": \"%s\"}}", token, i);
        Bundle bundle = new Bundle();
        bundle.putInt("modul", Constants.modul_meggnets);
        bundle.putInt("mode", 5);
        rLoader rloader = new rLoader(url, handler, bundle, rLoader.Http_type.put, null, param);
        rloader.start();
    }

    public static void getResult(String s) {
        so.result = new Result();
        so.result.setjSon(s);
        try {
            JSONObject jo = new JSONObject(s);
            so.result.setStatus(jo.getInt("status"));
            so.result.setSuccess(jo.getBoolean("success"));
            so.result.setInfo(jo.getString("info"));
        } catch (JSONException e) {
            so.result.setStatus(-1);
            so.result.setSuccess(Boolean.valueOf(false));
            so.result.setInfo(e.getMessage());
        }
    }
}
