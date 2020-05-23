package core;

import models.Book;

import java.util.*;
import java.util.stream.Collectors;

public class BookLand implements BookStore {
    private Set<Book> bookSet;
    private Map<String, Book> byKey;
    private Map<String, Set<Book>> byAuthor;
    private Map<String, Set<Book>> byTitle;

    public BookLand() {
        this.bookSet = new TreeSet<>(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
        this.byKey = new HashMap<>();
        this.byAuthor = new TreeMap<>();
        this.byTitle = new TreeMap<>();
    }


    @Override
    public void add(Book book) {
        if (this.bookSet.contains(book)) {
            throw new IllegalArgumentException();
        }

        this.bookSet.add(book);
        this.byKey.put(book.getKey(), book);

        this.byAuthor.putIfAbsent(book.getAuthor(), new TreeSet<>(Comparator.comparing(Book::getPriceCents).thenComparing(Book::getTitle)));
        this.byAuthor.get(book.getAuthor()).add(book);

        this.byTitle.putIfAbsent(book.getTitle(), new TreeSet<>(Comparator.comparing(Book::getPriceCents).thenComparing(Book::getTitle)));
        this.byTitle.get(book.getTitle()).add(book);
    }

    @Override
    public boolean contains(Book book) {
        return this.bookSet.contains(book);
    }

    @Override
    public int size() {
        return this.bookSet.size();
    }

    @Override
    public Book getBook(String key) {
        Book book = this.byKey.get(key);

        if (book == null) {
            throw new IllegalArgumentException();
        }

        return book;
    }

    @Override
    public Book remove(String key) {
        Book book = this.byKey.remove(key);

        if (book == null) {
            throw new IllegalArgumentException();
        }

        this.bookSet.remove(book);
        this.byTitle.get(book.getTitle()).remove(book);
        this.byAuthor.get(book.getAuthor()).remove(book);

        return book;
    }

    @Override
    public Collection<Book> removeSold() {
        List<Book> result = new ArrayList<>();

        for (Book book : bookSet) {
            if (book.isSold()){
                result.add(book);

                this.byKey.remove(book.getKey());
                this.byTitle.get(book.getTitle()).remove(book);
                this.byAuthor.get(book.getAuthor()).remove(book);
            }
        }

        for (Book book : result) {
            this.bookSet.remove(book);
        }

        return result;
    }

    @Override
    public void sellBook(String key) {
        Book book = this.byKey.get(key);

        if (book == null) {
            throw new IllegalArgumentException();
        }

        book.setSold(true);
        this.bookSet.add(book);
    }

    @Override
    public void replace(Book oldBook, Book newBook) {

        if (this.bookSet.contains(newBook)) {
            throw new UnsupportedOperationException();
        }

        if (!this.bookSet.contains(oldBook)) {
            throw new UnsupportedOperationException();
        }

        this.remove(oldBook.getKey());
        this.add(newBook);
    }

    @Override
    public Collection<Book> getAllByAuthor(String author) {
        Set<Book> result = this.byAuthor.get(author);

        if (result == null){
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Collection<Book> getAllByTitle(String title) {
        Set<Book> result =  this.byTitle.get(title);

        if (result == null){
            return new ArrayList<>();
        } else {
            return result;
        }
    }

    @Override
    public Collection<Book> getByPriceRange(int lowerBoundCents, int upperBoundCents) {
        return this.bookSet.stream()
                .filter(b -> b.getPriceCents() >= lowerBoundCents && b.getPriceCents() < upperBoundCents)
                .sorted(Comparator.comparing(Book::getPriceCents).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Book> getAllOrderedByAuthorThenByTitle() {
        if (this.bookSet.isEmpty()) {
            return new ArrayList<>();
        } else {
            return this.bookSet;
        }
    }
}
