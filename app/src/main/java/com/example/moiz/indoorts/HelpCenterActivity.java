package com.example.moiz.indoorts;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class HelpCenterActivity extends AppCompatActivity {

    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    Button submit;
    EditText ename, eemail, etitle, emessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.text);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        submit = (Button) findViewById(R.id.btn_submit);
        eemail = (EditText) findViewById(R.id.email);
        etitle = (EditText) findViewById(R.id.title);
        ename = (EditText) findViewById(R.id.name);
        emessage = (EditText) findViewById(R.id.body);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetwork()){
                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    if(eemail.getText().toString().isEmpty()
                            || etitle.getText().toString().isEmpty() || ename.getText().toString().isEmpty()
                            || emessage.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }
                    else{
                        if(!haveNetwork()){
                            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            EnquiryDialog enquiryDialog = new EnquiryDialog();
                            enquiryDialog.show(getSupportFragmentManager(),"enquiry dialog");
                            progressBar.setVisibility(View.GONE);
                            tvview.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        ExpandableLayout layout = (ExpandableLayout) findViewById(R.id.expandable_layout);

        layout.setRenderer(new ExpandableLayout.Renderer<HelpCategory, Help>() {
            @Override
            public void renderParent(View view, HelpCategory helpCategory, boolean isExpanded, int parentPosition) {
                ((TextView)view.findViewById(R.id.expandablehelp)).setText(helpCategory.name);
                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded?R.drawable.arrow_up:R.drawable.arrow_down);
            }

            @Override
            public void renderChild(View view, Help help, int parentPosition, int childPosition) {
                ((TextView)view.findViewById(R.id.childname)).setText(help.name);
            }

        });

        layout.addSection(getSection1());
        layout.addSection(getSection2());
        layout.addSection(getSection3());
        layout.addSection(getSection4());
        layout.addSection(getSection5());
        layout.addSection(getSection6());
    }

    public boolean haveNetwork(){
        boolean haveWifi = false;
        boolean haveData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info : networkInfos){
            if(info.getTypeName().equalsIgnoreCase("Wifi"))
                if(info.isConnected())
                    haveWifi = true;
            if(info.getTypeName().equalsIgnoreCase("Mobile"))
                if(info.isConnected())
                    haveData = true;
        }
        return haveData || haveWifi;
    }

    private Section<HelpCategory, Help> getSection1() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("What is the main purpose of connecting bluetooth?");

        List<Help> listHelp = new ArrayList<>();
            listHelp.add(new Help("To connect the app with beacons which send push notifications about the surroundings."));
            section.parent = helpCategory;
            section.children.addAll(listHelp);
        return section;
    }

    private Section<HelpCategory, Help> getSection2() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("Where can I find faculty current status?");

        List<Help> listHelp = new ArrayList<>();
        listHelp.add(new Help("Just logged in with student credentials, then go to dashboard and click on Faculty Status icon."));
        section.parent = helpCategory;
        section.children.addAll(listHelp);
        return section;
    }

    private Section<HelpCategory, Help> getSection3() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("What is the main purpose of viewing faculty status?");

        List<Help> listHelp = new ArrayList<>();
        listHelp.add(new Help("You are no longer required to go to faculty office to see whether a faculty member is available or not."));
        section.parent = helpCategory;
        section.children.addAll(listHelp);
        return section;
    }

    private Section<HelpCategory, Help> getSection4() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("How to find a particular location?");

        List<Help> listHelp = new ArrayList<>();
        listHelp.add(new Help("In University Map section, select your current floor, then choose your source and destination points from the list and press FIND. You will see the route map to your destination."));
        section.parent = helpCategory;
        section.children.addAll(listHelp);
        return section;
    }

    private Section<HelpCategory, Help> getSection5() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("How push notification works?");

        List<Help> listHelp = new ArrayList<>();
        listHelp.add(new Help("Simply, get in the zone of your nearest beacon, all the information about current activities will be delivered to you within seconds in PN."));
        section.parent = helpCategory;
        section.children.addAll(listHelp);
        return section;
    }

    private Section<HelpCategory, Help> getSection6() {
        Section<HelpCategory, Help> section = new Section<>();
        HelpCategory helpCategory = new HelpCategory("What information I get in push notifications?");

        List<Help> listHelp = new ArrayList<>();
        listHelp.add(new Help("All the information about what is heppening in classrooms, faculty rooms and in other areas."));
        section.parent = helpCategory;
        section.children.addAll(listHelp);
        return section;
    }
}
