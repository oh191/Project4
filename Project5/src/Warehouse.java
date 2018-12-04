
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 *
 * @author Junseok
 * @author JJaved
 * @version 12-03-18
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
        String packageID;
        String productName;
        double productWeight;
        double productPrice;
        String buyerName;
        String buyerAddress;
        String buyerCity;
        String buyerState;
        int buyerZIPCode;
        //TODO
        if (!isPrimeDay) {
            menu = "==========Options==========\n" +
                    "1) Add Package\n" +
                    "2) Add Vehicle\n" +
                    "3) Activate Prime Day\n" +
                    "4) Send Vehicle\n" +
                    "5) Print Statistics\n" +
                    "6) Exit\n" +
                    "===========================";
        } else {
            menu = "==========Options==========\n" +
                    "1) Add Package\n" +
                    "2) Add Vehicle\n" +
                    "3) Deactivate Prime Day\n" +
                    "4) Send Vehicle\n" +
                    "5) Print Statistics\n" +
                    "6) Exit\n" +
                    "===========================";
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
                    try {
                        s.nextLine();
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
                        System.out.println("\n" + pkg.shippingLabel() + "\n====================\n");
                    } catch (Exception e){
                        System.out.println("Please enter valid number");
                    }
                    break;
                case 2:
                    try {
                        int vehicleNumber;
                        String licensePlate;
                        int carryWeight;

                        System.out.println("Vehicle Options:\n1) Truck\n2) Drone\n3) Cargo Plane\n");
                        vehicleNumber = s.nextInt();
                        System.out.println("Enter License Plate No.:");
                        s.nextLine();
                        licensePlate = s.nextLine();
                        System.out.println("Enter Maximum Carry Weight:");
                        carryWeight = s.nextInt();

                        Vehicle currentVehicle;
                        if (vehicleNumber == 1) {
                            currentVehicle = new Truck(licensePlate, carryWeight);
                            vehicles.add(currentVehicle);
                        } else if (vehicleNumber == 2) {
                            currentVehicle = new Drone(licensePlate, carryWeight);
                            vehicles.add(currentVehicle);
                        } else if (vehicleNumber == 3) {
                            currentVehicle = new CargoPlane(licensePlate, carryWeight);
                            vehicles.add(currentVehicle);
                        } else {
                            System.out.println("Error No vehicles of selected type are available.");
                        }
                    } catch (Exception e){
                        System.out.println("Please enter a valid number.");
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
                                "===========================";
                        isPrimeDay = true;
                    } else {
                        menu = "==========Options==========\n" +
                                "1) Add Package\n" +
                                "2) Add Vehicle\n" +
                                "3) Activate Prime Day\n" +
                                "4) Send Vehicle\n" +
                                "5) Print Statistics\n" +
                                "6) Exit\n" +
                                "===========================";
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

                        if (choice == 1) {
                            for (Vehicle target : vehicles) {
                                if (target instanceof Truck) {
                                    Truck truck = (Truck) target;
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    if (choiceZIP == 1) {
                                        truck.setMaxWeight(target.getMaxWeight());
                                        target.setZipDest(pkgs.get(0).getDestination().zipCode);
                                        target.fill(pkgs);
                                        profit += truck.getProfit();
                                        for (Package pk : target.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    } else if (choiceZIP == 2) {
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
                                        truck.setZipDest(finalZip);
                                        truck.fill(pkgs);
                                        profit += truck.getProfit();
                                        for (Package pk : truck.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    }
                                    System.out.println(truck.report());
                                }
                            }
                        } else if (choice == 2) {
                            for (Vehicle target : vehicles) {
                                if (target instanceof Drone) {
                                    Drone drone = (Drone) target;
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    if (choiceZIP == 1) {
                                        drone.setZipDest(pkgs.get(0).getDestination().zipCode);
                                        drone.fill(pkgs);
                                        drone.report();
                                        profit += drone.getProfit();
                                        for (Package pk : drone.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    } else if (choiceZIP == 2) {
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
                                        drone.setZipDest(finalZip);
                                        drone.fill(pkgs);
                                        drone.report();
                                        profit += drone.getProfit();
                                        for (Package pk : drone.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    }
                                    System.out.println(drone.report());
                                }
                            }
                        } else if (choice == 3) {
                            for (Vehicle target : vehicles) {
                                if (target instanceof CargoPlane) {
                                    CargoPlane cargoPlane = (CargoPlane) target;
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    if (choiceZIP == 1) {
                                        cargoPlane.setZipDest(pkgs.get(0).getDestination().zipCode);
                                        cargoPlane.fill(pkgs);
                                        cargoPlane.report();
                                        profit += cargoPlane.getProfit();
                                        for (Package pk : cargoPlane.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    } else if (choiceZIP == 2) {
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
                                        cargoPlane.setZipDest(finalZip);
                                        cargoPlane.fill(pkgs);
                                        cargoPlane.report();
                                        profit += cargoPlane.getProfit();
                                        for (Package pk : cargoPlane.getPackages()) {
                                            nOfPackages++;
                                            pkgs.remove(pk);
                                        }
                                    }
                                    System.out.println(cargoPlane.report());
                                }
                            }
                        } else if (choice == 4) {

                            Vehicle target = vehicles.get(0);
                            if (target instanceof Truck) {
                                Truck targ = (Truck) target;
                                placeOfVehicle.add(vehicles.indexOf(target));
                                if (choiceZIP == 1) {
                                    targ.setMaxWeight(target.getMaxWeight());
                                    target.setZipDest(pkgs.get(0).getDestination().zipCode);
                                    target.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : target.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                } else if (choiceZIP == 2) {
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
                                    targ.setZipDest(finalZip);
                                    targ.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : targ.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                }
                                System.out.println(targ.report());
                            } else if (target instanceof Drone) {
                                Drone targ = (Drone) target;
                                placeOfVehicle.add(vehicles.indexOf(target));
                                if (choiceZIP == 1) {
                                    targ.setMaxWeight(target.getMaxWeight());
                                    target.setZipDest(pkgs.get(0).getDestination().zipCode);
                                    target.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : target.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                } else if (choiceZIP == 2) {
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
                                    targ.setZipDest(finalZip);
                                    targ.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : targ.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                }
                                System.out.println(targ.report());

                            } else {
                                CargoPlane targ = (CargoPlane) target;
                                placeOfVehicle.add(vehicles.indexOf(target));
                                if (choiceZIP == 1) {
                                    targ.setMaxWeight(target.getMaxWeight());
                                    target.setZipDest(pkgs.get(0).getDestination().zipCode);
                                    target.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : target.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                } else if (choiceZIP == 2) {
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
                                    targ.setZipDest(finalZip);
                                    targ.fill(pkgs);
                                    profit += targ.getProfit();
                                    for (Package pk : targ.getPackages()) {
                                        nOfPackages++;
                                        pkgs.remove(pk);
                                    }
                                }
                                System.out.println(targ.report());
                            }
                        } else {
                            System.out.println("Please enter valid number");
                            break;
                        }
                        if (placeOfVehicle.size() != 0) {
                            Vehicle[] c = new Vehicle[vehicles.size()];
                            for (int i = 0; i < vehicles.size(); i++) {
                                c[i] = vehicles.get(i);
                            }
                            for (int aa : placeOfVehicle){
                                vehicles.remove(c[aa]);
                            }
                            placeOfVehicle.clear();
                        } else {
                            System.out.println("Error: No vehicles of selected type are available");
                        }
                    }
                    break;
                case 5:
                    System.out.printf("==========Statistics==========\n" +
                            "Profits:                 $%.2f\n" +
                            "Packages Shipped:                %d\n" +
                            "Packages in Warehouse:           %d\n" +
                            "==============================\n", profit, nOfPackages, pkgs.size());
                    break;
                case 6:
                    break loop;
                default:
                    System.out.println("Error: Option not available");
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