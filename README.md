# url-shortener
Project Spring boot that transform a full URL and generate a shortened URL. and from a previously generated shortened URL, provide the full (original) URL. 

I use in technology (Spring boot, Redis and MurmurHash3)

#Testing urlShortener

## Url to UrlShortener
- method : post 
- url of service : http://localhost:9000/urlshortener/
- request :

{
    "url": "https://www.notarius.com/fr/"
}
- response :
{
    "key": "17d62dfd",
    "url": "https://www.notarius.com/fr/",
    "createdAt": "2021-10-11T20:25:28.5277514"
}

## UrlShortener to Url
- method : get 
- url of service : http://localhost:9000/urlshortener/17d62dfd

- response
{
    "key": "17d62dfd",
    "url": "https://www.notarius.com/fr/",
    "createdAt": "2021-10-11T20:25:28.5277514"
}

You can use Postman or swagger (http://localhost:9000/swagger-ui/) for testing service  UrlShortener.

## Requirement

Installing Redis in local for testing application


