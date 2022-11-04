package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

@Repository
public class TicketDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(SessionsDBStore.class.getName());
    private static final String ADD = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public boolean addTicket(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getPosRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (SQLIntegrityConstraintViolationException i) {
            return false;
        } catch (Exception e) {
            LOG.error("Exception in TicketDBStore.add()", e);
        }
        return true;
    }
}
