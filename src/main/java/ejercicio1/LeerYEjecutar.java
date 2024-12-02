package ejercicio1;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LeerYEjecutar {
    public static void main(String[] args) {
        File file = new File("scriptRepaso.sql");
        // Esto funciona si el fichero no es un fichero binario, si no sería con FileInputStream
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br  = new BufferedReader(fr);
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                sb.append(line);
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("Ha habido un error al leer el fichero");
        }

        String sql = sb.toString();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?allowMultiQueries=true", "root", "practica");
            Statement statement = connection.createStatement();
            String[] sentencias = sql.split(";");
            statement.executeUpdate(sentencias[0] + ";" + sentencias[1] + ";");
            statement.executeUpdate("USE repaso");

            for (int i = 2; i < sentencias.length; i++) {
                String query = sentencias[i].trim();
                if (!query.isEmpty()) {
                    statement.executeUpdate(query + ";");
                }
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
