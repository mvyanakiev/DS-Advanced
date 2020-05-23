package models;

public class Book {
    private String key;
    private String author;
    private String title;
    private boolean isSold;
    private int priceCents;

    public Book(String author, String title, int priceCents) {
        this.key = makeKey(author, title);
        this.author = author;
        this.title = title;
        this.priceCents = priceCents;
    }

    private String makeKey(String author, String title) {
        return  author + "_" + title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSold() {
        return isSold;
    }

    public Book setSold(boolean sold) {
        isSold = sold;
        return this;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }
}
