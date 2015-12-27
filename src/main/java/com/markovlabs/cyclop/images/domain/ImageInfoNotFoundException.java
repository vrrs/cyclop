package com.markovlabs.cyclop.images.domain;

public class ImageInfoNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    
    public ImageInfoNotFoundException(String message, Throwable e) {
        super(message, e);
    }
    
    public ImageInfoNotFoundException(Throwable e) {
        super(e);
    }
    
    public ImageInfoNotFoundException(String message) {
        super(message);
    }
}
