package com.wcs.rp.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.wcs.rp.entities.People;
import com.wcs.rp.persistence.IPeopleDao;

@Service("peopleServices")
@Profile("dev")
public class PeopleServices implements IPeopleServices {

	private final Logger logger = LoggerFactory.getLogger(PeopleServices.class);
	private IPeopleDao peopleDao;

	@Autowired
	public void setPeopleDao(IPeopleDao peopleDao) {
		
		this.peopleDao = peopleDao;
	}

	@Override
	public List<People> getPeople() {

		logger.debug("Selecting all people stored...");

		return peopleDao.getPeople();
	}

	@Override
	public People getPeopleById(Long id) {

		if (id == null) {
			return null;
		}

		logger.debug("Selecting people with id " + id + " ...");

		return peopleDao.getPeopleById(id);
	}
	
	@Override
	public People savePeople(People people) {
		
		if (people == null) {
			return null;
		}
		
		logger.debug("Saving people..." + people);
		
		return peopleDao.savePeople(people);
	}

	@Override
	public boolean deletePeople(Long id) {

		if (id == null) {
			return false;
		}

		logger.debug("Delete people with id " + id + " ...");

		return peopleDao.deletePeople(id);
	}
}
