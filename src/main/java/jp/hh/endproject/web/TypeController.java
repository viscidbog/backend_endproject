package jp.hh.endproject.web;

import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.hh.endproject.domain.TypeRepository;
import jp.hh.endproject.domain.HobbyType;

@Controller
public class TypeController {

    private final TypeRepository typeRepository;

    public TypeController(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @RequestMapping(value = "/typelist", method = RequestMethod.GET)
    public String showTypeList(Model model) {
        model.addAttribute("hobbytypes", typeRepository.findAll());
        return "typelist";
    }

    @RequestMapping(value = "/addtype", method = RequestMethod.GET)
    public String addType(Model model) {
        model.addAttribute("hobbytype", new HobbyType());
        return "addtype";
    }

    @RequestMapping(value = "/savetype", method = RequestMethod.POST)
    public String save(HobbyType hobbyType) {
        typeRepository.save(hobbyType);
        return "redirect:typelist";
    }

    // Delete a type by its ID value. endpoint has to be named differently from
    // hobby deleter, because if they're both called the same, it returns an
    // ambiguous handler error.
    @RequestMapping(value = "/deletetype/{hobbyTypeId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletetype(@PathVariable("hobbyTypeId") Long deltype, Boolean display, Model model) {
        Objects.requireNonNull(deltype);
        try {
            typeRepository.deleteById(deltype);
        } catch (Exception error) {
            display = true;
            model.addAttribute("display", display);
        }
        return "redirect:/typelist";
    }

}