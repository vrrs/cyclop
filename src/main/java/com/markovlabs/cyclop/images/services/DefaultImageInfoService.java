package com.markovlabs.cyclop.images.services;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.markovlabs.cyclop.images.domain.ImageInfo;
import com.markovlabs.cyclop.images.domain.ImageInfoNotFoundException;
import com.markovlabs.cyclop.images.domain.ImageInfoRepository;
import com.markovlabs.cyclop.images.domain.ImageInfoStorageException;
import com.markovlabs.cyclop.images.domain.ImageInfoTO;

import javaslang.control.Match;
import javaslang.control.Try;

public class DefaultImageInfoService implements ImageInfoService {
    
    private final ImageInfoRepository imageInfoRepository;
    private final Decoder base64Decoder;
    private final Encoder base64Encoder;
    
    @Autowired
    public DefaultImageInfoService(ImageInfoRepository imageInfoRepository) {
        this.imageInfoRepository = imageInfoRepository;
        this.base64Decoder = Base64.getDecoder();
        this.base64Encoder = Base64.getEncoder();
    }

    @Override
    public ImageInfoTO getImageInfo(long id) throws ImageInfoServiceException {
        return Try.of(() -> getImageInfoFromRepo(id))
                  .orElseThrow(anException -> Match
                          .caze((ImageInfoNotFoundException e) -> new ImageInfoServiceException(e))
                          .caze((Exception e) -> new ImageInfoServiceException(e))
                          .apply(anException));
    }

    private ImageInfoTO getImageInfoFromRepo(long id) throws ImageInfoNotFoundException {
        ImageInfo imageInfo = imageInfoRepository.getImageInfo(id);
        return new ImageInfoTO(imageInfo.getId(), base64Encoder.encodeToString(imageInfo.getImage()));
    }

    @Override
    public List<ImageInfoTO> getAllImageInfo() {
        return imageInfoRepository.getAllImageInfo()
                                  .stream()
                                  .map(imageInfo -> new ImageInfoTO(imageInfo.getId(), imageInfo.getImage()))
                                  .collect(Collectors.toList());
    }

    @Override
    public ImageInfoTO saveImageFromBase64Encoding(byte[] image) throws ImageInfoServiceException {
        return Try.of(() -> saveImage(image))
                  .orElseThrow(anException -> new ImageInfoServiceException(anException));
    }

    private ImageInfoTO saveImage(byte[] image) throws ImageInfoStorageException {
        ImageInfo imageInfo = imageInfoRepository.addImage(base64Decoder.decode(image));
        return new ImageInfoTO(imageInfo.getId(), imageInfo.getImage());
    }

}
