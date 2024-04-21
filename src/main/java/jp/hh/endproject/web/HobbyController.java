package jp.hh.endproject.web;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import jp.hh.endproject.domain.HobbyRepository;
import jp.hh.endproject.domain.HobbyType;
import jp.hh.endproject.domain.Genre;
import jp.hh.endproject.domain.GenreRepository;
import jp.hh.endproject.domain.TypeRepository;
import jp.hh.endproject.domain.Hobby;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HobbyController {

    private final HobbyRepository hobbyRepository;
    private final GenreRepository genreRepository;
    private final TypeRepository typeRepository;

    public HobbyController(HobbyRepository hobbyRepository, GenreRepository genreRepository,
            TypeRepository typeRepository) {
        this.genreRepository = genreRepository;
        this.hobbyRepository = hobbyRepository;
        this.typeRepository = typeRepository;
    }

    // Login page
    @RequestMapping(value = "/login")
    public String logIn() {
        return "login";
    }

    // Show list of hobbies
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String showIndex(Model model) {
        model.addAttribute("hobbies", hobbyRepository.findAll());
        return "index";
    }

    // Create a new hobby, creating a link to genres and types as well
    @RequestMapping(value = "/addhobby", method = RequestMethod.GET)
    public String addHobby(Model model) {
        model.addAttribute("hobby", new Hobby());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("hobbytypes", typeRepository.findAll());
        return "addhobby";
    }

    // Save a hobby to list
    @RequestMapping(value = "/savehobby", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("hobby") Hobby hobby, BindingResult bindingresult, Model model) {
        if (bindingresult.hasErrors()) {
            model.addAttribute("genres", genreRepository.findAll());
            model.addAttribute("hobbytypes", typeRepository.findAll());
            return "addhobby";
        } else {
            hobbyRepository.save(hobby);
            return "redirect:index";
        }
    }

    // Delete a hobby by its ID value. End point has to be unique if other
    // controllers have similar functionality.
    @RequestMapping(value = "/delete/{hobbyId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletehobby(@PathVariable("hobbyId") Long hobbydel, Model model) throws Exception {
        hobbyRepository.deleteById(hobbydel);
        return "redirect:/index";
    }

    // Edit a hobby
    @RequestMapping(value = "/edithobby/{hobbyId}")
    public String editing(@PathVariable("hobbyId") Long hobbyId, Model model) {
        model.addAttribute("hobby", hobbyRepository.findById(hobbyId));
        model.addAttribute("hobbytypes", typeRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "edithobby";
    }

    // Go to hobbysearch page
    @RequestMapping(value = "/hobbysearch")
    public String searching(Model model) {
        model.addAttribute("hobbytypes", typeRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "hobbysearch";
    }

    // Find a hobby by title
    @RequestMapping(value = "/findhobbytitle/{title}")
    public String getHobbyByTitle(@RequestParam(value = "title", required = false) String title, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByTitle(title));
        return "index";
    }

    // Find a hobby by author
    @RequestMapping(value = "/findhobbyauthor/{author}")
    public String getHobbyByAuthor(@RequestParam(value = "author", required = false) String author, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByAuthor(author));
        return "index";
    }

    // Find a hobby by publication year
    @RequestMapping(value = "/findhobbypublicationYear/{publicationYear}")
    public String getHobbyByPublicationYear(
            @RequestParam(value = "publicationYear", required = false) String publicationYear, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByPublicationYear(publicationYear));
        return "index";
    }

    // Find a hobby by recommend
    @RequestMapping(value = "/findhobbyrecommend/{recommend}")
    public String getHobbyByRecommend(
            @RequestParam(value = "recommend", required = false) String recommend, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByRecommend(recommend));
        return "index";
    }

    // Find a hobby by description
    @RequestMapping(value = "/findhobbydescription/{description}")
    public String getHobbyByDescription(
            @RequestParam(value = "description", required = false) String description, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByDescription(description));
        return "index";
    }

    // Find a hobby by type
    @RequestMapping(value = "/findhobbytype/{hobbyType}")
    public String getHobbyByType(
            @RequestParam(value = "hobbyType", required = false) HobbyType hobbyType, Model model) {
        model.addAttribute("hobbies", hobbyRepository.findByHobbyTypeName(hobbyType.name));
        return "index";
    }

    // Find a hobby by genre
    // This is overly complex and clumsy, but it works.
    @RequestMapping(value = "/findhobbygenre/{hobbyGenres}")
    public String getHobbyByGenre(@RequestParam(value = "genre") Genre genre, Model model) {
        // Make an iterable that contains all hobbies
        Iterable<Hobby> lister = hobbyRepository.findAll();
        // Make an iterator for previous iterable
        Iterator<Hobby> iterator = lister.iterator();
        // Make a list to contain desired hobbies
        List<Hobby> checked = new ArrayList<Hobby>();
        // Loop through the iterable, checking if the searched genre is contained in
        // each hobby, adding hobby to list if it is
        while (iterator.hasNext()) {
            Hobby hobe = iterator.next();
            if (hobe.hobbyGenres.contains(genre)) {
                checked.add(hobe);
            }
        }
        // Return list containing desired hobbies
        model.addAttribute("hobbies", checked);
        return "index";
    }

    // RESTful service to get all hobbies
    @RequestMapping(value = "/allhobbies", method = RequestMethod.GET)
    public @ResponseBody List<Hobby> hobbyListRest() {
        return (List<Hobby>) hobbyRepository.findAll();
    }

    // RESTful service to get hobby by id
    @RequestMapping(value = "/hobby/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Hobby> findHobbyRest(@PathVariable("id") Long hobbyId) {
        return hobbyRepository.findById(hobbyId);
    }

}
