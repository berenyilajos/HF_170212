package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Model implements DBInterface, Serializable {

  protected static Connection connection = null;

  public static void connect() throws ClassNotFoundException, SQLException {
    Class.forName(DRIVER);
    connection = DriverManager.getConnection(
            URL, USERNAME, PASSWORD);
  }

  public static void disconnect() {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      // Nothing we can really do
      e.printStackTrace();
    }
  }
}
