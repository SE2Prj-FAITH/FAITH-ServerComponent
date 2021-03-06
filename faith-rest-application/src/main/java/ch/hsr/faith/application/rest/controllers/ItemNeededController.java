package ch.hsr.faith.application.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.hsr.faith.application.rest.dto.BaseJSONResponse;
import ch.hsr.faith.domain.Facility;
import ch.hsr.faith.domain.ItemNeeded;
import ch.hsr.faith.exception.FAITHException;
import ch.hsr.faith.service.FacilityService;
import ch.hsr.faith.service.ItemNeededService;

@Controller
@RequestMapping("/items-needed")
public class ItemNeededController extends AbstractController {

	@Autowired
	private ItemNeededService itemNeededService;

	@Autowired
	private FacilityService facilityService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse getAllFurnitures(Model model) {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.itemNeededService.findAll());
	}

	@RequestMapping(value = "/findByFacility/{facilityId}", method = RequestMethod.GET)
	@ResponseBody
	public BaseJSONResponse getCategoryId(@PathVariable long facilityId) throws FAITHException {
		Facility facility = facilityService.get(facilityId);
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, itemNeededService.findByFacility(facility));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	@Secured("ROLE_USER")
	public BaseJSONResponse saveItemNeeded(Model model, @Valid @RequestBody ItemNeeded itemNeeded) {
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, this.itemNeededService.save(itemNeeded));
	}

	@RequestMapping(value = "/delete/{itemNeededId}", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public BaseJSONResponse delete(@PathVariable long itemNeededId) throws FAITHException {
		itemNeededService.delete(itemNeededId);
		return createResponse(BaseJSONResponse.STATUS_SUCCESS, messageSource.getMessage("message.item.needed.delete.successfull", null, LocaleContextHolder.getLocale()));
	}

}
