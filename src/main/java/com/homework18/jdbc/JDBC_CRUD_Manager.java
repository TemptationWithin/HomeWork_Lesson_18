package com.homework18.jdbc;

import java.sql.*;
import java.util.Scanner;


public class JDBC_CRUD_Manager {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "krendelek123456";

    {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database...");

            connection = DriverManager.getConnection(DATABASE_URL, LOGIN, PASSWORD);

        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     *  output of possible functions
     */
    public void printOperationTextInfo(){
        System.out.println("\nPlease select operation by number: \n 1.CREATE \n 2.UPDATE \n 3.READ \n 4.DELETE \n 5.EXIT");
    }

    /**
     *  display of possible functions for the selected СRUD mode
     */
    public void printTextInfoByIndex(int index){
        switch (index){
            case (1):
                System.out.println("\nPlease add info about new city by Enter: " +
                                    "\n 1.NAME " +
                                    "\n 2.COUNTRY_CODE " +
                                    "\n 3.DISTRICT " +
                                    "\n 4.POPULATION " +
                                    "\n (enter 'exit' anywhere to exit)");
                break;
            case (2):
                System.out.println("\nEditing modes(select by number): " +
                                    "\n 1.SET NAME BY OLD NAME " +
                                    "\n 2.SET COUNTRY_CODE BY CITY NAME" +
                                    "\n 3.SET DISTRICT BY CITY NAME" +
                                    "\n 4.SET POPULATION BY CITY NAME");
                break;
            case (3):
                System.out.println("\nReading modes(select by number): " +
                                    "\n 1.READ FULL TABLE: " +
                                    "\n 2.READ CITIES BY POPULATION RANGE (ORDERED BY POPULATION) " +
                                    "\n 3.READ CITIES BY COUNTRY CODE ");
                break;
            case (4):
                System.out.println("\nDeleting modes(select by number): " +
                                    "\n 1.DELETE BY NAME: " +
                                    "\n 2.DELETE CITIES BY RANGE OF POPULATION  " +
                                    "\n 3.DELETE DATABASE BY NAME (BY PASSWORD)");
                break;
            case (5):
                System.out.println("\nExit the program ");
                closingResources();
                break;
            default: System.out.println("Unknown operation. Please, try again.");
        }
    }

