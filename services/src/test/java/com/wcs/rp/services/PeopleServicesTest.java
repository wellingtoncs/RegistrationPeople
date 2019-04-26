package com.wcs.rp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wcs.rp.entities.People;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/services-context.xml")
@ActiveProfiles("test")
public class PeopleServicesTest {
	private final Logger logger = LoggerFactory.getLogger(PeopleServicesTest.class);
	
	private List<People> peopleList = new ArrayList<>();

	@Mock
	private IPeopleServices peopleServices;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		this.loadPeople();
	}

	@Test
	public void getPeopleTest() {
		
		logger.debug("testing PeopleServices.getPeople()...");

		Mockito.when(peopleServices.getPeople()).thenReturn(this.peopleList);

		List<People> result = peopleServices.getPeople();

		assertEquals(this.peopleList.size(), result.size());
	}

	@Test
	public void getPeopleByIdTest() {
		logger.debug("testing PeopleServices.getPeople(id)...");

		Mockito.when(peopleServices.getPeopleById(111L)).thenReturn(this.peopleList.get(0));

		People people = peopleServices.getPeopleById(111L);
		
		assertEquals(Long.valueOf(333L), people.getId());
		assertEquals("Acme Inc.", people.getName());
	}

	@Test
	public void savePeopleTest() {
		logger.debug("testing PeopleServices.savePeople(People)...");

		Mockito.when(peopleServices.savePeople(this.peopleList.get(0))).thenReturn(this.peopleList.get(0));

		People people = peopleServices.savePeople(this.peopleList.get(0));
		assertEquals(Long.valueOf(111L), people.getId());
	}

	@Test
	public void savePeopleTestNull() {
		logger.debug("testing PeopleServices.savePeople(People) null...");

		Mockito.when(peopleServices.savePeople(null)).thenReturn(null);
		People people = peopleServices.savePeople(null);

		assertNull(people);
	}
	
	//TODO fazer o teste delete	
	//TODO fazer o teste delete null
	
	private void loadPeople() {
		
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
		
		peopleList.add(peopleOne);

		People peopleTwo = new People();
		peopleOne.setId(222L);
		peopleTwo.setName("Nova Pessoa Cadastrada");
		peopleTwo.setStreet("Rua Cais do Apolo");
		peopleTwo.setNumber("999");
		peopleTwo.setNeighborhood("Bairro Novo");
		peopleTwo.setCity("Cidade Nova");
		peopleTwo.setState("Estados");
		peopleTwo.setCellPhone("+5581999999999");
		peopleTwo.setPhone("+558122222222");
		
		peopleList.add(peopleTwo);
		
		People peopleThree = new People();
		peopleOne.setId(333L);
		peopleThree.setName("Nova Pessoa Cadastrada");
		peopleThree.setStreet("Rua Cais do Apolo");
		peopleThree.setNumber("999");
		peopleThree.setNeighborhood("Bairro Novo");
		peopleThree.setCity("Cidade Nova");
		peopleThree.setState("Estados");
		peopleThree.setCellPhone("+5581999999999");
		peopleThree.setPhone("+558122222222");
		
		peopleList.add(peopleThree);
		
		
		
	}
}
