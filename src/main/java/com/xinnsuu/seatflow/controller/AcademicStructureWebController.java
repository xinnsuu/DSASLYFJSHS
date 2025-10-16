package com.xinnsuu.seatflow.controller;

import com.xinnsuu.seatflow.model.AcademicStructure;
import com.xinnsuu.seatflow.service.AcademicStructureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sections")
public class AcademicStructureWebController {

    @Autowired
    private AcademicStructureService academicStructureService;

    @GetMapping
    public String listStructures(Model model) {
        model.addAttribute("structures", academicStructureService.getAllSections());
        return "academic-structures";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("structure", new AcademicStructure());
        return "academic-structure-form";
    }

    @PostMapping("/new")
    public String createStructure(@Valid @ModelAttribute("structure") AcademicStructure structure, BindingResult result) {
        if (result.hasErrors()) {
            return "academic-structure-form";
        }
        academicStructureService.createAcademicStructure(structure);
        return "redirect:/sections";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        AcademicStructure structure = academicStructureService.getSectionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid structure Id:" + id));
        model.addAttribute("structure", structure);
        return "academic-structure-form";
    }

    @PostMapping("/edit/{id}")
    public String updateStructure(@PathVariable("id") Long id, @Valid @ModelAttribute("structure") AcademicStructure structure,
            BindingResult result) {
        if (result.hasErrors()) {
            return "academic-structure-form";
        }
        academicStructureService.updateAcademicStructure(id, structure);
        return "redirect:/sections";
    }

    @GetMapping("/delete/{id}")
    public String deleteStructure(@PathVariable("id") Long id) {
        academicStructureService.deleteAcademicStructure(id);
        return "redirect:/sections";
    }
}