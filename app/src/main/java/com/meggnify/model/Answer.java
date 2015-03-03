// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.model;


public class Answer {

    int id;
    String answer;
    Boolean is_other;
    String move_to;
    String type_answer;
    Boolean is_answered;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Answer() {
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getIs_other() {
        return is_other;
    }

    public String getMove_to() {
        return move_to;
    }

    public String getType_answer() {
        return type_answer;
    }

    public void setAnswer(String s) {
        answer = s;
    }

    public void setIs_other(Boolean boolean1) {
        is_other = boolean1;
    }

    public void setMove_to(String s) {
        move_to = s;
    }

    public void setType_answer(String s) {
        type_answer = s;
    }

    public Boolean getIs_answered() {
        return is_answered;
    }

    public void setIs_answered(Boolean is_answered) {
        this.is_answered = is_answered;
    }
}
