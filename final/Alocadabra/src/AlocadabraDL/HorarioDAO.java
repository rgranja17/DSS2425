package AlocadabraDL;

import AlocadabraLN.Horarios.Horario;
import AlocadabraLN.UCs.Sala;
import AlocadabraLN.UCs.Turno;

import java.sql.*;
import java.util.*;

public class HorarioDAO {

    public void adicionarHorario(Horario horario) throws Exception {
        String query = "INSERT IGNORE INTO Horario (codHorario) VALUES (?)";
        String queryTurnos = "INSERT IGNORE INTO Horario_Turno (codHorario, codTurno) VALUES (?, ?)";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, horario.getCodHorario());
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Falha ao adicionar horário: Nenhuma linha foi afetada.");
            }

            try (PreparedStatement stmTurnos = DAOconfig.conexao.prepareStatement(queryTurnos)) {
                for (Map.Entry<String, List<Turno>> entry : horario.getTurnos().entrySet()) {
                    for (Turno turno : entry.getValue()) {
                        stmTurnos.setString(1, horario.getCodHorario());
                        stmTurnos.setString(2, turno.getCodTurno());
                        stmTurnos.addBatch();
                    }
                }

                int[] rowsBatch = stmTurnos.executeBatch();
                for (int rows : rowsBatch) {
                    if (rows == 0) {
                        throw new Exception("Falha ao adicionar turnos ao horário: Nenhuma linha foi afetada.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao adicionar horário: " + e.getMessage(), e);
        }
    }

    public void removerHorario(String codHorario) throws Exception {
        String queryTurnos = "DELETE FROM Horario_Turno WHERE codHorario = ?";
        String query = "DELETE FROM Horario WHERE codHorario = ?";

        try (PreparedStatement stmTurnos = DAOconfig.conexao.prepareStatement(queryTurnos)) {
            stmTurnos.setString(1, codHorario);
            stmTurnos.executeUpdate();

            try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
                stm.setString(1, codHorario);
                int rowsAffected = stm.executeUpdate();
                if (rowsAffected == 0) {
                    throw new Exception("Falha ao remover horário: Nenhuma linha foi afetada.");
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao remover horário: " + e.getMessage(), e);
        }
    }

    public void atualizarHorario(Horario horario) throws Exception {
        removerHorario(horario.getCodHorario());
        adicionarHorario(horario);
    }

    public Optional<Horario> getHorario(String codHorario) throws Exception {
        String query = "SELECT * FROM Horario WHERE codHorario = ?";
        String queryTurnos = "SELECT * FROM Horario_Turno WHERE codHorario = ?";
        String queryGetTurnos = "SELECT * FROM Turno WHERE codTurno = ?";
        String querySala = "SELECT * FROM Sala WHERE idSala = ?";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codHorario);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Horario horario = new Horario(rs.getString("codHorario"), new HashMap<>());

                    try (PreparedStatement stmTurnos = DAOconfig.conexao.prepareStatement(queryTurnos)) {
                        stmTurnos.setString(1, codHorario);
                        try (ResultSet rsTurnos = stmTurnos.executeQuery()) {
                            while (rsTurnos.next()) {
                                String codTurno = rsTurnos.getString("codTurno");

                                try (PreparedStatement stmGetTurnos = DAOconfig.conexao.prepareStatement(queryGetTurnos)) {
                                    stmGetTurnos.setString(1, codTurno);
                                    try (ResultSet rsGetTurno = stmGetTurnos.executeQuery()) {
                                        if (rsGetTurno.next()) {
                                            Turno turno = new Turno(
                                                    rsGetTurno.getString("codTurno"),
                                                    rsGetTurno.getString("diaDaSemana"),
                                                    rsGetTurno.getTime("horaInicial").toLocalTime(),
                                                    rsGetTurno.getTime("horaFinal").toLocalTime(),
                                                    rsGetTurno.getString("duracao"),
                                                    rsGetTurno.getInt("ocupacao"),
                                                    null,
                                                    null,
                                                    rsGetTurno.getInt("limite")
                                            );

                                            try (PreparedStatement stmGetSala = DAOconfig.conexao.prepareStatement(querySala)){
                                                stmGetSala.setString(1, rsGetTurno.getString("idSala"));
                                                try (ResultSet rsSala = stmGetSala.executeQuery()) {
                                                    if (rsSala.next()) {
                                                        Sala sala = new Sala(
                                                                rsSala.getString("idSala"),
                                                                rsSala.getInt("capacidade")
                                                        );
                                                        turno.setSala(sala);
                                                    }
                                                }
                                            }

                                            horario.getTurnos()
                                                    .computeIfAbsent(turno.getDiaDaSemana(), k -> new ArrayList<>())
                                                    .add(turno);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Optional.of(horario);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter horÃ¡rio: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean existeHorario(String codHorario) throws Exception {
        String query = "SELECT 1 FROM Horario WHERE codHorario = ?";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codHorario);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar existência do horário: " + e.getMessage(), e);
        }
    }

    public List<String> getAllHorarios() throws Exception {
        String query = "SELECT codHorario FROM Horario";
        List<String> codHorarios = new ArrayList<>();

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    codHorarios.add(rs.getString("codHorario"));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter todos os códigos de horários: " + e.getMessage(), e);
        }

        return codHorarios;
    }
}
