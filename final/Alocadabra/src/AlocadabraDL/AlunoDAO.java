package AlocadabraDL;

import java.sql.*;
import java.util.*;

import AlocadabraLN.Alunos.Aluno;

public class AlunoDAO {

        public void adicionarAluno(Aluno aluno) throws Exception {
            String codAluno = aluno.getCodigoAluno();
            String nome = aluno.getNome();
            String genero = aluno.getGenero();
            String email = aluno.getEmail();
            boolean estatuto = aluno.getEstatuto();
            String codHorario = aluno.getCodigoHorario();

            String query = "INSERT INTO Aluno (codAluno, nome, genero, email, estatuto, codHorario) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
                stm.setString(1, codAluno);
                stm.setString(2, nome);
                stm.setString(3, genero);
                stm.setString(4, email);
                stm.setBoolean(5, estatuto);
                stm.setString(6, codHorario);

                int rowsAffected = stm.executeUpdate();

                if (rowsAffected == 0) {
                    throw new Exception("Falha ao adicionar aluno: Nenhuma linha foi afetada.");
                }
            } catch (SQLException e) {
                throw new Exception("Erro ao adicionar aluno: " + e.getMessage(), e);
            }
        }

    public void removerAluno(String codAluno) throws Exception {
        String query = "DELETE FROM Aluno WHERE codAluno = ?";
        String queryTurnos = "DELETE FROM Aluno_Uc WHERE codAluno = ?";
        try (PreparedStatement stmTurnos = DAOconfig.conexao.prepareStatement(queryTurnos)) {
            stmTurnos.setString(1, codAluno);
            int rowsAffected = stmTurnos.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao remover Aluno_Uc: Nenhuma linha foi afetada.");
            } else {
                try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
                    stm.setString(1, codAluno);
                    rowsAffected = stm.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new Exception("Falha ao remover aluno: Nenhuma linha foi afetada.");
                    }
                } catch (SQLException e) {
                    throw new Exception("Erro ao remover aluno: " + e.getMessage(), e);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao remover aluno: " + e.getMessage(), e);
        }
    }

    public void atualizarAluno(Aluno aluno) throws Exception {
        String query = "UPDATE Aluno SET nome = ?, genero = ?, email = ?, estatuto = ?, codHorario = ? WHERE codAluno = ?";
        String queryDel = "DELETE FROM Aluno_UC WHERE codAluno = ?";
        String queryUC = "INSERT INTO Aluno_UC(codAluno, codUc) VALUES (?, ?)";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, aluno.getNome());
            stm.setString(2, aluno.getGenero());
            stm.setString(3, aluno.getEmail());
            stm.setBoolean(4, aluno.getEstatuto());
            stm.setString(5, aluno.getCodigoHorario());
            stm.setString(6, aluno.getCodigoAluno());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao atualizar aluno: Nenhuma linha foi afetada.");
            } else {
                try (PreparedStatement stmDel = DAOconfig.conexao.prepareStatement(queryDel)) {
                    stmDel.setString(1, aluno.getCodigoAluno());
                    rowsAffected = stmDel.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new Exception("Falha ao atualizar Aluno_Uc: Nenhuma linha foi afetada.");
                    } else {
                        try (PreparedStatement stmUC = DAOconfig.conexao.prepareStatement(queryUC)) {
                            for (String codUC : aluno.getCodigosUC()) {
                                stmUC.setString(1, aluno.getCodigoAluno());
                                stmUC.setString(2, codUC);

                                stmUC.addBatch();
                            }
                            int[] rowsAffected1 = stmUC.executeBatch();

                            for (int row : rowsAffected1) {
                                if (row == 0) {
                                    throw new Exception("Falha ao atualizar Aluno_Uc: Nenhuma linha foi afetada.");
                                }
                            }
                        } catch (SQLException e) {
                            throw new Exception("Erro ao atualizar aluno: " + e.getMessage(), e);
                        }
                    }
                } catch (SQLException e) {
                    throw new Exception("Erro ao atualizar aluno: " + e.getMessage(), e);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    public String getAlunoCodHorario(String codAluno) throws Exception {
        String queryAluno = "SELECT codHorario FROM Aluno WHERE codAluno = ?";

        try (PreparedStatement stmAluno = DAOconfig.conexao.prepareStatement(queryAluno)) {
            stmAluno.setString(1, codAluno);
            try (ResultSet rsAluno = stmAluno.executeQuery()) {
                if (rsAluno.next()) {
                    // Criação do objeto Aluno
                    String cod = rsAluno.getString("codHorario");

                    return cod;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter aluno: " + e.getMessage(), e);
        }
        return null;
    }

    public Optional<Aluno> getAluno(String codAluno) throws Exception {
        String queryAluno = "SELECT * FROM Aluno WHERE codAluno = ?";
        String queryUCs = "SELECT codUC FROM Aluno_UC WHERE codAluno = ?";

        try (PreparedStatement stmAluno = DAOconfig.conexao.prepareStatement(queryAluno)) {
            stmAluno.setString(1, codAluno);
            try (ResultSet rsAluno = stmAluno.executeQuery()) {
                if (rsAluno.next()) {
                    // Criação do objeto Aluno
                    Aluno aluno = new Aluno(
                            rsAluno.getString("codAluno"),
                            rsAluno.getString("nome"),
                            rsAluno.getString("genero"),
                            rsAluno.getString("email"),
                            rsAluno.getBoolean("estatuto"),
                            rsAluno.getString("codHorario"),
                            new ArrayList<>()
                    );

                    // Agora buscamos as UCs em que o aluno está inscrito
                    try (PreparedStatement stmUCs = DAOconfig.conexao.prepareStatement(queryUCs)) {
                        stmUCs.setString(1, codAluno);
                        try (ResultSet rsUCs = stmUCs.executeQuery()) {
                            while (rsUCs.next()) {
                                aluno.getCodigosUC().add(rsUCs.getString("codUC"));
                            }
                        }
                    }

                    return Optional.of(aluno);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter aluno: " + e.getMessage(), e);
        }

        return Optional.empty();
    }


    public boolean existeAluno(String codAluno) throws Exception {
        String query = "SELECT 1 FROM Aluno WHERE codAluno = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codAluno);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar existência do aluno: " + e.getMessage(), e);
        }
    }

    public Map<String, Aluno> getAlunos() throws Exception {
        Map<String, Aluno> alunos = new HashMap<>();
        String queryAlunos = "SELECT * FROM Aluno";
        String queryUCs = "SELECT codUC FROM Aluno_UC WHERE codAluno = ?";

        try (Statement stmAlunos = DAOconfig.conexao.createStatement();
             ResultSet rsAlunos = stmAlunos.executeQuery(queryAlunos)) {

            while (rsAlunos.next()) {
                String codAluno = rsAlunos.getString("codAluno");

                Aluno aluno = new Aluno(
                        codAluno,
                        rsAlunos.getString("nome"),
                        rsAlunos.getString("genero"),
                        rsAlunos.getString("email"),
                        rsAlunos.getBoolean("estatuto"),
                        rsAlunos.getString("codHorario"),
                        new ArrayList<>()
                );

                try (PreparedStatement stmUCs = DAOconfig.conexao.prepareStatement(queryUCs)) {
                    stmUCs.setString(1, codAluno);
                    ArrayList<String> ucs = new ArrayList<>();
                    try (ResultSet rsUCs = stmUCs.executeQuery()) {
                        while (rsUCs.next()) {
                            ucs.add(rsUCs.getString("codUC"));
                        }
                        aluno.setCodigosUC(ucs);
                    }
                }

                alunos.put(codAluno, aluno);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar todos os alunos: " + e.getMessage(), e);
        }

        return alunos;
    }

    public void adicionarAlunoUC(String codAluno, String codUC) throws Exception {
        String query = "INSERT IGNORE INTO Aluno_UC (codAluno, codUC) VALUES (?, ?)";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codAluno);
            stm.setString(2, codUC);

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Aluno " + codAluno + " já estava matriculado na UC " + codUC);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao associar aluno e UC: " + e.getMessage(), e);
        }
    }

    public List<String> getUCsAluno(String codAluno) throws Exception {
        // Lista para armazenar os resultados
        List<String> codUCs = new ArrayList<>();

        // Declaração da query SQL
        String query = "SELECT codUC FROM Aluno_UC WHERE codAluno = ?";

        // Estabelecendo a conexão (substitua pelo método que usas para obter a conexão)
        try (PreparedStatement pstmt = DAOconfig.conexao.prepareStatement(query)) {
            pstmt.setString(1, codAluno);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    codUCs.add(rs.getString("codUC"));
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao buscar UCs do aluno: " + e.getMessage(), e);
        }

        // Retornar a lista de códigos de UCs
        return codUCs;
    }
}
