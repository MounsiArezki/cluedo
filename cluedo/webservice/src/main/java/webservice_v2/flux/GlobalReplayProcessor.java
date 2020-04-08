package webservice_v2.flux;

import reactor.core.publisher.ReplayProcessor;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.carte.ICarte;

import java.util.List;
import java.util.Map;

public class GlobalReplayProcessor {

    public static ReplayProcessor<List<String>> testNotifications = ReplayProcessor.create(0, false);

    public static ReplayProcessor<Partie> partieNotification = ReplayProcessor.create(0, false);

    public static ReplayProcessor<Invitation> invitationsNotification = ReplayProcessor.create(0,false);

    public static ReplayProcessor<Map<ICarte, Joueur>> ficheEnqueteNotification = ReplayProcessor.create(0,false);

    public static ReplayProcessor<List<ICarte>> cartesJoueurNotification = ReplayProcessor.create(0,false);
}
