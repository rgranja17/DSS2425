package AlocadabraLN.Horarios;

import java.util.Map;

public interface IGestHorarios {
    Horario consultarHorario(String codAluno);

    void adicionarHorario(Horario horario);

    void removerHorario(String codAluno);

    void atualizarHorario(Horario h);

    Map<String, Horario> getHorarios();

    boolean existeHorario(String codHorario);
}
