package com.example.demosb_api_1_1.services;

import com.example.demosb_api_1_1.modele.Cours;
import com.example.demosb_api_1_1.modele.Local;
import com.example.demosb_api_1_1.modele.SessionCours;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionCoursImplTest {

    private Cours cr;

    private SessionCours sc;

    private Local loc;

    @Autowired
    private CoursServiceImpl coursServiceImpl;

    @Autowired
    private SessionCoursImpl sessionCoursImpl;

    @Autowired
    private LocalServiceImpl localServiceImpl;

    @BeforeEach
    void setUp() {
        try {
            cr = new Cours(null, "Matiere2", 10, new ArrayList<>());

            coursServiceImpl.create(cr);
            System.out.println("Création du cours : " + cr);

            loc = new Local(null, "M22", 19, "Moyenne Classe", new ArrayList<>());
            localServiceImpl.create(loc);
            System.out.println("Création du local : " + loc);

            sc = new SessionCours(Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.now().plusDays(20)), 20, cr, loc);
            sessionCoursImpl.create(sc);
            System.out.println("Création de la session : " + sc);


        } catch (Exception e) {
            System.out.println("Erreur de création de sessions de cours " + e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            sessionCoursImpl.delete(sc);
            System.out.println("Effacement de la session réussi");
        } catch (Exception e) {
            System.out.println("Erreur d'effacement de session " + e);
        }
        try {
            coursServiceImpl.delete(cr);
            System.out.println("Effacement du cours réussi");
        } catch (Exception e) {
            System.out.println("Erreur d'effacement du cours " + e);
        }
        try {
            localServiceImpl.delete(loc);
            System.out.println("Effacement du local réussi");
        } catch (Exception e) {
            System.out.println("Erreur d'effacement du local " + e);
        }

    }

    @Test
    void create() {

        assertNotEquals(0, sc.getId_SessionCours(), "numéro  de sessions non incrémenté ");
        assertEquals(LocalDate.now(), sc.getDateDebut(), "Date différentes " + sc.getDateDebut() + " et " + LocalDate.now());
    }

    @Test
    void read() {
        try {
            int numsc = sc.getId_SessionCours();
            SessionCours sc2 = sessionCoursImpl.read(numsc);
            assertEquals(20, sc2.getNbreInscrits(), "Nombre d'inscrits différents");
        } catch (Exception e) {
            fail("Recherche infructueuse" + e);
        }
    }

    @Test
    void update() {
        try {
            sc.setDateDebut(Date.valueOf(LocalDate.now().plusDays(10)));
            sc = sessionCoursImpl.update(sc);
            assertEquals(sc.getDateDebut(), LocalDate.now().plusDays(10), "Date début différentes " + sc.getDateDebut() + " - " + LocalDate.now().plusDays(10));
        } catch (Exception e) {
            fail("Erreur de mise à jour");
        }
    }

    @Test
    void delete() {

        try {
            sessionCoursImpl.delete(sc);
            Assertions.assertThrows(Exception.class, () -> {
                sessionCoursImpl.read(sc.getId_SessionCours());
            }, "record non effacé");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void all() {
        try {

            List<SessionCours> sce = sessionCoursImpl.all();
            assertNotEquals(0, sce.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("Erreur de recherche");

        }
    }

    @Test
    void getSessionCours() {
        try {
            List<SessionCours> sce = sessionCoursImpl.getSessionCours(cr);
            assertNotEquals(0, sce.size(), "la liste ne contient aucun élément");
        } catch (Exception e) {
            fail("Erreur dans de recherche");
        }
    }

    @Test
    void rechNbreInscrit() {
        try{
            List<SessionCours> sce = sessionCoursImpl.rechNbreInscrit(20);
            boolean trouve = false;
            for(SessionCours s : sce){
                if(s.getNbreInscrits().equals(20)){
                    trouve = true;
                }
                else{
                    fail("un recorde ne correspond pas , nombres d'inscrits = " +s.getNbreInscrits());
                }
                assertTrue(trouve," record non trouve dans la liste ");
            }
        }
        catch (Exception e){
            fail("Recherche infrutueuse");
        }
    }
}