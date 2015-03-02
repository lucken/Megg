// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.model;


public class Result
{

    String info;
    String jSon;
    int mode;
    int modul;
    int status;
    Boolean success;

    public Result()
    {
        status = 0;
        success = Boolean.valueOf(false);
        info = "";
        jSon = "";
    }

    public String getInfo()
    {
        return info;
    }

    public int getMode()
    {
        return mode;
    }

    public int getModul()
    {
        return modul;
    }

    public int getStatus()
    {
        return status;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public String getjSon()
    {
        return jSon;
    }

    public void setInfo(String s)
    {
        info = s;
    }

    public void setMode(int i)
    {
        mode = i;
    }

    public void setModul(int i)
    {
        modul = i;
    }

    public void setStatus(int i)
    {
        status = i;
    }

    public void setSuccess(Boolean boolean1)
    {
        success = boolean1;
    }

    public void setjSon(String s)
    {
        jSon = s;
    }
}
