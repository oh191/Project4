import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {
    final static String folderPath = "files/";
    final static File VEHICLE_FILE = new File(folderPath + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(folderPath + "PackageList.csv");
    final static File PROFIT_FILE = new File(folderPath + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(folderPath + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(folderPath + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;

    static String menu;
    static String vehichleMenu = "Options :\n" +
            "1) Send Truck\n" +
            "2) Send Drone\n" +
            "3) Send Cargo Plane\n" +
            "4) Send First Available\n";
    static String zipCodeOptions = "ZIP Code Options:\n1) Send to first ZIP Code\n2) Send to mode of ZIP Codes";

    static String packageID;
    static String productName;
    static double productWeight;
    static double productPrice;
    static String buyerName;
    static String buyerAddress;
    static String buyerCity;
    static String buyerState;
    static int buyerZIPCode;

    static int vehicle;
    static String licensePlate;
    static int carryWeight;

    static boolean isPrimeDay;
    static double profit;
    static int nOfPackages;
    static ArrayList<Package> pkgs = new ArrayList<>();
    static ArrayList<Vehicle> vehicles = new ArrayList<>();

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        //TODO
        if (!isPrimeDay) {
            menu = "==========Options==========\n" +
                    "1) Add Package\n" +
                    "2) Add Vehicle\n" +
                    "3) Activate Prime Day\n" +
                    "4) Send Vehicle\n" +
                    "5) Print Statistics\n" +
                    "6) Exit\n" +
                    "===========================\n";
        } else {
            menu = "==========Options==========\n" +
                    "1) Add Package\n" +
                    "2) Add Vehicle\n" +
                    "3) Deactivate Prime Day\n" +
                    "4) Send Vehicle\n" +
                    "5) Print Statistics\n" +
                    "6) Exit\n" +
                    "===========================\n";
        }
        Scanner s = new Scanner(System.in);
        boolean isRepeating = false;
        int option;
        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        DatabaseManager databaseManager = new DatabaseManager();
        vehicles = databaseManager.loadVehicles(VEHICLE_FILE);
        pkgs = databaseManager.loadPackages(PACKAGE_FILE);
        profit = databaseManager.loadProfit(PROFIT_FILE);
        nOfPackages = databaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        isPrimeDay = databaseManager.loadPrimeDay(PRIME_DAY_FILE);
        //2) Show menu and handle user inputs
        loop:
        while (true) {
            do {
                System.out.println(menu);
                option = s.nextInt();
                try {
                    if (0 < option && option < 7) {
                        isRepeating = false;
                    } else {
                        System.out.println("Error: Option not available.");
                        isRepeating = true;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Option not available.");
                    isRepeating = true;
                }
            } while (isRepeating);
            switch (option) {
                case 1:
                    System.out.println("Enter Package ID:");
                    packageID = s.nextLine();

                    System.out.println("Enter Product Name:");
                    productName = s.nextLine();

                    System.out.println("Enter Weight:");
                    productWeight = s.nextDouble();

                    s.nextLine();
                    System.out.println("Enter Price:");
                    productPrice = s.nextDouble();
                    if (isPrimeDay) {
                        productPrice = productPrice * (1 - PRIME_DAY_DISCOUNT);
                    }

                    s.nextLine();
                    System.out.println("Enter Buyer Name:");
                    buyerName = s.nextLine();

                    System.out.println("Enter Address:");
                    buyerAddress = s.nextLine();

                    System.out.println("Enter City:");
                    buyerCity = s.nextLine();

                    System.out.println("Enter State:");
                    buyerState = s.nextLine();

                    System.out.println("Enter Zip Code:");
                    buyerZIPCode = s.nextInt();

                    ShippingAddress shippingAddress = new ShippingAddress
                            (buyerName, buyerAddress, buyerCity, buyerState, buyerZIPCode);
                    Package pkg = new Package(packageID, productName, productWeight, productPrice, shippingAddress);
                    pkgs.add(pkg);
                    System.out.println("\n" + pkg.shippingLabel());
                    break;
                case 2:
                    System.out.println("Vehicle Options:\n1) Truck\n2)Drone\n3)Cargo Plane\n");
                    vehicle = s.nextInt();
                    System.out.println("Enter License Plate No.:");
                    licensePlate = s.nextLine();
                    System.out.println("Enter Maximum Carry Weight:");
                    carryWeight = s.nextInt();

                    if (vehicle == 1) {
                        Truck truck = new Truck(licensePlate, carryWeight);
                        vehicles.add(truck);
                    } else if (vehicle == 2) {
                        Drone drone = new Drone(licensePlate, carryWeight);
                        vehicles.add(drone);
                    } else if (vehicle == 3) {
                        CargoPlane cargoPlane = new CargoPlane(licensePlate, carryWeight);
                        vehicles.add(cargoPlane);
                    }

                    break;
                case 3:
                    if (!isPrimeDay) {
                        menu = "==========Options==========\n" +
                                "1) Add Package\n" +
                                "2) Add Vehicle\n" +
                                "3) Deactivate Prime Day\n" +
                                "4) Send Vehicle\n" +
                                "5) Print Statistics\n" +
                                "6) Exit\n" +
                                "===========================\n";
                        isPrimeDay = true;
                    } else {
                        menu = "==========Options==========\n" +
                                "1) Add Package\n" +
                                "2) Add Vehicle\n" +
                                "3) Activate Prime Day\n" +
                                "4) Send Vehicle\n" +
                                "5) Print Statistics\n" +
                                "6) Exit\n" +
                                "===========================\n";
                        isPrimeDay = false;
                    }
                    break;
                case 4:
                    ArrayList<Integer> placeOfVehicle = new ArrayList<>();
                    if (vehicles.size() < 1) {
                        System.out.println("Error: No vehicles available.");
                    } else if (pkgs.size() < 1) {
                        System.out.println("Error: No packages available.");
                    } else {
                        System.out.println(vehichleMenu);
                        int choice = s.nextInt();
                        System.out.println(zipCodeOptions);
                        int choiceZIP = s.nextInt();
                        switch (choice) {
                            case 1:
                                for (Vehicle vehicle : vehicles) {

                                    if (vehicle instanceof Truck) {
                                        placeOfVehicle.add(vehicles.indexOf(vehicle));
                                        if (choiceZIP == 1) {
                                            vehicle.setZipDest(pkgs.get(0).getDestination().zipCode);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();
                                        }
                                        if (choiceZIP == 2) {
                                            int counter = 1;
                                            int isCounterBigger = 0;
                                            int zipToBeSet = 0;
                                            int finalZip = 0;
                                            for (int i = 0; i < pkgs.size(); i++) {
                                                int zipI = pkgs.get(i).getDestination().zipCode;
                                                for (int j = 0; j < pkgs.size(); j++) {
                                                    int zipJ = pkgs.get(j).getDestination().zipCode;
                                                    if (i != j && zipI == zipJ) {
                                                        zipToBeSet = zipI;
                                                        counter++;
                                                    }
                                                }
                                                if (counter > isCounterBigger) {
                                                    finalZip = zipToBeSet;
                                                    isCounterBigger = counter;
                                                }
                                                counter = 1;
                                            }
                                            vehicle.setZipDest(finalZip);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();

                                        }

                                    }
                                }
                                break;
                            case 2:
                                for (Vehicle vehicle : vehicles) {
                                    if (vehicle instanceof Drone) {
                                        if (choiceZIP == 1) {
                                            vehicle.setZipDest(pkgs.get(0).getDestination().zipCode);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();

                                        }
                                        if (choiceZIP == 2) {
                                            int counter = 1;
                                            int isCounterBigger = 0;
                                            int zipToBeSet = 0;
                                            int finalZip = 0;
                                            for (int i = 0; i < pkgs.size(); i++) {
                                                int zipI = pkgs.get(i).getDestination().zipCode;
                                                for (int j = 0; j < pkgs.size(); j++) {
                                                    int zipJ = pkgs.get(j).getDestination().zipCode;
                                                    if (i != j && zipI == zipJ) {
                                                        zipToBeSet = zipI;
                                                        counter++;
                                                    }
                                                }
                                                if (counter > isCounterBigger) {
                                                    finalZip = zipToBeSet;
                                                    isCounterBigger = counter;
                                                }
                                                counter = 1;
                                            }
                                            vehicle.setZipDest(finalZip);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();

                                        }
                                    }
                                }
                                break;
                            case 3:
                                for (Vehicle vehicle : vehicles) {
                                    if (vehicle instanceof CargoPlane) {
                                        if (choiceZIP == 1) {
                                            vehicle.setZipDest(pkgs.get(0).getDestination().zipCode);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();

                                        }
                                        if (choiceZIP == 2) {
                                            int counter = 1;
                                            int isCounterBigger = 0;
                                            int zipToBeSet = 0;
                                            int finalZip = 0;
                                            for (int i = 0; i < pkgs.size(); i++) {
                                                int zipI = pkgs.get(i).getDestination().zipCode;
                                                for (int j = 0; j < pkgs.size(); j++) {
                                                    int zipJ = pkgs.get(j).getDestination().zipCode;
                                                    if (i != j && zipI == zipJ) {
                                                        zipToBeSet = zipI;
                                                        counter++;
                                                    }
                                                }
                                                if (counter > isCounterBigger) {
                                                    finalZip = zipToBeSet;
                                                    isCounterBigger = counter;
                                                }
                                                counter = 1;
                                            }
                                            vehicle.setZipDest(finalZip);
                                            vehicle.fill(pkgs);
                                            vehicle.report();
                                            profit += vehicle.getProfit();
                                            for (Package pk : vehicle.getPackages()) {
                                                nOfPackages++;
                                                pkgs.remove(pk);
                                            }
                                            vehicle.empty();
                                        }
                                    }
                                }
                                break;

                        }

                    }
                case 5:
                    System.out.printf("==========Statistics==========\n" +
                            "Profits:                 $%.2f\n" +
                            "Packages Shipped:                %d\n" +
                            "Packages in Warehouse:           %d\n" +
                            "==============================", profit, nOfPackages, pkgs.size());
                    break;
                case 6:
                    break loop;
            }
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
        databaseManager.saveVehicles(VEHICLE_FILE, vehicles);
        databaseManager.savePackages(PACKAGE_FILE, pkgs);
        databaseManager.saveProfit(PROFIT_FILE, profit);
        databaseManager.savePackagesShipped(N_PACKAGES_FILE, nOfPackages);
        databaseManager.savePrimeDay(PRIME_DAY_FILE, isPrimeDay);

    }


}