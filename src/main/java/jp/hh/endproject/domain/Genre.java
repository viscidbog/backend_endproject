package jp.hh.endproject.domain;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long genreId;
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "hobbyGenres")
    private Set<Hobby> genreHobbies = new HashSet<Hobby>();

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Hobby> getGenreHobbies() {
        return genreHobbies;
    }

    public void setGenreHobbies(Set<Hobby> genreHobbies) {
        this.genreHobbies = genreHobbies;
    }

}
