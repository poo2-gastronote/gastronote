package controller;

import model.Sobremesa;
import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import java.sql.*;

public class SobremesaController extends ReceitaController {

    public int cadastrar(Sobremesa s) {
        String sqlSobremesa = "INSERT INTO sobremesa (receita_id, nivel_doce, categoria) VALUES (?, ?, ?)";
        int idReceita = -1;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
            conn.setAutoCommit(false);

            idReceita = cadastrarReceita(s, conn);

            try (PreparedStatement stmt = conn.prepareStatement(sqlSobremesa)) {
                stmt.setInt(1, idReceita);
                stmt.setInt(2, s.getNivelDoce());
                stmt.setString(3, s.getCategoria());
                stmt.executeUpdate();
            }

            conn.commit();
            return idReceita;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar sobremesa.");
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Sobremesa buscarSobremesaPorNome(String nome) throws NivelDifException, NomeException {
        Sobremesa s = null;
        Connection conn = null;

        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, " +
                     "s.nivel_doce, s.categoria " +
                     "FROM receita r " +
                     "JOIN sobremesa s ON r.id = s.receita_id " +
                     "WHERE r.nome = ?";

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    s = new Sobremesa();
                    s.setId(rs.getInt("id"));

                    try {
                        s.setNome(rs.getString("nome"));
                    } catch (NomeException e) {
                        System.err.println("Nome inválido vindo do banco: " + e.getMessage());
                        s.setNome("Nome inválido");
                    }

                    try {
                        s.setNivelDific(rs.getInt("nivel_dific"));
                    } catch (NivelDifException e) {
                        System.err.println("Nível de dificuldade inválido vindo do banco: " + e.getMessage());
                        s.setNivelDific(1);
                    }

                    s.setTempPreparo(rs.getDouble("temp_preparo"));
                    s.setValorKcal(rs.getInt("valor_kcal"));
                    s.setPorcoes(rs.getInt("porcoes"));
                    s.setNivelDoce(rs.getInt("nivel_doce"));
                    s.setCategoria(rs.getString("categoria"));

                    IngredienteController ct = new IngredienteController();
                    s.setIngredientes(ct.buscarIngredientePorId(s.getId()));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar sobremesa: " + e.getMessage());

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return s;
    }

    private int cadastrarReceita(Sobremesa s, Connection conn) throws SQLException {
        String sql = "INSERT INTO receita (nome, temp_preparo, nivel_dific, valor_kcal, porcoes) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getNome());
            stmt.setDouble(2, s.getTempPreparo());
            stmt.setInt(3, s.getNivelDific());
            stmt.setInt(4, s.getValorKcal());
            stmt.setInt(5, s.getPorcoes());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        throw new SQLException("Erro ao inserir receita.");
    }
}



