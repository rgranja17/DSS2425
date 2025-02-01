package AlocadabraLN.Horarios;

import AlocadabraDL.HorarioDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HorarioFacade implements IGestHorarios {
    private final HorarioDAO horarios;

    public HorarioFacade() {
        this.horarios = new HorarioDAO(); // Inicializa o DAO para manipular horários
    }

    public void adicionarHorario(Horario horario) {
        if(!existeHorario(horario.getCodHorario())){
            try {
                horarios.adicionarHorario(horario);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao adicionar horário: " + e.getMessage(), e);
            }
        }
    }

    public void removerHorario(String codHorario) {
        if(existeHorario(codHorario)){
            try {
                horarios.removerHorario(codHorario);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao remover horário: " + e.getMessage(), e);
            }
        }
    }

    public void atualizarHorario(Horario horario) {
        try {
            horarios.atualizarHorario(horario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar horário: " + e.getMessage(), e);
        }
    }

    public Map<String, Horario> getHorarios() {
        try {
            Map<String, Horario> mapHorarios = new HashMap<>();
            for (String codHorario : horarios.getAllHorarios()) {
                Optional<Horario> horario = horarios.getHorario(codHorario);
                horario.ifPresent(h -> mapHorarios.put(codHorario, h));
            }
            return mapHorarios;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Map de horários: " + e.getMessage(), e);
        }
    }

    public Horario consultarHorario(String codHorario) {
        if(existeHorario(codHorario)){
            try {
                return horarios.getHorario(codHorario).orElseThrow(() ->
                        new RuntimeException("Horário com código " + codHorario + " não encontrado."));
            } catch (Exception e) {
                throw new RuntimeException("Erro ao consultar horário: " + e.getMessage(), e);
            }
        }else
            return null;
    }

    public boolean existeHorario(String codHorario) {
        try {
            return horarios.existeHorario(codHorario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar existência do horário: " + e.getMessage(), e);
        }
    }
}
