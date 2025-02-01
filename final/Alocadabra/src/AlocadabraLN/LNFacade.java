package AlocadabraLN;
import AlocadabraLN.Alunos.Aluno;
import AlocadabraLN.Horarios.Horario;
import AlocadabraLN.UCs.Turno;
import AlocadabraLN.UCs.UC;
import AlocadabraLN.Users.User;
import AlocadabraLN.Alunos.AlunoFacade;
import AlocadabraLN.Horarios.HorarioFacade;
import AlocadabraLN.UCs.UCFacade;
import AlocadabraLN.Users.UserFacade;

import java.time.LocalTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;


public class LNFacade {
    private final UCFacade ucFacade = new UCFacade();
    private final AlunoFacade alunoFacade = new AlunoFacade();
    private final HorarioFacade horarioFacade = new HorarioFacade();
    private final UserFacade userFacade = new UserFacade();

    public User login(String email, String password){
        return userFacade.login(email, password);
    }

    public User register(String email, String password){
        return userFacade.register(email, password);
    }

    public List<Aluno> getAlunosSemHorario(){
        return alunoFacade.getAlunosSemHorario();
    }

    public void importarDados(String path) {
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Erro: O ficheiro não existe.");
            return;
        }

        String fileName = file.getName().toLowerCase();
        if (!(fileName.endsWith(".csv") || fileName.endsWith(".txt"))) {
            System.out.println("Erro: Apenas ficheiros .csv e .txt são suportados.");
            return;
        }

        Map<String, Aluno> alunosImportados = new HashMap<>();
        Map<String, UC> ucsImportadas = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("A ler o ficheiro: " + file.getName());

            String line;
            boolean headerProcessed = false;

            while ((line = reader.readLine()) != null) {
                if (!headerProcessed) {
                    headerProcessed = true;
                    continue;
                }

                String[] columns = line.split(",");

                if (columns.length < 6 || columns.length > 7) {
                    System.out.println("Linha inválida: " + line);
                    continue;
                }

                String codUC = columns[0].trim();
                String nomeUC = columns[1].trim();
                String codAluno = columns[2].trim();
                String nomeAluno = columns[3].trim();
                String email = columns[4].trim();
                String genero = columns[5].trim();
                boolean estatuto = columns.length == 7;

                UC uc = new UC(codUC, nomeUC);
                Aluno aluno = new Aluno(codAluno, nomeAluno, genero, email, estatuto, null, new ArrayList<>());

                if (!ucsImportadas.containsKey(codUC)) {
                    ucsImportadas.put(codUC, uc);
                    ucFacade.adicionarUC(uc);
                }

                if (!alunosImportados.containsKey(codAluno)) {
                    alunosImportados.put(codAluno, aluno);
                    alunoFacade.adicionarAluno(aluno);
                }

                alunoFacade.adicionarAlunoUC(codAluno, codUC);
            }

            System.out.println("\nAlunos Importados:");
            alunosImportados.values().forEach(System.out::println);

