package com.homework18;

import com.homework18.jdbc.JDBC_CRUD_Manager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int operationIndex = 0;

        JDBC_CRUD_Manager manager = new JDBC_CRUD_Manager();
        manager.createOrConnectDatabaseTextInfo();
        int response = Integer.parseInt(scanner.nextLine());
        manager.createOrConnectDatabase(response, scanner);

            while (operationIndex != 5) {
                manager.printOperationTextInfo();
                operationIndex = Integer.parseInt(scanner.nextLine());                   // choice of operation
                manager.printTextInfoByIndex(operationIndex);

                switch (operationIndex) {

                    case (1):
                        /**
                         ************************ CREATE *********************************
                         */
                        manager.createCity(scanner);
                        break; //break case(1)

                    case (2):
                        /**
                         ************************ UPDATE *********************************
                         */
                        int updateIndex = Integer.parseInt(scanner.nextLine());

                        switch (updateIndex) {

                            case (1):// ----------------------1.SET NAME BY OLD NAME----------------------
                                manager.updateNameByOldName(scanner);
                                break;  // break case (2->1)

                            case (2)://----------------------2.SET COUNTRY_CODE BY CITY NAME----------------------
                                manager.updateCountryCodeByCityName(scanner);
                                break; // break case (2->2)

                            case (3)://------------------3.SET DISTRICT BY CITY NAME----------------------
                                manager.updateDistrictByCityName(scanner);
                                break; // break case (2->3)

                            case (4)://------------------4.SET POPULATION BY CITY NAME------------------
                                manager.updatePopulationByCityName(scanner);
                                break; // break case (2->4)

                            default:
                                System.out.println("Unknown operationIndex. Please try again");
                        }
                        break; // break case (2)

                    case (3):

                        /**
                         ************************ READ *********************************
                         */
                        int readIndex = Integer.parseInt(scanner.nextLine());

                        switch (readIndex) {
                            case (1)://------------1.READ FULL TABLE------------------
                                manager.readFullTable();
                                break; // break case (3->1)

                            case (2)://------------2.READ CITIES AND POPULATION (ORDERED BY POPULATION)------------------
                                manager.readByPopulationRange(scanner);
                                break; // break case (3->2)

                            case (3)://------------3.READ CITIES BY COUNTRY CODE------------------
                                manager.readByCountryCode(scanner);
                                break; // break case (3->3)

                            default:
                                System.out.println("Unknown operationIndex. Please, try again.");
                        }

                        break; // break case (3)

                    case (4):
                        /**
                         ************************ DELETE *********************************
                         */

                        int deleteIndex = Integer.parseInt(scanner.nextLine());

                        switch (deleteIndex) {
                            case (1)://------------1.DELETE BY NAME------------------
                                manager.deleteCityByName(scanner);
                                break; // break case (4->1)

                            case (2)://------------2.DELETE CITIES BY RANGE OF POPULATION------------------
                                manager.deleteByPopulationRange(scanner);
                                break; // break case (4->2)

                            case (3)://-------------3. DELETE DATABASE BY NAME (BY PASSWORD)----------------
                                manager.deleteDatabase(scanner);
                                break;
                        }
                        break; // break case (4)
                    case (5):
                        /**
                         ************************ EXIT *********************************
                         */
                        break;
                    default:
                        System.out.println("Unknown operationIndex. Please, try again");
                }
            }
        scanner.close();
    }
}
