package com.kgstrivers.tinderc.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kgstrivers.tinderc.MainActivity;
import com.kgstrivers.tinderc.MainPageActivity;
import com.kgstrivers.tinderc.Model.Logind;
import com.kgstrivers.tinderc.Model.Users;
import com.kgstrivers.tinderc.R;
import com.kgstrivers.tinderc.SignupImageActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    TextView accountname,email,bio;

    CircleImageView profilepic;


    FirebaseAuth mAuth;

    StorageReference storageRef;
    String nam,em,bi,signuptime,int1,int2,int3,int4,int5,users;

    Uri imguri;

    Button upload;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null)
        {
            imguri = data.getData();
            profilepic.setImageURI(imguri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_account, container, false);

        initialize(view);

        usersexfind();

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(imguri!=null)
                {
                    Toast.makeText(getActivity(), "Picture picked from file explorer", Toast.LENGTH_SHORT).show();
                    uploadimage(imguri);
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Select Image to Update Profile Pic", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }


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



                    Users oneuser = new Users(nam, sntime,imgur,bi,"","","","","" );

                    Picasso.get().load(oneuser.getImageurl()).fit().into(profilepic);

                    accountname.setText(oneuser.getName());

                    bio.setText("Bio:"+oneuser.getBio());

                    email.setText(mAuth.getCurrentUser().getEmail());

                    setallstring(nam,emai,bi,sntime,intr1,intr2,intr3,intr4,intr5,"male");
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
            @SuppressLint("SetTextI18n")
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



                    Users oneuser = new Users(nam, sntime,imgur,bi,"","","","","" );

                    Picasso.get().load(oneuser.getImageurl()).fit().into(profilepic);

                    accountname.setText(oneuser.getName());

                    bio.setText("Bio:"+oneuser.getBio());

                    email.setText(mAuth.getCurrentUser().getEmail());

                    setallstring(nam,emai,bi,sntime,intr1,intr2,intr3,intr4,intr5,"female");

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

    void setallstring(String name,String email,String bioo,String sntime,String i1,String i2,String i3,String i4,String i5,String usersex)
    {
        nam = name;
        em = email;
        bi = bioo;
        signuptime = sntime;
        int1 = i1;
        int2 = i2;
        int3 = i3;
        int4 = i4;
        int5  = i5;
        users = usersex;

    }

    private void uploadimage(Uri uri)
    {
        StorageReference storageReference = storageRef.child(System.currentTimeMillis() + "."+ getextention(uri));

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri peruri) {
                        String userid = mAuth.getCurrentUser().getUid();

                        Users userl = new Users(nam, signuptime,peruri.toString(),bi,int1,int2,int3,int3,int5);


                        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Users").child(users.toLowerCase()).child(userid).child("Users");

                        dataref.setValue(userl);

                        Logind userp = new Logind(mAuth.getCurrentUser().getEmail(), signuptime);


                        DatabaseReference dataref1 = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Login");

                        dataref1.setValue(userp);

                        //Toast.makeText(getApplicationContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();



                        setimg(peruri);



                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(),"Image Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setimg(Uri uri)
    {
        System.out.println("URIIIIIIIIIIIIIIIII Check:"+uri.toString());

        MainPageActivity mainPageActivity = (MainPageActivity) getActivity();

        mainPageActivity.fillimage(uri.toString());
    }
    private String getextention(Uri uri)
    {
        ContentResolver cr =getActivity(). getContentResolver();
        MimeTypeMap mime  = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void initialize(View view)
    {

        accountname = view.findViewById(R.id.accountname);

        bio = view.findViewById(R.id.biodetails);

        mAuth = FirebaseAuth.getInstance();

        profilepic = view.findViewById(R.id.accountcircularimage);

        email = view.findViewById(R.id.emaildetails);

        storageRef= FirebaseStorage.getInstance().getReference();


        upload = view.findViewById(R.id.accountuploadbutton);

    }
}