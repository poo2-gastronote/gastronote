package controller;
import database.BDConnection;
import model.Ingrediente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteController {
    private Connection conn;

    public IngredienteController() {
        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados." + e.getMessage());
        }
    }

    public void inserirIngrediente(Ingrediente ing, int idReceita) {
        String sql = "INSERT INTO ingrediente (nome, quantidade, unid_medida, receita_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ing.getNome());
            stmt.setDouble(2, ing.getQuantidade());
            stmt.setString(3, ing.getUnidMedida());
            stmt.setInt(4, idReceita);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente." + e.getMessage());
        }
    }

    protected ArrayList<Ingrediente> buscarIngredientePorId(int idReceita){
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT nome, quantidade, unid_medida FROM ingrediente WHERE receita_id = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReceita);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Ingrediente ing = new Ingrediente();
                ing.setNome(rs.getString("nome"));
                ing.setQuantidade(rs.getDouble("quantidade"));
                ing.setUnidMedida(rs.getString("unid_media"));
                ingredientes.add(ing);
            }
        } catch (SQLException e){
            System.err.println("Erro ao buscar ingredientes: " + e.getMessage());
        }
        return ingredientes;
    }
    
}
