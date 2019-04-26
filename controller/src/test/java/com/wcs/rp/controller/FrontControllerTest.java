package com.wcs.rp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcs.rp.entities.People;
import com.wcs.rp.services.IPeopleServices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:app-context.xml", "classpath*:services-context.xml" })
@ActiveProfiles("test")
public class FrontControllerTest {
	Logger logger = LoggerFactory.getLogger(FrontControllerTest.class);
	@InjectMocks
	private FrontController frontController;
	@Mock
	private IPeopleServices peopleServices;

	private List<People> peopleList = new ArrayList<>();
	private MockMvc mockMvc;

	@Before
	public void setup() {

		this.loadPeople();
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(frontController).alwaysExpect(status().isOk())
				.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).build();
	}

	@Test
	public void listPeopleTest() throws Exception {

		logger.debug("testing GET request to /rest/pessoas ...");

		Mockito.when(peopleServices.getPeople()).thenReturn(this.peopleList);

		mockMvc.perform(get("/rest/pessoas").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].id").value(111)).andExpect(jsonPath("$[0].name").value("Acme Inc."))
				.andExpect(jsonPath("$[1].id").value(222)).andExpect(jsonPath("$[1].name").value("VanHack Inc."))
				.andExpect(jsonPath("$[2].id").value(333)).andExpect(jsonPath("$[2].name").value("VanHack Inc."));
	}

	@Test
	public void getPeopleTest() throws Exception {
		logger.debug("testing GET request to /rest/pessoa/{id} ...");

		Mockito.when(peopleServices.getPeopleById(111L)).thenReturn(this.peopleList.get(0));

		mockMvc.perform(get("/rest/pessoa/111").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(111));
	}

	@Test
	public void savePeopleTest() throws Exception {
		logger.debug("testing POST request to /rest/pessoa/save ...");

		People peopleOne = new People();
		peopleOne.setName("Nova Pessoa Cadastrada");
		peopleOne.setStreet("Rua Cais do Apolo");
		peopleOne.setNumber("999");
		peopleOne.setNeighborhood("Bairro Novo");
		peopleOne.setCity("Cidade Nova");
		peopleOne.setState("Estados");
		peopleOne.setCellPhone("+5581999999999");
		peopleOne.setPhone("+558122222222");

		Mockito.when(peopleServices.savePeople(peopleOne)).thenReturn(this.peopleList.get(0));

		mockMvc.perform(post("/services/companies/save").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(convertToJson(peopleOne))
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(111));
	}

	@Test
	public void deletePeopleTest() throws Exception {
		
		logger.debug("testing GET request to /rest/pessoa/remove/{id} ...");

		Mockito.when(peopleServices.deletePeople(111L)).thenReturn(true);

		mockMvc.perform(get("/rest/pessoa/111").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	private String convertToJson(Object obj) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String json = mapper.writeValueAsString(obj);
		return json;
	}

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