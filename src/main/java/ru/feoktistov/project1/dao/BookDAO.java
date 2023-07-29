package ru.feoktistov.project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.feoktistov.project1.models.Book;
import ru.feoktistov.project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=?, person_id=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), book.getPersonId(), id);
    }

    public Book find(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> getOwner(int idBook) {
        String sql = "SELECT Person.name, Person.id FROM person " +
                "JOIN book on book.person_id = person.id WHERE Book.id=?";

        return jdbcTemplate.query(sql, new Object[]{idBook},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }
}