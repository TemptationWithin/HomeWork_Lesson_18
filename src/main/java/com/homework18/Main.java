package com.homework18;

import java.sql.*;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/world";
    static final String LOGIN = "root";
    static final String PASSWORD = "krendelek123456";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{

            Class.forName(JDBC_DRIVER);


            System.out.println(" ");
            System.out.println(" ");
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(DATABASE_URL,LOGIN, PASSWORD);
            System.out.println("Connected database successfully...");

            statement =  connection.createStatement();

            /**
             ************************ CREATE *********************************
            */

            String sqlCreate = "INSERT city (Name, CountryCode, District, Population)" +
                                "VALUES ('Gomel', 'BLR', 'Gomel', 250000)";                         // CREATING GOMEL WITH POPULATION = 250k

            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" trying to add new entry... ");

            statement.executeUpdate(sqlCreate);

            System.out.println(" entry was added successfully! ");


            /**
             ************************ UPDATE *********************************
             */

            String sqlUpdate = "UPDATE city " +                                                     // SETTING POPULATION TO 500001
                                "SET  Population = 500001 WHERE Name LIKE 'Gomel'";

            statement.executeUpdate(sqlUpdate);


            /**
             ************************ READ *********************************
             */

            String sqlRead = "SELECT name, population " +
                            "FROM city " +
                            "WHERE Population > 500000 AND CountryCode = 'BLR'" +               // LOOKING FOR CITY WITH  COUNTRY_CODE = 'BLR' (from Belarus)
                            "ORDER BY Population ";                                             // WITH POPULATION > 500k

            resultSet = statement.executeQuery(sqlRead);

            System.out.println("  ");
            System.out.println("  ");
            System.out.println("*********  UPDATED TABLE: ************");

            while (resultSet.next()){
                String country = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println("City name: " + country +
                        ". Population: " + population);
            }

            /**
             ************************ DELETE *********************************
             */

            String sqlDelete = "DELETE FROM city WHERE Name = 'Gomel'";

            statement.executeUpdate(sqlDelete);

            resultSet = statement.executeQuery(sqlRead);

            System.out.println("  ");
            System.out.println("  ");
            System.out.println("*********  UPDATED TABLE AFTER DELETING: ************");

            while (resultSet.next()){
                String country = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println("City name: " + country +
                        ". Population: " + population);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
            finally {
            try {
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("*********  closing resources... ************");

                if(connection != null){
                    connection.close();
                    System.out.println("connection was closed successfully!");
                }
                if(statement != null){
                    statement.close();
                    System.out.println("statement was closed successfully!");
                }
                if(resultSet != null){
                    resultSet.close();
                    System.out.println("resultSet was closed successfully!");
                }

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

    }
}
