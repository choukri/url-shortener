package com.notarius.url.shortener.service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.notarius.url.shortener.model.Url;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

	@Autowired
	private RedisTemplate<String, Url> redisTemplate;

	@Value("${redis.ttl}")
	private long ttl;

	@Override
	public Url transformUrlToUrlShortener(@NotBlank String url) {

		String key = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
		Url createdUrl = new Url(key, url, LocalDateTime.now());
		redisTemplate.opsForValue().set(createdUrl.getKey(), createdUrl, ttl, TimeUnit.SECONDS);
		return createdUrl;

	}

	@Override
	public Url getOriginalUrlByKey(@NotBlank String key) {
		Url url = redisTemplate.opsForValue().get(key);
		return url;
	}

}