            System.out.println("\nUCs Importadas:");
            ucsImportadas.values().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro durante a importação: " + e.getMessage());
        }
    }

    private boolean isTurnoValido(Turno turno, Horario horario) {
        LocalTime inicioRestrito = LocalTime.of(13, 0);
        LocalTime fimRestrito = LocalTime.of(14, 0);

        LocalTime horaInicial = turno.getHoraInicial();
        LocalTime horaFinal = turno.getHoraFinal();

        if(turno.getOcupacao() == turno.getLimite()){
            return false;
        }

        if ((horaInicial.isBefore(fimRestrito) && horaFinal.isAfter(inicioRestrito))) {
            return false; // Turno cai no intervalo restrito
        }

        String dia = turno.getDiaDaSemana();
        List<Turno> turnosDia = horario.getTurnos().get(dia);

        if(turnosDia != null) {
            for (Turno t : turnosDia) {
                if (!(horaFinal.isBefore(t.getHoraInicial()) && horaInicial.isAfter(t.getHoraFinal()))) {
                    return false; // Turno sobrepõe outro turno existente
                }
            }
        }

        return true;
    }


    public void gerarHorarios() {
        Map<String, Aluno> alunos = this.alunoFacade.getAlunos();
        if (alunos == null) {
            throw new IllegalArgumentException("O mapa de alunos não pode ser nulo.");
        }
        ArrayList<Aluno> alunosSort = (ArrayList<Aluno>) alunos.values().stream()
                .sorted((a1, a2) -> Boolean.compare(a2.getEstatuto(), a1.getEstatuto()))
                .collect(Collectors.toList());

        for (Aluno aluno : alunosSort) {
            String codAluno = aluno.getCodigoAluno();
            String codHorario = 'H' + codAluno.substring(1);
            Horario horario = new Horario(codHorario, new HashMap<>());

            System.out.println("CodAluno: " + codAluno + ", Nome: " + aluno.getNome());

            ArrayList<String> codigosUcs = (ArrayList<String>) aluno.getCodigosUC();

            for (String codigoUc : codigosUcs) {
                ArrayList<Turno> turnosUC = (ArrayList<Turno>) this.ucFacade.getTurnosDeUC(codigoUc);
                ArrayList<Turno> turnosT = new ArrayList<>();
                ArrayList<Turno> turnosP = new ArrayList<>();

                // Dividir turnos por tipo
                for (Turno turno : turnosUC) {
                    if (turno.getCodTurno().startsWith("TP")) {
                        turnosP.add(turno);
                    } else {
                        turnosT.add(turno);
                    }
                }

                //ordenar turnos por capacidade
                turnosT.sort((t1, t2) -> Integer.compare(t1.getOcupacao(), t2.getOcupacao()));
                turnosP.sort((t1, t2) -> Integer.compare(t1.getOcupacao(), t2.getOcupacao()));

                // Adicionar turnos práticos
                for (Turno turno : turnosP) {
                    if (turno.getOcupacao() < turno.getLimite() && isTurnoValido(turno, horario)) {
                        horario.adicionarTurno(turno.getDiaDaSemana(), turno);
                        turno.adicionarOcupacao(1);
                        ucFacade.atualizarTurno(turno);
                        break; // Escolhe apenas um turno prático
                    }
                }

                // Adicionar turnos teóricos
                for (Turno turno : turnosT) {
                    if (turno.getOcupacao() < turno.getLimite() && isTurnoValido(turno, horario)) {
                        horario.adicionarTurno(turno.getDiaDaSemana(), turno);
                        turno.adicionarOcupacao(1);
                        ucFacade.atualizarTurno(turno);
                        break; // Escolhe apenas um turno teórico
                    }
                }
            }

            // Adicionar horário ao sistema
            try {
                if(!horario.getTurnos().isEmpty()){
                    horarioFacade.adicionarHorario(horario);
                    aluno.setCodigoHorario(horario.getCodHorario());
                    alunoFacade.atualizarAluno(aluno);

                    System.out.println(horario.toString());
                    System.out.println("Horário gerado para o aluno " + aluno.getNome() + ": " + horario);
                }else{
                    System.out.println("Não foi possível gerar horário para o aluno " + aluno.getNome() + ": " + horario);
                }
            } catch (Exception e) {
                System.out.println("Erro ao adicionar horário para o aluno " + aluno.getNome() + ": " + e.getMessage());
            }
        }
    }

    public void alocarManualmente(String codAluno, BufferedReader reader) {
        try{
            Aluno aluno = alunoFacade.getAluno(codAluno);
            if(aluno == null){
                System.out.println("Aluno não existe, Introduza um aluno válido ...");
                return;
            }
            List<String> codsUc = alunoFacade.getUCsAluno(codAluno);
            List<UC> ucs = new ArrayList<>();
            Horario horario = new Horario();
            String codHorario = 'H' + codAluno.substring(1);
            horario.setCodHorario(codHorario);

            for (String aux : codsUc) {
                UC uc = ucFacade.getUC(aux);
                ucs.add(uc);
            }
            int flag = 0;

            while (flag == 0) {
                // Mostrar as opções disponíveis ao utilizador
                System.out.println("\n--- Menu de Alocação ---");
                for (int i = 0; i < ucs.size(); i++) {
                    System.out.println((i + 1) + ". " + ucs.get(i).getNomeUC());
                }
                System.out.println("0. Sair (Todo o progresso será perdido)");

                System.out.print("\nEscolha uma UC para alocar (número): ");
                int escolha;

                try {
                    escolha = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida! Por favor insira um número.");
                    continue;
                }

                switch(escolha){
                    case 0:
                        System.out.println("Descartando alterações...");
                        Map<String, List<Turno>> turnosHorario = horario.getTurnos();
                        for(Map.Entry<String, List<Turno>> aux : turnosHorario.entrySet()){
                            for(Turno turno_aux : aux.getValue()){
                                turno_aux.adicionarOcupacao(-1);
                                ucFacade.atualizarTurno(turno_aux);
                            }
                        }
                        flag = 1;
                        break;
                    default :
                        UC uc_aux = ucs.get(escolha - 1);
                        List<Turno> turnosUC = ucFacade.getTurnosDeUC(uc_aux.getCodUC());
                        System.out.println("\n----- Lista de Turnos da UC -----");
                        for(Turno turno_aux : turnosUC){
                            System.out.println(turno_aux.getCodTurno());
                        }
                        System.out.print("Introduz um turno teórico para alocar:\n");
                        String codTurno = reader.readLine();

                        if(codTurno.startsWith("TP")){
                            System.out.println("Turno inválido, introduz um id de Turno Teórico existente");
                            break;
                        }

                        System.out.print("Introduz um turno tp/pl para alocar:\n");
                        String codTurno2 = reader.readLine();

                        if(!codTurno2.startsWith("TP") && !codTurno2.startsWith("PL")){
                            System.out.println("Turno inválido, introduz um id de Turno TP/PL existente");
                            break;
                        }

                        Turno turno = ucFacade.getTurno(codTurno + "-" + uc_aux.getNomeUC());

                        Turno turnoTP = ucFacade.getTurno(codTurno2 + "-" + uc_aux.getNomeUC());

                        if(turno == null){
                            System.out.println("Turno teórico não existe!");
                            break;
                        }else if (turnoTP == null){
                            System.out.println("Turno prático não existe!");
                            break;
                        }

                        if(!isTurnoValido(turno, horario) || !isTurnoValido(turnoTP, horario)){
                            System.out.println("Turno inválido!");
                            break;
                        }

                        turno.adicionarOcupacao(1);
                        horario.adicionarTurno(turno.getDiaDaSemana(), turno);
                        turnoTP.adicionarOcupacao(1);
                        horario.adicionarTurno(turnoTP.getDiaDaSemana(), turnoTP);

                        ucFacade.atualizarTurno(turno);
                        ucFacade.atualizarTurno(turnoTP);

                        ucs.remove(uc_aux);
                        if(ucs.isEmpty()){
                            flag = 1;
                        }
                }
            }
            if(!horario.getTurnos().isEmpty()){
                horarioFacade.adicionarHorario(horario);
                aluno.setCodigoHorario(horario.getCodHorario());
                alunoFacade.atualizarAluno(aluno);

                System.out.println(horario.toString());
                System.out.println("Todas as UCs foram alocadas com sucesso!");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public Horario consultarHorario(String codAluno){
        String cod = alunoFacade.getAlunoCodHorario(codAluno);

        if(cod == null){
            System.out.println("Aluno ainda não possui horário!\n");
            return null;
        }
        return horarioFacade.consultarHorario(cod);
    }

    public List<Turno> adicionarRestricao(String nome, String turno, int restricao) {
        String codUC = ucFacade.getCodUCByNome(nome);
        if(codUC == null){
            System.out.println("Nome de UC inválido!");
            return null;
        }

        if(turno.equals("y")){
            List<Turno> aux = ucFacade.getTurnosDeUC(codUC);

            for(Turno turnoAux : aux){
                turnoAux.setLimite(restricao);
                ucFacade.atualizarTurno(turnoAux);
            }
            return aux;
        }
        List<Turno> turnos = new ArrayList<>();
        String codTurno = turno + "-" + nome;

        Turno turno_aux = ucFacade.getTurno(codTurno);
        if(turno_aux == null){
            System.out.println("Código de turno inválido!");
            return null;
        }

        turno_aux.setLimite(restricao);
        ucFacade.atualizarTurno(turno_aux);

        turnos.add(turno_aux);
        return turnos;
    }
}
