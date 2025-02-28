package com.example.salonprojekt;

import java.time.LocalDateTime;

public class Create {
    private String customerName;
    private String customerPhone;
    private String customerGender;
    private int treatmentId;
    private LocalDateTime appointmentDatetime;
    private int employeeId;
    private String status;
    private int extraTime;
    private double extraCost;

    public Create(String customerName, String customerPhone, String customerGender, int treatmentId,
                  LocalDateTime appointmentDatetime, int employeeId, String status, int extraTime, double extraCost) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerGender = customerGender;
        this.treatmentId = treatmentId;
        this.appointmentDatetime = appointmentDatetime;
        this.employeeId = employeeId;
        this.status = status;
        this.extraTime = extraTime;
        this.extraCost = extraCost;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerGender() {
        return customerGender;
    }
    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public int getTreatmentId() {
        return treatmentId;
    }
    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public LocalDateTime getAppointmentDatetime() {
        return appointmentDatetime;
    }
    public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getExtraTime() {
        return extraTime;
    }
    public void setExtraTime(int extraTime) {
        this.extraTime = extraTime;
    }

    public double getExtraCost() {
        return extraCost;
    }
    public void setExtraCost(double extraCost) {
        this.extraCost = extraCost;
    }
}
