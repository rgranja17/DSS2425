package AlocadabraLN.UCs;

import java.util.Objects;

public class UC {
    private String codUC;
    private String nomeUC;

    public UC(String codUC, String nomeUC) {
        this.codUC = codUC;
        this.nomeUC = nomeUC;
    }

    public String getCodUC() {
        return codUC;
    }

    public void setCodUC(String codUC) {
        this.codUC = codUC;
    }

    public String getNomeUC() {
        return nomeUC;
    }

    public void setNomeUC(String nomeUC) {
        this.nomeUC = nomeUC;
    }

    @Override
    public String toString() {
        return "UC{" +
                "codUC='" + codUC + '\'' +
                ", nomeUC='" + nomeUC + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UC uc)) return false;
        return Objects.equals(getCodUC(), uc.getCodUC()) && Objects.equals(getNomeUC(), uc.getNomeUC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodUC(), getNomeUC());
    }
}