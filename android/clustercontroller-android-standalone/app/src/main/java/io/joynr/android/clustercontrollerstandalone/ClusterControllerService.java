package io.joynr.android.clustercontrollerstandalone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import io.joynr.runtime.JoynrRuntime;

public class ClusterControllerService extends Service {

    public static final String CHANNEL_ID = "JoynrClusterControllerNotificationsChannel";

    private JoynrRuntime joynrRuntime;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        if (intent != null) {
            final String uriBroker = intent.getStringExtra(MainActivity.EXTRA_BROKER_URI);

            final Notification notification = createNotification(uriBroker);

            startForeground(1, notification);

            joynrRuntime = ClusterController.run(this, uriBroker);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    private Notification createNotification(final String uriBroker) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID,
                                                                               CHANNEL_ID,
                                                                               NotificationManager.IMPORTANCE_DEFAULT);

            final NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

        final Intent notificationIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this,
                                              CHANNEL_ID).setContentTitle(getString(R.string.clustercontrollerservice))
                                                         .setContentText(getString(R.string.clustercontrolleruseduri)
                                                                 + uriBroker)
                                                         .setSmallIcon(R.drawable.ic_closed_caption_black_24dp)
                                                         .setContentIntent(pendingIntent)
                                                         .setOngoing(true)
                                                         .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        joynrRuntime.prepareForShutdown();
        joynrRuntime.shutdown(true);
    }
}
