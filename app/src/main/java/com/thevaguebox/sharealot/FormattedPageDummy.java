package com.thevaguebox.sharealot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FormattedPageDummy extends Activity {

    String dirpath;
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


        //paste the content-not-empty part of code here from the for loop for the last message display

        generateObj.setOnClickListener(view -> {
            layoutToImage(chatLayoutObj);
            try {
                imageToPDF();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        /* companyNameObj.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://play.google.com/store/apps/dev?id=8908431179062757410"));
            Toast.makeText(this, "Opening Google PlayStore", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }); */
    }

    public void layoutToImage(View view) {
        // convert view group to bitmap
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = Environment.getExternalStorageDirectory().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(dirpath + "/NewPDF.pdf"))); //  Change pdf's name.
            }
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scale = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scale);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("File Not Found", "imageToPDF: ");
        }
    }

}