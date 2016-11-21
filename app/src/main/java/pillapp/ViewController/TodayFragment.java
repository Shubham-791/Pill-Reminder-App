package pillapp.ViewController;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.vyom.pillapp.R;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import pillapp.Model.Alarm;
import pillapp.Model.PillBox;


public class TodayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        TableLayout stk = (TableLayout) rootView.findViewById(R.id.table_today);

        Typeface lightFont = Typeface.createFromAsset(container.getContext().getAssets(), "fonts/Roboto-Light.ttf");

        PillBox pillBox = new PillBox();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        List<Alarm> alarms = Collections.emptyList();



        // note that we're looking for a button with id="@+id/myButton" in your inflated layout
        // Naturally, this can be any View; it doesn't have to be a button
       /* Button mButton = (Button) rootView.findViewById(R.id.button);
        mButton.setOnLongClickListener(new View.OnLongClickListener() {
                                                   @Override
                                                   public boolean onLongClick(View v) {
                                                       Intent intr=new Intent(getActivity(),editlist.class);
                                                        startActivity(intr);


                                                           //Toast.makeText(getActivity(),"double",Toast.LENGTH_LONG).show();



                                                      return true;
                                                   }
                                               });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here you set what you want to do when user clicks your button,
                // e.g. launch a new activity
                //Panic pan=new Panic();
                DbHelper db=new DbHelper(getActivity());
                List<Panic> pa=db.getPanicList();
                String location1;
               if(!pa.isEmpty()) {
                   if(pa.get(0).getMobile2().equals("0"))
                   pa.get(0).setMobile2("");
                   if(pa.get(0).getMobile3().equals("0"))
                       pa.get(0).setMobile3("");
                   if(pa.get(0).getMobile4().equals("0"))
                       pa.get(0).setMobile4("");
                   if(pa.get(0).getMobile5().equals("0"))
                       pa.get(0).setMobile5("");

                   String mobile = pa.get(0).getMobile1() + ";" + pa.get(0).getMobile2() + ";" + pa.get(0).getMobile3() + ";" + pa.get(0).getMobile4() + ";" + pa.get(0).getMobile5();
                   Toast.makeText(getActivity(),"Initiating SMS Process",Toast.LENGTH_LONG).show();
                   GPSTracker gps = new GPSTracker(getActivity());

                   // check if GPS enabled
                   if(gps.canGetLocation()){

                       double latitude = gps.getLatitude();
                       double longitude = gps.getLongitude();
                       Geocoder myLocation = new Geocoder(getActivity(), Locale.getDefault());
                       List<Address> myList = null;
                       try {
                           myList = myLocation.getFromLocation(latitude,longitude, 1);
                       } catch (Exception e) {
                           Toast.makeText(getActivity(),"ENABLE YOUR INTERNET CONNECTION",Toast.LENGTH_LONG).show();
                       }
                       try {
                           Address address = (Address) myList.get(0);

                           String addressStr = "";
                           addressStr += address.getAddressLine(0) + ", ";
                           addressStr += address.getAddressLine(1) + ", ";
                           //addressStr += address.getAddressLine(2);
                           location1 = "My Location is - \n" + addressStr + " \nLat: " + latitude + "\nLong: " + longitude;

                       Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                       smsIntent.setData(Uri.parse("smsto:"));
                       smsIntent.setType("vnd.android-dir/mms-sms");
                       smsIntent.putExtra("address", mobile);
                       smsIntent.putExtra("sms_body", "Its an Medical Emergency\n"+location1);
                           /*try {
                               SmsManager smsManager = SmsManager.getDefault();
                               smsManager.sendTextMessage(mobile, null, " Its an Emergency\n"+location1, null, null);
                               Toast.makeText(getActivity(), "SMS Sent!",
                                       Toast.LENGTH_LONG).show();
                           } catch (Exception e) {
                               Toast.makeText(getActivity(),
                                       "SMS faild, please try again later  !",
                                       Toast.LENGTH_LONG).show();
                               e.printStackTrace();
                           }*/

                     // try {
                         //  startActivity(smsIntent);
                           //finish();
                        /*   Log.i("Finished Sending SMS...", "");
                       } catch (android.content.ActivityNotFoundException ex) {
                           Toast.makeText(getActivity(),
                                   "SMS faild, Please Try Again Later.", Toast.LENGTH_SHORT).show();
                       }
                       }
                       catch (Exception e) {
                           Toast.makeText(getActivity(),"ENABLE YOUR INTERNET CONNECTION",Toast.LENGTH_LONG).show();
                       }

                       // \n is for new line
                      // Toast.makeText(getActivity(), , Toast.LENGTH_LONG).show();
                   }else{
                       // can't get location
                       // GPS or Network is not enabled
                       // Ask user to enable GPS/network in settings
                       gps.showSettingsAlert();
                   }
                   // String mobile =  "8726899;353545;454555;465456;5646546546" ;
                   //SmsManager smsMgr = SmsManager.getDefault();
                   //ArrayList<string> smsMessageText = smsMgr.divideMessage(smsMessage.getMsgBody());
                   //PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_SENT"), 0);
                   //PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent("SMS_DELIVERED"), 0);
                   //smsMgr.sendTextMessage(mobile.toString(), null, "HEY I am in a Emergency", sentPI, deliveredPI);


               }
               // Toast.makeText(getActivity(),"vgghh",Toast.LENGTH_LONG).show();

                else
                    Toast.makeText(getActivity(),"You Haven't Store Any Contact Detail For Your Panic List",Toast.LENGTH_LONG).show();

            }


        });*/

        // after you've done all your manipulation, return your layout to be shown

        try {
            alarms = pillBox.getAlarms(container.getContext(), day);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if(alarms.size() != 0) {
            for(Alarm alarm: alarms) {
                TableRow tbrow = new TableRow(container.getContext());

                TextView t1v = new TextView(container.getContext());
                t1v.setText(alarm.getPillName());
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.CENTER);
                t1v.setPadding(30, 30, 30, 30);
                t1v.setTextSize(25);
                t1v.setTypeface(lightFont);
                t1v.setMaxEms(6);

                tbrow.addView(t1v);

                TextView t2v = new TextView(container.getContext());

                String time = alarm.getStringTime();
                t2v.setText(time);
                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(30, 30, 30, 30);
                t2v.setTextSize(25);
                t2v.setTypeface(lightFont);
                tbrow.addView(t2v);

                stk.addView(tbrow);
            }
        } else {
            TableRow tbrow = new TableRow(container.getContext());

            TextView t1v = new TextView(container.getContext());
            t1v.setText("You don't have any alarms for Today!");
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            t1v.setPadding(30, 30, 30, 30);
            t1v.setTextSize(25);
            t1v.setTypeface(lightFont);
            t1v.setMaxEms(10);
            tbrow.addView(t1v);

            stk.addView(tbrow);
        }
        return rootView;
    }
}
