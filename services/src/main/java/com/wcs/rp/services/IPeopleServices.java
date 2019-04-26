package com.wcs.rp.services;

import java.util.List;

import com.wcs.rp.entities.People;

public interface IPeopleServices {
	
	/** Return a list the people */
	public List<People> getPeople();
	
	/** Return a People by id */
	public People getPeopleById(Long id);
	
	/** Create new People or update an existing one */
	public People savePeople(People people);

	/** Delete People an existing one */
	public boolean deletePeople(Long id);
	
}
