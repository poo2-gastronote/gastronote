package controller;

import model.Lanche;
import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import java.sql.*;

public class LancheController extends ReceitaController {

    public int cadastrar(Lanche l) {
        String sqlLanche = "INSERT INTO lanche (receita_id, tipo_lanche, temperatura) VALUES (?, ?, ?)";
        int idReceita = -1;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
            conn.setAutoCommit(false);

            idReceita = cadastrarReceita(l, conn);

            try (PreparedStatement stmt = conn.prepareStatement(sqlLanche)) {
                stmt.setInt(1, idReceita);
                stmt.setString(2, l.getTipoLanche());
                stmt.setString(3, l.getTemperatura());
                stmt.executeUpdate();
            }

            conn.commit();
            return idReceita;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar lanche.");
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

    public Lanche buscarLanchePorNome(String nome) throws NivelDifException, NomeException {
        Lanche l = null;
        Connection conn = null;

        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, " +
                     "l.tipo_lanche, l.temperatura " +
                     "FROM receita r " +
                     "JOIN lanche l ON r.id = l.receita_id " +
                     "WHERE r.nome = ?";

        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nome);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    l = new Lanche();
                    l.setId(rs.getInt("id"));

                    try {
                        l.setNome(rs.getString("nome"));
                    } catch (NomeException e) {
                        System.err.println("Nome inválido vindo do banco: " + e.getMessage());
                        l.setNome("Nome inválido");
                    }

                    try {
                        l.setNivelDific(rs.getInt("nivel_dific"));
                    } catch (NivelDifException e) {
                        System.err.println("Nível de dificuldade inválido vindo do banco: " + e.getMessage());
                        l.setNivelDific(1);
                    }

                    l.setTempPreparo(rs.getDouble("temp_preparo"));
                    l.setValorKcal(rs.getInt("valor_kcal"));
                    l.setPorcoes(rs.getInt("porcoes"));
                    l.setTipoLanche(rs.getString("tipo_lanche"));
                    l.setTemperatura(rs.getString("temperatura"));

                    IngredienteController ct = new IngredienteController();
                    l.setIngredientes(ct.buscarIngredientePorId(l.getId()));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar lanche: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return l;
    }

    private int cadastrarReceita(Lanche l, Connection conn) throws SQLException {
        String sql = "INSERT INTO receita (nome, temp_preparo, nivel_dific, valor_kcal, porcoes) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, l.getNome());
            stmt.setDouble(2, l.getTempPreparo());
            stmt.setInt(3, l.getNivelDific());
            stmt.setInt(4, l.getValorKcal());
            stmt.setInt(5, l.getPorcoes());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("Erro ao inserir receita.");
    }
}
