package AlocadabraLN.UCs;

import AlocadabraDL.UCDAO;
import AlocadabraDL.TurnoDAO;
import AlocadabraLN.Alunos.Aluno;

import java.util.List;
import java.util.Optional;

public class UCFacade implements IGestUCS {
    private final UCDAO ucDao;
    private final TurnoDAO turnoDAO;

    public UCFacade() {
        this.ucDao = new UCDAO();
        this.turnoDAO = new TurnoDAO();
    }

    public void adicionarUC(UC uc) {
        try {
            if(!existeUC(uc.getCodUC())) {
                ucDao.adicionarUC(uc);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerUC(String codUC) {
        try {
            ucDao.removerUC(codUC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UC getUC(String codUC) {
        Optional<UC> uc;
        try {
            uc = ucDao.getUC(codUC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return uc.orElse(null);
    }

    public String getCodUCByNome(String nome) {
        try {
            return ucDao.getCodUCByNome(nome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existeUC(String codUC) {
        try {
            return ucDao.existeUC(codUC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UC> getUCs() {
        try {
            return ucDao.getUCs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarTurno(String codUC, Turno turno) {
        try {
            if (!ucDao.existeUC(codUC)) {
                throw new IllegalArgumentException("A UC com código " + codUC + " não existe.");
            }

            turnoDAO.adicionarTurno(turno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizarTurno(Turno turno) {
        try {
            turnoDAO.atualizarTurno(turno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerTurno(String codTurno) {
        try {
            turnoDAO.removerTurno(codTurno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Turno getTurno(String codTurno) {
        Optional<Turno> turno = null;
        try {
            turno = turnoDAO.getTurno(codTurno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Retorna o turno se existir e null caso contrário
        return turno.orElse(null);
    }

    public List<Turno> getTurnosDeUC(String codUC) {
        try {
            if (!ucDao.existeUC(codUC)) {
                throw new IllegalArgumentException("A UC com código " + codUC + " não existe.");
            }

            return turnoDAO.getTurnosDeUC(codUC);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
