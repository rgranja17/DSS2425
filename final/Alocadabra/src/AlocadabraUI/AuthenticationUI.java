package AlocadabraUI;

import AlocadabraLN.LNFacade;
import AlocadabraLN.Users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthenticationUI {
    private final LNFacade lnFacade;

    // Color codes
    private static final String COLOR_TITLE = "\033[1;35m";
    private static final String COLOR_OPTION = "\033[1;36m";
    private static final String COLOR_WARNING = "\033[1;31m";
    private static final String COLOR_INPUT = "\033[1;33m";
    private static final String COLOR_SUCCESS = "\033[1;32m";
    private static final String COLOR_RESET = "\033[0m";

    public AuthenticationUI(LNFacade lnFacade) {
        this.lnFacade = lnFacade;
    }

    public User authenticate() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println(COLOR_TITLE + "\n--- Autenticação ---" + COLOR_RESET);
            System.out.println(COLOR_OPTION + "1. Login" + COLOR_RESET);
            System.out.println(COLOR_OPTION + "2. Registar" + COLOR_RESET);
            System.out.println(COLOR_WARNING + "0. Sair" + COLOR_RESET);
            System.out.print(COLOR_INPUT + "Escolha uma opção: " + COLOR_RESET);

            String option = reader.readLine();

            switch (option) {
                case "1":
                    User user = login(reader);
                    if(user != null) {
                        return user;
                    }
                    break;
                case "2":
                    User user2 = register(reader);
                    if(user2 != null) {
                        return user2;
                    }
                    break;
                case "0":
                    System.out.println(COLOR_WARNING + "A sair... Até breve!" + COLOR_RESET);
                    return null;
                default:
                    System.out.println(COLOR_WARNING + "Opção inválida! Por favor, escolha novamente." + COLOR_RESET);
            }
        }
    }

    private User login(BufferedReader reader) throws IOException {
        System.out.println(COLOR_SUCCESS + "\n--- Login ---" + COLOR_RESET);

        System.out.print(COLOR_INPUT + "Introduza o teu email: " + COLOR_RESET);
        String userEmail = reader.readLine();

        System.out.print(COLOR_INPUT + "Introduza a tua password: " + COLOR_RESET);
        String userPassword = reader.readLine();

        User user = lnFacade.login(userEmail, userPassword);

        if (user != null) {
            System.out.println(COLOR_SUCCESS + "Sessão iniciada com sucesso!\nBem-vindo(a), " + user.getCodAluno() + "!" + COLOR_RESET);
            return user;
        } else {
            System.out.println(COLOR_WARNING + "Email ou password incorretos. Tente novamente." + COLOR_RESET);
            return null;
        }
    }

    private User register(BufferedReader reader) throws IOException {
        System.out.println(COLOR_SUCCESS + "\n--- Registrar ---" + COLOR_RESET);

        System.out.print(COLOR_INPUT + "Introduza o email para a nova conta: " + COLOR_RESET);
        String newUserEmail = reader.readLine();

        System.out.print(COLOR_INPUT + "Introduza a tua password para a nova conta: " + COLOR_RESET);
        String newUserPassword = reader.readLine();

        User user = lnFacade.register(newUserEmail, newUserPassword);

        if (user != null) {
            System.out.println(COLOR_SUCCESS + "Conta criada com sucesso!\nBem-vindo(a), " + user.getCodAluno() + "!" + COLOR_RESET);
            return user;
        } else {
            System.out.println(COLOR_WARNING + "Erro ao criar a conta. O email pode já estar em uso. Tente novamente." + COLOR_RESET);
            return null;
        }
    }
}