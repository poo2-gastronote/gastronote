package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnection {
        public static String driverJDBC = "org.postgresql.Driver";
        public static String url = "jdbc:postgresql://localhost:5432/postgres";
        public static String user = "postgres";
        public static String senha = "admin";

        public static void main(String[] args) {
            try {
                System.out.println("Carregando o driver...");
                Class.forName(driverJDBC);
                System.out.println("Driver carregado com sucesso");
            } catch (Exception e) {
                System.out.println("Falha no carregamento");
            }

            try {
                System.out.println("Conectando ao BD...");
                Connection conexao = DriverManager.getConnection(url, user, senha);
                System.out.println("Conexao realizada com sucesso");
            } catch (Exception e) {
                System.out.println("Falha na conexao com o BD");
                e.printStackTrace();
            }
        }
    }
