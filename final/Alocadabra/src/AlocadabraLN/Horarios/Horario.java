package AlocadabraLN.Horarios;
import AlocadabraLN.UCs.Turno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Horario {
    private String codHorario;
    private Map<String, List<Turno>> turnos;

    public Horario() {
        this.codHorario = "";
        this.turnos = new HashMap<>();
    }

    public Horario(String codHorario, Map<String, List<Turno>> turnos) {
        this.codHorario = codHorario;
        this.turnos = new HashMap<>(turnos);
    }

    public Horario(Horario h) {
        this.codHorario = h.codHorario;
        this.turnos = new HashMap<>(h.turnos);
    }

    public String getCodHorario() {
        return codHorario;
    }

    public void setCodHorario(String codHorario) {
        if (codHorario == null || codHorario.trim().isEmpty()) {
            throw new IllegalArgumentException("O código de horário não pode ser nulo ou vazio.");
        }
        this.codHorario = codHorario;
    }

    public Map<String, List<Turno>> getTurnos() {
        return turnos;
    }

    public void setTurnos(Map<String, List<Turno>> turnos) {
        this.turnos = turnos;
    }

    public void adicionarTurno(String codTurno, Turno turno) {
        if (codTurno == null || codTurno.trim().isEmpty()) {
            throw new IllegalArgumentException("O código do turno não pode ser nulo ou vazio.");
        }
        if (turno == null) {
            throw new IllegalArgumentException("O turno não pode ser nulo.");
        }
        List<Turno> turnosDia = this.turnos.get(turno.getDiaDaSemana());
        if (turnosDia == null) {
            turnosDia = new ArrayList<>();
        }
        turnosDia.add(turno);
        turnos.put(turno.getDiaDaSemana(), turnosDia);
    }

    public void removerTurno(Turno turno) {
        if (!turnos.get(turno.getDiaDaSemana()).contains(turno)) {
            throw new IllegalArgumentException("O turno com código " + turno.getCodTurno() + " não existe.");
        }
        turnos.remove(turno);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Códigos de escape ANSI para cores
        final String RESET = "\u001B[0m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String RED = "\u001B[31m";

        sb.append(BLUE).append("\nHorário: ").append(CYAN).append(codHorario).append(RESET).append("\n");
        sb.append(YELLOW).append("========================================").append(RESET).append("\n");

        if (turnos.isEmpty()) {
            sb.append(RED).append("Nenhum turno foi adicionado a este horário.").append(RESET).append("\n");
        } else {
            for (String dia : turnos.keySet()) {
                sb.append(GREEN).append("Dia: ").append(YELLOW).append(dia).append(RESET).append("\n");

                List<Turno> turnosDia = turnos.get(dia);
                for (Turno turno : turnosDia) {
                    sb.append("  ").append(CYAN).append("Turno: ").append(RESET).append(turno.getCodTurno()).append("\n");
                    sb.append("    ").append(CYAN).append("Hora: ").append(RESET).append(turno.getHoraInicial())
                            .append(" - ").append(turno.getHoraFinal()).append("\n");
                    sb.append("    ").append(CYAN).append("Sala: ").append(RESET).append(turno.getSala()).append("\n");
                }
                sb.append(YELLOW).append("----------------------------------------").append(RESET).append("\n");
            }
        }

        return sb.toString();
    }


    @Override
    public Horario clone() {
        return new Horario(this);
    }
}