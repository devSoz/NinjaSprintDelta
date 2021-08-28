package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    //private List<Integer> scoreList = new ArrayList<Integer>();

    private PopupWindow popUp;
    private Switch  aSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        clickListener();

        popup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test","resume");

    }


    @Override
    protected void onRestart() {

        super.onRestart();
    }


    private void clickListener()
    {
        Button b1 = (Button) findViewById(R.id.btnnew);
        Button b2 = (Button) findViewById(R.id.btnhelp);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                aSwitch = (Switch) findViewById(R.id.switch1);
                Integer temp;
                if(aSwitch.isChecked())
                {    temp=1;    }
                else
                    temp=0;
                Intent intentCanva = new Intent(MainActivity.this, Canva.class);
                intentCanva.putExtra("volume", temp);
                finish();
                startActivity(intentCanva);

            }
        });


      /*  b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder alertText1 = new StringBuilder(50);
                alertText1.append("Scoring Criteria:\n\n");
                alertText1.append("One Player mode: +2 points per hit \nCom vs Player mode: +2 points per hit, \n\t\t+5 for when computer misses");
                //alertText1.append(Double.toString(lorentzFactor));
                alertShow(alertText1);
            }
        });*/
    }

    private void alertShow(StringBuilder alertText)
    {
        AlertDialog.Builder alertAnswer = new AlertDialog.Builder(MainActivity.this);

        alertAnswer.setMessage(alertText)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
        AlertDialog createAlert = alertAnswer.create();
        createAlert.show();
    }

    //for help
    public void popup()
    {
        ScrollView rel = (ScrollView) findViewById(R.id.rel12) ;
        Button but = (Button) findViewById(R.id.btnhelp);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Display display = getWindowManager().getDefaultDisplay();

                // Load the resolution into a Point object
                Point size = new Point();

                display.getSize(size);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View custview = inflater.inflate(R.layout.popup, null);
                popUp = new PopupWindow(custview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv = custview.findViewById(R.id.tv);
                tv.setText(Html.fromHtml("<br><br><br><br><u><b>Rules:</b></u><br><br>" +
                        "To be added later<br><br>"
                         ));



                ImageButton btnclose =  custview.findViewById(R.id.btnclose);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                popUp.showAtLocation(rel, Gravity.NO_GRAVITY, 10, 10);
                popUp.update(50, 50, size.x-100, size.y-100);
            }});
    }

}