package com.wcs.rp.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wcs.rp.entities.People;;

@Repository
@Transactional
@Profile("dev")
public class PeopleDao implements IPeopleDao {
	
	private Logger logger = LoggerFactory.getLogger(PeopleDao.class);
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly = true)
	@Override
	public List<People> getPeople() {

		logger.debug("Selecting all people stored...");
		
		People peopleOne = new People();
		peopleOne.setId(111L);
		peopleOne.setName("Nova Pessoa Cadastrada");
		peopleOne.setStreet("Rua Cais do Apolo");
		peopleOne.setNumber("999");
		peopleOne.setNeighborhood("Bairro Novo");
		peopleOne.setCity("Cidade Nova");
		peopleOne.setState("Estados");
		peopleOne.setCellPhone("+5581999999999");
		peopleOne.setPhone("+558122222222");
		
		

		//Query query = entityManager.createNamedQuery("People.findAll", People.class);

		//logger.debug("Querying database after all people => " + query.toString());

		@SuppressWarnings("unchecked")
		List<People> peopleList = new ArrayList<>();//query.getResultList();
		peopleList.add(peopleOne);

		//logger.debug("List of people found! Retrieving " + peopleList.size() + " people.");

		return peopleList;
	}

	@Transactional(readOnly = true)
	@Override
	public People getPeopleById(Long id) {

		logger.debug("Selecting people with id " + id + " ...");

		People person = entityManager.find(People.class, id);

		return person;
	}

	@Override
	public People savePeople(People people) {

		logger.debug("Saving people..." + people);

		if (people == null) {
			return null;
		}

		if (people.getId() == null) {

			logger.info("Creating new people " + people + "...");
			entityManager.persist(people);

		} else {

			logger.info("Updating people " + people + "...");
			entityManager.merge(people);
		}

		return people;
	}

	@Override
	public boolean deletePeople(Long id) {
		
		if (id == null) {
			return false;
		}
		
		logger.debug("Selecting people with id " + id + " ...");
		People people = this.getPeopleById(id);
		
		logger.info("Delete people id " + id + "...");
		entityManager.remove(people);
		
		return true;
	}
}
