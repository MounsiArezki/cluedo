package client.client.modele.entite.io;

import client.client.vue.cluedoPlateau.CluedoBoard;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Function;

public enum TextStream {



    CLUEDOBOARD(CluedoBoard.class.getResourceAsStream("/txt/board.txt"));

    public final static Function<InputStream, String[]> PARSE = (stream) -> {
        Scanner s = new Scanner(stream).useDelimiter("\\A");
        String rawMap = s.hasNext() ? s.next() : "";
        return rawMap.split("\\r?\\n");
    };

    private InputStream inputStream;

    TextStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
