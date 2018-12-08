
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
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


    public static void printStatisticsReport(double profits, int packagesShipped, int numberOfPackages) {
        String forProfits = String.format("%26s", NumberFormat.getCurrencyInstance
                (new Locale("en", "US")).format(profits));
        System.out.printf("==========Statistics==========\n" +
                "Profits:%s\n" +
                "Packages Shipped:                %d\n" +
                "Packages in Warehouse:           %d\n" +
                "==============================\n", forProfits, packagesShipped, numberOfPackages);
    }




    public static void deleteVehicles(Vehicle[] lists) {
        for (Vehicle thisOne : lists) {
            thisOne.empty();
            vehicles.remove(thisOne);
        }
    }


    public static int setZipCode(int choice) {
        int maxValue = 0, maxCount = 0;
        if (choice == 1) {
            return pkgs.get(0).getDestination().zipCode;
        } else {
            for (int i = 0; i < pkgs.size(); ++i) {
                int count = 1;
                for (int j = 0; j < pkgs.size(); ++j) {
                    if (i != j && pkgs.get(j).getDestination().zipCode == pkgs.get(i).getDestination().zipCode)
                        ++count;
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = pkgs.get(i).getDestination().zipCode;
                }
            }

            return maxValue;
        }
    }

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
        boolean isRepeating;
        int option;
        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager

        vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        pkgs = DatabaseManager.loadPackages(PACKAGE_FILE);
        profit = DatabaseManager.loadProfit(PROFIT_FILE);
        nOfPackages = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        isPrimeDay = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);
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
                        System.out.println("\n" + pkg.shippingLabel() + "\n");
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter valid number !");
                    }
                    break;
                case 2:
                    try {
                        int vehicleNumber;
                        String licensePlate;
                        int carryWeight;

                        System.out.println("Vehicle Options:\n1) Truck\n2) Drone\n3) Cargo Plane");
                        vehicleNumber = s.nextInt();
                        s.nextLine();
                        System.out.println("Enter License Plate No.:");
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
                    } catch (InputMismatchException e) {
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
                                    target.setZipDest(setZipCode(choiceZIP));
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    target.fill(pkgs);
                                    profit += target.getProfit();
                                    System.out.println(target.report());
                                    nOfPackages += target.getPackages().size();
                                }
                            }
                        } else if (choice == 2) {
                            for (Vehicle target : vehicles) {
                                if (target instanceof Drone) {
                                    target.setZipDest(setZipCode(choiceZIP));
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    target.fill(pkgs);
                                    profit += target.getProfit();
                                    System.out.println(target.report());
                                    nOfPackages += target.getPackages().size();
                                }
                            }
                        } else if (choice == 3) {
                            for (Vehicle target : vehicles) {
                                if (target instanceof CargoPlane) {
                                    target.setZipDest(setZipCode(choiceZIP));
                                    placeOfVehicle.add(vehicles.indexOf(target));
                                    target.fill(pkgs);
                                    profit += target.getProfit();
                                    System.out.println(target.report());
                                    nOfPackages += target.getPackages().size();
                                }
                            }
                        } else if (choice == 4) {
                            Vehicle target = vehicles.get(0);
                            target.setZipDest(setZipCode(choiceZIP));
                            placeOfVehicle.add(0);
                            target.fill(pkgs);
                            profit += target.getProfit();
                            System.out.println(target.report());
                            nOfPackages += target.getPackages().size();
                        } else {
                            System.out.println("Please enter valid number");
                        }
                        if (placeOfVehicle.size() == 0) {
                            System.out.println("Error: No vehicles of selected type are available");
                        } else {
                            Vehicle[] lists = new Vehicle[placeOfVehicle.size()];
                            for (int i = 0; i < placeOfVehicle.size(); i++) {
                                lists[i] = vehicles.get(placeOfVehicle.get(i));
                            }
                            deleteVehicles(lists);
                        }

                    }
                    break;
                case 5:
                    printStatisticsReport(profit, nOfPackages, pkgs.size());
                    break;
                case 6:
                    break loop;
                default:
                    System.out.println("Error: Option not available");
            }
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);
        DatabaseManager.savePackages(PACKAGE_FILE, pkgs);
        DatabaseManager.saveProfit(PROFIT_FILE, profit);
        DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, nOfPackages);
        DatabaseManager.savePrimeDay(PRIME_DAY_FILE, isPrimeDay);

    }


}