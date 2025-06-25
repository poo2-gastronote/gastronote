package controller;

import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import model.Refeicao;
import java.sql.*;

public class RefeicaoController extends ReceitaController {

    public int cadastrar(Refeicao f) {
        String sqlRefeicao = "INSERT INTO refeicao (receita_id, acompanhamento, bebida) VALUES (?, ?, ?)";
        int idReceita = -1;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
            conn.setAutoCommit(false);

            idReceita = cadastrarReceita(f, conn);

            try (PreparedStatement stmt = conn.prepareStatement(sqlRefeicao)) {
                stmt.setInt(1, idReceita);
                stmt.setString(2, f.getAcompanhamento());
                stmt.setString(3, f.getBebida());
                stmt.executeUpdate();
            }

            conn.commit();
            return idReceita;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar refeição.");
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

    public Refeicao buscarRefeicaoPorNome(String nome) throws NivelDifException, NomeException {
        Refeicao f = null;
        Connection conn = null;
        IngredienteController ct = new IngredienteController();

        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, " +
                     "f.acompanhamento, f.bebida " +
                     "FROM receita r " +
                     "JOIN refeicao f ON r.id = f.receita_id " +
                     "WHERE r.nome = ?";

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    f = new Refeicao();
                    f.setId(rs.getInt("id"));

                    try {
                        f.setNome(rs.getString("nome"));
                    } catch (NomeException e) {
                        System.err.println("Nome inválido do banco: " + e.getMessage());
                        f.setNome("Nome inválido");
                    }

                    try {
                        f.setNivelDific(rs.getInt("nivel_dific"));
                    } catch (NivelDifException e) {
                        System.err.println("Nível de dificuldade inválido do banco: " + e.getMessage());
                        f.setNivelDific(1);
                    }

                    f.setTempPreparo(rs.getDouble("temp_preparo"));
                    f.setValorKcal(rs.getInt("valor_kcal"));
                    f.setPorcoes(rs.getInt("porcoes"));
                    f.setAcompanhamento(rs.getString("acompanhamento"));
                    f.setBebida(rs.getString("bebida"));

                    f.setIngredientes(ct.buscarIngredientePorId(f.getId()));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar refeição: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return f;
    }

    private int cadastrarReceita(Refeicao f, Connection conn) throws SQLException {
        String sql = "INSERT INTO receita (nome, temp_preparo, nivel_dific, valor_kcal, porcoes) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setDouble(2, f.getTempPreparo());
            stmt.setInt(3, f.getNivelDific());
            stmt.setInt(4, f.getValorKcal());
            stmt.setInt(5, f.getPorcoes());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }

        throw new SQLException("Erro ao inserir receita.");
    }
}
