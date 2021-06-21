package com.dynnamicdevz.storageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.dynnamicdevz.storageapp.util.Constants.DATA_KEY;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dynnamicdevz.storageapp.databinding.ActivityMobiusBinding;


public class MobiusActivity extends AppCompatActivity {

    private ActivityMobiusBinding binding2;
    private SharedPreferences sharedPreferences2;
    private String data;
    String key = "name";

    Button send_button, clear_conv;
    EditText send_text;

    TextView receiver_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding2 = ActivityMobiusBinding.inflate(getLayoutInflater());
        setContentView(binding2.getRoot());
        sharedPreferences2 = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        readFromSharedPref();

        receiver_msg = (TextView)findViewById(R.id.mobius_display_textview);

        ImageView mobiusImageView = findViewById(R.id.mobius_profile);
        Glide.with(MobiusActivity.this)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(R.drawable.mobius)
                .into(mobiusImageView);

        send_button = (Button)findViewById(R.id.text_loki_button);
        send_text = (EditText)findViewById(R.id.mobius_edit_text);
        clear_conv = (Button) findViewById(R.id.clear_conversation_morbius);

        Intent rI = getIntent();
        String message = rI.getStringExtra("message_key");
        receiver_msg.setText(message);

        if(message.length() == 0)
            Toast.makeText(this, "Text empty", Toast.LENGTH_LONG).show();
        else {
            if(data.equals("empty"))
                data = "";
            sharedPreferences2.edit()
                    .putString(DATA_KEY, data+"\n"+message+"\n")
                    .apply();
            readFromSharedPref();
        }

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                // get the value which input by user in EditText
                // and convert it to string
                String str = "- Mobius says: " + send_text.getText().toString();

                // Create the Intent object of this class Context() to Second_activity class
                Intent intent = new Intent(getApplicationContext(), LokiActivity.class);

                // now by putExtra method put the value in key, value pair
                // key is message_key by this key we will receive the value, and put the string

                intent.putExtra("message_key", str);

                // start the Intent
                startActivity(intent);
            }
        });

        clear_conv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearValue();
                receiver_msg.setText(fetchValue(key));
            }
        });


    }

    private void readFromSharedPref() {
        data = sharedPreferences2.getString(DATA_KEY, "empty");
        binding2.mobiusDisplayTextview.setText(data);
    }
    public String fetchValue(String key) {
        String value = sharedPreferences2.getString(key, null);
        return value;
    }

    public void clearValue() {
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.clear();
        editor.commit();
    }
}