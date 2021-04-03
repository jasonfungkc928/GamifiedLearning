package au.edu.unsw.infs3634.gamifiedlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String userUID;

    private String fName;
    private String lName;
    private String email;
    private int currentAvatar;
    private int avatar;
    private int points;

    private ImageView testImage1;
    private TextView userGreeting;

    private TextView avatarsHeader;

    private ImageView wingedShoe;
    private ImageView noAvatar;
    private ImageView cartoonBasketball;
    private ImageView cartoonDog;
    private ImageView cartoonCat;
    private ImageView cartoonLion;
    private ImageView cartoonHedgehog;
    private ImageView cartoonCow;
    private ImageView cartoonFox;
    private ImageView cartoonPig;
    private ImageView cartoonWhale;
    private ImageView cartoonOwl;
    private ImageView cartoonKoala;
    private ImageView pandaHeart;
    private ImageView pandaPaper;
    private ImageView pandaCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //Set Page Title
        setTitle("Change Avatar");

        //Set Default Avatar
        int avatar = R.drawable.no_avatar;

        //Set Default Greeting
        String greeting = "Your Current Avatar";

        testImage1 = findViewById(R.id.testImage1);
        userGreeting = findViewById(R.id.userGreeting);

        avatarsHeader = findViewById(R.id.avatarsHeader);

        wingedShoe = findViewById(R.id.wingedShoe);
        noAvatar = findViewById(R.id.noAvatar);
        cartoonBasketball = findViewById(R.id.cartoonBasketball);
        cartoonDog = findViewById(R.id.cartoonDog);
        cartoonCat = findViewById(R.id.cartoonCat);
        cartoonLion = findViewById(R.id.cartoonLion);
        cartoonHedgehog = findViewById(R.id.cartoonHedgehog);
        cartoonCow = findViewById(R.id.cartoonCow);
        cartoonFox = findViewById(R.id.catoonFox);
        cartoonPig = findViewById(R.id.cartoonPig);
        cartoonWhale = findViewById(R.id.cartoonWhale);
        cartoonOwl = findViewById(R.id.cartoonOwl);
        cartoonKoala = findViewById(R.id.koala);
        pandaHeart = findViewById(R.id.pandaHeart);
        pandaCoffee = findViewById(R.id.pandaCoffee);
        pandaPaper = findViewById(R.id.pandaPaper);

        //Grey Out all Images as Default - except for the avatars that are always available to users
        cartoonCat.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonLion.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonHedgehog.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonCow.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonFox.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonPig.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonWhale.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonOwl.setColorFilter(Color.argb(155, 112, 112, 112));
        cartoonKoala.setColorFilter(Color.argb(155, 112, 112, 112));
        pandaHeart.setColorFilter(Color.argb(155, 112, 112, 112));
        pandaCoffee.setColorFilter(Color.argb(155, 112, 112, 112));
        pandaPaper.setColorFilter(Color.argb(155, 112, 112, 112));

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userUID = user.getUid();

        databaseReference.child(userUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                fName = profile.firstName;
                lName = profile.lastName;
                email = profile.email;
                points = profile.points;
                currentAvatar = profile.avatar;
                if (profile != null){
                    //Get Current Avatar ID
                    int avatar = profile.avatar;
                    //Set Image to Display Current Avatar
                    testImage1.setImageResource(avatar);

                    //Get User's First Name from Database
                    String fName = profile.firstName;
                    //Format User's First Name - Capitalise the First Letter
                    String customGreeting = fName.substring(0, 1).toUpperCase() + fName.substring(1);
                    //Set Customised Greeting
                    userGreeting.setText(customGreeting + "'s Current Avatar \nNumber of Points: " + points);

                    if (points >= 100){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonCat.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonCat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_cat);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonCat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 200){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonLion.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonLion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_lion);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonLion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 300){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonHedgehog.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonHedgehog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_hedgehog);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonHedgehog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 400){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonCow.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonCow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_cow);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonCow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 500){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonFox.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonFox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_fox);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonFox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 600){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonPig.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonPig.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_pig);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonPig.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 700){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonWhale.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonWhale.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_whale);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonWhale.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 800){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonOwl.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonOwl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.cartoon_owl);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonOwl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 900){
                        //Clear Colour Filters for Unlocked Avatars
                        cartoonKoala.clearColorFilter();

                        //Set On Click Listener for Unlocked Avatars - Direct User to Confirmation Page
                        cartoonKoala.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.koala);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        cartoonKoala.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    if (points >= 1000){
                        //Clear Colour Filters for Unlocked Avatars
                        pandaHeart.clearColorFilter();
                        pandaPaper.clearColorFilter();
                        pandaCoffee.clearColorFilter();

                        //Set On Click Listeners for Unlocked Avatars - Direct User to Confirmation Page
                        pandaHeart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.panda_heart);
                            }
                        });

                        pandaCoffee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.panda_coffee);
                            }
                        });

                        pandaPaper.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkImageSelection(R.drawable.panda_paper);
                            }
                        });
                    } else{
                        //Set On Click Listeners for Locked Avatars - Error Message
                        pandaHeart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });

                        pandaCoffee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });

                        pandaPaper.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MyAccount.this, "You Haven't Unlocked this Avatar!!!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //these 4 on click listeners don't need the number of points to inform their onclicklistener as they are always available to users
        wingedShoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImageSelection(R.drawable.logo);
            }
        });

        noAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImageSelection(R.drawable.no_avatar);
            }
        });

        cartoonBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImageSelection(R.drawable.cartoon_basketball);
            }
        });

        cartoonDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImageSelection(R.drawable.cartoon_dog);
            }
        });
    }

    private void checkImageSelection(int imageID){
        Intent intent = new Intent(MyAccount.this, ChangeAvatar.class);
        intent.putExtra("Image ID", imageID);
        startActivity(intent);
    }
}