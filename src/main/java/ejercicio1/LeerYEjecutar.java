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

        // Una vez leído el fichero lo guardo en la String a ejecutar
        String sql = sb.toString();

        try {
            // Todavía no se mete la base de datos ya que la creo en el script (si ya estuviese creada se pondría jdbc:mysql://localhost:3306/nombreBD)
            // además el usuario tiene que ser el root para poder crear la base de datos (Si se hace así, tendremos que usar siempre el usuario root).
            // Hay que añadir ?allowMultiQueries=true para poder ejecutar múltiples sentencias
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?allowMultiQueries=true", "root", "practica");
            Statement statement = connection.createStatement();

            // Separo las sentencias por ;
            String[] sentencias = sql.split(";");

            // Ejecuto las 2 primeras sentencias añadiéndole el ;
            // De esta manera ya crearíamos la base de datos
            statement.executeUpdate(sentencias[0] + ";" + sentencias[1] + ";");

            // Nos conectamos ahora sí a la base de datos creada
            statement.executeUpdate("USE repaso");

            // Ejecutamos el resto de sentencias
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
