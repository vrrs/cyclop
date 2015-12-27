package com.markovlabs.cyclop.images.services;

import com.markovlabs.cyclop.images.domain.ImageInfo;

public interface ImageMappingService {

    String getImageAsString(ImageInfo imageInfo) throws ImageMappingException;
}
