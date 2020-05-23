package core;

import models.Book;

import java.util.Collection;

public class BookLand implements BookStore {
    @Override
    public void add(Book book) {

    }

    @Override
    public boolean contains(Book book) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Book getBook(String key) {
        return null;
    }

    @Override
    public Book remove(String key) {
        return null;
    }

    @Override
    public Collection<Book> removeSold() {
        return null;
    }

    @Override
    public void sellBook(String key) {

    }

    @Override
    public void replace(Book oldBook, Book newBook) {

    }

    @Override
    public Collection<Book> getAllByAuthor(String author) {
        return null;
    }

    @Override
    public Collection<Book> getAllByTitle(String title) {
        return null;
    }

    @Override
    public Collection<Book> getByPriceRange(int lowerBoundCents, int upperBoundCents) {
        return null;
    }

    @Override
    public Collection<Book> getAllOrderedByAuthorThenByTitle() {
        return null;
    }
}
