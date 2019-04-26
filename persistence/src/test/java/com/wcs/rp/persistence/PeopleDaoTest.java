package com.wcs.rp.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.wcs.rp.entities.People;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-context.xml")
@Transactional
@ActiveProfiles("test")
public class PeopleDaoTest {
	Logger logger = LoggerFactory.getLogger(PeopleDaoTest.class);
	
	@Autowired
	PeopleDao peopleDao;
	
	@Test
	public void getPeopleTest(){
		
		logger.debug("testing PeopleDao.getPeople()...");
		List<People> peopleList = peopleDao.getPeople();
		
		assertEquals(3, peopleList.size());
		assertEquals(Long.valueOf(111L), peopleList.get(0).getId());
		assertEquals("People 1", peopleList.get(0).getName());
		assertEquals(Long.valueOf(222L), peopleList.get(1).getId());
		assertEquals("People 2", peopleList.get(1).getName());
		assertEquals(Long.valueOf(333L), peopleList.get(2).getId());
		assertEquals("People 3", peopleList.get(2).getName());
	}
	
	@Test
	public void getPeopleByIdTest(){
		logger.debug("testing PeopleDao.getPeople(id)...");
		People people = peopleDao.getPeopleById(222L);
		
		assertEquals(Long.valueOf(222L), people.getId());
		assertEquals("People 2", people.getName());
	}
	
	@Test
	public void savePeopleTestCreate(){
		logger.debug("testing PeopleDao.savePeople() persist...");
		
		People people = new People();
		people.setName("Nova Pessoa Cadastrada");
		people.setStreet("Rua Cais do Apolo");
		people.setNumber("999");
		people.setNeighborhood("Bairro Novo");
		people.setCity("Cidade Nova");
		people.setState("Estados");
		people.setCellPhone("+5581999999999");
		people.setPhone("+558122222222");
		
		people = peopleDao.savePeople(people);
		assertNotNull(people.getId());
	}
	
	@Test
	public void savePeopleTestUpdate(){
		logger.debug("testing PeopleDao.savePeople() merge...");
		People people = peopleDao.getPeopleById(222L);
		
		people.setName("Altera Pessoa Cadastrada");
		
		people = peopleDao.savePeople(people);
		
		People updatePeople = peopleDao.getPeopleById(222L);
		
		assertEquals(people.getId(), updatePeople.getId());
		assertEquals("Altera Pessoa Cadastrada", updatePeople.getName());
	}
	
	@Test
	public void savePeopleTestNull(){
		logger.debug("testing PeopleDao.savePeople() null...");
		
		People people = peopleDao.savePeople(null);
		assertNull(people);
	}
	
	//teste do delete correto
	
	//teste do delete id inexistente
	
	//teste do delete null
}
