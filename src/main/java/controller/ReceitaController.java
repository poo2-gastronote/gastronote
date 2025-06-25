package controller;

import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import model.Receita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaController {
    private Connection conn;

    public ReceitaController() {
        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
    }
    
    public int cadastrarReceita(Receita r) throws SQLException {
          String sql = "INSERT INTO receita (nome, temp_preparo, nivel_dific, valor_kcal, porcoes) VALUES (?, ?, ?, ?, ?) RETURNING id";

          try (PreparedStatement stmt = conn.prepareStatement(sql)) {
              stmt.setString(1, r.getNome());
              stmt.setDouble(2, r.getTempPreparo());
              stmt.setInt(3, r.getNivelDific());
              stmt.setInt(4, r.getValorKcal());
              stmt.setInt(5, r.getPorcoes());

              ResultSet rs = stmt.executeQuery();
              if (rs.next()) {
                  return rs.getInt("id");
              } else {
                  throw new SQLException("Falha ao inserir na tabela receita.");
              }
        }
    }
   
    public List<String> listarNomesReceitas(){
        List<String> nomes = new ArrayList<>();
        String sql = "SELECT nome FROM receita ORDER BY nome";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomes.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar nomes das receitas:" + e.getMessage());
        }
        return nomes;
    }
    
    public Receita buscarReceitaPorNome(String nome) throws NomeException, NivelDifException{
        Receita receita = null;
        
        receita = new LancheController().buscarLanchePorNome(nome);
        if (receita != null) return receita;
        
        receita = new RefeicaoController().buscarRefeicaoPorNome(nome);
        if (receita != null) return receita;
        
        receita = new SobremesaController().buscarSobremesaPorNome(nome);
        return receita;   
    }
    
    public boolean excluirReceitaPorNome(String nome) {
        String sql = "DELETE FROM receita WHERE nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir receita: " + e.getMessage());
            return false;
        }
    }
}
