package com.kgstrivers.tinderc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kgstrivers.tinderc.Model.Logind;
import com.kgstrivers.tinderc.Model.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignupImageActivity extends AppCompatActivity {


    String email,pass,name,sex;

    FirebaseAuth mAuth;
    ImageView imageView;

    Button Signup;
    StorageReference storageRef;

    private Uri imguri;
    EditText bio;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data!=null)
        {
            imguri = data.getData();
            imageView.setImageURI(imguri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_image);


        initialize();

        Intent i = getIntent();
        email = i.getStringExtra("email");
        pass = i.getStringExtra("pass");
        name = i.getStringExtra("name");
        sex = i.getStringExtra("sex");



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth != null) {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignupImageActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupImageActivity.this, "Sign Up Error:", Toast.LENGTH_SHORT).show();
                            } else {


                                if(imguri!=null)
                                {

                                    uploadimage(imguri);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Please Select a Picture", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Mauth Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });





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
                       DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, hh:mm::ss a");
                       String date = df.format(Calendar.getInstance().getTime());

                       Users userl = new Users(name, date,peruri.toString(),bio.getText().toString());


                       DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("Users").child(sex.toLowerCase()).child(userid).child("Users");

                       dataref.setValue(userl);

                       Logind userp = new Logind(mAuth.getCurrentUser().getEmail(), date);


                       DatabaseReference dataref1 = FirebaseDatabase.getInstance().getReference().child("Siginandsignout").child(userid).child("Login");

                       dataref1.setValue(userp);

                       Toast.makeText(getApplicationContext(), "Sign up Successful", Toast.LENGTH_SHORT).show();
                       //Toast.makeText(getApplicationContext(),"Image Uploaded Successfully",Toast.LENGTH_SHORT).show();

                       Intent hj = new Intent(SignupImageActivity.this,MainPageActivity.class);
                       startActivity(hj);
                       finish();
                       return;
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
                Toast.makeText(getApplicationContext(),"Image Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getextention(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime  = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void initialize()
    {

        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.signupimage);
        storageRef= FirebaseStorage.getInstance().getReference();

        Signup = findViewById(R.id.signupwhole);
        bio = findViewById(R.id.Bio);
    }
}