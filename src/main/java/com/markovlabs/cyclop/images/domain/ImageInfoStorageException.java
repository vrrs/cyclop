package com.markovlabs.cyclop.images.domain;

public class ImageInfoStorageException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ImageInfoStorageException(String message, Throwable e) {
        super(message, e);
    }
    
    public ImageInfoStorageException(Throwable e) {
        super(e);
    }
    
    public ImageInfoStorageException(String message) {
        super(message);
    }
}
