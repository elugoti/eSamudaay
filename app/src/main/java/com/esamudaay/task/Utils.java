package com.esamudaay.task;

import android.net.ParseException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Utils {
    static String format = "dd MM yyyy";
    public static String uTCToLocal(String datesToConvert) {


        String dateToReturn = datesToConvert;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gmt = null;
        SimpleDateFormat sdfOutPutToSend = new SimpleDateFormat(format);
        sdfOutPutToSend.setTimeZone(TimeZone.getDefault());

        try {
            gmt = sdf.parse(datesToConvert);
            dateToReturn = sdfOutPutToSend.format(gmt);
            Log.d("DatesCheckingFromUTC", "info1: " + dateToReturn);

        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
            Log.d("DatesCheckingFromUTC", "info222: " + e.getMessage());
            return datesToConvert;
        }
        return dateToReturn;
    }

    public static long getDaysDiff(String pastDate){
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String today = formatter.format( new Date());
        try {
            Date date1 = myFormat.parse(pastDate);
            Date date2 = myFormat.parse(today);
            long diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
