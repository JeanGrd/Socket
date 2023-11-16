package org.socket;

public interface LA {

    boolean creer(String login, String passwd);
    boolean mettreAJour(String login, String passwd);
    boolean supprimer(String login, String passwd);
    boolean tester(String login, String passwd);

}
