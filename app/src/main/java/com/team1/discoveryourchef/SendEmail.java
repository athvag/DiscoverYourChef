package com.team1.discoveryourchef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SendEmail extends AppCompatActivity {

    Button BtSend;
    ImageView back;
    EditText EtSubject;
    EditText EtBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email);

        EtSubject = findViewById (R.id.EtSubject);
        EtBody = findViewById (R.id.EtBody);
        BtSend = findViewById (R.id.send_button);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        back = findViewById(R.id.sendmail_backButton);

        BtSend = findViewById(R.id.send_button);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        BtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSend();
            }
        });
    }

    public void DoSend ()
    {
        String[] Recipients = new String [1];
        Recipients[0] = getString(R.string.contactowner);
        String Subj = EtSubject.getText ().toString ();
        String Body = EtBody.getText ().toString ();
        Intent intent = new Intent (Intent.ACTION_SEND);
        //intent.setData (Uri.parse ("mailto:"));
        intent.setType ("message/rfc822");
        intent.putExtra (Intent.EXTRA_EMAIL, Recipients);
        intent.putExtra (Intent.EXTRA_SUBJECT, Subj);
        intent.putExtra (Intent.EXTRA_TEXT, Body);
        try
        {
            startActivity (Intent.createChooser (intent, "Send mail..."));
            ShowMessage ("Sending Email");
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            ShowMessage (getString(R.string.noappforemailsString));
        }
    }

    void ShowMessage (String Mess)
    {
        Toast Tst = Toast.makeText (getApplicationContext (), Mess, Toast.LENGTH_LONG);
        Tst.show ();
    }
}


