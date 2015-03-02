// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.meggnify.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.raaf.rDialog;
import com.raaf.rNet;
import java.util.ArrayList;

public class Util
{

    public Util()
    {
    }

    public static Boolean addMyRunning(Context context, Integer integer)
    {
        String s = PreferenceManager.getDefaultSharedPreferences(context).getString("my_running", "");
        String s1;
        SharedPreferences.Editor editor;
        if (s.length() == 0)
        {
            StringBuilder stringbuilder = new StringBuilder();
            s1 = stringbuilder.append(s).append(integer).toString();
        } else
        {
            StringBuilder stringbuilder1 = new StringBuilder();
            s1 = stringbuilder1.append(s).append(";").append(integer).toString();
        }
        editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("my_running", s1);
        return Boolean.valueOf(editor.commit());
    }

    public static Boolean cekInet(Context context)
    {
        if (!rNet.isNetworkConnected(context))
        {
            rDialog.SetToast(context, "Cannot connect Internet");
            return Boolean.valueOf(false);
        } else
        {
            return Boolean.valueOf(true);
        }
    }

    public static ArrayList getMyRunning(Context context)
    {
        String as[] = PreferenceManager.getDefaultSharedPreferences(context).getString("my_running", "").split(";");
        ArrayList arraylist = new ArrayList(as.length);
        for (int i = 0; i < as.length; i++)
        {
            arraylist.add(Integer.valueOf(Integer.parseInt(as[i])));
        }

        return arraylist;
    }

    public static String getToken(Context context)
    {
        String s = PreferenceManager.getDefaultSharedPreferences(context).getString("user_token", "");
        if (s.isEmpty())
        {
            return null;
        } else
        {
            return s;
        }
    }

    public static Boolean setMyRunning(Context context, ArrayList arraylist)
    {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        String s = "";
        for (int i = 0; i < arraylist.size(); i++)
        {
            StringBuilder stringbuilder = new StringBuilder();
            s = stringbuilder.append(s).append(((Integer)arraylist.get(i)).toString()).append(";").toString();
        }

        if (s.length() > 1)
        {
            s = s.substring(0, -1 + s.length());
        }
        editor.putString("my_running", s);
        return Boolean.valueOf(editor.commit());
    }
}
