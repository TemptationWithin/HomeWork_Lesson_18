package com.homework18;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/world";
    static final String LOGIN = "root";
    static final String PASSWORD = "krendelek123456";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Scanner scanner = new Scanner(System.in);
        String cityName;
        int index;

        try{

            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(DATABASE_URL,LOGIN, PASSWORD);
            System.out.println("Connected database successfully...");

            statement =  connection.createStatement();

            System.out.println("\nPlease select operation by number: \n 1.CREATE \n 2.UPDATE \n 3.READ \n 4.DELETE ");
            index = Integer.parseInt(scanner.nextLine());
            switch (index){
                case (1):

                    /**
                     ************************ CREATE *********************************
                     */

                    System.out.println("\nPlease add info about new city by Enter: " +
                            "\n 1.NAME " +
                            "\n 2.COUNTRY_CODE " +
                            "\n 3.DISTRICT " +
                            "\n 4.POPULATION ");
                    String name = scanner.nextLine();
                    String country_code = scanner.nextLine();
                    String district = scanner.nextLine();
                    int city_population = Integer.parseInt(scanner.nextLine());

                    preparedStatement = connection.prepareStatement("INSERT city (Name, CountryCode, District, Population)" +
                                                                        "VALUES (?, ?, ?, ?)");

                    System.out.println("\n\n Trying to add new entry... ");

                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, country_code);
                    preparedStatement.setString(3, district);
                    preparedStatement.setInt(4, city_population);

                    preparedStatement.executeUpdate();

                    System.out.println("\n entry was added successfully! ");
                    break;

                case (2):

                    /**
                     ************************ UPDATE *********************************
                     */
                    System.out.println("\nEditing modes(select by number): " +
                            "\n 1.SET NAME BY OLD NAME " +
                            "\n 2.SET COUNTRY_CODE BY CITY NAME" +
                            "\n 3.SET DISTRICT BY CITY NAME" +
                            "\n 4.SET POPULATION BY CITY NAME");
                    int updateIndex = Integer.parseInt(scanner.nextLine());

                    switch (updateIndex){

                        case(1):// ----------------------1.SET NAME BY OLD NAME----------------------
                            System.out.println("You have selected the mode: 1.SET NAME BY OLD NAME " +
                                               "\nPlease, enter the name of the city you want to change");
                            cityName = scanner.nextLine();
                            System.out.println("Please, enter the new name of the city you want to change");
                            String newCityName = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("UPDATE city " +
                                                                                "SET Name = ? " +
                                                                                "WHERE Name LIKE ?");

                            System.out.println("\n\n Trying to set entry... ");
                            preparedStatement.setString(1, newCityName);
                            preparedStatement.setString(2, cityName);
                            preparedStatement.executeUpdate();
                            System.out.println("\n entry was set successfully! ");
                            break;

                        case(2)://----------------------2.SET COUNTRY_CODE BY CITY NAME----------------------
                            System.out.println("You have selected the mode: 2.SET COUNTRY_CODE BY CITY NAME " +
                                    "\nPlease, enter the name of the city you want to change");
                            cityName = scanner.nextLine();
                            System.out.println("Please, enter the new country code");
                            String newCountryCode = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("UPDATE city " +
                                                                                "SET CountryCode = ? " +
                                                                                "WHERE Name LIKE ?");

                            System.out.println("\n\n Trying to set entry... ");
                            preparedStatement.setString(1, newCountryCode);
                            preparedStatement.setString(2, cityName);
                            preparedStatement.executeUpdate();
                            System.out.println("\n entry was set successfully! ");
                            break;
                        case(3)://------------------3.SET DISTRICT BY CITY NAME----------------------
                            System.out.println("You have selected the mode: 3.SET DISTRICT BY CITY NAME " +
                                    "\nPlease, enter the name of the city you want to change");
                            cityName = scanner.nextLine();
                            System.out.println("Please, enter the new district code");
                            String newDistrict = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("UPDATE city " +
                                                                                "SET District = ? " +
                                                                                "WHERE Name LIKE ?");

                            System.out.println("\n\n Trying to set entry... ");
                            preparedStatement.setString(1, newDistrict);
                            preparedStatement.setString(2, cityName);
                            preparedStatement.executeUpdate();
                            System.out.println("\n entry was set successfully! ");
                            break;
                        case(4)://------------------4.SET POPULATION BY CITY NAME------------------
                            System.out.println("You have selected the mode: 3.SET DISTRICT BY CITY NAME " +
                                    "\nPlease, enter the name of the city you want to change");
                            cityName = scanner.nextLine();
                            System.out.println("Please, enter the new population of the city " + cityName);
                            String newPopulation = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("UPDATE city " +
                                                                                "SET Population = ? " +
                                                                                "WHERE Name LIKE ?");

                            System.out.println("\n\n Trying to set entry... ");
                            preparedStatement.setString(1, newPopulation);
                            preparedStatement.setString(2, cityName);
                            preparedStatement.executeUpdate();
                            System.out.println("\n entry was set successfully! ");
                            break;
                        default: System.out.println("Unknown index. Please try again");
                        }

                    break;

                case (3):

                    /**
                     ************************ READ *********************************
                     */
                    System.out.println("\nReading modes(select by number): " +
                            "\n 1.READ FULL TABLE: " +
                            "\n 2.READ CITIES AND POPULATION MORE THAN 'X' (ORDERED BY POPULATION) " +
                            "\n 3.READ CITIES BY COUNTRY CODE ");
                    int readIndex =  Integer.parseInt(scanner.nextLine());

                    switch (readIndex){
                        case (1)://------------1.READ FULL TABLE------------------
                            System.out.println("You have selected the mode: 1.READ FULL TABLE: ");
                            String sqlReadAll = "SELECT *" +
                                                "FROM city ";

                            resultSet = statement.executeQuery(sqlReadAll);

                            while (resultSet.next()){
                                String city = resultSet.getString("Name");
                                String countryCode = resultSet.getString("CountryCode");
                                String cityDistrict = resultSet.getString("District");
                                int population = resultSet.getInt("Population");
                                System.out.println("CITY_NAME: " + city + ", "+
                                        " CODE: " + countryCode + ", "+
                                        " DISTRICT " + cityDistrict + ", "+
                                        " POPULATION: " + population + ".");
                            }
                            break;
                        case (2)://------------2.READ CITIES AND POPULATION (ORDERED BY POPULATION)------------------
                            System.out.println("You have selected the mode: 2.READ CITIES AND POPULATION MORE THAN 'X' (ORDERED BY POPULATION): " +
                                                "\nPlease indicate the low limit of the population ");


                            int populationLowLimit = Integer.parseInt(scanner.nextLine());
                            System.out.println("Please indicate the top limit of the population ");
                            int populationTopLimit = Integer.parseInt(scanner.nextLine());

                            preparedStatement = connection.prepareStatement("SELECT Name, Population " +
                                                                                "FROM city " +
                                                                                "WHERE Population BETWEEN ? AND ? " +
                                                                                "ORDER BY Population DESC ");

                            preparedStatement.setInt(1, populationLowLimit);
                            preparedStatement.setInt(2, populationTopLimit);
                            resultSet = preparedStatement.executeQuery();

                            while (resultSet.next()){
                                String city = resultSet.getString("Name");
                                int population = resultSet.getInt("Population");
                                System.out.println("CITY_NAME: " + city + ", "+
                                                   " POPULATION: " + population + ".");
                            }
                            break;

                        case (3)://------------3.READ CITIES BY COUNTRY CODE------------------
                            System.out.println("You have selected the mode: 3.READ CITIES BY COUNTRY CODE: " +
                                    "\nPlease indicate the country code ");

                            String readCountryCode = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("SELECT Name, CountryCode " +
                                                                                "FROM city " +
                                                                                "WHERE CountryCode = ?");

                            preparedStatement.setString(1, readCountryCode);
                            resultSet = preparedStatement.executeQuery();

                            while (resultSet.next()){
                                String city = resultSet.getString("Name");
                                String countryCode = resultSet.getString("CountryCode");
                                System.out.println("CITY_NAME: " + city + ", "+
                                        " COUNTRY_CODE: " + countryCode + ".");
                            }
                            break;
                        default:
                            System.out.println("Unknown index. Please, try again.");
                    }

                    break;

                case(4):
                    /**
                     ************************ DELETE *********************************
                     */
                    System.out.println("\nDeleting modes(select by number): " +
                            "\n 1.DELETE BY NAME: " +
                            "\n 2.DELETE CITIES BY RANGE OF POPULATION  ");

                    int deleteIndex =  Integer.parseInt(scanner.nextLine());
                    switch (deleteIndex){
                        case (1)://------------1.DELETE BY NAME------------------
                            System.out.println("You have selected the mode: 1.DELETE BY NAME: " +
                                    "\nPlease indicate the city name ");
                            String deleteName = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("DELETE FROM city " +
                                                                                "WHERE Name = ?");
                            System.out.println("\n Trying to delete... ");
                            preparedStatement.setString(1, deleteName);
                            System.out.println("\n entry was deleted successfully! ");
                            break;
                        case (2)://------------2.DELETE CITIES BY RANGE OF POPULATION------------------
                            String deleteLowRangePopulation = scanner.nextLine();
                            String deleteTopRangePopulation = scanner.nextLine();

                            preparedStatement = connection.prepareStatement("DELETE FROM city " +
                                                                                "WHERE Population BETWEEN ? AND ?");
                            System.out.println("\n Trying to delete... ");
                            preparedStatement.setString(1, deleteLowRangePopulation);
                            preparedStatement.setString(2, deleteTopRangePopulation);
                            System.out.println("\n entry was deleted successfully! ");
                            break;
                        default: System.out.println("Unknown index. Please, try again");
                    }
                    break;
                default:
                    System.out.println("Unknown operation. Please, try again.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
            finally {
            try {
                System.out.println("\n\n *********  closing resources... ************");

                if(connection != null){
                    connection.close();
                    System.out.println("connection was closed successfully!");
                }
                if(statement != null){
                    statement.close();
                    System.out.println("statement was closed successfully!");
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                    System.out.println("preparedStatement was closed successfully!");
                }
                if(resultSet != null){
                    resultSet.close();
                    System.out.println("resultSet was closed successfully!");
                }
                System.out.println("Scanner was closed successfully!");
                scanner.close();

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

    }
}
