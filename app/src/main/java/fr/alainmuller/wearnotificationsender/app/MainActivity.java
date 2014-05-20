package fr.alainmuller.wearnotificationsender.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
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

        // On peut utiliser un identifiant de notification pour les distinguer lors de l'envoi au NotificationManager
        int notificationId = 001;

        // On crée un l'intent par défaut associé à la notification
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // On peut créer autant d'Intent qu'on souhaite

        // Un Intent de type Dial
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + getString(R.string.phone_number)));
        PendingIntent pDialIntent = PendingIntent.getActivity(this, 0, dialIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Un Intent de type URL
        String url = "http://www.alainmuller.fr";
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(url));
        PendingIntent pWebIntent = PendingIntent.getActivity(this, 0, webIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Un Intent de type Map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(getString(R.string.location)));
        mapIntent.setData(geoUri);
        PendingIntent pMapIntent = PendingIntent.getActivity(this, 0, mapIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Un Intent paramétré
        Intent intentBis = new Intent(this, SecondActivity.class);
        intentBis.putExtra(getString(R.string.param_key), SecondActivity.SAVE);
        PendingIntent pParamIntent = PendingIntent.getActivity(this, 1, intentBis, PendingIntent.FLAG_CANCEL_CURRENT);

        // On peut utiliser un style d'affichage en mode 'big view'
        // afin d'afficher du texte trop long pour tenir dans le setContentText
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(getString(R.string.lipsum));

        // Construction de la notification (support des anciennes versions)
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                // Titre de la notification
                .setContentTitle(getString(R.string.notif_title))
                        // setContentText remplacé par setStyle avec un bigText
//                        .setContentText(getString(R.string.lipsum))
                        // Icône miniature du system tray
                .setSmallIcon(R.drawable.ic_notif_cloud)
                        // Icône large visible quand on ouvre les notifications sur le téléphone et fond de la notif Wear
                        // cf. http://latestimagesphotos.blogspot.fr/2010/12/infrared-landscape-photography-image.html
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.back))
                        // Affichage d'un bigText scrollable sur Wear et dans la description de la notif sur téléphone
                .setStyle(bigStyle)
                        // L'Intent par défaut
                .setContentIntent(pIntent)
                        // Cliquer sur la notif la détruit (non persistente)
                .setAutoCancel(true)
                        // Ajout de diverses actions
                .addAction(R.drawable.ic_notif_call, getString(R.string.notif_action_call), pDialIntent)
                .addAction(R.drawable.ic_notif_net, getString(R.string.notif_action_net), pWebIntent)
                .addAction(R.drawable.ic_notif_nav, getString(R.string.notif_action_nav), pMapIntent)
                .addAction(R.drawable.ic_notif_save, getString(R.string.notif_action_save), pParamIntent);

        // Get an instance of the NotificationManager service (support anciennes versions)
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        // Méthode classique : NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // On crér une WearableNotification pour accéder aux fonctionnalités spécifiques comme setHintHideIcon pour masquer l'icône de l'appli
        Notification notification = new WearableNotifications.Builder(notificationBuilder)
//                .setHintHideIcon(true)
                .build();

        // On construit la notif à la volée
        notificationManager.notify(notificationId, notification);

        Toast.makeText(this, "Notif envoyée! =)", Toast.LENGTH_SHORT).show();
    }
}
