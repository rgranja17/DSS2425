package AlocadabraLN.Alunos;

import AlocadabraDL.AlunoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AlunoFacade implements IGestAlunos {
    private final AlunoDAO alunos;

    public AlunoFacade() {
        this.alunos = new AlunoDAO();
    }

    public void adicionarAluno(Aluno a) {
        try {
            if(!existeAluno(a.getCodigoAluno())){
                alunos.adicionarAluno(a);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerAluno(Aluno a) {
        try {
            alunos.removerAluno(a.getCodigoAluno());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizarAluno(Aluno a) {
        try {
            alunos.atualizarAluno(a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Aluno getAluno(String codAluno) {
        Optional<Aluno> aluno = null;
        try {
            aluno = alunos.getAluno(codAluno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return aluno.orElse(null);
    }

    public boolean existeAluno(String codAluno) {
        try {
            return alunos.existeAluno(codAluno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarAlunoUC(String codAluno, String codUC) {
        try{
            if(existeAluno(codAluno)){
                alunos.adicionarAlunoUC(codAluno, codUC);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Aluno> getAlunos() {
        Map<String, Aluno> alunos = null;
        try {
            alunos = this.alunos.getAlunos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return alunos;
    }

    public List<Aluno> getAlunosSemHorario(){
        Map<String, Aluno> alunos = getAlunos();
        List<Aluno> alunosSemHorario = new ArrayList<>();
        for(Map.Entry<String, Aluno> aluno : alunos.entrySet()){
            if(aluno.getValue().getCodigoHorario() == null){
                alunosSemHorario.add(aluno.getValue());
            }
        }
        return alunosSemHorario;
    }

    public String getAlunoCodHorario(String codAluno){
        try {
            if(existeAluno(codAluno)){
                return alunos.getAlunoCodHorario(codAluno);
            }else
                return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUCsAluno(String codAluno) {
        try {
            if(existeAluno(codAluno)){
                return alunos.getUCsAluno(codAluno);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
