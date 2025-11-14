package com.gabriel.catalog.model;

public class Book {
    private Long id;
    private String title;
    private String author;
    private int yearPublished;
    private Long genreId;
    private String genre;
    private String synopsis;

    public Book() {}

    public Book(Long id, String title, String author, int yearPublished, String genre, String synopsis) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genre = genre;
        this.synopsis = synopsis;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYearPublished() { return yearPublished; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }
    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }
    public Long getGenreId() { return genreId; }
    public void setGenreId(Long genreId) { this.genreId = genreId; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}