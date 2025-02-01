package AlocadabraDL;

import java.sql.*;
import java.util.*;

import AlocadabraLN.UCs.UC;

public class UCDAO{
    public void adicionarUC(UC uc) throws Exception{
        String query = "INSERT INTO Uc (codUC, nomeUC) VALUES (?, ?)";
        try(PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)){
            stm.setString(1, uc.getCodUC());
            stm.setString(2, uc.getNomeUC());
            
            int rowsAffected = stm.executeUpdate();
            if(rowsAffected == 0){
                throw new Exception("Não foi possível adicionar a UC. Nenhuma linha afetada.");
        }
        }catch(SQLException e){
            throw new Exception("Erro ao adicionar a UC: " + e.getMessage(),e);
        }
    }

    public void removerUC(String codUC) throws Exception {
        String query = "DELETE FROM Uc WHERE codUC = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codUC);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao remover UC: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao remover UC: " + e.getMessage(), e);
        }
    }

    public void atualizarUC(UC uc) throws Exception{
        String query = "UPDATE Uc SET nomeUC = ? WHERE codUC = ?";
        try(PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)){
            stm.setString(1, uc.getNomeUC());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Falha ao atualizar UC: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar UC: " + e.getMessage(), e);
        }
    }

    public Optional<UC> getUC(String codUC) throws Exception{
        String query = "SELECT * FROM Uc WHERE codUC = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codUC);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()){
                    UC uc = new UC(rs.getString("codUC"), rs.getString("nomeUC"));
                    return Optional.of(uc);
                }
        }catch(SQLException e){
            throw new Exception("Erro ao obter UC: " + e.getMessage(), e);
        }
        return Optional.empty();
        }
    }

    public String getCodUCByNome(String nome) throws Exception {
        String query = "SELECT codUC FROM Uc WHERE nomeUC = ?";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, nome);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()){
                    return rs.getString("codUC");
                }
            }catch(SQLException e){
                throw new Exception("Erro ao obter UC: " + e.getMessage(), e);
            }
            return null;
        }
    }

    public boolean existeUC(String codUC) throws Exception{
        String query = "SELECT 1 FROM Uc WHERE codUC = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, codUC);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar existência da UC: " + e.getMessage(), e);
        }
    }

    public boolean validaUC(UC uc) throws Exception {
        String query = "SELECT 1 FROM Uc WHERE codUC = ? AND nomeUC = ?";
        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(query)) {
            stm.setString(1, uc.getCodUC());
            stm.setString(2, uc.getNomeUC());
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next(); // Retorna true se os dados do aluno forem válidos
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao validar UC: " + e.getMessage(), e);
        }
    }

    public List<UC> getUCs() throws Exception {
        List<UC> ucs = new ArrayList<>();
        String query = "SELECT * FROM Uc";

        try (Statement stm = DAOconfig.conexao.createStatement();
             ResultSet rs = stm.executeQuery(query)) {

            while (rs.next()) {
                UC uc = new UC(
                        rs.getString("codUC"),
                        rs.getString("nomeUC")
                );
                ucs.add(uc);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao obter todas as UCs: " + e.getMessage(), e);
        }
        return ucs;
    }    
}


