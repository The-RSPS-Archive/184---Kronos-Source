package net.runelite.standalone;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomMisc {

    public static int DRAG = 5;
    public static boolean HIDE_TITLES = false;
    public static boolean SHOW_GROUND_ITEM_COLORS = false;
    public static boolean SNOW_ON_GROUND = false;

    public static void handleCustomVarp(int id, int value) {
        id -= 20000;
        if(id == 0) {
            DRAG = value;
            return;
        }

        if(id == 2) {
            boolean hide = value == 1;
            Widget minimise = Canvas.get(90, 6);
            minimise.isHidden = hide;
            WorldMapSectionType.method116(minimise);
            return;
        }

        if(id == 4) {
            CustomWidgetTimer.SHOW_WIDGETS = value == 1;
            return;
        }
        /*if(id == 1) {
            SwitchGrading.required = value;
            return;
        }

        if(id == 2) {
            boolean hide = value == 1;
            for(int i = 0; i < 11; i++) {
                Widget c1 = GameCanvas.get(90, 29 + i);
                c1.isHidden = hide;
                WorldMapSectionType.method116(c1);

                Widget c2 = GameCanvas.get(90, 40 + i);
                c2.isHidden = hide;
                WorldMapSectionType.method116(c2);
            }
            Widget minimise = GameCanvas.get(90, 51);
            minimise.isHidden = hide;
            WorldMapSectionType.method116(minimise);
            return;
        }
        if(id == 3) {
            boolean pvpZone = value == 1;
            if(pvpZone) {
                if((Client.flags & 0x4) != 0)
                    return;
                Client.flags |= 0x4;
            } else {
                if((Client.flags & 0x4) == 0)
                    return;
                Client.flags &= ~0x4;
            }
            return;
        }
        if (id == 5) {
            HIDE_TITLES = value == 1;
            return;
        }
        if(id == 6) {
            SHOW_GROUND_ITEM_COLORS = value == 1;
            return;
        }*/
    }

    public static int handleCustomInstructions(int opcode, Script script, boolean flag) {
        int[] intStack = Interpreter.Interpreter_intStack;
        int isp = Interpreter.Interpreter_intStackSize;
        String[] stringStack = Interpreter.Interpreter_stringStack;
        int ssp = Interpreter.Interpreter_stringStackSize;

        switch (opcode) {
            case 6300: { // date_minutes
                intStack[isp++] = (int) (class33.method680() / 60000L);
                break;
            }

            case 6301: { // date_runeday
                intStack[isp++] = (int) (class33.method680() / 86400000L) - 11745;
                break;
            }

            case 6302: { // date_runeday_fromdate
                isp -= 3;
                int day = intStack[isp];
                int month = intStack[isp + 1];
                int year = intStack[isp + 2];

                Calendar.Calendar_calendar.clear();
                //noinspection MagicConstant
                Calendar.Calendar_calendar.set(year, month, day, 12, 0, 0);
                long timestamp = Calendar.Calendar_calendar.getTime().getTime();

                int runeday = (int) (timestamp / 86400000L) - 11745;
                if (year < 1970) {
                    --runeday;
                }

                intStack[isp++] = runeday;
                break;
            }

            case 6303: { // date_year
                Interpreter.Interpreter_calendar.setTime(new Date(class33.method680()));
                intStack[isp++] = Interpreter.Interpreter_calendar.get(java.util.Calendar.YEAR);
                break;
            }

            case 6304: { // date_isleapyear
                int year = intStack[--isp];

                boolean leap = true;
                if (year < 0) {
                    leap = (year + 1) % 4 == 0;
                } else if (year < 1582) {
                    leap = year % 4 == 0;
                } else if (year % 4 != 0) {
                    leap = false;
                } else if (year % 100 != 0) {
                    leap = true;
                } else if (year % 400 != 0) {
                    leap = false;
                }

                intStack[isp++] = leap ? 1 : 0;
                break;
            }

            case 6305: { // date_runeday_todate
                int runeday = intStack[--isp];

                Interpreter.Interpreter_calendar.setTime(new Date(86400000L * (long) (runeday + 11745)));

                intStack[isp++] = Interpreter.Interpreter_calendar.get(java.util.Calendar.DATE);
                intStack[isp++] = Interpreter.Interpreter_calendar.get(java.util.Calendar.MONTH);
                intStack[isp++] = Interpreter.Interpreter_calendar.get(java.util.Calendar.YEAR);
                break;
            }

            case 6306: { // date_minutes_fromruneday
                int runeday = intStack[--isp];
                intStack[isp++] = (int) ((86400000L * (long) (runeday + 11745)) / 60000L);
                break;
            }

            case 6307: { // format_datetime_from_minutes
                int minutes = intStack[--isp];

                Calendar.Calendar_calendar.setTime(new Date((long) minutes * 60000L));

                int date = Calendar.Calendar_calendar.get(java.util.Calendar.DATE);
                int month = Calendar.Calendar_calendar.get(java.util.Calendar.MONTH);
                int year = Calendar.Calendar_calendar.get(java.util.Calendar.YEAR);
                int hour = Calendar.Calendar_calendar.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = Calendar.Calendar_calendar.get(java.util.Calendar.MINUTE);

                String dateString = Integer.toString(date / 10) + date % 10 + '-' + Calendar.MONTH_NAMES_ENGLISH_GERMAN[0][month] + '-' + year;
                String timeString = Integer.toString(hour / 10) + hour % 10 + ":" + minute / 10 + minute % 10;
                stringStack[ssp++] = dateString + " " + timeString + " UTC";
                break;
            }

            case 6308: { // format_datetime_from_minutes_custom
                int minutes = intStack[--isp];
                SimpleDateFormat format = new SimpleDateFormat(stringStack[--ssp]);

                Calendar.Calendar_calendar.setTime(new Date(minutes * 60000L));

                stringStack[ssp++] = format.format(Calendar.Calendar_calendar.getTime());
                break;
            }

            default:
                return 2;
        }

        Interpreter.Interpreter_intStackSize = isp;
        Interpreter.Interpreter_stringStackSize = ssp;
        return 1;
    }

}