package AlocadabraUI;

import AlocadabraLN.Alunos.Aluno;
import AlocadabraLN.Horarios.Horario;
import AlocadabraLN.LNFacade;
import AlocadabraLN.UCs.Turno;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

public class DirecaoUI {
    private BufferedReader reader;
    private final LNFacade lnFacade;

    // Códigos ANSI para cores
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    public DirecaoUI() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.lnFacade = new LNFacade();
    }

    public void execute() throws IOException {
        try{
            int choice;
            do {
                System.out.println(RED + "\n====================================" + RESET);
                System.out.println("           Menu da Direção");
                System.out.println(RED + "====================================" + RESET);
                System.out.println(CYAN + "1." + RESET + " Gerar Horários");
                System.out.println(CYAN + "2." + RESET + " Alocar manualmente alunos");
                System.out.println(CYAN + "3." + RESET + " Importar Dados(UCs/Alunos)");
                System.out.println(CYAN + "4." + RESET + " Restringir Turnos");
                System.out.println(CYAN + "5." + RESET + " Consultar horário de Aluno");
                System.out.println(RED + "0." + RESET + " Sair");
                System.out.print(YELLOW + "Escolha uma opção: " + RESET);
                choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        lnFacade.gerarHorarios();
                        break;
                    case 2:
                        System.out.println(CYAN + "Os seguintes alunos ainda não possuem horários:" + RESET);
                        List<Aluno> alunosSemHorario = lnFacade.getAlunosSemHorario();
                        for (Aluno aluno : alunosSemHorario) {
                            System.out.println(CYAN + "Aluno: " + aluno.toString());
                        }

                        System.out.println(CYAN + "\nQue aluno deseja alocar (introduzir codigo de aluno)?" + RESET);
                        String codAluno = reader.readLine();

                        lnFacade.alocarManualmente(codAluno, reader);
                        break;
                    case 3:
                        String currentPath = Paths.get("").toAbsolutePath().toString();
                        System.out.println(RED + "\nDiretoria atual: " + RESET + currentPath + RESET);

                        System.out.println(BLUE + "Introduza o caminho para o ficheiro a ser importado\n" + RESET);
                        String path = reader.readLine();
                        lnFacade.importarDados(path);
                        break;
                    case 4:
                        System.out.println(CYAN + "Introduza a UC a que pertence o/s turno/s a restringir\n" + RESET);
                        String uc = reader.readLine();
                        System.out.println(CYAN + "Introduza o Turno(TP) pretendido (y para mudar todos)\n" + RESET);
                        String turno = reader.readLine();
                        System.out.println(CYAN + "Qual o limite personalizado para o turno?\n" + RESET);
                        int restricao = Integer.parseInt(reader.readLine());

                        List<Turno> turnos = lnFacade.adicionarRestricao(uc, turno, restricao);

                        if(turnos == null) break;

                        for (int i = 0; i < turnos.size(); i++) {
                            System.out.println(CYAN + turnos.get(i).toString());
                        }
                        break;
                    case 5:
                        System.out.println(CYAN + "Introduza o codigo do Aluno desejado" + RESET);
                        String codAluno1 = reader.readLine();
                        Horario horario = lnFacade.consultarHorario(codAluno1);
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
                }
                while (choice != 0);
            } catch (IOException | NumberFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }
