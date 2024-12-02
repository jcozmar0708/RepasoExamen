package ejercicio4;

import java.sql.*;

public class ProcedimientoFuncion {
    public static void main(String[] args) {
        String procedimiento =
                "CREATE PROCEDURE IF NOT EXISTS InsertarCliente(\n" +
                        "    IN p_nombre VARCHAR (100),\n" +
                        "    IN p_apellido VARCHAR (100),\n" +
                        "    IN p_email VARCHAR (150)\n" +
                        ")\n" +
                        "BEGIN\n" +
                        "INSERT INTO clientes (nombre, apellido, email)\n" +
                        "VALUES (p_nombre, p_apellido, p_email);\n" +
                        "END;";

        String funcion =
                "CREATE FUNCTION IF NOT EXISTS ObtenerTotalOrdenes(p_id_cliente INT)\n" +
                        "    RETURNS DECIMAL(10, 2)\n" +
                        "    DETERMINISTIC\n" +
                        "BEGIN\n" +
                        "    DECLARE total DECIMAL(10, 2);\n" +
                        "SELECT IFNULL(SUM(total), 0)\n" +
                        "INTO total\n" +
                        "FROM ordenes\n" +
                        "WHERE id_cliente = p_id_cliente;\n" +
                        "RETURN total;\n" +
                        "END";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/repaso?allowMultiQueries=true", "root", "practica");
            Statement statement = connection.createStatement();

            // Creamos el procedimiento y la función
            statement.execute(procedimiento);
            statement.execute(funcion);

            String callFuncion = "{ ? = call ObtenerTotalOrdenes(?) }";
            String callProcedimiento = "{ call InsertarCliente(?, ?, ?) }";

            // Llamada a la función
            CallableStatement callableStatementFuncion = connection.prepareCall(callFuncion);
            callableStatementFuncion.registerOutParameter(1, Types.DOUBLE);
            callableStatementFuncion.setInt(2, 1);
            callableStatementFuncion.execute();

            double total = callableStatementFuncion.getDouble(1);
            System.out.println("Función : " + total);

            // Llamada al procedimiento
            CallableStatement callableStatementProcedimiento = connection.prepareCall(callProcedimiento);
            callableStatementProcedimiento.setString(1, "José Manuel");
            callableStatementProcedimiento.setString(2, "Cózar Martínez");
            callableStatementProcedimiento.setString(3, "añlsdfkj@gmail.com");
            callableStatementProcedimiento.execute();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
