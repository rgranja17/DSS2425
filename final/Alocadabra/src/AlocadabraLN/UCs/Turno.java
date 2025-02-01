package AlocadabraLN.UCs;

import java.time.LocalTime;

public class Turno {
    private Sala sala;
    private String codTurno;
    private String diaDaSemana;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private String duracao; // Em minutos
    private int ocupacao;
    private String codUC;
    private int limite;

    public Turno() {
        this.sala = null;
        this.codTurno = "";
        this.diaDaSemana = "";
        this.horaInicial = null;
        this.horaFinal = null;
        this.duracao = "";
        this.ocupacao = 0;
        this.codUC = "";
        this.limite = 0;
    }

    public Turno(String codTurno, String diaDaSemana, LocalTime horaInicial, LocalTime horaFinal, String duracao, int ocupacao, String codUC, Sala sala, int limite) {
        this.sala = sala;
        this.codTurno = codTurno;
        this.diaDaSemana = diaDaSemana;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.duracao = duracao;
        this.ocupacao = ocupacao;
        this.codUC = codUC;
        this.limite = limite;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getCodTurno() {
        return this.codTurno;
    }

    public void setCodTurno(String codTurno) {
        this.codTurno = codTurno;
    }

    public String getDiaDaSemana() {
        return this.diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public LocalTime getHoraInicial() {
        return this.horaInicial;
    }

    public void setHoraInicial(LocalTime horaInicial) {
        this.horaInicial = horaInicial;
    }

    public LocalTime getHoraFinal() {
        return this.horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getDuracao() {
        return this.duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public int getOcupacao() {
        return this.ocupacao;
    }

    public void setOcupacao(int ocupacao) {
        this.ocupacao = ocupacao;
    }

    public void adicionarOcupacao(int quantidade) {
        this.ocupacao += quantidade;
    }

    public String getCodUC() {
        return this.codUC;
    }

    public void setCodUC(String codUC) {
        this.codUC = codUC;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    @Override
    public String toString() {
        return "Turno {" +
                "Sala=" + (sala != null ? sala.getidSala() : "Sem sala definida") +
                ", Código='" + codTurno + '\'' +
                ", Dia da Semana='" + diaDaSemana + '\'' +
                ", Hora Inicial=" + horaInicial +
                ", Hora Final=" + horaFinal +
                ", Duração=" + duracao +
                ", Ocupação=" + ocupacao +
                ", codUC=" + codUC +
                ", limite=" + limite +
                '}';
    }
}
