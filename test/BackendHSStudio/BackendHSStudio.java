package BackendHSStudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackendHSStudio {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/abc";
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();

            //Agregar producto a la base de datos
            File imagenFile = new File("C:\\Users\\alexa\\OneDrive\\Desktop\\Tennisia\\71VmlL9BDdS._AC_SY500_.jpg"); //  ruta de tu imagen del producto
            FileInputStream fis = new FileInputStream(imagenFile);
            PreparedStatement pstmt = conexion.prepareStatement("INSERT INTO PRODUCTO(NOMBRE, DESCRIPCION, IMAGEN, PRECIO, TALLA, COLOR, GENERO) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, "Sandalias"); // Nombre del producto
            pstmt.setString(2, " con correa ajustable"); // Descripción del producto
            pstmt.setBinaryStream(3, fis, (int) imagenFile.length()); // Imagen del producto
            pstmt.setFloat(4, 140000); // Precio del producto
            pstmt.setInt(5, 36); // Talla del producto
            pstmt.setString(6, "ROSA"); // Color del producto
            pstmt.setString(7, "Mujer"); // Género del producto
            pstmt.executeUpdate();
            
              // Actualización de un producto
            float nuevoPrecio =150000; // Precio actualizado
            int idProductoAActualizar = 13; // Id del producto a actualizar
            String sqlUpdate = "UPDATE PRODUCTO SET PRECIO = ? WHERE Id_Producto = ?";
            PreparedStatement pstmtUpdate = conexion.prepareStatement(sqlUpdate);
            pstmtUpdate.setFloat(1, nuevoPrecio); // Aquí se establece el valor del precio en el primer parámetro
            pstmtUpdate.setInt(2, idProductoAActualizar);
            int filasActualizadas = pstmtUpdate.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("El producto ha sido actualizado correctamente.");
            } else {
                System.out.println("No se ha podido actualizar el producto.");
            }

            
            // Eliminación de un producto
            int idProductoAEliminar = 11; // Id del producto a eliminar
            String sqlDelete = "DELETE FROM PRODUCTO WHERE Id_Producto = ?";
            PreparedStatement pstmtDelete = conexion.prepareStatement(sqlDelete);
            pstmtDelete.setInt(1, idProductoAEliminar);
            int filasEliminadas = pstmtDelete.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("El producto ha sido eliminado correctamente.");
            } else {
                System.out.println("No se ha podido eliminar el producto.");
            }


            
            // Recuperar la imagen de la base de datos
            rs = statement.executeQuery("SELECT * FROM PRODUCTO");
            while (rs.next()) {
                int idProducto = rs.getInt("Id_Producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("Descripcion");
                float precio = rs.getFloat("precio");
                int talla = rs.getInt("talla");
                String color = rs.getString("color");
                String genero = rs.getString("genero");

                // información recuperada, en consola
                System.out.println(idProducto + " : " + nombre + " : " + descripcion + " : " + precio + " : " + talla + " : " + color + " : " + genero);
            }
        } catch (ClassNotFoundException | SQLException | FileNotFoundException ex) {
            Logger.getLogger(BackendHSStudio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BackendHSStudio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
