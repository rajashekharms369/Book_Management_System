package com.example.book_store_management.controller;

import com.example.book_store_management.entity.Book;
import com.example.book_store_management.entity.MyBookList;
import com.example.book_store_management.service.BookService;
import com.example.book_store_management.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MyBookListService myBookListService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/book_register")
    public String bookRegister() {
        return "book_register";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook(){
        List<Book> list = bookService.getAllBook();
        return new ModelAndView("booklist", "book", list);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b) {
        bookService.save(b);
        return "redirect:/available_books";
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model)
    {
        List<MyBookList>list = myBookListService.getAllMyBooks();
        model.addAttribute("book",list);
        return "myBooks";
    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Book b = bookService.getBookById(id);
        MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
        myBookListService.saveMyBooks(mb);
        return "redirect:/my_books";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id,Model model) {
        Book b = bookService.getBookById(id);
        model.addAttribute("book",b);
        return "bookEdit";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id")int id) {
        bookService.deleteById(id);
        return "redirect:/available_books";
    }

}