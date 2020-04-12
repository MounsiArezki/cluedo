package client.client.listner;

import java.util.EventListener;

public interface Observateur extends EventListener {
    public void modelChanged(ModeleEvent event);
}
