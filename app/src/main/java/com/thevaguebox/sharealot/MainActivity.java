package com.thevaguebox.sharealot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText dataObj;
    TextView convertObj, companyNameObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataObj = findViewById(R.id.data);
        convertObj = findViewById(R.id.convert);
        companyNameObj = findViewById(R.id.company);

        convertObj.setOnClickListener(view -> {
            String data = dataObj.getText().toString();
            Intent intent = new Intent(this, FormattedPage.class);
            intent.putExtra("chatData", data);
            startActivity(intent);
        });

        companyNameObj.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://play.google.com/store/apps/dev?id=8908431179062757410"));
            Toast.makeText(this, "Opening Google PlayStore", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

    }
}