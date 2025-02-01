package AlocadabraLN.UCs;

import java.util.List;

public interface IGestUCS {
    void adicionarUC(UC uc);

    void removerUC(String codUC);

    UC getUC(String codUC);

    boolean existeUC(String codUC);

    List<UC> getUCs();

    void adicionarTurno(String codUC, Turno turno);

    String getCodUCByNome(String nome);

    void atualizarTurno(Turno turno);

    void removerTurno(String codTurno);

    Turno getTurno(String codTurno);

    List<Turno> getTurnosDeUC(String codUC);
}
