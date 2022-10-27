package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

@Repository
public class SessionsDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(SessionsDBStore.class.getName());
    private static final String ADD = "INSERT INTO sessions(name, photo) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM sessions";
    private static final String FIND_BY_ID = SELECT_ALL + " WHERE id = ?";

    public SessionsDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

        public Session add(Session session) {
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps =  cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, session.getName());
                ps.setBytes(2, session.getPhoto());
                ps.execute();
                try (ResultSet id = ps.getGeneratedKeys()) {
                    if (id.next()) {
                       session.setId(id.getInt(1));
                    }
                }
            } catch (Exception e) {
                LOG.error("Exception in SessionsDBStore.add(Session session)", e);
            }
            return session;
    }

    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    Session session = new Session();
                    session.setId(it.getInt("id"));
                    session.setName(it.getString("name"));
                    session.setPhoto(it.getBytes("photo"));
                    sessions.add(session);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in SessionsDBStore.findAll()", e);
        }
        return sessions;
    }

    public Session findById(Integer id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    Session session = new Session();
                    session.setId(it.getInt("id"));
                    session.setName(it.getString("name"));
                    session.setPhoto(it.getBytes("photo"));
                    return session;
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in SessionDBStore.findById(Integer id)", e);
        }
        return null;
    }

}

