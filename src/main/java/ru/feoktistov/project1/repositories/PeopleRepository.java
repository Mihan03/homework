package ru.feoktistov.project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.feoktistov.project1.models.Person;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
