package org.example.permisosenmarshmallow;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServicioMusica extends Service {
    MediaPlayer reproductor;
    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT).show();
        reproductor = MediaPlayer.create(this, R.raw.audio);
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CANAL_ID, "Mis Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this, CANAL_ID).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Reproducotor").setContentText("Musica sonando");
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());
        Toast.makeText(this, "Servicio arrancado " + idArranque, Toast.LENGTH_SHORT).show();

        PendingIntent intencionPendiente = PendingIntent.getActivity( this, 0, new Intent(this, Main2Activity.class), 0);
        notificacion.setContentIntent(intencionPendiente);
        reproductor.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();
        notificationManager.cancel(NOTIFICACION_ID);
        reproductor.stop();
        reproductor.release();
    }

    @Override
    public IBinder onBind(Intent intencion) {
        return null;
    }
}
