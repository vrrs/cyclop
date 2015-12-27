package com.markovlabs.cyclop.images.services;

import java.util.List;
import com.markovlabs.cyclop.images.domain.ImageInfoTO;

public interface ImageInfoService {
    
    ImageInfoTO getImageInfo(long id) throws ImageInfoServiceException;
    List<ImageInfoTO> getAllImageInfo();
    
    ImageInfoTO saveImageFromBase64Encoding(byte[] image) throws ImageInfoServiceException;

}
