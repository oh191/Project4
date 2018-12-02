import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    static String menu = "==========Options==========\n" +
            "1) Add Package\n" +
            "2) Add Vehicle\n" +
            "3) Activate Prime Day\n" +
            "4) Send Vehicle\n" +
            "5) Print Statistics\n" +
            "6) Exit\n" +
            "===========================\n";

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

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        //TODO
        Scanner s = new Scanner(System.in);
        boolean isRepeating = false;
        int option;
        //1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager


        //2) Show menu and handle user inputs
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

                System.out.printf("\n====================\nTO: %s\n%s\n%s, %s, %d" +
                                "\nWeight:         %f\nPrice:        $%f\nProduct: %s",
                        buyerName, buyerAddress, buyerCity, buyerState,
                        buyerZIPCode, productWeight, productPrice, productName);

                try {
                    FileWriter fw = new FileWriter(PACKAGE_FILE, true);
                    fw.write("a, b, c, d");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("Vehicle Options:\n1) Truck\n2)Drone\n3)Cargo Plane\n");
                vehicle = s.nextInt();
                System.out.println("Enter License Plate No.:");
                licensePlate = s.nextLine();
                System.out.println("Enter Maximum Carry Weight:");
                carryWeight = s.nextInt();

                break;
            case 3:
                if (menu.contains("Deactivate")) {
                    menu = "==========Options==========\n" +
                            "1) Add Package\n" +
                            "2) Add Vehicle\n" +
                            "3) Activate Prime Day\n" +
                            "4) Send Vehicle\n" +
                            "5) Print Statistics\n" +
                            "6) Exit\n" +
                            "===========================\n";
                    isPrimeDay = true;
                } else if (menu.contains("Activate")) {
                    menu = "==========Options==========\n" +
                            "1) Add Package\n" +
                            "2) Add Vehicle\n" +
                            "3) Deactivate Prime Day\n" +
                            "4) Send Vehicle\n" +
                            "5) Print Statistics\n" +
                            "6) Exit\n" +
                            "===========================\n";
                    isPrimeDay = false;
                }
                break;
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager


    }


}