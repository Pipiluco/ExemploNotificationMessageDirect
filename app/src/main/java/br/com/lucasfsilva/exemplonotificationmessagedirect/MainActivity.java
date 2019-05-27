package br.com.lucasfsilva.exemplonotificationmessagedirect;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static br.com.lucasfsilva.exemplonotificationmessagedirect.App.CHANNEL_ID_01;
import static br.com.lucasfsilva.exemplonotificationmessagedirect.App.CHANNEL_ID_02;


public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText edtTitulo, edtMensagem;

    private MediaSessionCompat mediaSessionCompat;

    static List<Mensagem> MENSAGENS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        edtMensagem = (EditText) findViewById(R.id.edtMensagem);

        mediaSessionCompat = new MediaSessionCompat(this, "tag");

        MENSAGENS.add(new Mensagem("bom dia!", "Lucas"));
        MENSAGENS.add(new Mensagem("Olá!", null));
        MENSAGENS.add(new Mensagem("Oi", "Vívia"));
    }

    public void enviarNoChannel01(View view) {
        enviarNoChannel01Notification(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public static void enviarNoChannel01Notification(Context context) {
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply").setLabel("Sua resposta...").build();

        Intent replyIntent;
        PendingIntent replyPendingIntent = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            replyIntent = new Intent(context, DirectReplyReceiver.class);
            replyPendingIntent = PendingIntent.getBroadcast(context, 0, replyIntent, 0);
        } else {
            //start chat activity instead (PendingIntent.getActivity)
            //cancel notification with notificationManagerCompat.cancel(id)
        }

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.ic_reply, "Reply", replyPendingIntent).addRemoteInput(remoteInput).build();

        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("Me");
        messagingStyle.setConversationTitle("Group Chat");

        for (Mensagem chatMensagem : MENSAGENS) {
            NotificationCompat.MessagingStyle.Message message = new NotificationCompat.MessagingStyle.Message(chatMensagem.getTexto(), chatMensagem.getTimestamp(), chatMensagem.getRemetente());
            messagingStyle.addMessage(message);
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_01)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }

    public void enviarNoChannel02(View view) {
        String titulo = edtTitulo.getText().toString();
        String mensagem = edtMensagem.getText().toString();

        Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.agua);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_02)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(titulo)
                .setContentText(mensagem)
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_dislike_01, "Dislike", null)
                .addAction(R.drawable.ic_back_01, "Antes", null)
                .addAction(R.drawable.ic_pause_01, "Pausa", null)
                .addAction(R.drawable.ic_next_01, "Próximo", null)
                .addAction(R.drawable.ic_like_01, "Like", null)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setSubText("Sub texto")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(2, notification);
    }
}

