package com.example.salonprojekt;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Table {
    private String customer_name;
    private String customerPhone;
    private String customerGender;
    private String treatmentName;
    private double treatmentPrice;
    //tiden skal kaldes med noget der hedder LocalDateTime
    private LocalDateTime appointmentDatetime;
    private String employeeName;
    private String status;


    public Table(String customer_name, String customerPhone, String customerGender, String treatmentName, double treatmentPrice,
                 LocalDateTime appointmentDatetime, String employeeName, String status) {
        this.customer_name = customer_name;
        this.customerPhone = customerPhone;
        this.customerGender = customerGender;
        this.treatmentName = treatmentName;
        this.treatmentPrice = treatmentPrice;
        this.appointmentDatetime = appointmentDatetime;
        this.employeeName = employeeName;
        this.status = status;


    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public double getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(double treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }

    public LocalDateTime getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
