package com.markovlabs.cyclop.images.domain;

public class ImageInfo {
    
    private final String imagePath;
    private final byte[] image;
    private final long id;

    public ImageInfo(long id, String imagePath, byte[] image) {
        this.imagePath = imagePath;
        this.id = id;
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

}
