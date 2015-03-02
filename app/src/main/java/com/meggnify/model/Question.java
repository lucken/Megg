// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.model;

import java.util.ArrayList;

public class Question {

    String answer_type;
    ArrayList<Answer> answers = new ArrayList<Answer>();
    int id;
    String question;
    String question_type;

    public String getAnswer_type() {
        return answer_type;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setAnswer_type(String s) {
        answer_type = s;
    }


    public void setId(int i) {
        id = i;
    }

    public void setQuestion(String s) {
        question = s;
    }

    public void setQuestion_type(String s) {
        question_type = s;
    }


    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
