package com.example.spring_jpa_notes;

import com.example.spring_jpa_notes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // RowMapper example
    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        }
    };

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, rn) -> {
            return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        });
    }

    public User findById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new BeanPropertyRowMapper<>(User.class),
                id
        );
    }

    public String findNameById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT name FROM users WHERE id = ?",
                String.class,
                id
        );
    }

    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (name, email) VALUES (?, ?)",
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public void updateEmailTransactional(int id, String email) {
        jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ?", email, id);

        // Simulate exception to trigger rollback
        if (email.contains("error")) {
            throw new RuntimeException("Simulated error for rollback");
        }
    }

    public List<User> findByNameNamed(String name) {
        String sql = "SELECT * FROM users WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(User.class));
    }

    public void batchInsert(List<User> users) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO users (name, email) VALUES (?, ?)",
                users,
                users.size(),
                (ps, user) -> {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                }
        );
    }
}