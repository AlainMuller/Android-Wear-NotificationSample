package fr.alainmuller.wearnotificationsender.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_activity_main));
    }

    /**
     * Listener du clic sur bouton d'envoi de notification
     * On va envoyer une notif bidon à chaque clic pour tester la connection avec le device Wear
     *
     * @param v le bouton
     */
    public void sendNotif(View v) {
        Toast.makeText(this, "Notif envoyée! =)", Toast.LENGTH_SHORT).show();

        // On crée un Intent permettant de lancer une Activity sur sélection de la notif
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // On peut créer autant d'Intet qu'on souhaite
        String url = "http://www.alainmuller.fr";
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        PendingIntent pWebIntent = PendingIntent.getActivity(this, 0, webIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Construction de la notification
        Notification n = new Notification.Builder(this)
                .setContentTitle("Essai de notif")
                .setContentText("Essai de notification vers l'émulateur Wear")
                .setSmallIcon(R.drawable.ic_notif_cloud)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_notif_call, "Appeler", pIntent)
                .addAction(R.drawable.ic_notif_net, "Site", pWebIntent)
                .addAction(R.drawable.ic_notif_save, "Sauvegarder", pIntent).build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }
}
