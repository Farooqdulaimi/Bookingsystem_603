package model;

import java.sql.Date;

public class Booking {

    private int id;
    private int carId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
    private String status;

    public Booking(int id, int carId, int userId, Date startDate, Date endDate, double totalPrice, String status) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() { return id; }
    public int getCarId() { return carId; }
    public int getUserId() { return userId; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}