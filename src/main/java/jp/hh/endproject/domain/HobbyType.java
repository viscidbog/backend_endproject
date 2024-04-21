package jp.hh.endproject.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class HobbyType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hobbyTypeId;
    @NotNull
    public String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "hobbyType")
    private List<Hobby> hobbies;

    public HobbyType(String name) {
        this.name = name;
    }

    public HobbyType() {

    }

    public Long getHobbyTypeId() {
        return hobbyTypeId;
    }

    public void setHobbyTypeId(Long hobbyTypeId) {
        this.hobbyTypeId = hobbyTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

}
