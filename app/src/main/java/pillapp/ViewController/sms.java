package pillapp.ViewController;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.vyom.pillapp.R;

import java.util.List;
import java.util.Locale;

import pillapp.Model.DbHelper;
import pillapp.Model.Panic;



public class sms extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        DbHelper db = new DbHelper(getApplicationContext());
        List<Panic> pa = db.getPanicList();
        String location1;
        if (!pa.isEmpty()) {
            if (pa.get(0).getMobile2().equals("0"))
                pa.get(0).setMobile2("");
            if (pa.get(0).getMobile3().equals("0"))
                pa.get(0).setMobile3("");
            if (pa.get(0).getMobile4().equals("0"))
                pa.get(0).setMobile4("");
            if (pa.get(0).getMobile5().equals("0"))
                pa.get(0).setMobile5("");

            String mobile = pa.get(0).getMobile1() + ";" + pa.get(0).getMobile2() + ";" + pa.get(0).getMobile3() + ";" + pa.get(0).getMobile4() + ";" + pa.get(0).getMobile5();
            Toast.makeText(getApplicationContext(), "Initiating SMS Process", Toast.LENGTH_LONG).show();
            GPSTracker gps = new GPSTracker(this);
            // location=getLocation();
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> myList = null;
                try {
                    myList = myLocation.getFromLocation(latitude, longitude, 1);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Location could'nt be found", Toast.LENGTH_LONG).show();
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
                    smsIntent.putExtra("sms_body", "Its an Medical Emergency\n" + location1);
                /*try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(mobile, null, " Its an Emergency\n"+location1, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later  !",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }*/

                    try {
                        startActivity(smsIntent);
                        finish();
                        Log.i("Finished Sending SMS...", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, Please Try Again Later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ENABLE YOUR INTERNET CONNECTION", Toast.LENGTH_LONG).show();
                    // gps.showSettingsAlert();
                }


                // check if GPS enabled
           /* if (gps.isGPSEnabled && gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Geocoder myLocation = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> myList = null;
                try {
                    myList = myLocation.getFromLocation(latitude, longitude, 1);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Location could'nt be found", Toast.LENGTH_LONG).show();
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
                    smsIntent.putExtra("sms_body", "Its an Medical Emergency\n" + location1);
                           ///*try {
                               SmsManager smsManager = SmsManager.getDefault();
                               smsManager.sendTextMessage(mobile, null, " Its an Emergency\n"+location1, null, null);
                               Toast.makeText(getApplicationContext(), "SMS Sent!",
                                       Toast.LENGTH_LONG).show();
                           } catch (Exception e) {
                               Toast.makeText(getApplicationContext(),
                                       "SMS faild, please try again later  !",
                                       Toast.LENGTH_LONG).show();
                               e.printStackTrace();
                           }*///

                    /*try {
                        startActivity(smsIntent);
                        finish();
                        Log.i("Finished Sending SMS...", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(),
                                "SMS faild, Please Try Again Later.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ENABLE YOUR INTERNET CONNECTION", Toast.LENGTH_LONG).show();
                   // gps.showSettingsAlert();
                }

                // \n is for new line
                // Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
               // gps.showSettingsAlert();

            }
            // String mobile =  "8726899;353545;454555;465456;5646546546" ;
            //SmsManager smsMgr = SmsManager.getDefault();
            //ArrayList<string> smsMessageText = smsMgr.divideMessage(smsMessage.getMsgBody());
            //PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT"), 0);
            //PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_DELIVERED"), 0);
            //smsMgr.sendTextMessage(mobile.toString(), null, "HEY I am in a Emergency", sentPI, deliveredPI);

*/
            }
            // Toast.makeText(getApplicationContext(),"vgghh",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(getApplicationContext(),"Enable your Internet connection",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }

            else
                Toast.makeText(getApplicationContext(), "You Haven't Store Any Contact Detail For Your Panic List", Toast.LENGTH_LONG).show();


        }
    /*public Location getLocation() {


        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
        }
        return location;
    }*/

}

   