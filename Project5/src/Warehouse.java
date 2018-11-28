import java.io.File;
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

    /**
     * Main Method
     *
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
        //TODO
        Scanner s = new Scanner(System.in);
        boolean isRepeating = false;
        String menu = "==========Options==========\n" +
                "1) Add Package\n" +
                "2) Add Vehicle\n" +
                "3) Activate Prime Day\n" +
                "4) Send Vehicle\n" +
                "5) Print Statistics\n" +
                "6) Exit\n" +
                "===========================\n";
        int option;
        String packageID;
        String productName;
        double productWeight;
        double productPrice;
        String buyerName;
        String buyerAddress;
        String buyerCity;
        String buyerState;
        int buyerZIPCode;
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
                break;
        }
        //3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager


    }


}