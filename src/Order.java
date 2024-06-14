import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.transform.stream.StreamSource;

class Order {
    HashMap<Customer, BikeModels> makeOrder;
    Customer c;
    BikeModels bikemodel;
    String name;
    long no;
    ListCustomer ls = new ListCustomer();
    Scanner scan = new Scanner(System.in);
    Connection con;

    Order() throws Exception {
        makeOrder = new HashMap<>();
        String s = "com.mysql.cj.jdbc.Driver";
        Class.forName(s);
        String dburl = "jdbc:mysql://localhost:3306/bike_booking";
        String dbname = "root";
        String pass = "";
        con = DriverManager.getConnection(dburl, dbname, pass);
    }

    public void Details() throws Exception {

        System.out.println("Enter Name");
        name = scan.nextLine();
        System.out.println("Enter Mobile Number");
        no = scan.nextLong();

    }

    private void cardPay(int b_no, String mname) throws Exception {
        System.out.println("Enter Card No");
        long c_no = scan.nextLong();
        int l = 3;
        while (l != 0) {
            String ss = String.valueOf(c_no);
            if (ss.length() == 10) {
                System.out.println("Valid");
                break;
            }
            l--;
            System.out.println("Enter 10 Digit No " + l + " Chances Left");
            c_no = scan.nextLong();
        }
        if (l != 0) {
            String sql = "{call AddValues(?,?,?)}";
            CallableStatement cst = con.prepareCall(sql);
            cst.setInt(1, b_no);
            cst.setString(2, name);
            cst.setLong(3, c_no);
            int k = cst.executeUpdate();
            if (k != 0) {
                System.out.println("Card Added SuccsessFully");
            } else {
                System.out.println("Error In Card Adding");
            }
            scan.nextLine();
            System.out.println("You Want Complete Your Perchase (Yes For continue)");
            String ss = scan.nextLine();
            if (ss.equalsIgnoreCase("yes")) {
                UpdateStatuse(c_no);
            } else {
                System.out.println("Purchase Last Step Remain");
            }
        } else {
            System.out.println("Try Again Purchase ");
            return;
        }

    }

    private void UpdateStatuse(long c_no) throws Exception {
        String sql = "{call statusUpdateByCard(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setLong(1, c_no);
        System.out.println("Enter CVV");
        int k = scan.nextInt();
        if (String.valueOf(k).length() == 3) {
            int i = cst.executeUpdate();
            if (i != 0) {
                System.out.println("done");
            } else {
                System.out.println("Error in database");
            }
        } else {
            System.out.println("Try Again Later");
        }
    }

    public void placeOrder() throws Exception {
        Employee e = new Employee();
        e.pListBike(con);
        System.out.println("Enter Which Number Of Bike You Want Purchase");
        int i = scan.nextInt();
        scan.nextLine();
        ResultSet rs = listGetBikes(i);
        if (rs != null) {
            while (rs.next()) {
                System.out.println("Good Choice " + rs.getString(3));
                Details();
                long price = rs.getLong(4);
                c = new Customer(name, rs.getString(3), rs.getString(2), price, no);
                ListCustomer.addCustomer(c);
                System.out.println("Select Purchase Style");
                System.out.println("Option 1 > UPI");
                System.out.println("Option 2 > Card");
                int choice = scan.nextInt();
                scan.nextLine();
                if (choice == 1) {
                    System.out.println("Enter Your UPI Id");
                    String upi1 = scan.next();
                    System.out.println("Enter Revecier UPI Number");
                    String Recever = scan.next();
                    System.out.println("Enter Amount ");
                    long am = scan.nextLong();
                    scan.nextLine();
                    System.out.println("Your Payment ID := " + name + "@" + (int) (Math.random() * 1000));

                    System.out.println("Enter UPI password");
                    String pass = scan.next();
                    if (upi1.length() == 10 && Recever.length() == 10 && pass.length() == 6) {
                        System.out.println(am + " Payment Done");
                    } else {
                        System.out.println("Check Values");
                    }
                } else if (choice == 2) {
                    cardPay(rs.getInt(1), rs.getString(3));
                } else {
                    System.out.println("Make Correct Choice");
                    return;
                }
            }

        } else {
            System.out.println("No Model Found");
        }
        // System.out.println(price);
        // c = new Customer(name, bikemodel.getName(), bikemodel.getCompnay(), price,
        // no);
        // ListCustomer.addCustomer(c);

    }

    private ResultSet listGetBikes(int i) throws Exception {

        String sql = "{call getBike(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setInt(1, i);

        ResultSet rs = cst.executeQuery();
        return rs;
    }

    public void coplatePayment() throws Exception {
        System.out.println("Enter Card No");
        long c_no = scan.nextLong();
        scan.nextLine();
        int l = 3;
        while (l != 0) {
            String ss = String.valueOf(c_no);
            if (ss.length() == 10) {
                System.out.println("Valid");
                break;
            }
            l--;
            System.out.println("Enter 10 Digit No " + l + " Chances Left");
            c_no = scan.nextLong();
        }
        int check = checkCard(c_no);

        if (check != 0) {
            updateStatus(c_no);
        } else {
            System.out.println("Card Details Not Found");
        }
    }

    private void updateStatus(long c_no) throws Exception {
        String sql = "{call statusUpdateByCard(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setLong(1, c_no);
        int k = cst.executeUpdate();
        if (k != 0) {
            System.out.println("Payment Done");
        } else {
            System.out.println("Error Accurre..............");
        }
    }

    private int checkCard(long c_no) throws Exception {
        String sql = "{call getCard(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setLong(1, c_no);
        return cst.executeUpdate();
    }
}