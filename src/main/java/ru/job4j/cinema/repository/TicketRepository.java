package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class TicketRepository {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(SessionRepository.class.getName());
    private static final String ADD = "INSERT INTO ticket(session_id, pos_row, cell, user_id) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID = "SELECT * FROM ticket WHERE id = ?";

    public TicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<Ticket> addTicket(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
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
                    result = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in TicketRepository.add()", e);
        }
        return result;
    }

    public Optional<Ticket> findById(Integer id) {
        Optional<Ticket> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(it.getInt("id"));
                    ticket.setSessionId(it.getInt("session_id"));
                    ticket.setPosRow(it.getInt("pos_row"));
                    ticket.setCell(it.getInt("cell"));
                    ticket.setUserId(it.getInt("user_id"));
                    result = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in TicketRepository.findById()", e);
        }
        return result;
    }
}
