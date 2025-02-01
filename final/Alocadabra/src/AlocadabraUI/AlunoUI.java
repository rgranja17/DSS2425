package AlocadabraUI;

import AlocadabraLN.Horarios.Horario;
import AlocadabraLN.LNFacade;
import AlocadabraLN.Users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AlunoUI {
    private BufferedReader reader;
    private final LNFacade lnFacade;
    private User userLogged;

    // Códigos ANSI para cores
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";

    public AlunoUI(LNFacade ln, User user) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.lnFacade = ln;
        this.userLogged = user;
    }

    public void execute() throws IOException {
        try {
            int choice;

            do {
                System.out.println(MAGENTA + "\n====================================" + RESET);
                System.out.println("           Menu do Aluno");
                System.out.println(MAGENTA + "====================================" + RESET);
                System.out.println(BLUE + "1." + RESET + " Consultar Horário");
                System.out.println(RED + "0." + RESET + " Sair");
                System.out.print(YELLOW + "Escolha uma opção: " + RESET);
                choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        Horario horario = lnFacade.consultarHorario(userLogged.getCodAluno());
                        if(horario != null){
                            System.out.println(horario.toString());
                        }
                        System.out.println("Prima qualquer tecla para continuar...");
                        reader.readLine();
                        break;
                    case 0:
                        System.out.println("A sair...");
                        break;
                    default:
                        System.out.println(RED + "Opção inválida! Por favor, tente novamente." + RESET);
                }
            } while (choice != 0);

        } catch (IOException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
