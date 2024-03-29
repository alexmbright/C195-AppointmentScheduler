package helper.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class makes database calls for contact operations.
 *
 * @author Alex Bright
 */
public class ContactDB {

    /**
     * Calls to database and returns list of all contacts.
     *
     * @return observable list of all contacts
     */
    public static ObservableList<Contact> getAll() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        try {
            String q = "select * from contacts";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("contact_id");
                String name = res.getString("contact_name");
                String email = res.getString("email");

                contacts.add(new Contact(id, name, email));
            }
        } catch (SQLException e) {
            System.out.println("contact getAll() error: " + e.getMessage());
        }
        return contacts;
    }

    /**
     * Calls to database and returns matching instance of Contact.
     *
     * @param id ID of contact to find
     * @return instance of Contact if successful, otherwise null
     */
    public static Contact getContact(int id) {
        try {
            String q = "select * from contacts where contact_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String name = res.getString("contact_name");
                String email = res.getString("email");

                return new Contact(id, name, email);
            }
        } catch (SQLException e) {
            System.out.println("getContact() error: " + e.getMessage());
        }
        return null;
    }

}
