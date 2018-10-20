package myapp.com.project;

import java.io.Serializable;

public class Customer implements Serializable{

    public String name;
    public String phone;
    public String address;
    public String quantity;
    public double rate;
    public int id;
    public int status;


    public  String type;

    public Customer(){

    }

    public Customer(String date, String name, String phone, String address, String quantity, double rate, int id, String type, int status) {

        this.name = name;
        this.phone = phone;
        this.address = address;
        this.quantity = quantity;
        this.rate = rate;
        this.id=id;
        this.type=type;
        this.status=status;

    }

    @Override
    public String toString() {
        return "Customer" +
                "\nName = " + name  +
                "\n Phone No = " + phone  +
                "\n Address = " + address +
                "\n Quantity = " + quantity +
                "\n Rate = " + rate +
                "\n Status = " + status +
                "\n Milk Type Selected " +type;
    }
}
