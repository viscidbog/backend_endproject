package jp.hh.endproject.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Set;

public interface HobbyRepository extends CrudRepository<Hobby, Long> {
    @Query(value = "SELECT * FROM HOBBY where LOWER(title) like %:hobbyTitle%", nativeQuery = true)
    List<Hobby> findByTitle(String hobbyTitle);

    @Query(value = "SELECT * FROM HOBBY where LOWER(author) like %:hobbyAuthor%", nativeQuery = true)
    List<Hobby> findByAuthor(String hobbyAuthor);

    List<Hobby> findByPublicationYear(String publicationYear);

    List<Hobby> findByRecommend(String recommend);

    @Query(value = "SELECT * FROM HOBBY where LOWER(description) like %:description%", nativeQuery = true)
    List<Hobby> findByDescription(String description);

    List<Hobby> findByHobbyGenres(Set<Genre> hobbyGenres);

    List<Hobby> findByHobbyTypeName(String name);

}
