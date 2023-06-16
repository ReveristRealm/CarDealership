package Models;

import java.util.ArrayList;

public class DealerShip {
    private int dealership_id;
    private String namee;
    private String phone;
    private String address;
    private ArrayList<Vehicles> Inventory;

    public DealerShip(int dealership_id, String namee, String phone, String address) {
        this.dealership_id = dealership_id;
        this.namee = namee;
        this.phone = phone;
        this.address = address;
        Inventory = new ArrayList<>();
    }

    public int getDealership_id() {
        return dealership_id;
    }

    public void setDealership_id(int dealership_id) {
        this.dealership_id = dealership_id;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void addVehicle(Vehicles vehicle){
        Inventory.add(vehicle);
    }
}
