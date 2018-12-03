import javax.print.attribute.standard.Destination;
import java.io.*;
import java.util.ArrayList;

/**
 * <h1>Database Manager</h1>
 * <p>
 * Used to locally save and retrieve data.
 */
public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        FileReader fr;
        BufferedReader br;
        ArrayList<Vehicle> arrayList = new ArrayList<>();
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                Vehicle vehicle = new Vehicle(temp[1], Double.parseDouble(temp[2]));
                arrayList.add(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * <p>
     * If filePath does not exist, a blank ArrayList will be returned.
     *
     * @param file CSV File
     * @return ArrayList of packages
     */
    public static ArrayList<Package> loadPackages(File file) {
        FileReader fr;
        BufferedReader br;
        ArrayList<Package> arrayList = new ArrayList<>();
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                ShippingAddress destination = new ShippingAddress(temp[4], temp[5], temp[6], temp[7], Integer.parseInt(temp[8]));
                Package pkg = new Package(temp[0], temp[1], Double.parseDouble(temp[2]), Double.parseDouble(temp[3]), destination);
                arrayList.add(pkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }


    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     *
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        FileReader fr;
        BufferedReader br = null;
        double profit = 0.0;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            return 0;
        }
        try {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    profit += Double.parseDouble(line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Math Error (Parse Double)");
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        return profit;
    }


    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     *
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        FileReader fr;
        BufferedReader br = null;
        int packageCount = 0;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            return 0;
        }
        try {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    packageCount += Integer.parseInt(line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Math Error (Parse Double)");
                }
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        return packageCount;
    }


    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     *
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        FileReader fr;
        BufferedReader br = null;
        boolean isTrue = true;
        String line;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            return false;
        }
        try {
            while ((line = br.readLine()) != null) {
                if (line.equalsIgnoreCase("0"))
                    isTrue = false;
            }
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        return isTrue;
    }


    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     *
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        PrintWriter pw = null;
        String saveThisPlz = "";
        try{
            pw = new PrintWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).equals(CargoPlane))
        }
        pw.write(saveThisPlz);
        pw.flush();
        pw.close();
    }


    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     *
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        PrintWriter pw = null;
        String saveThisPlz = "";
        try{
            pw = new PrintWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < packages.size(); i++) {
            Package thisPkg = packages.get(i);
            ShippingAddress shipping = packages.get(i).getDestination();
            saveThisPlz += String.format("%s,%s,%f,%f,%s,%s,%s,%s,%d\n",
                    thisPkg.getID(), thisPkg.getProduct(), thisPkg.getWeight(), thisPkg.getPrice(),
                    shipping.name, shipping.address, shipping.city, shipping.state, shipping.zipCode);
        }
        pw.write(saveThisPlz);
        pw.flush();
        pw.close();
    }


    /**
     * Saves profit to text file.
     *
     * @param file   File to write profits to
     * @param profit Total profits
     */

    public static void saveProfit(File file, double profit) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (IOException e){
            e.printStackTrace();
        }
        pw.write(profit + "");
        pw.flush();
        pw.close();
    }


    /**
     * Saves number of packages shipped to text file.
     *
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        //TODO
    }


    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * writtern, otherwise "0" will be written.
     *
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        if (primeDay){

        }
    }
}