package br.com.lucasfsilva.exemplonotificationmessagedirect;

public class Mensagem {
    private CharSequence texto;
    private long timestamp;
    private CharSequence remetente;

    public Mensagem(CharSequence texto, CharSequence remetente) {
        this.texto = texto;
        timestamp = System.currentTimeMillis();
        this.remetente = remetente;
    }

    public CharSequence getTexto() {
        return texto;
    }

    public void setTexto(CharSequence texto) {
        this.texto = texto;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public CharSequence getRemetente() {
        return remetente;
    }

    public void setRemetente(CharSequence remetente) {
        this.remetente = remetente;
    }
}
