import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = DBConnection.getConnection();

        while (true) {
            System.out.println("\n===== Food Bank System =====");
            System.out.println("1. Record Donation");
            System.out.println("2. Register Family");
            System.out.println("3. Distribute Food");
            System.out.println("4. View Current Stock");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            try {
                switch (ch) {

                    case 1:
                        System.out.print("Donor Name: ");
                        String donor = sc.nextLine();

                        System.out.print("Item: ");
                        String item = sc.nextLine();

                        System.out.print("Quantity (kg): ");
                        double qty = sc.nextDouble();

                        String q1 = "INSERT INTO donations(donor,item,qty_kg) VALUES(?,?,?)";
                        PreparedStatement ps1 = con.prepareStatement(q1);
                        ps1.setString(1, donor);
                        ps1.setString(2, item);
                        ps1.setDouble(3, qty);
                        ps1.executeUpdate();

                        System.out.println("Donation Recorded!");
                        break;

                    case 2:
                        System.out.print("Family Name: ");
                        String name = sc.nextLine();

                        System.out.print("Address: ");
                        String addr = sc.nextLine();

                        System.out.print("Family Size: ");
                        int size = sc.nextInt();

                        String q2 = "INSERT INTO families(name,address,size) VALUES(?,?,?)";
                        PreparedStatement ps2 = con.prepareStatement(q2);
                        ps2.setString(1, name);
                        ps2.setString(2, addr);
                        ps2.setInt(3, size);
                        ps2.executeUpdate();

                        System.out.println("Family Registered!");
                        break;

                    case 3:
                        System.out.print("Family ID: ");
                        int fid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Item: ");
                        String ditem = sc.nextLine();

                        System.out.print("Quantity (kg): ");
                        double dqty = sc.nextDouble();

                        String q3 = "INSERT INTO distributions(fam_id,item,qty_kg) VALUES(?,?,?)";
                        PreparedStatement ps3 = con.prepareStatement(q3);
                        ps3.setInt(1, fid);
                        ps3.setString(2, ditem);
                        ps3.setDouble(3, dqty);
                        ps3.executeUpdate();

                        System.out.println("Food Distributed!");
                        break;

                    case 4:
                        String q4 = "SELECT item, " +
                                "SUM(d.qty_kg) AS donated, " +
                                "IFNULL((SELECT SUM(qty_kg) FROM distributions WHERE item=d.item),0) AS distributed " +
                                "FROM donations d GROUP BY item";

                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(q4);

                        while (rs.next()) {
                            String it = rs.getString("item");
                            double donated = rs.getDouble("donated");
                            double distributed = rs.getDouble("distributed");

                            double stock = donated - distributed;

                            System.out.println(it + " : " + stock + " kg available");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}