package com.ipi.jva324.commande.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller // et non @RestController sinon home.html affiche "home" (réponse REST) et non le template rendu correspondant !
//@RequestMapping(value = "/") // /accueil / home NON PREFIX TODO pas besoin SAUF pour ex. nombre ?!
public class HomeController {

    @GetMapping("/")
    //@GetMapping(value = { "", "/" })
    public String index() {
        return "index";
    }

}