    /**
     *  Creating a new record in the City table
     */
    public void createCity(Scanner scanner){
        try {
            while (true) {
                preparedStatement = connection.prepareStatement("INSERT city " +
                                                                    "(Name, CountryCode, District, Population)" +
                                                                    "VALUES (?, ?, ?, ?)");
                    String name = scanner.nextLine();
                    if (isNeedBreak(name)) break;
                    String country_code = scanner.nextLine();
                    if (isNeedBreak(country_code)) break;
                    String district = scanner.nextLine();
                    if (isNeedBreak(district)) break;
                    String string_city_population = scanner.nextLine();
                    if (isNeedBreak(string_city_population)) break;
                    int city_population = Integer.parseInt(string_city_population);
                    System.out.println("\n\n Trying to add new entry... ");

                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, country_code);
                    preparedStatement.setString(3, district);
                    preparedStatement.setInt(4, city_population);
                    preparedStatement.executeUpdate();

                    System.out.println("Entry was added successfully! ");
                }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     *  Reading the entire table City
     */
    public void readFullTable(){
        try {
            System.out.println("You have selected the mode: 1.READ FULL TABLE: ");
            preparedStatement = connection.prepareStatement("SELECT *" +
                                                                "FROM city ");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String city = resultSet.getString("Name");
                String countryCode = resultSet.getString("CountryCode");
                String cityDistrict = resultSet.getString("District");
                int population = resultSet.getInt("Population");
                System.out.println("CITY_NAME: " + city + ", " +
                        " CODE: " + countryCode + ", " +
                        " DISTRICT " + cityDistrict + ", " +
                        " POPULATION: " + population + ".");
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     *  Reading cities by population range from the City table
     */
    public void readByPopulationRange(Scanner scanner){
        try {
            System.out.println("You have selected the mode: 2.READ CITIES BY POPULATION RANGE (ORDERED BY POPULATION): " +
                    "\nPlease indicate the low limit of the population ");


            int lowerLimit = Integer.parseInt(scanner.nextLine());
            System.out.println("Please indicate the upper limit of the population ");
            int upperLimit = Integer.parseInt(scanner.nextLine());
            preparedStatement = connection.prepareStatement("SELECT Name, Population " +
                    "FROM city " +
                    "WHERE Population BETWEEN ? AND ? " +
                    "ORDER BY Population DESC ");

            preparedStatement.setInt(1, lowerLimit);
            preparedStatement.setInt(2, upperLimit);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String city = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.println("CITY_NAME: " + city + ", " +
                        " POPULATION: " + population + ".");
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     *  Reading cities by country code from the City table
     */
    public void readByCountryCode(Scanner scanner){

        try {
            System.out.println("You have selected the mode: 3.READ CITIES BY COUNTRY CODE: " +
                    "\nPlease indicate the country code ");
            String country_code = scanner.nextLine();
            preparedStatement = connection.prepareStatement("SELECT Name, CountryCode " +
                    "FROM city " +
                    "WHERE CountryCode = ?");


            preparedStatement.setString(1, country_code);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String city = resultSet.getString("Name");
                String countryCode = resultSet.getString("CountryCode");
                System.out.println("CITY_NAME: " + city + ", "+
                        " COUNTRY_CODE: " + countryCode + ".");
            }
            } catch (SQLException sqlException){
            sqlException.printStackTrace();
            }
    }

    /**
     *  Changing the city name from the old name in the City table
     */
    public void updateNameByOldName(Scanner scanner){
        try {
            System.out.println("You have selected the mode: 1.SET NAME BY OLD NAME " +
                    "\nPlease, enter the name of the city you want to change");
            String oldCityName = scanner.nextLine();
            System.out.println("Please, enter the new name of the city you want to change");
            String newCityName = scanner.nextLine();
            preparedStatement = connection.prepareStatement("UPDATE city " +
                    "SET Name = ? " +
                    "WHERE Name LIKE ?");

            System.out.println("\n Trying to set entry... ");
            preparedStatement.setString(1, newCityName);
            preparedStatement.setString(2, oldCityName);
            preparedStatement.executeUpdate();
            System.out.println("\n Entry was set successfully! ");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     *  Changing the country code by city name in the City table
     */
    public void updateCountryCodeByCityName(Scanner scanner){
        try {
            System.out.println("You have selected the mode: 2.SET COUNTRY_CODE BY CITY NAME " +
                    "\nPlease, enter the name of the city you want to change");
            String cityName = scanner.nextLine();
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
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Changing a district by city name in the City table
     */
    public void updateDistrictByCityName(Scanner scanner){
        try{
            System.out.println("You have selected the mode: 3.SET DISTRICT BY CITY NAME " +
                    "\nPlease, enter the name of the city you want to change");
            String cityName = scanner.nextLine();
            System.out.println("Please, enter the new district");
            String newDistrict = scanner.nextLine();
            preparedStatement = connection.prepareStatement("UPDATE city " +
                    "SET District = ? " +
                    "WHERE Name LIKE ?");

            System.out.println("\n\n Trying to set entry... ");
            preparedStatement.setString(1, newDistrict);
            preparedStatement.setString(2, cityName);
            preparedStatement.executeUpdate();
            System.out.println("\n entry was set successfully! ");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Population change by city name in the City table
     */
    public void updatePopulationByCityName(Scanner scanner){
        try {
            System.out.println("You have selected the mode: 3.SET DISTRICT BY CITY NAME " +
                    "\nPlease, enter the name of the city you want to change");
            String cityName = scanner.nextLine();
            System.out.println("Please, enter the new population of the city " + cityName);
            int newPopulation = Integer.parseInt(scanner.nextLine());
            preparedStatement = connection.prepareStatement("UPDATE city " +
                                                                "SET Population = ? " +
                                                                "WHERE Name LIKE ?");
            System.out.println("\n\n Trying to set entry... ");
            preparedStatement.setInt(1, newPopulation);
            preparedStatement.setString(2, cityName);
            preparedStatement.executeUpdate();
            System.out.println("\n entry was set successfully! ");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Deleting a city by name
     */
    public void deleteCityByName(Scanner scanner){
        try {
            System.out.println("You have selected the mode: 1.DELETE BY NAME: " +
                    "\nPlease indicate the city name " +
                    "\n (enter 'exit' anywhere to exit));");
            while (true) {
                String cityName = scanner.nextLine();
                if (isNeedBreak(cityName)) break;
                preparedStatement = connection.prepareStatement("DELETE FROM city " +
                        "WHERE Name = ?");
                System.out.println("\n Trying to delete... ");
                preparedStatement.setString(1, cityName);
                System.out.println(" Entry was deleted successfully! ");
                preparedStatement.executeUpdate();
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Removing cities by population range
     */
    public void deleteByPopulationRange(Scanner scanner){
        try{
            System.out.println("You have selected the mode: 2.DELETE CITIES BY RANGE OF POPULATION: " +
                    "\nPlease indicate the low limit of the population ");

            int lowerLimit = Integer.parseInt(scanner.nextLine());
            System.out.println("Please indicate the upper limit of the population ");
            int upperLimit = Integer.parseInt(scanner.nextLine());
            preparedStatement = connection.prepareStatement("DELETE FROM city " +
                    "WHERE Population BETWEEN ? AND ?");
            System.out.println("\n Trying to delete... ");
            preparedStatement.setInt(1, lowerLimit);
            preparedStatement.setInt(2, upperLimit);
            System.out.println("\n entry was deleted successfully! ");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Closing the required resources
     */
    public void closingResources(){
        System.out.println("\n*********  closing resources... ************");
        try {
            if (connection != null){
                connection.close();
            }
            if (statement != null){
                statement.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            System.out.println("Resources was closed successfully!");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Check: is creating dataBase needed
     */
    public void createOrConnectDatabaseTextInfo(){
        System.out.println("\nWelcome to the management system! " +
        "\n To work with the database, please select one of the following options (by number): " +
                "\n 1.I want to create a new database. " +
                "(The creation of a new database is accompanied by the creation of a new City table " +
                "\n 2.I already have a database.");
    }

    /**
     * Choose: Create or Connect to dataBase
     */
    public void createOrConnectDatabase(int response, Scanner scanner){
        String DATABASE_URL = "jdbc:mysql://localhost:3306/";
        System.out.println("Please, enter the name of database.");
        String dataBaseName = scanner.nextLine();
        DATABASE_URL += dataBaseName;
        try {
            switch (response) {
                case (1):     // -----------CREATE NEW DATABASE WITH CITY TABLE --------------------------------------
                    createDatabase(dataBaseName);
                    connection = DriverManager.getConnection(DATABASE_URL, LOGIN, PASSWORD);
                    System.out.println("Connected database successfully...");
                    String sql_createTableCity =
                            "CREATE TABLE city " +
                                    " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                                    " Name VARCHAR(35) not NULL, " +
                                    " CountryCode VARCHAR(3) not NULL, " +
                                    " District VARCHAR(20) not NULL, " +
                                    " Population INT not NULL)";
                    statement = connection.createStatement();
                    statement.executeUpdate(sql_createTableCity);
                    System.out.println("For convenient work, an empty city table was created...");
                    break; // break case (1)
                case (2):     // ------------CONNECT TO DATABASE------------------------------------
                    System.out.println("Ok, we will trying connect to the database " + dataBaseName + "...");
                    connection = DriverManager.getConnection(DATABASE_URL, LOGIN, PASSWORD);
                    System.out.println("Connected database successfully...");
                    break;  // break case (2)
                default:
                    System.out.println("You can only answer 'yes' or 'no'. Please try again");
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Creating dataBase - used only inside class
     */
    private void createDatabase(String name){
        System.out.println("Creating a new database...");
        try {
            Statement statement = connection.createStatement();
            String sql_createDataBase = "CREATE DATABASE " + name;
            statement.executeUpdate(sql_createDataBase);
            System.out.println("Creation of a new database was successful!");
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    /**
     * Drop dataBase
     */
    public void deleteDatabase(Scanner scanner){
        System.out.println("You have selected the database drop mode. " +
                "\nPlease enter a password, thereby confirming your access to delete such objects: ");
        String password = scanner.nextLine();
        if (password.equals(PASSWORD)){
            try {
                System.out.println("You have gained access to delete databases. " +
                                    "Please enter the name of the database you want to delete. \nEnter 'exit' to leave ");
                statement = connection.createStatement();
                while (true){
                    String name = scanner.nextLine();
                    if (name.equals("exit")){
                        break;
                    }
                    String sql_dataBaseDelete = "DROP DATABASE " + name;
                    statement.executeUpdate(sql_dataBaseDelete);
                    System.out.println("Database was deleted!");
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else System.out.println("You entered an incorrect password. Please try again.");
    }

    /**
     * Check for equality to the 'exit' value
     */
    private boolean isNeedBreak(String line){
        if (line.equals("exit")){
            System.out.println("You entered 'exit'.");
        return true;
        } else return false;
    }

}
