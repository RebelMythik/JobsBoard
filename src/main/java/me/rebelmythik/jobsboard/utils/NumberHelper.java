package me.rebelmythik.jobsboard.utils;

public class NumberHelper {

    public static boolean isInt(String s) {
        if (s == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        if (s == null) {
            return false;
        }
        try {
            Double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
