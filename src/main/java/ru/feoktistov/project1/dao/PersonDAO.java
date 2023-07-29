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
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public List<Book> getBooksByPerson(int personId) {
        String query = "SELECT b.name, b.author, b.year FROM Book b WHERE person_id=?";
        return jdbcTemplate.query(query, new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_birthday) VALUES(?, ?)",
                person.getName(), person.getYearBirthday());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_birthday=? WHERE id=?",
                person.getName(), person.getYearBirthday(), id);
    }

    public Person find(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}