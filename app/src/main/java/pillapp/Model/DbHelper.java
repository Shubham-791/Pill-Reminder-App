package pillapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;



public class DbHelper extends SQLiteOpenHelper {

    /** Database name */
    private static final String DATABASE_NAME = "pill_model_database";

    /** Database version */
    private static final int DATABASE_VERSION = 3;

    /** Table names */
    private static final String PILL_TABLE          = "pills";
    private static final String ALARM_TABLE         = "alarms";
    private static final String PILL_ALARM_LINKS    = "pill_alarm";
    private static final String HISTORIES_TABLE     = "histories";
    private static final String PANIC_LIST     = "panic";

    /** Common column name and location */
    public static final String KEY_ROWID            = "id";

    /** Pill table columns, used by History Table */
    private static final String KEY_PILLNAME        = "pillName";

    /** Alarm table columns, Hour & Minute used by History Table */
    private static final String KEY_INTENT           = "intent";
    private static final String KEY_HOUR             = "hour";
    private static final String KEY_MINUTE           = "minute";
    private static final String KEY_DAY_WEEK         = "day_of_week";
    private static final String KEY_ALARMS_PILL_NAME = "pillName";
    private static final String KEY_MOBILE1 = "mobile1";
    private static final String KEY_MOBILE2 = "mobile2";
    private static final String KEY_MOBILE3 = "mobile3";
    private static final String KEY_MOBILE4 = "mobile4";
    private static final String KEY_MOBILE5 = "mobile5";

    /** Pill-Alarm link table columns */
    private static final String KEY_PILLTABLE_ID    = "pill_id";
    private static final String KEY_ALARMTABLE_ID   = "alarm_id";

    /** History Table columns, some used above */
    private static final String KEY_DATE_STRING     = "date";

    /** Pill Table: create statement */
    private static final String CREATE_PILL_TABLE =
            "create table " + PILL_TABLE + "("
                    + KEY_ROWID + " integer primary key not null,"
                    + KEY_PILLNAME + " text not null" + ")";

    /** Alarm Table: create statement */
    private static final String CREATE_ALARM_TABLE =
            "create table "         + ALARM_TABLE + "("
                    + KEY_ROWID     + " integer primary key,"
                    + KEY_INTENT    + " text,"
                    + KEY_HOUR      + " integer,"
                    + KEY_MINUTE    + " integer,"
                    + KEY_ALARMS_PILL_NAME  + " text not null,"
                    + KEY_DAY_WEEK  + " integer" + ")";

    /** Pill-Alarm link table: create statement */
    private static final String CREATE_PILL_ALARM_LINKS_TABLE =
            "create table "             + PILL_ALARM_LINKS + "("
                    + KEY_ROWID         + " integer primary key not null,"
                    + KEY_PILLTABLE_ID  + " integer not null,"
                    + KEY_ALARMTABLE_ID + " integer not null" + ")";

    /** Histories Table: create statement */
    private static final String CREATE_HISTORIES_TABLE =
            "CREATE TABLE "             + HISTORIES_TABLE + "("
                    + KEY_ROWID         + " integer primary key, "
                    + KEY_PILLNAME      + " text not null, "
                    + KEY_DATE_STRING   + " text, "
                    + KEY_HOUR          + " integer, "
                    + KEY_MINUTE        + " integer " + ")";
    private static final String CREATE_PANIC_LIST =
            "CREATE TABLE " +PANIC_LIST + "("
                    + KEY_ROWID         + " integer primary key, "
                    + KEY_MOBILE1     + " text,"
                    + KEY_MOBILE2  + " text, "
                    + KEY_MOBILE3  + " text, "
                    + KEY_MOBILE4  + " text, "
                    + KEY_MOBILE5  + " text )";

    /** Constructor */
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /** Creating tables */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PILL_TABLE);
        db.execSQL(CREATE_ALARM_TABLE);
        db.execSQL(CREATE_PILL_ALARM_LINKS_TABLE);
        db.execSQL(CREATE_HISTORIES_TABLE);
        db.execSQL(CREATE_PANIC_LIST);
    }

    @Override
    // TODO: change this so that updating doesn't delete old data
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PILL_ALARM_LINKS);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PANIC_LIST);
        onCreate(db);
    }

