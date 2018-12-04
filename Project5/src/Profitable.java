/**
 * <h1>Profitable</h1>
 * 
 * This interface represents something that can be used to make a profit. Along
 * with returning total profits it must also be able to provide a report.
 * @author Junseok
 * @author JJaved
 * @version 12-03-18
 */
public interface Profitable {


    double getProfit();
    String report();

}