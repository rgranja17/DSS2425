package AlocadabraLN.UCs;

import java.util.Objects;

public class Sala {
    private String idSala;
    private int capacidade;

    public String getidSala() {
        return idSala;
    }

    public void setidSala(String idSala) {
        this.idSala = idSala;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Sala(String idSala, int capacidade) {
        this.idSala = idSala;
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "idSala='" + idSala + '\'' +
                ", capacidade=" + capacidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sala sala)) return false;
        return getCapacidade() == sala.getCapacidade() && Objects.equals(getidSala(), sala.getidSala());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getidSala(), getCapacidade());
    }
}