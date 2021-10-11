package com.notarius.url.shortener.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notarius.url.shortener.model.Url;
import com.notarius.url.shortener.model.UrlError;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlShortenerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void transformUrlToUrlShortenerTest_OK() throws Exception {
		Url originalUrl = new Url();
		originalUrl.setUrl("https://www.notarius.com/fr/");
		mvc.perform(post("/urlshortener").contentType(MediaType.APPLICATION_JSON).content(jsonToString(originalUrl)))
				.andExpect(status().isOk());
	}

	@Test
	public void transformUrlToUrlShortenerTest_when_url_not_valid() throws Exception {
		Url originalUrl = new Url();
		originalUrl.setUrl("notarius");
		UrlError error = new UrlError("url", originalUrl.getUrl(), "L'URL renseigné n'est pas valide ");
		mvc.perform(post("/urlshortener").contentType(MediaType.APPLICATION_JSON).content(jsonToString(originalUrl)))
				.andExpect(status().isBadRequest()).andExpect(content().json(jsonToString(error)));
	}

	@Test
	public void getOriginalUrlByKeyTest_OK() throws Exception {
		Url originalUrl = new Url();
		originalUrl.setUrl("https://www.notarius.com/fr/");
		originalUrl.setKey("cf426ac4");
		originalUrl.setCreatedAt(LocalDateTime.now());
		mvc.perform(get("/urlshortener/cf426ac4")).andExpect(status().isOk());
	}

	@Test
	public void getOriginalUrlByKeyTest_when_url_shortener_not_found() throws Exception {
		UrlError error = new UrlError("Key", "sdsd59466", "URL raccourcie non trouvé");

		mvc.perform(get("/urlshortener/sdsd59466")).andExpect(status().isNotFound())
				.andExpect(content().json(jsonToString(error)));
	}

	private String jsonToString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
