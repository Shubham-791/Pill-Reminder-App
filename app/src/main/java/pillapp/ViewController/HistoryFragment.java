package pillapp.ViewController;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vyom.pillapp.R;

import java.io.File;
import java.util.List;

import pillapp.Model.DbHelper;
import pillapp.Model.History;
import pillapp.Model.PillBox;



public class HistoryFragment extends Fragment {
    Button report;
    //TextView t1v;
   // PillBox pill =new PillBox();
   // List<History> history=pill.getHistory(getActivity());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        report=(Button)rootView.findViewById(R.id.repot);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper abc=new DbHelper(getActivity());
                PillBox pill=new PillBox();
                List<History> history=pill.getHistory(getActivity());

                if(!history.isEmpty())
                {
                    String filename = abc.createCSV();
                    String filename1 = "report.pdf";
                    //Toast.makeText(getActivity(),filename, Toast.LENGTH_LONG).show();

                    File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Folder/" + filename1);
                    //Toast.makeText(getActivity(),filelocation.toString(), Toast.LENGTH_LONG).show();
                    Uri path = Uri.fromFile(filelocation);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
                    emailIntent.setType("vnd.android.cursor.dir/email");
                    //String to[] = {"asd@gmail.com"};
                    // emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
                    emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PILL-HISTORY");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
                else
                    Toast.makeText(getActivity(), "No History", Toast.LENGTH_LONG).show();

            }
        });

        TableLayout stk = (TableLayout) rootView.findViewById(R.id.table_history);

        TableRow tbrow0 = new TableRow(container.getContext());

        final TextView tt1 = new TextView(container.getContext());
        tt1.setPaintFlags(tt1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tt1.setText("Pill Name");
        tt1.setTextColor(Color.WHITE);
        tt1.setGravity(Gravity.CENTER);
        tt1.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt1);

        TextView tt2 = new TextView(container.getContext());

        tt2.setPaintFlags(tt2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tt2.setText("Date Taken");
        tt2.setTextColor(Color.WHITE);
        tt2.setGravity(Gravity.CENTER);
        tt2.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt2);

        TextView tt3 = new TextView(container.getContext());
        tt3.setPaintFlags(tt3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tt3.setText("Time Taken");
        tt3.setTextColor(Color.WHITE);
        tt3.setGravity(Gravity.CENTER);
        tt3.setTypeface(null, Typeface.BOLD);
        tbrow0.addView(tt3);

        stk.addView(tbrow0);

        PillBox pillBox = new PillBox();
        TextView t1v;
        for (History history: pillBox.getHistory(container.getContext())){
            TableRow tbrow = new TableRow(container.getContext());

            t1v = new TextView(container.getContext());
            t1v.setText(history.getPillName());
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            t1v.setMaxEms(4);
            tbrow.addView(t1v);

            TextView t2v = new TextView(container.getContext());
            String date = history.getDateString();
            t2v.setText(date);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);

            TextView t3v = new TextView(container.getContext());

            int nonMilitaryHour = history.getHourTaken() % 12;
            if (nonMilitaryHour == 0)
                nonMilitaryHour = 12;

            String minute;
            if (history.getMinuteTaken() < 10)
                minute = "0" + history.getMinuteTaken();
            else
                minute = "" + history.getMinuteTaken();

            String time = nonMilitaryHour + ":" + minute + " " + history.getAm_pmTaken();
            t3v.setText(time);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            stk.addView(tbrow);
        }
        //final TextView finalT1v = t1v;

        return rootView;
    }
}