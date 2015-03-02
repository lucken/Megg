package com.meggnify.model;

import java.util.Date;

/**
 * Created by Luqman Hakim on 3/1/2015.
 */
public class User {
    String name;
    String full_name;
    String email;
    String rank_pict;
    String rank_name;
    String date;
    int point;
    int next_rank_point;
    int number_of_pending;
    double harvest_value;
    double consumed_value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRank_pict() {
        return rank_pict;
    }

    public void setRank_pict(String rank_pict) {
        this.rank_pict = rank_pict;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNext_rank_point() {
        return next_rank_point;
    }

    public void setNext_rank_point(int next_rank_point) {
        this.next_rank_point = next_rank_point;
    }

    public double getHarvest_value() {
        return harvest_value;
    }

    public void setHarvest_value(double harvest_value) {
        this.harvest_value = harvest_value;
    }

    public double getConsumed_value() {
        return consumed_value;
    }

    public void setConsumed_value(double consumed_value) {
        this.consumed_value = consumed_value;
    }


    public int getNumber_of_pending() {
        return number_of_pending;
    }

    public void setNumber_of_pending(int number_of_pending) {
        this.number_of_pending = number_of_pending;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
