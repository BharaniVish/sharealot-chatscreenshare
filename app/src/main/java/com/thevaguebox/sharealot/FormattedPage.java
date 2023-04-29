package com.thevaguebox.sharealot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


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
            convertLayoutToPdf();
        });
    }



    private void convertLayoutToPdf() {
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        String fileName = "sal_css"+System.currentTimeMillis()/1000+".pdf";
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));
            }
            document.open();

            ScrollView layout = findViewById(R.id.chatScrollLayout);

            // Get the height of the layout
            int height = layout.getHeight();

            // Create a bitmap with the same height
            Bitmap bitmap = Bitmap.createBitmap(layout.getWidth(), height, Bitmap.Config.ARGB_8888);

            // Draw the layout onto the bitmap
            Canvas canvas = new Canvas(bitmap);
            layout.draw(canvas);

            // Calculate the number of pages needed
            int pageCount = (int) Math.ceil(height / document.getPageSize().getHeight());

            // Add the bitmap to each page
            for (int i = 0; i < pageCount; i++) {
                if (i > 0) {
                    addNewPage(document);
                }
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                document.add(com.itextpdf.text.Image.getInstance(byteArray));
            }

            // Close the document
            document.close();
            Toast.makeText(this, "PDF created", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error creating the PDF", Toast.LENGTH_SHORT).show();
            Log.e("ERROR!!!", String.valueOf(e));
        }
    }

    private void addNewPage(Document document) {
        if (document.isOpen()) {
            document.newPage();
        }
    }

}