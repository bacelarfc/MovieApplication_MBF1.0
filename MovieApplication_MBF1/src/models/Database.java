package models;

import models.Movie;
import models.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Database implements Serializable {
    private ArrayList<Movie> movies;
    private ArrayList<User> users;
    //call the initialized movie list inside, so it can get initialized by the constructor

    public Database() {
        this.movies = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Method to add users to a user list, adding the user
    // will happen upon registration or by reading all users from an existing file (user-data.txt)
    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String toString() {
        return "Models.Database " +
                "movies=" + movies +
                ", users=" + users;
    }
}