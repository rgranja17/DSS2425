package AlocadabraLN.Alunos;

import java.util.ArrayList;
import java.util.List;

public class Aluno{
    private String codAluno;
    private String nome;
    private String genero;
    private String email;
    private boolean estatuto;
    private String codHorario;
    private List<String> codsUC;

    public Aluno(){
        this.nome = "";
        this.genero = "";
        this.email = "";
        this.codAluno = "";
        this.estatuto = false;
        this.codHorario = "";
        this.codsUC = new ArrayList<>();
    }

    public Aluno(String codAluno, String nome, String genero, String email, boolean estatuto,
                 String codHorario, List<String> codsUC){
        this.nome = nome;
        this.genero = genero;
        this.email = email;
        this.codAluno = codAluno;
        this.estatuto = estatuto;
        this.codHorario = codHorario;
        this.codsUC = new ArrayList<>(codsUC);
    }

    public Aluno(Aluno a){
        this.nome = a.nome;
        this.genero = a.genero;
        this.email = a.email;
        this.codAluno = a.codAluno;
        this.estatuto = a.estatuto;
        this.codHorario = a.codHorario;
        this.codsUC = a.codsUC;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O género não pode ser nulo ou vazio.");
        }
        this.genero = genero;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode ser nulo ou vazio.");
        }
        this.email = email;
    }

    public String getCodigoAluno() {
        return this.codAluno;
    }

    public void setCodigoAluno(String codAluno) {
        if (codAluno == null || codAluno.trim().isEmpty()) {
            throw new IllegalArgumentException("O código de aluno não pode ser nulo ou vazio.");
        }
        this.codAluno = codAluno;
    }

    public boolean getEstatuto() {
        return this.estatuto;
    }

    public void setEstatuto(boolean estatuto) {
        this.estatuto = estatuto;
    }

    public String getCodigoHorario() {
        return this.codHorario;
    }

    public void setCodigoHorario(String codHorario) {
        if (codHorario == null || codHorario.trim().isEmpty()) {
            throw new IllegalArgumentException("O código de horário não pode ser nulo ou vazio.");
        }
        this.codHorario = codHorario;
    }

    public List<String> getCodigosUC() {
        // Retorna uma cópia para evitar acesso direto à lista interna
        return new ArrayList<>(this.codsUC);
    }

    public void setCodigosUC(List<String> codsUC) {
        if (codsUC == null) {
            throw new IllegalArgumentException("A lista de códigos de UC não pode ser nula.");
        }
        // Garante que a lista interna seja uma cópia da passada como parâmetro
        this.codsUC = new ArrayList<>(codsUC);
    }


    @Override
    public String toString() {
        return "Aluno {" +
                "nome='" + getNome() + '\'' +
                ", genero='" + getGenero() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", codAluno='" + getCodigoAluno() + '\'' +
                ", estatuto=" + getEstatuto() +
                ", codHorario='" + getCodigoHorario() + '\'' +
                ", codsUC=" + new ArrayList<>(getCodigosUC()) +
                '}';
    }

    @Override
    public Aluno clone(){
        return new Aluno(this);
    }
}