package com.meggnify.model;

import android.content.SharedPreferences;

import com.meggnify.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Luqman Hakim on 2/28/2015.
 */
public class Assignment {
    int id;
    String name;
    String objective;
    String start_date;
    String end_date;
    String price;
    String start_time;
    String end_time;
    String instruction;
    Boolean has_screeners;
    String assignment_type;
    Date date_start;
    Date date_end;
    ArrayList<Question> questions = new ArrayList<Question>();
int current_question = 0;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Boolean getHas_screeners() {
        return has_screeners;
    }

    public void setHas_screeners(Boolean has_screeners) {
        this.has_screeners = has_screeners;
    }

    public String getAssignment_type() {
        return assignment_type;
    }

    public void setAssignment_type(String assignment_type) {
        this.assignment_type = assignment_type;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public int getIcon() {
        if (assignment_type.equals("Mystery Audit"))
            return R.drawable.ic_mystery_audit;
        else
            return R.drawable.ic_general_survey;
    }

    public int getIcon_dark() {
        if (assignment_type.equals("Mystery Audit"))
            return R.drawable.ic_mystery_audit_dark;
        else
            return R.drawable.ic_mystery_audit_dark;

    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public int getCurrent_question() {
        return current_question;
    }

    public void setCurrent_question(int current_question) {
        this.current_question = current_question;
    }
}
