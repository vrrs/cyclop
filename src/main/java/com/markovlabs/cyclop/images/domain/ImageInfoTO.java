package com.markovlabs.cyclop.images.domain;

public class ImageInfoTO {
    
    private final long id;
    private final String image;
    
    public ImageInfoTO(long id, String image) {
        this.id = id;
        this.image = image;
    }

    public ImageInfoTO(long id, byte[] imageAsByteArray) {
        this.id = id;
        this.image = new String(imageAsByteArray);
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

}
