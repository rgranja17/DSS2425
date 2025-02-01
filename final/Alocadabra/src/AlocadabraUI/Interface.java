package AlocadabraUI;

import AlocadabraLN.LNFacade;
import AlocadabraLN.Users.User;
import AlocadabraUI.AuthenticationUI;

import java.io.IOException;

public class Interface implements Runnable {
    private final LNFacade lnFacade = new LNFacade();

    public void run() {
        AuthenticationUI auth = new AuthenticationUI(lnFacade);
        User loggedUser = null;
        try {
            loggedUser = auth.authenticate();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        if(loggedUser != null){
            boolean modoOperacao = loggedUser.isAdmin();

            if (modoOperacao) {
                DirecaoUI ui = new DirecaoUI();
                try {
                    ui.execute();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            else{
                try {
                    AlunoUI ui = new AlunoUI(lnFacade, loggedUser);
                    ui.execute();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
