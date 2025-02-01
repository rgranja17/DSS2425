package AlocadabraLN.Alunos;

import java.util.List;
import java.util.Map;

public interface IGestAlunos {
    void adicionarAluno(Aluno a);

    void removerAluno(Aluno a);

    void atualizarAluno(Aluno a);

    Aluno getAluno(String codAluno);

    boolean existeAluno(String codAluno);

    void adicionarAlunoUC(String codAluno, String codUC);

    Map<String, Aluno> getAlunos();

    String getAlunoCodHorario(String codHorario);

    List<Aluno> getAlunosSemHorario();

    List<String> getUCsAluno(String codAluno);
}
