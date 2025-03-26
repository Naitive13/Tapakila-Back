package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    private Datasource datasource;

    @Override
    public List<Reservation> getALl() {
        String sql = """
                SELECT reservation_id, user_id, ticket_id, reservation_date from reservation_details;
                """;

        List<Reservation> reservationList = new ArrayList<>();

        try (Connection connection = datasource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reservationList.add(new Reservation(
                        rs.getString("reservation_id"),
                        rs.getString("user_id"),
                        rs.getString("ticket_id"),
                        rs.getTimestamp("reservation_date").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }
}
