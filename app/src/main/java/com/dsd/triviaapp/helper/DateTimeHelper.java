package com.dsd.triviaapp.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Get the System Timestamp in seconds
 * @return
 */
public class DateTimeHelper {
    public static Long getCurrentDateTimeInUnixTimeStamp() {
        try {
            return System.currentTimeMillis() / 1000;
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
            return 0L;
        }
    }

    /**
     * Get the System Timestamp in Milli seconds
     * @return
     */
    public static Long getCurrentDateTimeInUnixTimeStampMillis() {
        try {
            return System.currentTimeMillis();
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
            return 0L;
        }
    }

    /**
     * Pass any time stamp in millis to get the desired date format in device timezone
     * @param timeStamp
     * @param dateFormatStr
     * @param timeZone
     * @return
     */
    public static String convertUnixTimeStampIntoDateStr(long timeStamp, String dateFormatStr, TimeZone timeZone) {
        try {
            Date date = new Date(timeStamp);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormatStr, Locale.getDefault());
            dateFormatter.setTimeZone(timeZone);
            return dateFormatter.format(date);
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
            return "";
        }
    }

}
