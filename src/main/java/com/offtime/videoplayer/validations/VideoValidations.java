package com.offtime.videoplayer.validations;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.offtime.videoplayer.exceptions.InvalidVideoException;
import com.offtime.videoplayer.utils.PropertyLoader;

@Component
public class VideoValidations {

	private static final Logger logger = LoggerFactory.getLogger(VideoValidations.class);

    @Autowired
    PropertyLoader propertyLoader;
    
    private static final String YOUTUBE_URL_REGEX =
            "^(https?://)?(www\\.)?(youtube\\.com|youtu\\.be)/(watch\\?v=|shorts/)?[\\w-]{11}$";

    private static final Pattern YOUTUBE_URL_PATTERN = Pattern.compile(YOUTUBE_URL_REGEX);
    
    public void validateVideoUrl(String url) {
    	if (url == null || url.trim().isEmpty() || YOUTUBE_URL_PATTERN.matcher(url).matches() == false)
    	{
    		throw new InvalidVideoException(
                    String.format("Invalid youtube url."));
    	}
    }
}
