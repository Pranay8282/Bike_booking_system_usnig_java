
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
// import java.util.Iterator;
import java.util.Scanner;

class BikeModels {
    String name, compnay;
    double price;
    int avarge;

    public BikeModels(String name, String compnay, double price, int avarge) {
        this.name = name;
        this.compnay = compnay;
        this.price = price;
        this.avarge = avarge;
    }

    public String getName() {
        return name;
    }

    public String getCompnay() {
        return compnay;
    }

    public double getPrice() {
        return price;
    }

    public int getAvarge() {
        return avarge;
    }

    @Override
    public String toString() {
        return " name = " + name + "   |   compnay= " + compnay + "   |   price  =   " + price + " /-   |   avarge  =  "
                + avarge + " KM/H";
    }

}

class Bike {
    ArrayList<BikeModels> bikes;
    Scanner sc = new Scanner(System.in);

    public Bike() throws Exception {
        bikes = new ArrayList<>();
        setModels();
        // BikeDataBase();
    }

    public void setModels() {
        bikes.add(new BikeModels("Honda Activa 6G", "Honda", 70000, 60));
        bikes.add(new BikeModels(" Royal Enfield Classic 350", "Royal Enfield", 140000, 35));
        bikes.add(new BikeModels("YZF R15", "YAMAHA", 110000, 45));
        bikes.add(new BikeModels(" Bajaj Pulsar NS200", "Bajaj", 100000, 40));
        bikes.add(new BikeModels("Suzuki Gixxer SF", "Suzuki", 120000, 50));
        bikes.add(new BikeModels("KTM Duke 390", " KTM", 200000, 30));
        bikes.add(new BikeModels("TVS Apache RTR 160", "TVS", 80000, 50));
        bikes.add(new BikeModels("Kawasaki Ninja 300", "Kawasaki", 300000, 30));
        bikes.add(new BikeModels("Harley-Davidson Street 750", "Harley-Davidson", 500000, 20));
        bikes.add(new BikeModels("TVS Jupiter", "TVS", 65000, 55));
        bikes.add(new BikeModels("Hero Xtreme 160R", "Hero", 90000, 50));
        bikes.add(new BikeModels("Suzuki Access 125", "Suzuki", 75000, 50));
        bikes.add(new BikeModels("KTM RC 200", "KTM", 220000, 30));
        bikes.add(new BikeModels("Bajaj Dominar 400", "Bajaj", 170000, 35));
        bikes.add(new BikeModels("Yamaha FZS-FI", "Yamaha", 95000, 40));
        bikes.add(new BikeModels("Royal Enfield Himalayan", "Royal Enfield", 180000, 30));
        bikes.add(new BikeModels("Hero Maestro Edge", "Hero", 60000, 45));
        bikes.add(new BikeModels("Harley-Davidson Iron 883", "Harley-Davidson", 750000, 18));
        bikes.add(new BikeModels("Honda CB Unicorn 150", "Honda", 80000, 50));
    }

    // To Add Bike Model In
    // DataBase.................................................................................................
    // public void BikeDataBase() throws Exception {
    // String s = "com.mysql.cj.jdbc.Driver";
    // Class.forName(s);
    // String dburl = "jdbc:mysql://localhost:3306/bike_booking";
    // String name = "root";
    // String pass = "";
    // Connection con = DriverManager.getConnection(dburl, name, pass);

    // /* To Confirm Connection */
    // // if (con != null) {
    // // System.out.println("Connection Done");
    // // } else {
    // // System.out.println("Connection Not Done");
    // // }
    // String sql = "insert into bikemodel (c_name,m_name, price,avarage)
    // values(?,?,?,?)";
    // PreparedStatement pst = con.prepareStatement(sql);

    // for (BikeModels k : bikes) {
    // pst.setString(1, k.compnay);
    // pst.setString(2, k.name);
    // pst.setLong(3, (long) k.price);
    // pst.setInt(4, k.avarge);

    // int r = pst.executeUpdate();

    // }
    // }
    public void ListBike(Connection con) throws Exception {
        String sql = "{call getBikesDetails()}";
        CallableStatement cst = con.prepareCall(sql);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println("+------------+-------------------+-------------------+------------+------------+");
            System.out.printf("| %-10s | %-17s | %-17s | %-10s | %-10s |\n", "Model Id", "Company Name", "Model Name",
                    "Price", "Average");
            System.out.println("+------------+-------------------+-------------------+------------+------------+");
            System.out.printf("| %-10d | %-17s | %-17s | %-10.2f | %-10.2f |\n", rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getDouble(4), rs.getDouble(5));
            System.out.println("+------------+-------------------+-------------------+------------+------------+");

        }
        getDesc(con);

    }

    private void getDesc(Connection con) throws Exception {
        System.out.println("Enter Your Choice To Get Description");
        int k = sc.nextInt();
        String sql = "{call getDesc(?)}";
        CallableStatement cst = con.prepareCall(sql);
        cst.setInt(1, k);
        ResultSet rs = cst.executeQuery();

        if (!rs.equals(null)) {
            while (rs.next()) {
                System.out.println("-------------------Desc------------------");
                System.out.println("\u001B[36m" + rs.getString(1) + "\u001B[0m");
            }
        } else {
            System.out.println("-----------------------Decs Not Found-------------------");
        }
    }

    public void PerchaseList(Connection con) throws Exception {
        String sql = "{call getBikesDetails()}";
        CallableStatement cst = con.prepareCall(sql);
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println("+------------+-------------------+-------------------+------------+------------+");
            System.out.printf("| %-10s | %-17s | %-17s | %-10s | %-10s |\n", "Model Id", "Company Name", "Model Name",
                    "Price", "Average");
            System.out.println("+------------+-------------------+-------------------+------------+------------+");
            System.out.printf("| %-10d | %-17s | %-17s | %-10.2f | %-10.2f |\n", rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getDouble(4), rs.getDouble(5));
            System.out.println("+------------+-------------------+-------------------+------------+------------+");

        }
    }

}
