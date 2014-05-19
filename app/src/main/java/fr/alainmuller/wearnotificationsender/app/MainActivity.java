package fr.alainmuller.wearnotificationsender.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Listener du clic sur bouton d'envoi de notification
     * On va envoyer une notif bidon à chaque clic pour tester la connection avec le device Wear
     *
     * @param v le bouton
     */
    public void sendNotif(View v) {
        Toast.makeText(this, "Notif envoyée! =)", Toast.LENGTH_SHORT).show();

        // prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity2.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new Notification.Builder(this)
                .setContentTitle("Essai de notif")
                .setContentText("Essai de notification de GenyMotion vers l'émulateur Wear")
                .setSmallIcon(R.drawable.ic_notif_cloud)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_notif_call, "Appeler", pIntent)
                .addAction(R.drawable.ic_notif_share, "Envoyer", pIntent)
                .addAction(R.drawable.ic_notif_autre, "Sauvegarder", pIntent).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }
}
