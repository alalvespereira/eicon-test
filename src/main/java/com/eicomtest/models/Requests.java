package com.eicomtest.models;

import org.javalite.activejdbc.Model;

import java.util.Date;

public class Requests extends Model {

    public int id;
    public Date date;
    public String description;
    public int customer_id;
    public int quantity;
    public double unitary_value;
    public double amount;

    public Requests(){
        id=0;
    }

    public Requests(int id, Date date, String description, int customer_id, int quantity, double unitary_value, double amount){
        this.id = id;
        this.date = date;
        this.description = description;
        this.customer_id = customer_id;
        this.quantity = quantity;
        this.unitary_value = unitary_value;
        this.amount = amount;
    }

    // Getter & Setter
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitary_value() {
        return unitary_value;
    }

    public void setUnitary_value(double unitary_value) {
        this.unitary_value = unitary_value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
