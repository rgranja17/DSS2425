 package AlocadabraDL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import AlocadabraLN.UCs.Turno;
import AlocadabraLN.UCs.Sala;
import java.time.LocalTime;


public class TurnoDAO {


    public void adicionarTurno(Turno turno) throws Exception {
        String query = "INSERT INTO Turno (codTurno, diaDaSemana, horaInicial, horaFinal, duracao, ocupacao, codUC, idSala, limite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, turno.getCodTurno());
            stm.setString(2, turno.getDiaDaSemana());
            stm.setTime(3, Time.valueOf(turno.getHoraInicial()));
            stm.setTime(4, Time.valueOf(turno.getHoraFinal()));
            stm.setString(5, turno.getDuracao());
            stm.setInt(6, turno.getOcupacao());
            stm.setString(7,turno.getCodUC());
            stm.setString(8, turno.getSala().getidSala());
            stm.setInt(9, turno.getLimite());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao adicionar turno: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao adicionar turno: " + e.getMessage(), e);
        }
    }

    public void removerTurno(String codTurno) throws Exception {
        String query = "DELETE FROM Turno WHERE codAluno = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codTurno);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao remover turno: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao remover turno: " + e.getMessage(), e);
        }
    }

    public void atualizarTurno(Turno turno) throws Exception {
        String query = "UPDATE Turno SET diaDaSemana = ?, horaInicial = ?, horaFinal = ?, duracao = ?, ocupacao = ?, codUC = ?, idSala = ?, limite = ? WHERE codTurno = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, turno.getDiaDaSemana());
            stm.setTime(2, Time.valueOf(turno.getHoraInicial()));
            stm.setTime(3, Time.valueOf(turno.getHoraFinal()));
            stm.setString(4, turno.getDuracao());
            stm.setInt(5, turno.getOcupacao());
            stm.setString(6, turno.getCodUC());
            stm.setString(7, turno.getSala().getidSala());
            stm.setInt(8, turno.getLimite());
            stm.setString(9, turno.getCodTurno());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao atualizar turno: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar turno: " + e.getMessage(), e);
        }
    }

    public Optional<Turno> getTurno(String codTurno) throws Exception {
        String query = "SELECT * FROM Turno WHERE codTurno = ?";
        String salaQuery = "SELECT * FROM Sala WHERE idSala = ?";


        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codTurno);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    //Optional<UC> uc = this.ucDAO.getUC(rs.getString("codUC"));

                    LocalTime horaInicial = rs.getTime("horaInicial").toLocalTime();
                    LocalTime horaFinal = rs.getTime("horaFinal").toLocalTime();

                    Turno turno = new Turno(
                            rs.getString("codTurno"),
                            rs.getString("diaDaSemana"),
                            horaInicial,
                            horaFinal,
                            rs.getString("duracao"),
                            rs.getInt("ocupacao"),
                            rs.getString("codUC"),
                            null,
                            rs.getInt("limite")
                    );

                    // Obter o ID da sala e consultar a tabela Sala
                    String nomeSala = rs.getString("idSala");
                    try (PreparedStatement salaStm = DAOconfig.conexao.prepareStatement(salaQuery)) {
                        salaStm.setString(1, nomeSala);
                        try (ResultSet rsSala = salaStm.executeQuery()) {
                            if (rsSala.next()) {
                                Sala sala = new Sala(
                                        rsSala.getString("idSala"), // Supondo que a tabela Sala tem a coluna 'ID'
                                        rsSala.getInt("capacidade") // Supondo que a tabela Sala tem a coluna 'nome'
                                );
                                // Atribui o objeto Sala ao Turno
                                turno.setSala(sala);
                            }
                        }
                    } catch (SQLException e) {
                        throw new Exception("Erro ao obter informações da Sala: " + e.getMessage(), e);
                    }

                    return Optional.of(turno);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter turno: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean existeTurno(String codTurno) throws Exception {
        String query = "SELECT 1 FROM Turno WHERE codTurno = ?";
        String salaQuery = "SELECT * FROM Sala WHERE ID = ?";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codTurno);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar existência do turno: " + e.getMessage(), e);
        }
    }

    public List<Turno> getTurnosDeUC(String codUC) throws Exception {
        String query = "SELECT * FROM Turno WHERE codUC = ?";
        String salaQuery = "SELECT * FROM Sala WHERE idSala = ?";

        List<Turno> turnos = new ArrayList<>();
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codUC);
            try (ResultSet rs = stm.executeQuery()) {

                //Optional<UC> uc = this.ucDAO.getUC(codUC);

                while (rs.next()) {
                    LocalTime horaInicial = rs.getTimestamp("horaInicial").toLocalDateTime().toLocalTime();
                    LocalTime horaFinal = rs.getTimestamp("horaFinal").toLocalDateTime().toLocalTime();
                    String duracao = rs.getString("duracao");

                    Turno turno = new Turno(
                            rs.getString("codTurno"),
                            rs.getString("diaDaSemana"),
                            horaInicial,
                            horaFinal,
                            rs.getString("duracao"),
                            rs.getInt("ocupacao"),
                            rs.getString("codUC"),
                            null,
                            rs.getInt("limite")
                    );
                    // Obter o ID da sala e consultar a tabela Sala
                    String idSala = rs.getString("idSala");
                    try (PreparedStatement salaStm = DAOconfig.conexao.prepareStatement(salaQuery)) {
                        salaStm.setString(1, idSala);
                        try (ResultSet rsSala = salaStm.executeQuery()) {
                            if (rsSala.next()) {
                                Sala sala = new Sala(
                                        rsSala.getString("idSala"), // Supondo que a tabela Sala tem a coluna 'ID'
                                        rsSala.getInt("capacidade") // Supondo que a tabela Sala tem a coluna 'nome'
                                );
                                // Atribui o objeto Sala ao Turno
                                turno.setSala(sala);
                            }
                        }
                    } catch (SQLException e) {
                        throw new Exception("Erro ao obter informações da Sala: " + e.getMessage(), e);
                    }
                    turnos.add(turno);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter turnos: " + e.getMessage(), e);
        }
        return turnos;
    }
}