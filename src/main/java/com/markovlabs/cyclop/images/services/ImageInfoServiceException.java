package com.markovlabs.cyclop.images.services;

public class ImageInfoServiceException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ImageInfoServiceException(String message, Throwable e) {
        super(message, e);
    }
    
    public ImageInfoServiceException(Throwable e) {
        super(e);
    }
    
    public ImageInfoServiceException(String message) {
        super(message);
    }
}
