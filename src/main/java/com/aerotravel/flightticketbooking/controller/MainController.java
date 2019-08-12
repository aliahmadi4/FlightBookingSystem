package com.aerotravel.flightticketbooking.controller;

import com.aerotravel.flightticketbooking.model.Aircraft;
import com.aerotravel.flightticketbooking.model.Airport;
import com.aerotravel.flightticketbooking.services.AircraftService;
import com.aerotravel.flightticketbooking.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@Controller
public class MainController {

    @Autowired
    AirportService airportService;
    @Autowired
    AircraftService aircraftService;

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/airport/new")
    public String showAddAirportPage(Model model) {
        model.addAttribute("airport", new Airport());
        return "newAirport";
    }

    @PostMapping("/airport/new")
    public String saveAirport(@Valid @ModelAttribute("airport") Airport airport, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "newAirport";
        }
        airportService.saveAirport(airport);
        return "index";
    }

    @GetMapping("/airports")
    public String showAirportsList(@RequestParam(defaultValue = "0") int pageNo, Model model){
        model.addAttribute("airports", airportService.getAllAirportsPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "airports";
    }

    @GetMapping("/aircraft/new")
    public String showAddAircraftPage(Model model) {
        model.addAttribute("aircraft", new Aircraft());
        return "newAircraft";
    }

    @PostMapping("/aircraft/new")
    public String saveAircraft(@Valid @ModelAttribute("aircraft") Aircraft aircraft, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("aircraft", bindingResult.getAllErrors());
            return "newAircraft";
        }
        aircraftService.saveAircraft(aircraft);
        return "index";
    }

    @GetMapping("/aircrafts")
    public String showAircraftsList(@RequestParam(defaultValue = "0") int pageNo, Model model){
        model.addAttribute("aircrafts", aircraftService.getAllAircraftsPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "aircrafts";
    }





}
