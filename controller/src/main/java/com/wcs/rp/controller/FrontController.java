package com.wcs.rp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wcs.rp.entities.People;
import com.wcs.rp.services.IPeopleServices;

@RestController
@RequestMapping("/rest")
@Profile("dev")
public class FrontController {
	private final Logger logger = LoggerFactory.getLogger(FrontController.class);
	private IPeopleServices peopleServices;

	@Autowired
	public void setPeopleServices(IPeopleServices peopleServices) {
		this.peopleServices = peopleServices;
	}

	@RequestMapping(value = "/pessoas", method = RequestMethod.GET)
	public ResponseEntity<List<People>> listPeople() {

		logger.debug("Listing people...");

		List<People> peopleList = peopleServices.getPeople();

		return new ResponseEntity<List<People>>(peopleList, HttpStatus.OK);
	}

	@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<People> getPeople(@PathVariable long id) {

		logger.debug("Looking for people " + id + "...");

		People people = peopleServices.getPeopleById(id);
		return new ResponseEntity<People>(people, HttpStatus.OK);
	}

	@RequestMapping(value = "/pessoa/save", method = RequestMethod.POST)
	public ResponseEntity<People> savePeople(@RequestBody People people) {
		if (people == null) {
			return new ResponseEntity<People>(people, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		logger.debug("Saving people" + people + "...");

		people = peopleServices.savePeople(people);

		return new ResponseEntity<People>(people, HttpStatus.OK);
	}

	@RequestMapping(value = "/pessoa/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity<People> deletePeople(@PathVariable long peopleId) {
		logger.debug("Looking for people " + peopleId + "...");

		boolean status = peopleServices.deletePeople(peopleId);
		return new ResponseEntity<People>(new People(), HttpStatus.OK);
	}
}
