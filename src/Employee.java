
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class Employee {
    static Connection con;

    Employee() throws Exception {
        String s = "com.mysql.cj.jdbc.Driver";
        Class.forName(s);
        String dburl = "jdbc:mysql://localhost:3306/bike_booking";
        String dbname = "root";
        String pass = "";
        con = DriverManager.getConnection(dburl, dbname, pass);
    }

    void login() throws Exception {
        Scanner sc = new Scanner(System.in);

        String sname = "booking_099";
        String pass = "b_0_8";
        boolean c = true;

        System.out.println("Enter User Name");
        String name = sc.nextLine();
        if (name.equals(sname)) {
            System.out.println("Enter PassWord");
            String pass1 = sc.nextLine();

            if (pass.equals(pass1)) {
                while (c) {
                    listEmployee();
                    System.out.println("Make Your Choice");
                    int i = sc.nextInt();
                    sc.nextLine();
                    switch (i) {
                        case 1:
                            BikeModels();
                            break;
                        case 2:
                            CustomerList();
                            break;

                        case 3:
                            getPerticuler(sc);
                            break;
                        case 4:
                            add_model(sc);
                            break;
                        case 5:
                            c = false;
                            break;
                        default:
                            System.out.println("Make Correct Choice");
                            break;
                    }

                }
            } else {
                System.out.println("Incorrect Password.");
            }
        } else {
            System.out.println(" Enter Valid UserName");
        }

    }

    static void listEmployee() {
        String orangeColor = "\u001B[38;5;208m";
        String resetFormat = "\u001B[0m"; // Reset formatting

        System.out.println("------------------------------------------------------------");
        // Box top line
        System.out
                .println(orangeColor + "┌────────────────────────────────────────────────────────────┐" + resetFormat);

        // Menu options with formatting
        System.out.printf(orangeColor + "│ %-56s │%n", "------------------List Employee Action--------------------");
        System.out.printf(orangeColor + "│ %-58s │%n", "1. For See All Bikes Model");
        System.out.printf(orangeColor + "│ %-58s │%n", "2. For See All Customer Details");
        System.out.printf(orangeColor + "│ %-58s │%n", "3. For Get Particular Customer Details");
        System.out.printf(orangeColor + "│ %-58s │%n", "4. For Add New Model Of Bike");
        System.out.printf(orangeColor + "│ %-58s │%n", "5. Exit");

        // Box bottom line
        System.out.println(orangeColor
                + "└────────────────────────────────────────────────────────────┘" + resetFormat);
        System.out.println("------------------------------------------------------------");
    }

    static void BikeModels() throws Exception {
        new Bike().ListBike(con);
    }

    void ListBike() throws Exception {
        new Bike().ListBike(con);
    }

    static void add_model(Scanner sc) throws Exception {
        System.out.println("Enter Compnay");
        String com = sc.nextLine();
        System.out.println("Enter Model Name");
        String model = sc.nextLine();
        System.out.println("Enter Price (INR)");
        long price = sc.nextLong();
        System.out.println("Enter AVG");
        int avg = sc.nextInt();
        if (com == null || model == null || price < 0 || avg < 0) {
            System.out.println("Make Correct Input And Try Again");
        } else {
            String sql = "insert into bikemodel (c_name,m_name,price,avarage) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, com);
            pst.setString(2, model);
            pst.setLong(3, price);
            pst.setLong(4, avg);
            sc.nextLine();
            int k = pst.executeUpdate();
            System.out.println("Enter Descripotion Of Code");
            String Desc = sc.nextLine();
            String sql2 = "{call Adddesc(?,?)}";
            CallableStatement cst = con.prepareCall(sql2);
            int id = 0;
            getId(model, id);
            cst.setInt(1, id);
            cst.setString(2, Desc);
            int s = cst.executeUpdate();
            if (k > 0 && s > 0) {
                System.out.println("--------------New Bike Added---------------------");
            } else {
                System.out.println("Error In Inserion");
            }

        }
    }

    private static void getId(String model, int id) throws Exception {
        String sql = "{call GetNo(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setString(1, model);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);
        }

    }

    static void CustomerList() throws Exception {
        String sql = "select * from customer";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs == null) {
            System.out.println("No customer in database");
        } else {
            while (rs.next()) {
                System.out.println(
                        "\u001B[38;2;148;0;211m-------------------------------------------------------\u001B[0m");
                System.out.println(
                        "\u001B[38;2;148;0;211mid := " + rs.getInt(1) + " name := " + rs.getString(2) + " Model := "
                                + rs.getString(3) + " mobile := " + rs.getLong(4) + " Order No := " + rs.getLong(5)
                                + " Company Name := " + rs.getString(6) + " Amount := " + rs.getLong(7));
                System.out.println(
                        "\u001B[38;2;148;0;211m-------------------------------------------------------\u001B[0m");

            }
        }
    }

    public static void getPerticuler(Scanner sc) throws Exception {

        System.out.println("Enter Which Customer Detail You Want.");
        String name = sc.nextLine();
        String sql = "{call getCustomerDetails(?)}";
        CallableStatement cst = con.prepareCall(sql);

        cst.setString(1, name);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println("\u001B[38;2;255;165;0m---------------Your Customer----------------\u001B[0m");
            System.out.println("\u001B[38;2;255;165;0mname :=\u001B[0m " + rs.getString(2));
            System.out.println("\u001B[38;2;255;165;0mModel Name :=\u001B[0m " + rs.getString(3));
            System.out.println("\u001B[38;2;255;165;0mMobile Number:=\u001B[0m " + rs.getLong(4));
            System.out.println("\u001B[38;2;255;165;0mOrder Number:=\u001B[0m " + rs.getLong(5));
            System.out.println("\u001B[38;2;255;165;0mCompany :=\u001B[0m " + rs.getString(6));
            System.out.println("\u001B[38;2;255;165;0mAmount :=\u001B[0m " + rs.getLong(7));
            System.out.println("\u001B[38;2;255;165;0m-----------------------------------\u001B[0m");

        }

    }

    public void pListBike(Connection con) throws Exception {
        new Bike().PerchaseList(con);
    }
}