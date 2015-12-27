package com.markovlabs.cyclop.images.domain;

import java.util.List;

public interface ImageInfoRepository {
    
   ImageInfo getImageInfo(long id) throws ImageInfoNotFoundException;
   List<ImageInfo> getAllImageInfo();
   
   ImageInfo addImage(byte[] image) throws ImageInfoStorageException;
}
