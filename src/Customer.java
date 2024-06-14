import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class Customer {
    String name, model, com;
    double amount;
    long mobileNo;

    public Customer(String name, String model, String com, double amount, long mobileNo) {
        this.name = name;
        this.model = model;
        this.com = com;
        this.amount = amount;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getCom() {
        return com;
    }

    public double getAmount() {
        return amount;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    @Override
    public String toString() {
        return " name=" + name + ", model=" + model + ", com=" + com + ", amount=" + amount + ", mobileNo="
                + mobileNo;
    }

}

class ListCustomer {
    static ArrayList<Customer> customers = new ArrayList<>();
    int pn;

    public static void addCustomer(Customer c) throws Exception {
        customers.add(c);
        CustomerDataBase(c);
        addfilr(c);
    }

    // To Add Customer.........................
    public static void addfilr(Customer c) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src//Bike//" + c.getName() + ".txt", true));
        String line1 = "Customer :- " + c.toString();

        bw.write(line1);
        bw.newLine();
        bw.close();
    }

    public static void CustomerDataBase(Customer c) throws Exception {
        String s = "com.mysql.cj.jdbc.Driver";
        Class.forName(s);
        String dburl = "jdbc:mysql://localhost:3306/bike_booking";
        String name = "root";
        String pass = "";
        Connection con = DriverManager.getConnection(dburl, name, pass);
        // To Cheak
        // Connection...........................................................................................

        // if (con != null) {
        // System.out.println("dne");
        // } else {
        // System.out.println("not ");
        // }
        int Order_no = (int) (Math.random() * 10000);

        String sql = "insert into customer(name, model,mobile_no,Order_no,company,amount) values (?,?,?,?,?,?)  ";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, c.getName());
        pst.setString(2, c.getModel());
        pst.setLong(3, c.getMobileNo());
        pst.setLong(4, Order_no);
        pst.setString(5, c.getCom());
        pst.setLong(6, (long) c.getAmount());
        pst.executeLargeUpdate();
    }

    public void GenerateBill(Scanner sc) throws Exception {

        System.out.println("Enter Your Name (if your purchase name match then bill will generates)");
        String name = sc.nextLine();
        String s = "com.mysql.cj.jdbc.Driver";
        Class.forName(s);
        String dburl = "jdbc:mysql://localhost:3306/bike_booking";
        String dbname = "root";
        String pass = "";
        Connection con = DriverManager.getConnection(dburl, dbname, pass);

        String sql = "{call getCustomerDetails(?)}";
        CallableStatement cst = con.prepareCall(sql);

        cst.setString(1, name);
        ResultSet rs = cst.executeQuery();
        int o = 0;
        while (rs.next()) {
            System.out.println("\u001B[38;2;255;165;0m---------------Bill----------------\u001B[0m");
            System.out.println("\u001B[38;2;255;165;0mname :=\u001B[0m " + rs.getString(2));
            System.out.println("\u001B[38;2;255;165;0mModel Name :=\u001B[0m " + rs.getString(3));
            System.out.println("\u001B[38;2;255;165;0mMobile Number:=\u001B[0m " + rs.getLong(4));
            System.out.println("\u001B[38;2;255;165;0mOrder Number:=\u001B[0m " + rs.getLong(5));
            System.out.println("\u001B[38;2;255;165;0mCompnay :=\u001B[0m " + rs.getString(6));
            System.out.println("\u001B[38;2;255;165;0mAmount :=\u001B[0m " + rs.getLong(7));
            System.out.println("\u001B[38;2;255;165;0m-----------------------------------\u001B[0m");

            o++;
        }
        if (o == 0) {
            System.out.println("Not Found.");
        }
    }
}
