package com.example.myproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtil {
    public static String getRealPathFromURI(Context paramContext, Uri paramUri) {
        Cursor localCursor = null;
        try {
            String[] arrayOfString = {"_data"};
            localCursor = paramContext.getContentResolver().query(paramUri, arrayOfString, null, null, null);
            int i = 0;
            if (localCursor != null)
                i = localCursor.getColumnIndexOrThrow("_data");
            localCursor.moveToFirst();
            String str = localCursor.getString(i);
            return str;
        } finally {
            if (localCursor != null)
                localCursor.close();
        }
    }

    public static String getRealPathFromURI1(Context paramContext, Uri paramUri) {
        Cursor localCursor = null;
        try {
            Uri localUri = handleImageUri(paramUri);
            String[] arrayOfString = {"_data"};
            localCursor = paramContext.getContentResolver().query(localUri, arrayOfString, null, null, null);
            int i = 0;
            if (localCursor != null)
                i = localCursor.getColumnIndexOrThrow("_data");
            localCursor.moveToFirst();
            String str = localCursor.getString(i);
            return str;
        } catch (Exception localException) {
            return null;
        } finally {
            if (localCursor != null)
                localCursor.close();
        }
    }

    public static Uri handleImageUri(Uri paramUri) {
        Pattern localPattern = Pattern.compile("(content://media/.*\\d)");
        if (paramUri.getPath().contains("content")) {
            Matcher localMatcher = localPattern.matcher(paramUri.getPath());
            if (localMatcher.find())
                paramUri = Uri.parse(localMatcher.group(1));
        } else {
            return paramUri;
        }
        throw new IllegalArgumentException("Cannot handle this URI");
    }
}
