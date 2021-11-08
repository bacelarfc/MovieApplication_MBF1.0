package models;

import models.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String email;
    private String password;
    private List<Movie> favMovieList;
    private List<MovieRecord> movieRecords;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.favMovieList = new ArrayList<>();
        this.movieRecords = new ArrayList<>();
    }

    // DEFAULT constructor
    public User() {
        this.email = "";
        this.password = "";
        this.name = "";
        this.favMovieList = new ArrayList<>();
        this.movieRecords = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getFavMovieList() {
        return favMovieList;
    }

    public void setFavMovieList(List<Movie> favMovieList) {
        this.favMovieList = favMovieList;
    }

    public List<MovieRecord> getMovieRecords() {
        return movieRecords;
    }

    public void setMovieRecords(List<MovieRecord> movieRecords) {
        this.movieRecords = movieRecords;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Email: " + this.email;
    }
}
