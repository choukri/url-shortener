package com.notarius.url.shortener.controller;

import javax.validation.constraints.NotNull;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.notarius.url.shortener.model.Url;
import com.notarius.url.shortener.model.UrlError;
import com.notarius.url.shortener.service.UrlShortenerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/urlshortener")
@Api("Service pour transformer URL complète, générez une URL raccourcie et à partir d'une URL raccourcie précédemment générée, fournissez l'URL complète (originale). ")
public class UrlShortenerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortenerController.class);

	@Autowired
	private UrlShortenerService urlShortenerService;

	@PostMapping
	@ResponseBody
	@ApiOperation(value = "Transformer URL vers URL  raccourcie", nickname = "getAllAffiliationStatements")
	public ResponseEntity<Object> transformUrlToUrlShortener(@RequestBody @NotNull Url url) {

		UrlValidator validator = new UrlValidator(new String[] { "http", "https" });
		if (!validator.isValid(url.getUrl())) {
			UrlError error = new UrlError("url", url.getUrl(), "L'URL renseigné n'est pas valide ");
			LOGGER.error("Invalid URL");
			return ResponseEntity.badRequest().body(error);
		}
		Url urlShortener = urlShortenerService.transformUrlToUrlShortener(url.getUrl());
		return ResponseEntity.ok(urlShortener);
	}

	@GetMapping(value = "/{key}")
	@ResponseBody
	@ApiOperation(value = "récupérer URL original à partir d'un URL raccourcie", nickname = "getOriginalUrlByKey")
	public ResponseEntity<Object> getOriginalUrlByKey(@PathVariable String key) {

		Url url = urlShortenerService.getOriginalUrlByKey(key);

		if (url == null) {
			UrlError error = new UrlError("Key", key, "URL raccourcie non trouvé");
			LOGGER.error("Key not exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}

		return ResponseEntity.ok(url);
	}

}
