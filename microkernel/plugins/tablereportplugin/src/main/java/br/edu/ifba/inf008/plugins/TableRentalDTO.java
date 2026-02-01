package br.edu.ifba.inf008.plugins;

public class TableRentalDTO {
    private String rentalId;
    private String customerName;
    private String customerType;
    private String vehicle;
    private String vehicleType;
    private String startDate;
    private Double totalAmount;
    private String rentalStatus;
    private String paymentStatus;

    public TableRentalDTO(
            String rentalId,
            String customerName,
            String customerType,
            String vehicle,
            String vehicleType,
            String startDate,
            Double totalAmount,
            String rentalStatus,
            String paymentStatus
    ) {
        this.rentalId = rentalId;
        this.customerName = customerName;
        this.customerType = customerType;
        this.vehicle = vehicle;
        this.vehicleType = vehicleType;
        this.startDate = startDate;
        this.totalAmount = totalAmount;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
    }

    public String getRentalId() {return rentalId;}
    public String getCustomerName() {return customerName;}
    public String getCustomerType() {return customerType;}
    public String getVehicle() {return vehicle;}
    public String getVehicleType() {return vehicleType;}
    public String getStartDate() {return startDate;}
    public Double getTotalAmount() {return totalAmount;}
    public String getRentalStatus() {return rentalStatus;}
    public String getPaymentStatus() {return paymentStatus;}
}
