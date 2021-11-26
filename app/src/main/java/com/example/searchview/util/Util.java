package com.example.searchview.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

public class Util {
    /**
     * Force hide keyboard if open
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static List<Follower> searchFollowersFilter(List<Follower> list, String charString) {
        List<Follower> filteredTempList = new ArrayList<>();
        for (Follower follower : list) {
            if (follower != null) {
                // Filter by user name and user id
                if (containsIgnoreCase(follower.getLogin(), charString)
                        || containsIgnoreCase(String.valueOf(follower.getId()), charString)) {
                    filteredTempList.add(follower);
                }
            }
        }
        return filteredTempList;
    }
    public static boolean containsIgnoreCase(String src, String charString) {
        final int length = charString.length();
        if (length == 0) {
            return true;
        }
        for (int i = src.length() - length; i >= 0; i--) {
            if (src.regionMatches(true, i, charString, 0, length)) {
                return true;
            }
        }
        return false;
    }
}
