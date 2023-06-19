package Models;



public class LeaseContract {
    private String vin;
    private String startdate;
    private String enddate;
    private String monthlypayment;

    public LeaseContract(String vin, String startdate, String enddate, String monthlypayment){
        this.vin = vin;
        this.startdate = startdate;
        this.enddate = enddate;
        this.monthlypayment = monthlypayment;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getMonthlypayment() {
        return monthlypayment;
    }

    public void setMonthlypayment(String monthlypayment) {
        this.monthlypayment = monthlypayment;
    }
}
