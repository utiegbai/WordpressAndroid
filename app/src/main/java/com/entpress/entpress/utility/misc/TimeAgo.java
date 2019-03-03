package com.entpress.entpress.utility.misc;

/**
 * Created by ray on 6/1/2016.
 */

public class TimeAgo {

    public static String get(long timeStamp) {
            long timeDiffernce;
            String perd = "";
            long unixTime = System.currentTimeMillis() / 1000L;  //get_theme current time in seconds.
            int j;
            String[] periods = {"sec", "min", "hr", "day", "week", "month", "year", "decade"};
            // you may choose to write full time intervals like seconds, minutes, days and so on
            double[] lengths = {60, 60, 24, 7, 4.35, 12, 10};
            timeDiffernce = unixTime - timeStamp;
            String tense = "Ago";
            for (j = 0; timeDiffernce >= lengths[j] && j < lengths.length - 1; j++) {
                timeDiffernce /= lengths[j];
            }
            if (timeDiffernce > 1) {
                perd = periods[j] + 's';
            } else {
                perd = periods[j];
            }
            if(timeDiffernce < 1){
                return "just now";
            }
            return timeDiffernce + perd + " ";
    }
}