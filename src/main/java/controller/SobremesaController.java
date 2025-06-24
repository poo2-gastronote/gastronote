package controller;
import model.Sobremesa;
import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import java.sql.*;


public class SobremesaController extends ReceitaController{
    private Connection conn;

    public SobremesaController() {
        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
        } catch (SQLException e) {
            System.out.println("Não foi possível se conectar a classe do Banco");
            e.printStackTrace();
        }
    }

    public int cadastrar(Sobremesa s) {
        String sqlSobremesa = "INSERT INTO sobremesa (receita_id, nivel_doce, categoria) VALUES (?, ?, ?)";
        int idReceita = -1;
        
        try{
            conn.setAutoCommit(false);
            
            idReceita = cadastrarReceita(s);
            
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
    
    public Sobremesa buscarSobremesaPorNome(String nome) throws NivelDifException, NomeException {
        IngredienteController ct = new IngredienteController();
        Sobremesa s = null;
        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, "
                + "s.nivel_doce, s.categoria "
                + "FROM receita r"
                + "JOIN sobremesa s ON r.id = s.receita_id"
                + "WHERE s.nome = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                s = new Sobremesa();
                s.setId(rs.getInt("id"));
                try {
                    s.setNome(rs.getString("nome"));
                } catch (NomeException e) {
                    System.err.println("Nome inválido do banco: " + e.getMessage());
                    s.setNome("Nome inválido");
                }
                try {
                    s.setNivelDific(rs.getInt("nivel_dific"));
                } catch (NivelDifException e) {
                    System.err.println("Nível de dificuldade inválido do banco: " + e.getMessage());
                    s.setNivelDific(1);
                }                
                s.setTempPreparo(rs.getDouble("temp_preparo"));
                s.setValorKcal(rs.getInt("valor_kcal"));
                s.setPorcoes(rs.getInt("porcoes"));
                s.setNivelDoce(rs.getInt("nivel_doce"));
                s.setCategoria(rs.getString("categoria"));
                
                s.setIngredientes(ct.buscarIngredientePorId(s.getId()));
            }
        } catch (SQLException e){
            System.err.println("Erro ao buscar sobremesa: " + e.getMessage());
        }
        return s;
    } 

}


