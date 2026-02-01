package br.edu.ifba.inf008.plugins;

public class PieReportDTO {
    String fuel_type;
    int total;
    int available;
    int rented;
    Double percentage;
    String color;

    public PieReportDTO(String fuel_type, int total, int available, int rented, Double percentage, String color){
        this.fuel_type = fuel_type;
        this.total = total;
        this.available = available;
        this.rented = rented;
        this.percentage = percentage;
        this.color = color;
    }
}
