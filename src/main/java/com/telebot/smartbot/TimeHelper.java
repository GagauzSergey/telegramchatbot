package com.telebot.smartbot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Gagauz Sergey
 * Created by user on 07.08.2017.
 */
public class TimeHelper {
    public String getTimeDate (){
        Calendar calendar = new GregorianCalendar();
        int hourHelp = calendar.get(Calendar.HOUR);
        int minuteHelp = calendar.get(Calendar.MINUTE);
        int secondHelp = calendar.get(Calendar.SECOND);
        return String.valueOf(hourHelp+":"+minuteHelp+":"+secondHelp);
    }
}
