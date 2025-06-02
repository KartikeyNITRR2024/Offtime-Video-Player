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

    // Only allow embedded YouTube URLs: https://www.youtube.com/embed/VIDEO_ID
    private static final String YOUTUBE_EMBED_URL_REGEX =
            "^https://www\\.youtube\\.com/embed/[\\w-]{11}$";

    private static final Pattern YOUTUBE_EMBED_URL_PATTERN = Pattern.compile(YOUTUBE_EMBED_URL_REGEX);

    public void validateVideoUrl(String url) {
        if (url == null || url.trim().isEmpty() || !YOUTUBE_EMBED_URL_PATTERN.matcher(url).matches()) {
            throw new InvalidVideoException("Invalid YouTube embed URL. Please provide a valid embedded video link.");
        }
    }
}
