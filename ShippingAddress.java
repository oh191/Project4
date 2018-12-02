/**
 * <h1>Shipping Address</h1> Represents a shipping address
 */
public class ShippingAddress {
    String name;
    String address;
    String city;
    String state;
    int zipCode;

    public ShippingAddress(){
        name = "";
        address = "";
        city = "";
        state = "";
        zipCode = 0;
    }
    public ShippingAddress(String name, String address, String city, String state, int zipCode){
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}