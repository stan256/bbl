package com.github.stan256.springtest.sql;

import java.sql.*;

public class SimplePureNativeSqlConnection {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "testuser", "testuser")){
            // работа с базой данных
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from product");

            while (resultSet.next()){
                String name = resultSet.getString(2);
                int id = resultSet.getInt(1);
                float price = resultSet.getInt(3);
                Date updated = resultSet.getDate(4);
                int categoryId = resultSet.getInt(6);
                System.out.printf("%d %s %f %s %d\n", id, name, price, updated.toString(), categoryId);
            }

        }
    }
}
