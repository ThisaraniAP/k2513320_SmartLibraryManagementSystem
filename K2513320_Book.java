/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */
import java.util.*;

public class K2513320_Book implements K2513320_BookComponent {

    private int bookID;
    private String title;
    private String author;
    private String category;
    private String isbn;

    private K2513320_BookState state;
    private List<String> borrowHistory;

    private List<String> tags;
    private String edition;
    private List<String> reviews;

    private K2513320_Book(K2513320_BookBuilder builder) {
        this.bookID = builder.bookID;
        this.title = builder.title;
        this.author = builder.author;
        this.category = builder.category;
        this.isbn = builder.isbn;

        this.tags = builder.tags;
        this.edition = builder.edition;
        this.reviews = builder.reviews;

        this.state = new K2513320_AvailableState();
        this.borrowHistory = new ArrayList<>();
    }

    public static class K2513320_BookBuilder {
        private int bookID;
        private String title;
        private String author;
        private String category;
        private String isbn;

        private List<String> tags = new ArrayList<>();
        private String edition;
        private List<String> reviews = new ArrayList<>();

        public K2513320_BookBuilder(int bookID, String title, String author) {
            this.bookID = bookID;
            this.title = title;
            this.author = author;
        }

        public K2513320_BookBuilder category(String category) {
            this.category = category;
            return this;
        }

        public K2513320_BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public K2513320_BookBuilder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public K2513320_BookBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public K2513320_BookBuilder addReview(String review) {
            this.reviews.add(review);
            return this;
        }

        public K2513320_Book build() {
            return new K2513320_Book(this);
        }
    }

    @Override
    public String getDescription() {
        return title;
    }

    public String getStateName() {
        return state.getStateName();
    }

    public void borrow() {
        state.borrowBook(this);
        borrowHistory.add("Borrowed");
    }

    public void returnBook() {
        state.returnBook(this);
        borrowHistory.add("Returned");
    }

    public void reserve() {
        state.reserveBook(this);
        borrowHistory.add("Reserved");
    }

    public int getBookID() { return bookID; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getIsbn() { return isbn; }
    public K2513320_BookState getState() { return state; }
    public void setState(K2513320_BookState s) { state = s; }
    public List<String> getBorrowHistory() { return borrowHistory; }
    public List<String> getTags() { return tags; }
    public String getEdition() { return edition; }
    public List<String> getReviews() { return reviews; }
}
