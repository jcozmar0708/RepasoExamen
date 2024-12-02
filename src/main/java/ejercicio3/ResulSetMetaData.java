package ejercicio3;

import java.sql.*;

public class ResulSetMetaData {
    public static void main(String[] args) {
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/practica", "practica", "practica");

            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery("SELECT * FROM clientes");

            ResultSetMetaData rsmd = rs.getMetaData();

            int nColumnas = rsmd.getColumnCount();
            String nula;
            System.out.printf("Número de columnas recuperadas: %d%n",
                    nColumnas);
            for (int i = 1; i <= nColumnas; i++) {
                System.out.printf("Columna %d: %n", i);
                System.out.printf("Nombre: %s %n Tipo: %s %n ",
                        rsmd.getColumnName(i), rsmd.getColumnTypeName(i));

                if (rsmd.isNullable(i) == 0)
                    nula = "NO";
                else
                    nula = "SI";

                System.out.printf(" Puede ser nula?: %s %n ", nula);
                System.out.printf(" Máximo ancho de la columna: %d %n",
                        rsmd.getColumnDisplaySize(i));
            }

            sentencia.close();
            rs.close();
            conexion.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
