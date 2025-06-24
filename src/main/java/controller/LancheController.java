package controller;
import model.Lanche;
import database.BDConnection;
import exceptions.NivelDifException;
import exceptions.NomeException;
import java.sql.*;

public class LancheController extends ReceitaController{
    private Connection conn;

    public LancheController() {
        try {
            conn = DriverManager.getConnection(BDConnection.url, BDConnection.user, BDConnection.senha);
        } catch (SQLException e) {
            System.out.println("Não foi possível se conectar a classe do Banco");
            e.printStackTrace();
        }
    }

    public int cadastrar(Lanche l) {
        String sqlLanche = "INSERT INTO lanche (receita_id, tipo_lanche, temperatura) VALUES (?, ?, ?)";
        int idReceita = -1;
        
        try{
            conn.setAutoCommit(false);
            
            idReceita = cadastrarReceita(l);
            
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
    
    public Lanche buscarLanchePorNome(String nome) throws NivelDifException, NomeException {
        IngredienteController ct = new IngredienteController();
        Lanche l = null;
        String sql = "SELECT r.id, r.nome, r.temp_preparo, r.nivel_dific, r.valor_kcal, r.porcoes, "
                + "l.tipo_lanche, l.temperatura "
                + "FROM receita r"
                + "JOIN lanche l ON r.id = l.receita_id"
                + "WHERE l.nome = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                l = new Lanche();
                l.setId(rs.getInt("id"));
                try {
                    l.setNome(rs.getString("nome"));
                } catch (NomeException e) {
                    System.err.println("Nome inválido do banco: " + e.getMessage());
                    l.setNome("Nome inválido");
                }
                try {
                    l.setNivelDific(rs.getInt("nivel_dific"));
                } catch (NivelDifException e) {
                    System.err.println("Nível de dificuldade inválido do banco: " + e.getMessage());
                    l.setNivelDific(1);
                }                
                l.setTempPreparo(rs.getDouble("temp_preparo"));
                l.setValorKcal(rs.getInt("valor_kcal"));
                l.setPorcoes(rs.getInt("porcoes"));
                l.setTipoLanche(rs.getString("tipo_lanche"));
                l.setTemperatura(rs.getString("temperatura"));
                
                l.setIngredientes(ct.buscarIngredientePorId(l.getId()));
            }
        } catch (SQLException e){
            System.err.println("Erro ao buscar lanche: " + e.getMessage());
        }
        return l;
    } 
       
}

