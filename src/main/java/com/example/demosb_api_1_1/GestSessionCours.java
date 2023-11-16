package com.example.demosb_api_1_1;

import com.example.demosb_api_1_1.modele.Cours;
import com.example.demosb_api_1_1.modele.Local;
import com.example.demosb_api_1_1.modele.SessionCours;
import com.example.demosb_api_1_1.services.InterfCoursService;
import com.example.demosb_api_1_1.services.InterfSessionCoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sessioncours")
public class GestSessionCours {

    @Autowired
    private InterfSessionCoursService sessionCoursService;

    @Autowired
    private InterfCoursService coursService;

    @RequestMapping("/tous")
    public String affTous(Map<String, Object> model) {
        System.out.println("recherche sessions");
        List<SessionCours> liste;
        try {

            liste = sessionCoursService.all();
            model.put("mesSessions", liste);
        } catch (Exception e) {
            System.out.println("----------erreur lors de la recherche-------- " + e);
            return "error";
        }
        return "affichagetousSessions";
    }

    @RequestMapping("/rechercheParCours")
    public String read(@RequestParam int id_cours, Map<String, Object> model){
        System.out.println("Recherche du cours n° " + id_cours);
        try {
            Cours cr = coursService.read(id_cours);
            List<SessionCours> lsc = sessionCoursService.getSessionCours(cr);
            model.put("moncours",cr);
            model.put("sesscours",lsc);
        }
        catch (Exception e){
            System.out.println("----------erreur lors de la recherche -------- " + e);
            model.put("error", e.getMessage());
            return "error";
        }
        return "affsesscours";
    }

    @RequestMapping("/selectionNbreInscrit")
    String NbreInscrit(@RequestParam("nbreInscrit") int nbreInscrit, Map<String, Object> model) {
        Collection<SessionCours> lsc;

        try {

            lsc = sessionCoursService.rechNbreInscrit(nbreInscrit);
            model.put("sesscours", lsc);

        } catch (Exception e) {
            System.out.println("erreur lors de la lecture " + e);
            model.put("error", e);
            return "error";
        }
        return "affsesscours";


    }

}
