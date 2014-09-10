package net.syspherice.controller;

import net.syspherice.form.DocumentObjet;
import net.syspherice.service.DocumentObjetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class DocumentObjetController {
	@Autowired
    private DocumentObjetService doService;
     
    @RequestMapping(value = "/documentobjet", method = RequestMethod.GET)  
    public ModelAndView getAllList(ModelMap model) {  
        model.addAttribute("list", doService.listDocumentObjet());  
        return new ModelAndView();  
    }  
     
    @RequestMapping(value = "/documentobjet/save", method = RequestMethod.POST)  
    public ModelAndView createPerson(@ModelAttribute DocumentObjet docObjet, ModelMap model) {
        if(StringUtils.hasText(docObjet.getId())) {
            doService.updateDocumentObjet(docObjet);
        } else {
            doService.addDocumentObjet(docObjet);
        }
        return new ModelAndView("documentobjet");  
    }
         
    @RequestMapping(value = "/documentobjet/delete", method = RequestMethod.GET)  
    public ModelAndView deletePerson(@ModelAttribute DocumentObjet docObjet, ModelMap model) {  
        doService.deleteDocumentObjet(docObjet);  
        return new ModelAndView("documentobjet");  
    }   
}
