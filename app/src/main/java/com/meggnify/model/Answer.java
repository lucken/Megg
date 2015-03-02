// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.model;


public class Answer
{

    String answer;
    Boolean is_other;
    String move_to;
    String type_answer;

    public Answer()
    {
    }

    public String getAnswer()
    {
        return answer;
    }

    public Boolean getIs_other()
    {
        return is_other;
    }

    public String getMove_to()
    {
        return move_to;
    }

    public String getType_answer()
    {
        return type_answer;
    }

    public void setAnswer(String s)
    {
        answer = s;
    }

    public void setIs_other(Boolean boolean1)
    {
        is_other = boolean1;
    }

    public void setMove_to(String s)
    {
        move_to = s;
    }

    public void setType_answer(String s)
    {
        type_answer = s;
    }
}
