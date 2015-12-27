package com.markovlabs.cyclop.images.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.markovlabs.cyclop.IdDTO;
import com.markovlabs.cyclop.images.domain.ImageDTO;
import com.markovlabs.cyclop.images.domain.ImageInfoTO;
import com.markovlabs.cyclop.images.domain.ImagesInfoDTO;
import com.markovlabs.cyclop.images.services.ImageInfoService;
import com.markovlabs.cyclop.images.services.ImageInfoServiceException;

@RestController
public class ImageController {
    
    private final ImageInfoService imageInfoService;
    
    @Autowired
    public ImageController(ImageInfoService imageInfoService){
        this.imageInfoService = imageInfoService;
    }
    
    @RequestMapping(path = "/cyclop/v1/images/", method = RequestMethod.GET)
    public @ResponseBody ImagesInfoDTO getImages() {
        return new ImagesInfoDTO(imageInfoService.getAllImageInfo());
    }
    
    @RequestMapping(path = "/cyclop/v1/images/{id}", method = RequestMethod.GET)
    public @ResponseBody ImageInfoTO getImage(@PathVariable("id") long id) throws ImageInfoServiceException {
        return imageInfoService.getImageInfo(id);
    }

    @RequestMapping(path = "/cyclop/v1/images/", method = RequestMethod.POST)
    public @ResponseBody IdDTO saveImage(@RequestBody ImageDTO image) throws ImageInfoServiceException{
        return new IdDTO(imageInfoService.saveImageFromBase64Encoding(image.getImage()).getId());
    }

}
