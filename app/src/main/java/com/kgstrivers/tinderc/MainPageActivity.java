package com.kgstrivers.tinderc;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kgstrivers.tinderc.Fragment.AccountFragment;
import com.kgstrivers.tinderc.Fragment.MainFragment;
import com.kgstrivers.tinderc.Fragment.MessageFragment;
import com.kgstrivers.tinderc.Model.Cards;
import com.kgstrivers.tinderc.Model.Logind;
import com.kgstrivers.tinderc.Model.Logoutd;
import com.kgstrivers.tinderc.Model.Users;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPageActivity extends AppCompatActivity {


    private TextView email,notfoundtext;
    private ImageView notfoundimage;
    private ImageButton logout;
    private FirebaseAuth mAuth;
    private long backPressedtime;
    private Window w;
    BottomNavigationView bottomNavigationView;
    public CircleImageView circularimage;


    NotificationManagerCompat notificationCompat;
    Notification notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initialize();


        MainFragment mainFragment = new MainFragment();

        bottomNavigationView.setSelectedItemId(R.id.fraghome);
        if(mAuth!=null)
        {
            usersexfind();

        }


        notific(mAuth.getCurrentUser().getEmail());
        loadFragment(mainFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment=null;
                switch(item.getItemId())
                {
                    case R.id.fraghome:

                        fragment =new MainFragment();
                        break;

                    case R.id.fragmsg:

                        fragment = new MessageFragment();

                        break;

                    case R.id.fragaacount:

                        fragment = new AccountFragment();

                        break;

                }

                loadFragment(fragment);
                return true;



            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(MainPageActivity.this,SignInActivity.class);

                String userid = mAuth.getCurrentUser().getUid();
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm::ss a");
                String date = df.format(Calendar.getInstance().getTime());

                Logoutd userl = new Logoutd(mAuth.getCurrentUser().getEmail(),date);


                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Logout");

                dataref.setValue(userl);
                mAuth.signOut();
                email.setText("");
                Toast.makeText(MainPageActivity.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                startActivity(b);
                finish();
            }
        });

    }

    Users oneuser;
    public void usersexfind()
    {

        final DatabaseReference maleref = FirebaseDatabase.getInstance().getReference().child("Users").child("male");
        maleref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {


                Log.d("UserKey:", snapshot.getKey());
                if (snapshot.getKey().equals(mAuth.getUid())) {
                    System.out.println(snapshot.getKey().equals(mAuth.getUid()));
                    String nam = snapshot.child("Users").child("name").getValue().toString();
                    String sntime = snapshot.child("Users").child("signupdatetime").getValue().toString();
                    String emai = mAuth.getCurrentUser().getEmail();
                    String imgur = snapshot.child("Users").child("imageurl").getValue().toString();
                    String bi = snapshot.child("Users").child("bio").getValue().toString();
                    String intr1 = snapshot.child("Users").child("intr1").getValue().toString();
                    String intr2 = snapshot.child("Users").child("intr2").getValue().toString();
                    String intr3 = snapshot.child("Users").child("intr3").getValue().toString();
                    String intr4 = snapshot.child("Users").child("intr4").getValue().toString();
                    String intr5 = snapshot.child("Users").child("intr5").getValue().toString();



                    oneuser = new Users(nam, sntime,imgur,bi,"","","","","" );

                    Picasso.get().load(oneuser.getImageurl()).fit().into(circularimage);

                    email.setText(oneuser.getName());
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final DatabaseReference femaleref = FirebaseDatabase.getInstance().getReference().child("Users").child("female");

        femaleref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals(mAuth.getUid()))
                {
                    System.out.println(snapshot.getKey().equals(mAuth.getUid()));
                    String nam = snapshot.child("Users").child("name").getValue().toString();
                    String sntime = snapshot.child("Users").child("signupdatetime").getValue().toString();
                    String emai = mAuth.getCurrentUser().getEmail();
                    String imgur = snapshot.child("Users").child("imageurl").getValue().toString();
                    String bi = snapshot.child("Users").child("bio").getValue().toString();
                    String intr1 = snapshot.child("Users").child("intr1").getValue().toString();
                    String intr2 = snapshot.child("Users").child("intr2").getValue().toString();
                    String intr3 = snapshot.child("Users").child("intr3").getValue().toString();
                    String intr4 = snapshot.child("Users").child("intr4").getValue().toString();
                    String intr5 = snapshot.child("Users").child("intr5").getValue().toString();



                    oneuser = new Users(nam, sntime,imgur,bi,"","","","","" );

                    Picasso.get().load(oneuser.getImageurl()).fit().into(circularimage);

                    email.setText(oneuser.getName());

                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }


    private void initialize()
    {

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        bottomNavigationView = findViewById(R.id.bottomnavigationview);
        circularimage = findViewById(R.id.circularimage);

        changestatusbarcolor();


    }

    private void changestatusbarcolor()
    {
        if(Build.VERSION.SDK_INT>=21) {
            w = this.getWindow();

            w.setStatusBarColor(this.getResources().getColor(android.R.color.holo_red_dark));
        }
    }



    private void bckpress()
    {
        if(backPressedtime + 2000 >System.currentTimeMillis())
        {
            super.onBackPressed();
            Intent in = new Intent(Intent.ACTION_MAIN);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in.addCategory(Intent.CATEGORY_HOME);

            String userid = mAuth.getCurrentUser().getUid();
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm::ss a");
            String date = df.format(Calendar.getInstance().getTime());

            Logoutd userl = new Logoutd(mAuth.getCurrentUser().getEmail(),date);


            DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Logout");

            dataref.setValue(userl);
            startActivity(in);
            finish();
            System.exit(0);
        }else
        {
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedtime = System.currentTimeMillis();
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    public void fillimage(String url)
    {
        Picasso.get().load(url).fit().into(circularimage);
    }
    public void notific(String h)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel("Mych","Kaushik", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getBaseContext(). getSystemService(NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Mych").setSmallIcon(R.drawable.tinder).setContentTitle("Welcome Back").setContentText(h).
                setContentTitle("Welcome Back "+h);

        notification = builder.build();

        notificationCompat = NotificationManagerCompat.from(this);

        notificationCompat.notify(1,notification);

    }
    @Override
    public void onBackPressed() {
        if(mAuth!=null)
        {
            bckpress();
        }

    }
}