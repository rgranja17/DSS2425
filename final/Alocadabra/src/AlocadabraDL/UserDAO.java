package AlocadabraDL;

import AlocadabraLN.Users.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticate(String email, String password) {
        String queryEmail = "SELECT password, role FROM User WHERE email = ?";

        try (PreparedStatement stm = DAOconfig.conexao.prepareStatement(queryEmail)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Email incorreto!");
                    return null;
                }

                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) {
                    String userType = rs.getString("role");
                    boolean admin = userType.equalsIgnoreCase("Diretor");

                    return new User(email, password, admin);
                } else {
                    System.out.println("Password incorreto!");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
    }


    public User register(String email, String password) {
        String checkEmailQuery = "SELECT 1 FROM User WHERE LOWER(SUBSTRING_INDEX(email, '@', 1)) = LOWER(SUBSTRING_INDEX(?, '@', 1))";
        String checkAlunoQuery = "SELECT 1 FROM Aluno WHERE LOWER(SUBSTRING_INDEX(email, '@', 1)) = LOWER(SUBSTRING_INDEX(?, '@', 1))";
        String insertUserQuery = "INSERT INTO User (email, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement checkAlunoStm = DAOconfig.conexao.prepareStatement(checkAlunoQuery)) {
            checkAlunoStm.setString(1, email.trim());
            try (ResultSet rsAluno = checkAlunoStm.executeQuery()) {
                if (!rsAluno.next()) {
                    System.out.println("Email não pertence a nenhum aluno na plataforma!");
                    return null;
                }
            }

            try (PreparedStatement checkEmailStm = DAOconfig.conexao.prepareStatement(checkEmailQuery)) {
                checkEmailStm.setString(1, email);
                try (ResultSet rs = checkEmailStm.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Email já está registado!");
                        return null; // O email já existe na tabela User
                    }
                }

                try (PreparedStatement insertUserStm = DAOconfig.conexao.prepareStatement(insertUserQuery)) {
                    insertUserStm.setString(1, email);
                    insertUserStm.setString(2, password);
                    insertUserStm.setString(3, "Aluno"); // Supondo que "Aluno" seja o role do usuário
                    int rowsAffected = insertUserStm.executeUpdate();
                    if (rowsAffected > 0) {
                        User user = new User(email, password, false);
                        return user;
                    } else {
                        throw new RuntimeException("Falha ao inserir o usuário no banco de dados.");
                    }
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Erro ao registrar usuário: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao verificar o email na tabela Aluno: " + e.getMessage(), e);
        }
    }
}
