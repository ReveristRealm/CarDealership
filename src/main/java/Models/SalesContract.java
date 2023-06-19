package Models;

public class SalesContract {
    private String vin;
    private String date;
    private double price;

        public SalesContract(String vin,String date, double price){
        this.vin = vin;
        this.date = date;
        this.price = price;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
