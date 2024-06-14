import java.util.Scanner;

class Main {
    static void list() {
        String cyanColor = "\u001B[36m"; // Cyan text color
        String resetFormat = "\u001B[0m"; // Reset formatting

        System.out.println("-------------------------------");
        // Box top line
        System.out.println(cyanColor + "┌───────────────────────────────┐" + resetFormat);

        // Menu options with formatting
        System.out.printf(cyanColor + "│ %-29s │%n", "------Make choice----");
        System.out.printf(cyanColor + "│ %-29s │%n", "1. For See List of Bikes");
        System.out.printf(cyanColor + "│ %-29s │%n", "2. For Make Purchase");
        System.out.printf(cyanColor + "│ %-29s │%n", "3. Generate Bill");
        System.out.printf(cyanColor + "│ %-29s │%n", "4. Employee");
        System.out.printf(cyanColor + "│ %-29s │%n", "5. Payment");
        System.out.printf(cyanColor + "│ %-29s │%n", "6. Exit");

        // Box bottom line
        System.out.println(cyanColor + "└───────────────────────────────┘" + resetFormat);
        System.out.println("-------------------------------");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Employee e = new Employee();
        System.out.println(
                "\u001B[38;2;255;0;0m+---------------------------------------------------------------------------+");
        System.out
                .println("|                           ! Welcome To Bike Booking System !              |");
        System.out.println("+---------------------------------------------------------------------------+\u001B[0m");

        Order o = new Order();
        boolean choice = true;
        while (choice) {
            list();
            int i = sc.nextInt();
            sc.nextLine();
            switch (i) {
                case 1:
                    e.ListBike();
                    break;

                case 2:
                    o.placeOrder();
                    break;
                case 3:
                    new ListCustomer().GenerateBill(sc);
                    break;

                case 4:
                    e.login();
                    break;
                case 5:
                    o.coplatePayment();
                    break;
                case 6:
                    choice = false;
                    sc.close();
                    break;
                default:
                    System.out.println("Make Correct Choice");
                    break;
            }
        }
        System.out.println(
                "------------------------------------------------------------------------------------------------------");
        sc.close();
    }
}