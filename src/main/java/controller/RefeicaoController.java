package controller;

import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import model.Refeicao;
import java.sql.*;

public class RefeicaoController extends ReceitaController{
    private Connection conn;

    public RefeicaoController() {
        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
        } catch (SQLException e) {
            System.out.println("Não foi possível se conectar ao banco de dados.");
            e.printStackTrace();
        }
    }

    public int cadastrar(Refeicao f) {
        String sqlRefeicao = "INSERT INTO refeicao (receita_id, acompanhamento, bebida) VALUES (?, ?, ?)";
        int idReceita = -1;
        
        try{
            conn.setAutoCommit(false);
            
            idReceita = cadastrarReceita(f);
            
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
            try {conn.rollback();} catch (SQLException ex) {}
            return -1;
        } finally {
            try{
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Refeicao buscarRefeicaoPorNome(String nome) throws NivelDifException, NomeException {
        IngredienteController ct = new IngredienteController();
        Refeicao f = null;
        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, "
                + "f.acompanhamento, f.bebida "
                + "FROM receita r"
                + "JOIN refeicao f ON r.id = f.receita_id"
                + "WHERE r.nome = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
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
        } catch (SQLException e){
            System.err.println("Erro ao buscar refeição: " + e.getMessage());
        }
        return f;
    } 
    
    public boolean deletarPorNome(String nomeBusca) {
        String sql = "DELETE FROM refeicao WHERE nome = ?";

        try (Connection conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeBusca);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar refeição: " + e.getMessage());
        }

        return false;
    }
}
