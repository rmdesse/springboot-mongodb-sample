package org.home.devhub.springboot2jpacrudexample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.home.devhub.Application;
import org.home.devhub.faq.model.Faq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FaqControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAlls() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/faqs",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetfaqById() {
		Faq faq = restTemplate.getForObject(getRootUrl() + "/faqs/1", Faq.class);
		System.out.println(faq.getQuestion());
		assertNotNull(faq);
	}

	@Test
	public void testCreatefaq() {
		Faq faq = new Faq();
		faq.setQuestion("Q1");
		faq.setAnswer("A1");
		faq.setPosition(1);

		ResponseEntity<Faq> postResponse = restTemplate.postForEntity(getRootUrl() + "/faqs", faq, Faq.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatefaq() {
		int id = 1;
		Faq faq = restTemplate.getForObject(getRootUrl() + "/faqs/" + id, Faq.class);
		faq.setQuestion("Q2");
		faq.setAnswer("A2");
		faq.setPosition(2);

		restTemplate.put(getRootUrl() + "/faqs/" + id, faq);

		Faq updatedfaq = restTemplate.getForObject(getRootUrl() + "/faqs/" + id, Faq.class);
		assertNotNull(updatedfaq);
	}

	@Test
	public void testDeletefaq() {
		int id = 2;
		Faq faq = restTemplate.getForObject(getRootUrl() + "/faqs/" + id, Faq.class);
		assertNotNull(faq);

		restTemplate.delete(getRootUrl() + "/faqs/" + id);

		try {
			faq = restTemplate.getForObject(getRootUrl() + "/faqs/" + id, Faq.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
