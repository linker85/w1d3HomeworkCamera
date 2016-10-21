package com.example.com.cameraexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityTAG_";
    private static final int CAMERA_REQUEST = 1888;
    ImageView mimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Image from camera
        mimageView = (ImageView) this.findViewById(R.id.image_from_camera);
        // Button to open camera
        Button button = (Button) this.findViewById(R.id.take_image_from_camera);
    }

    // Call camera from intent
    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    // Get result back from camera
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If photo was taken show it
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            mimageView.setImageBitmap(mphoto);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: App is on the background");
        showNotification();
    }

    public void showNotification() {
        /*
        * Create the title text (ticker text) to display in the status bar when the notification is shown.
        * Use an icon to show in the status bar after the notification goes away.
        * Create a view/text to show in the notification drawer.
        * Create a PendingIntent that is fired when the user taps the notification in the drawer (or on the lock screen).
        * */
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Notification title ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Content title Notification title")
                .setContentText("Content text notification text")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
