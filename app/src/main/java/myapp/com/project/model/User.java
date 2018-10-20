package myapp.com.project.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    public int id;
    public double quantity;
    public double rate;
    public String date;
public  String type;

    public User(){

    }

    public User(double quantity, double rate, String date,int id,String type) {
        this.quantity = quantity;
        this.rate = rate;
        this.date = date;
        this.id=id;
        this.type=type;
    }

    @Override
    public String toString() {
        return "Quantity='" + quantity +
                "\n Rate=" + rate +
                "\n Date=" + date +
                "\n Milk type selected is=" + type ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
