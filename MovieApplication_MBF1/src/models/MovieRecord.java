package models;

import models.Movie;
import models.User;

import java.io.Serializable;
import java.util.Date;

//

public class MovieRecord implements Serializable {

    private Movie movie;
    private Date datePlayed;


    public MovieRecord(Movie movie, Date datePlayed) {
        this.movie = movie;
        this.datePlayed = datePlayed;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Date getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(Date datePlayed) {
        this.datePlayed = datePlayed;
    }

    public String toString() {
        return "Title: " + movie.getTitle() + " was played on " + datePlayed + " by a user: /"+"/\n";
    }
}
