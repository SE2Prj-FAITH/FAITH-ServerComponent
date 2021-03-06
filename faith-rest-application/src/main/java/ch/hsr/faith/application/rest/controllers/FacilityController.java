package ch.hsr.faith.application.rest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.hsr.faith.application.rest.dto.BaseJSONResponse;
import ch.hsr.faith.application.rest.validator.FacilityValidator;
import ch.hsr.faith.domain.Facility;
import ch.hsr.faith.domain.FacilityCategory;
import ch.hsr.faith.exception.FAITHException;
import ch.hsr.faith.service.FacilityCategoryService;
import ch.hsr.faith.service.FacilityService;
import ch.hsr.faith.service.UserAccountService;

@Controller
@RequestMapping("/facilities")
public class FacilityController extends AbstractController {
	Logger logger = Logger.getRootLogger();

	@Autowired
	private FacilityService facilityService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private FacilityCategoryService facilityCategoryService;

	@Autowired
	private FacilityValidator facilityValidator;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse getAllFacilities(Model model) {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.facilityService.findAll());
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	@Secured("ROLE_USER")
	public BaseJSONResponse saveFacility(Model model, @Valid @RequestBody Facility facility) throws FAITHException {
		facility.setUserAccount(getLoggedInUser());
		if (facility.getId() != null && facility.getId() > 0) {
			return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.facilityService.save(facility));
		} else {
			return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.facilityService.addAndFetchCoordinates(facility));
		}
	}

	@RequestMapping(value = "/first", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse getFirstFacility(Model model) {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.facilityService.get(1l));
	}

	@RequestMapping(value = "/findByCategoryId/{categoryId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse getCategoryId(@PathVariable long categoryId, @RequestParam(value = "latitude") double latitude, @RequestParam(value = "longitude") double longitude)
			throws FAITHException {
		FacilityCategory facilityCategory = facilityCategoryService.findById(categoryId);
		List<Facility> facilityList = facilityService.findByCategory(facilityCategory);
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, facilityService.fetchDistance(facilityList, latitude, longitude));
	}

	@RequestMapping(value = "/findUsersFacilities", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public BaseJSONResponse getLoggedInUsersFacilities() throws FAITHException {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, facilityService.findByUserAccount(getLoggedInUser()));
	}

	@RequestMapping(value = "/findByPieceOfFurnitureNeededWithDistanceFrom/{pieceOfFurnitureId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse findByPieceOfFurnitureNeededWithDistanceFrom(@PathVariable long pieceOfFurnitureId, @RequestParam(value = "latitude") double latitude,
			@RequestParam(value = "longitude") double longitude) {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS,
				this.facilityService.fetchDistance(this.facilityService.findByPieceOfFurnitureNeededId(pieceOfFurnitureId), latitude, longitude));
	}

}
