package ejercicio2;

import java.sql.*;
import java.util.Random;

public class Sentencias {
    public static void main(String[] args) {
        // En este ejercicio sólo inserto una nueva orden con PreparedStatement (en este caso no es necesario poner el id_orden ya que se autoincrementa)

        String sql = "SELECT * FROM ordenes";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/repaso?allowMultiQueries=true", "root", "practica");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            Random rand = new Random();
            double total = Math.round(rand.nextDouble(500) + 1);
            String insert = "INSERT INTO ordenes (total) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setDouble(1, total);
            int fila = ps.executeUpdate();

            if (fila > 0) {
                System.out.println("Orden insertada con éxito");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
