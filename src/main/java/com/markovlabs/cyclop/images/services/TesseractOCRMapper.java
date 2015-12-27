package com.markovlabs.cyclop.images.services;

import org.springframework.stereotype.Service;
import com.markovlabs.cyclop.images.domain.ImageInfo;

import org.bytedeco.javacpp.BytePointer;
import static org.bytedeco.javacpp.lept.PIX;
import static org.bytedeco.javacpp.lept.pixRead;
import static org.bytedeco.javacpp.lept.pixDestroy;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;

@Service
public class TesseractOCRMapper implements ImageMappingService {
    
    @Override
    public String getImageAsString(ImageInfo imageInfo) throws ImageMappingException {     
        PIX image = pixRead(imageInfo.getImagePath());
        
        TessBaseAPI tesseractOCREngine = newTesseractOCREngineWithoutTrainingData();
        tesseractOCREngine.SetImage(image);
        String imageAsString = getImageAsString(tesseractOCREngine);

        tesseractOCREngine.End(); 
        pixDestroy(image);
        
        return imageAsString;
    }

    private String getImageAsString(TessBaseAPI tesseractOCREngine) {
        BytePointer imageText = tesseractOCREngine.GetUTF8Text();
        String imageAsString = imageText.getString();
        imageText.deallocate();
        return imageAsString;
    }

    private TessBaseAPI newTesseractOCREngineWithoutTrainingData() throws ImageMappingException {
        TessBaseAPI tesseractOCREngine = new TessBaseAPI();
        if (tesseractOCREngine.Init(null, "eng") != 0) {
            throw new ImageMappingException("Could not initialize Tesseract OCR Engine for english without trainaing data.");
        }
        
        return tesseractOCREngine;
    }

}
