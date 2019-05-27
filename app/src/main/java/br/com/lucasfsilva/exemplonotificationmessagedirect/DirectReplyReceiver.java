package br.com.lucasfsilva.exemplonotificationmessagedirect;

import android.annotation.TargetApi;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class DirectReplyReceiver extends BroadcastReceiver {
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        if (remoteInput != null) {
            CharSequence replyTexto = remoteInput.getCharSequence("key_text_reply");
            Mensagem resposta = new Mensagem(replyTexto, null);
            MainActivity.MENSAGENS.add(resposta);

            MainActivity.enviarNoChannel01Notification(context);
        }
    }
}
