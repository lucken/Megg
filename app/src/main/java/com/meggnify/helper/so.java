package com.meggnify.helper;

import com.meggnify.model.Assignment;
import com.meggnify.model.Result;
import com.meggnify.model.User;

import java.util.ArrayList;

public class so {

    static ArrayList<Assignment> myAssignments;
    static ArrayList<Assignment> newAssignments;
    public static Assignment currentAssignment;
    public static ArrayList myRunMission;
    public static Result result;
    public static Boolean onMission = false;
    static User user;


    public static ArrayList<Assignment> getAssignments() {
        if (myAssignments == null)
            myAssignments = new ArrayList<Assignment>();
        return myAssignments;
    }

    public static ArrayList<Assignment> getAssignmentsNew() {
        if (newAssignments == null)
            newAssignments = new ArrayList<Assignment>();
        return newAssignments;
    }

    public static User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

}

