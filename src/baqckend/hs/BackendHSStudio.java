package baqckend.hs;

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
            pstmt.setString(1, "abc"); // Nombre del producto
            pstmt.setString(2, " altos"); // Descripción del producto
            pstmt.setBinaryStream(3, fis, (int) imagenFile.length()); // Imagen del producto
            pstmt.setFloat(4, 140000); // Precio del producto
            pstmt.setInt(5, 36); // Talla del producto
            pstmt.setString(6, "Azul"); // Color del producto
            pstmt.setString(7, "Mujer"); // Género del producto
            pstmt.executeUpdate();
            
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
