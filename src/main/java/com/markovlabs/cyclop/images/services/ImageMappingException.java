package com.markovlabs.cyclop.images.services;

public class ImageMappingException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ImageMappingException(String message, Throwable e) {
        super(message, e);
    }
    
    public ImageMappingException(Throwable e) {
        super(e);
    }
    
    public ImageMappingException(String message) {
        super(message);
    }
}
