package com.thevaguebox.sharealot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class FormattedPage extends Activity {

    LinearLayout chatLayoutObj;
    TextView companyNameObj;
    TextView generateObj;
    String month = "", date = "", time = "", content = "", name = "";
    Boolean mDone = false, dDone = false, tDone = false, nDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = getIntent();
        String data = in.getStringExtra("chatData");
        setContentView(R.layout.activity_formatted_page);

        chatLayoutObj = findViewById(R.id.chatLayout);
        companyNameObj = findViewById(R.id.company);
        generateObj = findViewById(R.id.generate);

        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '[') {
                if (!content.isEmpty()) {

                    LinearLayout dockLayout = new LinearLayout(this);
                    dockLayout.setOrientation(LinearLayout.VERTICAL);
                    dockLayout.setBackgroundResource(R.drawable.sender);
                    dockLayout.setPadding(10,10,10,10);

                    TextView nameView = new TextView(this);
                    nameView.setTextSize(14);
                    nameView.setTextColor(getResources().getColor(R.color.red));
                    nameView.setText(name);
                    nameView.setTypeface(null, Typeface.BOLD);

                    TextView timeStampView = new TextView(this);
                    timeStampView.setTextSize(14);
                    timeStampView.setText(time);
                    timeStampView.setTextColor(getResources().getColor(R.color.off_white));
                    timeStampView.setGravity(Gravity.END);

                    TextView messageView = new TextView(this);
                    messageView.setTextColor(getResources().getColor(R.color.white));
                    messageView.setTextSize(16);
                    messageView.setText(content);
                    Log.d("cSET", content);
                    content = "";
                    Log.d("cRESET", content);
                    Log.d("mSET", month);
                    month = "";
                    Log.d("mRESET", month);
                    Log.d("dSET", date);
                    date = "";
                    Log.d("dRESET", date);
                    Log.d("tSET", time);
                    time = "";
                    Log.d("tRESET", time);
                    Log.d("nSET", name);
                    name = "";
                    Log.d("nRESET", name);

                    mDone = false; dDone = false; tDone = false; nDone = false;

                    dockLayout.addView(nameView);
                    dockLayout.addView(messageView);
                    dockLayout.addView(timeStampView);
                    chatLayoutObj.addView(dockLayout);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20);

                    LinearLayout space = new LinearLayout(this);
                    space.setLayoutParams(layoutParams);
                    chatLayoutObj.addView(space);
                }

                else {
                    Log.d("First Time", "Only Once");
                }
            }

            else if (data.charAt(i) != ']' && content.isEmpty() && !tDone) {
                if (data.charAt(i) != ',' && time.isEmpty() && !dDone) {
                    if (data.charAt(i) != '/' && date.isEmpty() && !mDone) {
                        month = month + data.charAt(i);
                    }
                    else if (data.charAt(i) == '/') {
                        mDone = true;
                        Log.d("month", month);
                    }
                    else {
                        date = date + data.charAt(i);
                    }
                }
                else if (data.charAt(i) == ',') {
                    dDone = true;
                    Log.d("date", date);
                }
                else {
                    time = time + data.charAt(i);
                }
            }
            else if (data.charAt(i) == ']') {
                tDone = true;
                Log.d("time", time);
            }

            else {
                if(data.charAt(i) != ':' && !nDone) {
                    name = name + data.charAt(i);
                }
                else if (data.charAt(i) == ':') {
                    nDone = true;
                    Log.d("name", name);
                }
                else {
                    content = content + data.charAt(i);
                }
            }
        }
        if (!content.isEmpty()) {

            LinearLayout dockLayout = new LinearLayout(this);
            dockLayout.setOrientation(LinearLayout.VERTICAL);
            dockLayout.setBackgroundResource(R.drawable.sender);
            dockLayout.setPadding(10,10,10,10);

            TextView nameView = new TextView(this);
            nameView.setTextSize(14);
            nameView.setTextColor(getResources().getColor(R.color.red));
            nameView.setText(name);
            nameView.setTypeface(null, Typeface.BOLD);

            TextView timeStampView = new TextView(this);
            timeStampView.setTextSize(14);
            timeStampView.setText(time);
            timeStampView.setTextColor(getResources().getColor(R.color.off_white));
            timeStampView.setGravity(Gravity.END);

            TextView messageView = new TextView(this);
            messageView.setTextColor(getResources().getColor(R.color.white));
            messageView.setTextSize(16);
            messageView.setText(content);
            Log.d("cSET", content);
            content = "";
            Log.d("cRESET", content);
            Log.d("mSET", month);
            month = "";
            Log.d("mRESET", month);
            Log.d("dSET", date);
            date = "";
            Log.d("dRESET", date);
            Log.d("tSET", time);
            time = "";
            Log.d("tRESET", time);
            Log.d("nSET", name);
            name = "";
            Log.d("nRESET", name);

            mDone = false; dDone = false; tDone = false; nDone = false;

            dockLayout.addView(nameView);
            dockLayout.addView(messageView);
            dockLayout.addView(timeStampView);
            chatLayoutObj.addView(dockLayout);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,20);

            LinearLayout space = new LinearLayout(this);
            space.setLayoutParams(layoutParams);
            chatLayoutObj.addView(space);
        }

        generateObj.setOnClickListener(view -> {
            //Convert the Layout to PDF
            Toast.makeText(this, "Error saving the file, please grant permissions", Toast.LENGTH_SHORT).show();
        });

        companyNameObj.setOnClickListener(view -> {
            String link = "https://play.google.com/store/apps/dev?id=8908431179062757410";
            String toastMessage = "Opening Google PlayStore";
            hyperlinkClick (link, toastMessage);
        });
    }

    public void hyperlinkClick (String link, String toastMessage) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(link));
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}