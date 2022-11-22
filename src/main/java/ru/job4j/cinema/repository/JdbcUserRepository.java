package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final DataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcUserRepository.class.getName());
    private static final String ADD = "INSERT INTO users(username, email, phone) VALUES (?, ?, ?)";
    private static final String FIND_EMAIL_PHONE = "SELECT * FROM users WHERE email = ? AND phone = ?";

    public JdbcUserRepository(DataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    result = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in JdbcUserRepository.add()", e);
        }
        return result;
    }

    public Optional<User> findUserByEmailAndPhone(String email, String phone) {
        Optional<User> result = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_EMAIL_PHONE)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in JdbcUserRepository.findUserByEmailAndPhone()", e);
        }
        return result;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}