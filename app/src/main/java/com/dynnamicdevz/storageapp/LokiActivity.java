package com.dynnamicdevz.storageapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dynnamicdevz.storageapp.databinding.ActivityLokiBinding;
import com.dynnamicdevz.storageapp.databinding.ActivityMobiusBinding;

import static com.dynnamicdevz.storageapp.util.Constants.DATA_KEY;

public class LokiActivity extends AppCompatActivity {

    private ActivityLokiBinding binding;
    private SharedPreferences sharedPreferences;
    private String data;
    String key = "name";

    TextView receiver_msg;
    Button send_button, clear_conv;
    EditText send_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLokiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        readFromSharedPref();

        receiver_msg = (TextView) findViewById(R.id.loki_display_textview);

        ImageView lokiImageView = findViewById(R.id.loki_profile);
        Glide.with(LokiActivity.this)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(R.drawable.loki)
                .into(lokiImageView);

        send_button = (Button) findViewById(R.id.text_mobius_button);
        clear_conv = (Button) findViewById(R.id.clear_conversation);
        send_text = (EditText) findViewById(R.id.loki_edit_text);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("message_key");
        receiver_msg.setText(msg);

        if(data.equals("empty"))
            data = "";

        sharedPreferences.edit()
                .putString(DATA_KEY, data + "\n" + msg + "\n")
                .apply();
        readFromSharedPref();


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the value which input by user in EditText
                // and convert it to string
                String str = "* Loki says: " + send_text.getText().toString();

                // Create the Intent object of this class Context() to Second_activity class
                Intent intent = new Intent(getApplicationContext(), MobiusActivity.class);

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

        data = sharedPreferences.getString(DATA_KEY, "empty");
        binding.lokiDisplayTextview.setText(data);
    }

    public String fetchValue(String key) {
        String value = sharedPreferences.getString(key, null);
        return value;
    }

    public void clearValue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}