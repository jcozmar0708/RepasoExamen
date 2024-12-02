package ejercicio3;

import java.sql.*;

public class DatabaseMetaData {
    public static void main(String[] args) {
        // Obtener los metadatos de toda la base de datos
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/repaso", "root", "practica");

            java.sql.DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet resul;

            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();

            System.out.println ("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.println ("-----------------------------------");
            System.out.printf("Nombre : %s %n", nombre);
            System.out.printf("Driver : %s %n", driver);
            System.out.printf("URL    : %s %n", url );
            System.out.printf("Usuario: %s %n", usuario);

            //Obtener información de las tablas y vistas que hay
            resul = dbmd.getTables("repaso", null, null, null);
            while (resul.next()){
                String catalogo = resul.getString(1);//columna 1
                String esquema = resul.getString(2); //columna 2
                String tabla= resul.getString(3);//columna 3
                String tipo= resul.getString(4); //columna 4
                System.out.printf("%s - Catáloqo : %s, Esquema: %s, Nombre: %s %n", tipo, catalogo, esquema, tabla);
            }

            //Información de columnas con el mét-odo
            System.out.println("COLUMNAS TABLA CLIENTES:");
            System.out.println("=============================");
            ResultSet columnas=null;
            columnas = dbmd.getColumns (null, null, "clientes", null);
            while (columnas.next ()) {
                String nombCol = columnas.getString("COLUMN_NAME"); //getString(4)
                String tipoCol = columnas.getString("TYPE_NAME");    //getString(6)
                String tamCol = columnas.getString("COLUMN_SIZE");   //getString(7)
                String nula = columnas.getString("IS_NULLABLE");     //getString(18)
                System.out.printf("Columna: %s, Tipo: %s, Tamaño: %s, ¿Puede ser Nula:? %s %n", nombCol, tipoCol, tamCol, nula);
            }

            //Información PrimaryKeys
            ResultSet pk = dbmd.getPrimaryKeys(null, null, "clientes");
            String pkDep = "", separador ="";
            while(pk.next()){
                pkDep = pkDep + separador + pk.getString("COLUMN_NAME"); //getString(4)
                separador = "+";
            }
            System.out.println("Clave Primaria: "+ pkDep);

            //Información ExportedKeys
            ResultSet fk = dbmd.getExportedKeys (null, null, "clientes") ;
            while(fk.next()) {
                String fk_name = fk.getString("FKCOLUMN_NAME");
                String pk_name = fk.getString("PKCOLUMN_NAME");
                String pk_tablename = fk.getString("PKTABLE_NAME");
                String fk_tablename = fk.getString ("FKTABLE_NAME");
                System.out.printf("Tabla PK: %s, Clave Primaria: %s %n", pk_tablename, pk_name);
                System.out.printf ("Tabla FK: %s, Clave Ajena: %s %n", fk_tablename, fk_name);
            }

            //Información Procedimientos
            ResultSet proc = dbmd.getProcedures(null, null, null);
            while(proc.next()){
                String proc_name = proc.getString("PROCEDURE_NAME");
                String proc_type = proc.getString("PROCEDURE_TYPE");
                System.out.printf("Nombre Procedimiento: %s - Tipo: %s %n", proc_name, proc_type);
            }

            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }
}