// ############################## create methods ###################################### //




    public long panic_add(String mobile1,String mobile2,String mobile3,String mobile4,String mobile5) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Panic pan =new Panic(mobile1,mobile2,mobile3,mobile4,mobile5);

        values.put(KEY_MOBILE1,pan.getMobile1());
        values.put(KEY_MOBILE2,pan.getMobile2());
        values.put(KEY_MOBILE3,pan.getMobile3());
        values.put(KEY_MOBILE4,pan.getMobile4());
        values.put(KEY_MOBILE5,pan.getMobile5());

        long panic = db.insert(PANIC_LIST, null, values);

        return panic;
    }

    public List<Panic> getPanicList() {
        List<Panic> panlist = new ArrayList<>();
        String dbList = "SELECT * FROM " + PANIC_LIST;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(dbList, null);

        /** Loops through all rows, adds to list */
        if (c.moveToFirst()) {
            do {
               Panic pan =new Panic();
                pan.setMobile1(c.getString(c.getColumnIndex(KEY_MOBILE1)));
                pan.setMobile2(c.getString(c.getColumnIndex(KEY_MOBILE2)));
                pan.setMobile3(c.getString(c.getColumnIndex(KEY_MOBILE3)));
                pan.setMobile4(c.getString(c.getColumnIndex(KEY_MOBILE4)));
                pan.setMobile5(c.getString(c.getColumnIndex(KEY_MOBILE5)));

                panlist.add(pan);
            } while (c.moveToNext());
        }
        c.close();
        return panlist;
    }
    public void updatepanic(Panic pan){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // Panic pan =new Panic(mobile1,mobile2,mobile3,mobile4,mobile5);
        String id=""+1;

        pan.setId(id);
        if(pan.getMobile1().equals(""))
            pan.setMobile1("0");
        if(pan.getMobile2().equals(""))
            pan.setMobile2("0");
         if(pan.getMobile3().equals(""))
            pan.setMobile3("0");
         if(pan.getMobile4().equals(""))
            pan.setMobile4("0");
         if(pan.getMobile5().equals(""))
            pan.setMobile5("0");


        values.put(KEY_ROWID,pan.getId());

        values.put(KEY_MOBILE1,pan.getMobile1());


        values.put(KEY_MOBILE2,pan.getMobile2());
        values.put(KEY_MOBILE3,pan.getMobile3());
        values.put(KEY_MOBILE4,pan.getMobile4());
        values.put(KEY_MOBILE5,pan.getMobile5());

       // new String[] { String.valueOf(pan.getId()) }

        String query="UPDATE panic SET "+KEY_MOBILE1+"="+values.getAsString(KEY_MOBILE1) +","+KEY_MOBILE2+"="+values.getAsString(KEY_MOBILE2) +","+KEY_MOBILE3+"="+values.getAsString(KEY_MOBILE3) +","+KEY_MOBILE4+"="+values.getAsString(KEY_MOBILE4) +","+KEY_MOBILE5+"="+values.getAsString(KEY_MOBILE5)+" WHERE "+KEY_ROWID+"= 1";
                db.execSQL(query);
       if(pan.getMobile1().equals("0"))
            pan.setMobile1("");
        if(pan.getMobile2().equals("0"))
            pan.setMobile2("");
        else if(pan.getMobile3().equals("0"))
            pan.setMobile3("");
        else if(pan.getMobile4().equals("0"))
            pan.setMobile4("");
        else if(pan.getMobile5().equals("0"))
            pan.setMobile5("");

       //return db.update(PANIC_LIST, values,KEY_ROWID + "=1", null);



    }



    public long createPill(Pill pill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLNAME, pill.getPillName());

        long pill_id = db.insert(PILL_TABLE, null, values);

        return pill_id;
    }

    /**
     * takes in a model alarm object and inserts a row into the database
     *      for each day of the week the alarm is meant to go off.
     * @param alarm a model alarm object
     * @param pill_id the id associated with the pill the alarm is for
     * @return a array of longs that are the row_ids generated by the database when the rows are inserted
     */
    public long[] createAlarm(Alarm alarm, long pill_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long[] alarm_ids = new long[7];

        /** Create a separate row in the table for every day of the week for this alarm */
        int arrayPos = 0;
        for (boolean day : alarm.getDayOfWeek()) {
            if (day) {
                ContentValues values = new ContentValues();
                values.put(KEY_HOUR, alarm.getHour());
                values.put(KEY_MINUTE, alarm.getMinute());
                values.put(KEY_DAY_WEEK, arrayPos + 1);
                values.put(KEY_ALARMS_PILL_NAME, alarm.getPillName());

                /** Insert row */
                long alarm_id = db.insert(ALARM_TABLE, null, values);
                alarm_ids[arrayPos] = alarm_id;

                /** Link alarm to a pill */
                createPillAlarmLink(pill_id, alarm_id);
            }
            arrayPos++;
        }
        return alarm_ids;
    }

    /**
     * private function that inserts a row into a table that links pills and alarms
     *
     * @param pill_id the row_id of the pill that is being added to or edited
     * @param alarm_id the row_id of the alarm that is being added to the pill
     * @return returns the row_id the database creates when a row is created
     */
    private long createPillAlarmLink(long pill_id, long alarm_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLTABLE_ID, pill_id);
        values.put(KEY_ALARMTABLE_ID, alarm_id);

        /** Insert row */
        long pillAlarmLink_id = db.insert(PILL_ALARM_LINKS, null, values);

        return pillAlarmLink_id;
    }

    /**
     * uses a history model object to store histories in the DB
     * @param history a history model object
     */
    public void createHistory(History history) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PILLNAME, history.getPillName());
        values.put(KEY_DATE_STRING, history.getDateString());
        values.put(KEY_HOUR, history.getHourTaken());
        values.put(KEY_MINUTE, history.getMinuteTaken());

        /** Insert row */
        db.insert(HISTORIES_TABLE, null, values);
    }

