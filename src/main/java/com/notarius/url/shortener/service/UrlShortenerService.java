package com.notarius.url.shortener.service;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.notarius.url.shortener.model.Url;

@Validated
public interface UrlShortenerService {

	public Url transformUrlToUrlShortener(@NotBlank String url);

	public Url getOriginalUrlByKey(@NotBlank String key);

}
