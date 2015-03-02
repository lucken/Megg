// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.helper;


public class Constants
{

    public static final String BASE_URL = "http://beta.meggnify.com/api/v1";
    public static final String PREF_SETTING_POPUP = "setting_popup";
    public static final String PREF_SETTING_RINGER = "setting_ringer";
    public static final String PREF_USER_TOKEN = "user_token";
    public static int jobs_count = 0;
    public static final int modul_session=0;
    public static final int modul_assignment = 1;
    public static final int modul_meggnets = 2;
    public static final int modul_rank = 3;
    public static final int modul_registration = 4;


    public Constants()
    {
    }

    static 
    {
        jobs_count = 0;
    }
}
