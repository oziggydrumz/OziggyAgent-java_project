package com.oziggyagent.oziggyagent.service;

public interface CacheService {
    void cacheShortUrl(String longUrl,String shortUrl);

    String getShortUrl(String longUrl);

    void cacheLongUrl(String shortUrl,String longUrl);

    String getLongUrl(String shortUrl);

}
