package helper.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class makes database calls for division operations.
 *
 * @author Alex Bright
 */
public class DivisionDB {

    /**
     * Calls to database and returns list of all divisions.
     *
     * @return observable list of all divisions
     */
    public static ObservableList<Division> getAll() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String q = "select * from first_level_divisions";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("division_id");
                String division = res.getString("division");
                int countryId = res.getInt("country_id");

                divisions.add(new Division(id, division, countryId));
            }
        } catch (SQLException e) {
            System.out.println("divisions getAll() error: " + e.getMessage());
        }
        return divisions;
    }

    /**
     * Calls to database and returns list of matching divisions by country ID.
     *
     * @param id existing country ID
     * @return observable list of matching divisions by country ID
     */
    public static ObservableList<Division> getAllByCountry(int id) {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String q = "select * from first_level_divisions where country_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int divId = res.getInt("division_id");
                String division = res.getString("division");

                divisions.add(new Division(divId, division, id));
            }
        } catch (SQLException e) {
            System.out.println("division getByCountry() error: " + e.getMessage());
        }
        return divisions;
    }

    /**
     * Calls to database and returns matching instance of Division.
     *
     * @param id ID of division to find
     * @return matching instance of Division if successful, otherwise null
     */
    public static Division get(int id) {
        try {
            String q = "select * from first_level_divisions " +
                    "where division_id = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setInt(1, id);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                String division = res.getString("division");
                int countryId = res.getInt("country_id");

                return new Division(id, division, countryId);
            }
        } catch (SQLException e) {
            System.out.println("division get() error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Calls to database and returns matching instance of Division.
     *
     * @param division name of division to find
     * @return matching instance of Division if successful, otherwise null
     */
    public static Division get(String division) {
        try {
            String q = "select * from first_level_divisions " +
                    "where division = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(q);
            ps.setString(1, division);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int id = res.getInt("division_id");
                division = res.getString("division");
                int countryId = res.getInt("country_id");

                return new Division(id, division, countryId);
            }
        } catch (SQLException e) {
            System.out.println("division get() error: " + e.getMessage());
        }
        return null;
    }

}
