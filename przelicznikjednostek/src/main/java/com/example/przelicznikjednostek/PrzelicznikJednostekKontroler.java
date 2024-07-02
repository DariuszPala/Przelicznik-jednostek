package com.example.przelicznikjednostek;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PrzelicznikJednostekKontroler {

    private static final Map<String, Double> przeliczniki = new HashMap<>();
    static {
        przeliczniki.put("metry", 1.0);
        przeliczniki.put("kilometry", 0.001);
        przeliczniki.put("cale", 39.3701);
        przeliczniki.put("stopy", 3.28084);
        przeliczniki.put("centymetry", 100.0);
        przeliczniki.put("jardy", 1.09361);
    }

    @GetMapping("/")
    public String pokazFormularz() {
        return "przelicznik";
    }

    @PostMapping("/przelicz")
    public String przeliczJednostki(
            @RequestParam("ilosc") String iloscStr,
            @RequestParam("jednostka-z") String jednostkaZ,
            @RequestParam("jednostka-na") String jednostkaNa,
            Model model) {

        iloscStr = iloscStr.replace(',', '.'); // Zamiana przecinka na kropkę
        double ilosc;
        try {
            ilosc = Double.parseDouble(iloscStr);
        } catch (NumberFormatException e) {
            model.addAttribute("wynik", "Proszę wprowadzić prawidłową liczbę.");
            return "przelicznik";
        }

        double iloscWMetrze = ilosc / przeliczniki.get(jednostkaZ);
        double przeliczonaIlosc = iloscWMetrze * przeliczniki.get(jednostkaNa);

        model.addAttribute("wynik", String.format("Przeliczona Ilość: %.2f %s", przeliczonaIlosc, jednostkaNa));
        return "przelicznik";
    }
}
