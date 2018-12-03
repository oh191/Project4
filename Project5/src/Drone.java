import java.util.ArrayList;

/**
 * <h1>Drone</h1> Represents a Drone
 */

public class Drone extends Vehicle implements Profitable{

    final double GAS_RATE = 1.33;
    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;

    public Drone() {
        this.licensePlate = "";
        this.maxWeight = 0.0;
        this.currentWeight = 0.0;
        this.zipDest = 0;
        this.packages = new ArrayList<>();

    }


    public Drone(String licensePlate , double maxWeight) {
        super();
        this.licensePlate = licensePlate;
        this.maxWeight = maxWeight;


    }

    /*
     * =============================================================================
     * | Methods from Profitable Interface
     * =============================================================================
     */
    /**
     * Returns the profits generated by the packages currently in the Truck.
     * <p>
     * &sum;p<sub>price</sub> - (range<sub>max</sub> &times; 1.33)
     * </p>
     */
    @Override
    public double getProfit() {
        double range = 0; // Put the range here
        double difference = range * GAS_RATE;
        double sum = 0.0;

        for (Package p: packages) {
            sum += p.getPrice();
        }

        return sum - difference;

    }

    /**
     * Generates a String of the Drone report. Drone report includes:
     * <ul>
     * <li>License Plate No.</li>
     * <li>Destination</li>
     * <li>Current Weight/Maximum Weight</li>
     * <li>Net Profit</li>
     * <li>Shipping labels of all packages in truck</li>
     * </ul>
     *
     * @return Truck Report
     */
    @Override
    public String report() {
        CargoPlane cargoPlane= new CargoPlane();

        String output = "==========Drone Report==========\n";
        output += "License Plate No.: " + cargoPlane.getLicensePlate() + "\n" ;
        output += "Destination: " + cargoPlane.getZipDest() + "\n";
        output += "Weight Load: " + cargoPlane.getCurrentWeight() + "/" + cargoPlane.getMaxWeight() + "\n";
        output += "Net Profit: (" +  getProfit() + ")" + "\n";
        output += "==============================" + "\n";
        ArrayList<Package> cargoPlanePackages = cargoPlane.getPackages();
        for (Package packages : cargoPlanePackages) {
            output += "=====Shipping Labels=====\n";

            output += packages.shippingLabel();

        }

        return output;
    }



}