package jp.hh.endproject.web;

import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.hh.endproject.domain.Genre;
import jp.hh.endproject.domain.GenreRepository;

@Controller
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @RequestMapping(value = "/genrelist", method = RequestMethod.GET)
    public String showTypeList(Model model) {
        model.addAttribute("hobbygenres", genreRepository.findAll());
        return "genrelist";
    }

    @RequestMapping(value = "/addgenre", method = RequestMethod.GET)
    public String addType(Model model) {
        model.addAttribute("hobbygenre", new Genre());
        return "addgenre";
    }

    @RequestMapping(value = "/savegenre", method = RequestMethod.POST)
    public String save(Genre genre) {
        genreRepository.save(genre);
        return "redirect:genrelist";
    }

    // Delete a type by its ID value. endpoint has to be named differently from
    // hobby deleter, because if they're both called the same, it returns an
    // ambiguous handler error.
    @RequestMapping(value = "/deletegenre/{genreId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletegenre(@PathVariable("genreId") Long delgenre, RedirectAttributes redirectAttributes,
            Model model) {
        Objects.requireNonNull(delgenre);
        try {
            genreRepository.deleteById(delgenre);
        } catch (Exception error) {
            model.addAttribute("deleteError", "You cannot delete a genre that is in use.");
        }
        return "redirect:/genrelist";

    }
}