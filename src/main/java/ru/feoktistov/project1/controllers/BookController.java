package ru.feoktistov.project1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.feoktistov.project1.dao.BookDAO;
import ru.feoktistov.project1.dao.PersonDAO;
import ru.feoktistov.project1.models.Book;
import ru.feoktistov.project1.models.Person;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDAO.find(id);

        model.addAttribute("book", book);

        if (book.getPersonId() != null) {
            Person owner = bookDAO.getOwner(id).get();
            model.addAttribute("person", owner);
            System.out.println(owner);
        } else {
            List<Person> people = personDAO.index();
            model.addAttribute("people", people);
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        Book book = bookDAO.find(id);
        model.addAttribute("book", book);

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/set")
    public String set(@ModelAttribute("person") Person person,
                         @PathVariable("id") int id) {

        Book book = bookDAO.find(id);
        System.out.println(person);
        System.out.println(book);
        book.setPersonId(person.getId());
        System.out.println(book);
        bookDAO.update(id, book);

        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/unset")
    public String set(@PathVariable("id") int id) {

        Book book = bookDAO.find(id);
        book.setPersonId(null);
        bookDAO.update(id, book);

        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }
}
