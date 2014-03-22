package ch.hsr.faith.application.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ch.hsr.faith.domain.Furniture;
import ch.hsr.faith.service.FurnitureService;

@Controller
@RequestMapping("/furnitures")
public class FurnitureController extends AbstractController {

	@Autowired
	private FurnitureService furnitureService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView getAllFurnitures(Model model) {
		List<Furniture> furnituresList = this.furnitureService.findAll();
		return new ModelAndView(jsonView, DATA_FIELD, furnituresList);
	}

}