package jp.hh.endproject.domain;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hobbyId;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String publicationYear;
    @NotBlank
    private String description;
    private String recommend;

    @ManyToMany
    @JoinTable(name = "hobby_genre", joinColumns = { @JoinColumn(name = "hobbyId") }, inverseJoinColumns = {
            @JoinColumn(name = "genreId") })
    @NotEmpty
    public Set<Genre> hobbyGenres = new HashSet<Genre>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobbyType")
    private HobbyType hobbyType;

    public Hobby() {
    }

    public Hobby(String title, String author, String publicationYear, String description,
            String recommend,
            HobbyType hobbyType,
            Set<Genre> hobbyGenres) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.description = description;
        this.recommend = recommend;
        this.hobbyType = hobbyType;
        this.hobbyGenres = hobbyGenres;
    }

    public Long getHobbyId() {
        return hobbyId;
    }

    public void setHobbyId(Long hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Set<Genre> getHobbyGenres() {
        return hobbyGenres;
    }

    public void setHobbyGenres(Set<Genre> hobbyGenres) {
        this.hobbyGenres = hobbyGenres;
    }

    public HobbyType getHobbyType() {
        return hobbyType;
    }

    public void setHobbyType(HobbyType hobbyType) {
        this.hobbyType = hobbyType;
    }

}
