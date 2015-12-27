package com.markovlabs.cyclop.images.domain;

public class ImageDTO {
    
    private final String image;
    
    public ImageDTO(String image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image.getBytes();
    }

}