// ############################# get methods ####################################### //

    /**
     * allows pillBox to retrieve a row from pill table in Db
     * @param pillName takes in a string of the pill Name
     * @return returns a pill model object
     */
    public Pill getPillByName(String pillName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String dbPill = "select * from "
                + PILL_TABLE        + " where "
                + KEY_PILLNAME      + " = "
                + "'"   + pillName  + "'";

        Cursor c = db.rawQuery(dbPill, null);

        Pill pill = new Pill();

        if (c.moveToFirst() && c.getCount() >= 1) {
            pill.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
            pill.setPillId(c.getLong(c.getColumnIndex(KEY_ROWID)));
            c.close();
        }
        return pill;
    }

    /**
     * allows the pillBox to retrieve all the pill rows from database
     * @return a list of pill model objects
     */
    public List<Pill> getAllPills() {
        List<Pill> pills = new ArrayList<>();
        String dbPills = "SELECT * FROM " + PILL_TABLE;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(dbPills, null);

        /** Loops through all rows, adds to list */
        if (c.moveToFirst()) {
            do {
                Pill p = new Pill();
                p.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
                p.setPillId(c.getLong(c.getColumnIndex(KEY_ROWID)));

                pills.add(p);
            } while (c.moveToNext());
        }
        c.close();
        return pills;
    }


    /**
     * Allows pillBox to retrieve all Alarms linked to a Pill
     * uses combineAlarms helper method
     * @param pillName string
     * @return list of alarm objects
     * @throws URISyntaxException honestly do not know why, something about alarm.getDayOfWeek()
     */
    public List<Alarm> getAllAlarmsByPill(String pillName) throws URISyntaxException {
        List<Alarm> alarmsByPill = new ArrayList<Alarm>();

        /** HINT: When reading string: '.' are not periods ex) pill.rowIdNumber */
        String selectQuery = "SELECT * FROM "       +
                ALARM_TABLE         + " alarm, "    +
                PILL_TABLE          + " pill, "     +
                PILL_ALARM_LINKS    + " pillAlarm WHERE "           +
                "pill."             + KEY_PILLNAME      + " = '"    + pillName + "'" +
                " AND pill."        + KEY_ROWID         + " = "     +
                "pillAlarm."        + KEY_PILLTABLE_ID  +
                " AND alarm."       + KEY_ROWID         + " = "     +
                "pillAlarm."        + KEY_ALARMTABLE_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Alarm al = new Alarm();
                al.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
                al.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
                al.setMinute(c.getInt(c.getColumnIndex(KEY_MINUTE)));
                al.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

                alarmsByPill.add(al);
            } while (c.moveToNext());
        }

        c.close();


        return combineAlarms(alarmsByPill);
    }

    /**
     * returns all individual alarms that occur on a certain day of the week,
     * alarms returned do not know of their counterparts that occur on different days
     * @param day an integer that represents the day of week
     * @return a list of Alarms (not combined into full-model-alarms)
     */
    public List<Alarm> getAlarmsByDay(int day) {
        List<Alarm> daysAlarms = new ArrayList<Alarm>();

        String selectQuery = "SELECT * FROM "       +
                ALARM_TABLE     + " alarm WHERE "   +
                "alarm."        + KEY_DAY_WEEK      +
                " = '"          + day               + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Alarm al = new Alarm();
                al.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
                al.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
                al.setMinute(c.getInt(c.getColumnIndex(KEY_MINUTE)));
                al.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

                daysAlarms.add(al);
            } while (c.moveToNext());
        }
        c.close();

        return daysAlarms;
    }


    /**
     *
     * @param alarm_id
     * @return
     * @throws URISyntaxException
     */
    public Alarm getAlarmById(long alarm_id) throws URISyntaxException {

        String dbAlarm = "SELECT * FROM "   +
                ALARM_TABLE + " WHERE "     +
                KEY_ROWID   + " = "         + alarm_id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(dbAlarm, null);

        if (c != null)
            c.moveToFirst();

        Alarm al = new Alarm();
        al.setId(c.getInt(c.getColumnIndex(KEY_ROWID)));
        al.setHour(c.getInt(c.getColumnIndex(KEY_HOUR)));
        al.setMinute(c.getInt(c.getColumnIndex(KEY_MINUTE)));
        al.setPillName(c.getString(c.getColumnIndex(KEY_ALARMS_PILL_NAME)));

        c.close();

        return al;
    }

    /**
     * Private helper function that combines rows in the databse back into a
     * full model-alarm with a dayOfWeek array.
     * @param dbAlarms a list of dbAlarms (not-full-alarms w/out day of week info)
     * @return a list of model-alarms
     * @throws URISyntaxException
     */
    private List<Alarm> combineAlarms(List<Alarm> dbAlarms) throws URISyntaxException {
        List<String> timesOfDay = new ArrayList<>();
        List<Alarm> combinedAlarms = new ArrayList<>();

        for (Alarm al : dbAlarms) {
            if (timesOfDay.contains(al.getStringTime())) {
                /** Add this db row to alarm object */
                for (Alarm ala : combinedAlarms) {
                    if (ala.getStringTime().equals(al.getStringTime())) {
                        int day = getDayOfWeek(al.getId());
                        boolean[] days = ala.getDayOfWeek();
                        days[day-1] = true;
                        ala.setDayOfWeek(days);
                        ala.addId(al.getId());
                    }
                }
            } else {
                /** Create new Alarm object with day of week array */
                Alarm newAlarm = new Alarm();
                boolean[] days = new boolean[7];

                newAlarm.setPillName(al.getPillName());
                newAlarm.setMinute(al.getMinute());
                newAlarm.setHour(al.getHour());
                newAlarm.addId(al.getId());

                int day = getDayOfWeek(al.getId());
                days[day-1] = true;
                newAlarm.setDayOfWeek(days);

                timesOfDay.add(al.getStringTime());
                combinedAlarms.add(newAlarm);
            }
        }

        Collections.sort(combinedAlarms);
        return combinedAlarms;
    }

    /**
     * Get a single pillapp.Model-Alarm
     * Used as a helper function
     */
    public int getDayOfWeek(long alarm_id) throws URISyntaxException {
        SQLiteDatabase db = this.getReadableDatabase();

        String dbAlarm = "SELECT * FROM "   +
                ALARM_TABLE + " WHERE "     +
                KEY_ROWID   + " = "         + alarm_id;

        Cursor c = db.rawQuery(dbAlarm, null);

        if (c != null)
            c.moveToFirst();

        int dayOfWeek = c.getInt(c.getColumnIndex(KEY_DAY_WEEK));
        c.close();

        return dayOfWeek;
    }

    /**
     * allows pillBox to retrieve from History table
     * @return a list of all history objects
     */
    public List<History> getHistory() {
        List<History> allHistory = new ArrayList<>();
        String dbHist = "SELECT * FROM " + HISTORIES_TABLE;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(dbHist, null);

        if (c.moveToFirst()) {
            do {
                History h = new History();
                h.setPillName(c.getString(c.getColumnIndex(KEY_PILLNAME)));
                h.setDateString(c.getString(c.getColumnIndex(KEY_DATE_STRING)));
                h.setHourTaken(c.getInt(c.getColumnIndex(KEY_HOUR)));
                h.setMinuteTaken(c.getInt(c.getColumnIndex(KEY_MINUTE)));

                allHistory.add(h);
            } while (c.moveToNext());
        }
        c.close();
        return allHistory;
    }
    public String createCSV(){

        boolean var = true;
        File folder = new File(Environment.getExternalStorageDirectory() + "/Folder");
        if (!folder.exists()) {
            var = folder.mkdir();

        }
        final String filename = folder.toString() + "/" + "report.pdf";
        Document document = new Document();
        try {



            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            Paragraph p1 = new Paragraph("PILL-REMINDER REPORT\n");
            Font paraFont= new Font(Font.TIMES_ROMAN);
            paraFont.setStyle(Font.UNDERLINE);
            paraFont.setSize(35.780f);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);


            //add paragraph to document
            document.add(p1);
            Calendar c=Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());
            Paragraph p2 = new Paragraph("This Is An Medical  Pill History Updated Till "+formattedDate +". This report can be handy for sending to your doctor.\n\n");
            Font paraFont2= new Font(Font.COURIER,14.0f, Color.BLACK);
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            p2.setFont(paraFont2);
            document.add(p2);
            Uri u1 = null;


            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+ HISTORIES_TABLE, null);


           // try {
              //  FileWriter fw = new FileWriter(filename);

                if (cursor != null) {
                    cursor.moveToFirst();
                   // fw.write("\t\tPILL-HISTORY\t\n");
                    PdfPTable table = new PdfPTable(5);
                    table.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell("S.NO");
                    table.addCell("PILL-NAME");
                    table.addCell("DATE-OF-INTAKE");
                    table.addCell("TIME(Hour)");
                    table.addCell("TIME(Minute)");
                    //float f=table.getTotalWidth();
                    //table.setTotalWidth(f+(f/2));
                   /* Paragraph p3 = new Paragraph("S.NO\tPILL-NAME\tDATE-OF-INTAKE\tTIME(Hour)\tTIME(Minute)\n");
                    Font paraFont3= new Font(Font.COURIER,14.0f, Color.GREEN);
                    p3.setAlignment(Paragraph.ALIGN_LEFT);
                    p3.setFont(paraFont3);
                    document.add(p3);*/
                   // f.write("S.NO\tPILL-NAME\tDATE-OF-INTAKE\tTIME(Hour)\tTIME(Minute)\n");
                    //  fw.write("PILL-HISTORY");
                    int n=cursor.getCount();
                    if(n>0) {
                        String s_no = cursor.getString(0);
                        String name = cursor.getString(1);
                        String date = cursor.getString(2);
                        String time1 = cursor.getString(3);
                        String time2 = cursor.getString(4);

                        table.addCell(s_no);
                        table.addCell(name);
                        table.addCell(date);
                        table.addCell(time1);
                        table.addCell(time2);

                        while (cursor.moveToNext()) {
                            s_no = cursor.getString(0);
                            name = cursor.getString(1);
                            date = cursor.getString(2);
                            time1 = cursor.getString(3);
                            time2 = cursor.getString(4);

                            table.addCell(s_no);
                            table.addCell(name);
                            table.addCell(date);
                            table.addCell(time1);
                            table.addCell(time2);


                        }
                    }



                        document.add(table);
                        Paragraph para4 = new Paragraph("*Time in 24 hour format is considered PM and Time in 12 hour format is considered AM\n");
                        para4.setAlignment(Element.ALIGN_LEFT);
                        document.add(para4);

                   // document.close();
                    /*for (int i = 0; i < cursor.getCount(); i++) {
                        for (int j = 0; j < cursor.getColumnNames().length; j++) {
                            Paragraph p4 = new Paragraph(cursor.getString(j) + "\t");
                            Font paraFont4= new Font(Font.COURIER,14.0f, Color.GREEN);
                            p4.setAlignment(Paragraph.ALIGN_LEFT);
                            p4.setFont(paraFont3);
                            document.add(p4);
                           // fw.append(cursor.getString(j) + "\t");
                        }
                        fw.append("\n");

                        cursor.moveToNext();
                    }*/
                    cursor.close();
                }







            //set footer
            Phrase footerText = new Phrase("This report can be handy for sending to your doctor.");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            document.setFooter(pdfFooter);
            document.addCreationDate();


        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally
        {
            document.close();
        }

        return filename;
    }
   /* public String createCSV(){

        boolean var = true;
        File folder = new File(Environment.getExternalStorageDirectory() + "/Folder");
        if (!folder.exists()) {
            var = folder.mkdir();

        }
        final String filename = folder.toString() + "/" + "report.csv";

        Uri u1 = null;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ HISTORIES_TABLE, null);


        try {
            FileWriter fw = new FileWriter(filename);

            if (cursor != null) {
                cursor.moveToFirst();
                fw.write("\t\tPILL-HISTORY\t\n");
                fw.write("S.NO\tPILL-NAME\tDATE-OF-INTAKE\tTIME(Hour)\tTIME(Minute)\n");
                //  fw.write("PILL-HISTORY");
                for (int i = 0; i < cursor.getCount(); i++) {
                    for (int j = 0; j < cursor.getColumnNames().length; j++) {

                        fw.append(cursor.getString(j) + "\t");
                    }
                    fw.append("\n");

                    cursor.moveToNext();
                }
                cursor.close();
            }
            fw.close();
        } catch(Exception e){
        }
        return filename;
    }*/

    
// ############################### delete methods##################################### //


    private void deletePillAlarmLinks(long alarmId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PILL_ALARM_LINKS, KEY_ALARMTABLE_ID
                + " = ?", new String[]{String.valueOf(alarmId)});
    }

    public void deleteAlarm(long alarmId) {
        SQLiteDatabase db = this.getWritableDatabase();

        /** First delete any link in PillAlarmLink Table */
        deletePillAlarmLinks(alarmId);

        /* Then delete alarm */
        db.delete(ALARM_TABLE, KEY_ROWID
                + " = ?", new String[]{String.valueOf(alarmId)});
    }

    public void deletePill(String pillName) throws URISyntaxException {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Alarm> pillsAlarms;

        /** First get all Alarms and delete them and their Pill-links */
        pillsAlarms = getAllAlarmsByPill(pillName);
        for (Alarm alarm : pillsAlarms) {
            long id = alarm.getId();
            deleteAlarm(id);
        }

        /** Then delete Pill */
        db.delete(PILL_TABLE, KEY_PILLNAME
                + " = ?", new String[]{pillName});
    }
}