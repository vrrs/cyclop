package com.markovlabs.cyclop.images.domain;

import java.util.List;

public class ImagesInfoDTO {

    private final List<ImageInfoTO> imageInfos;

    public ImagesInfoDTO(List<ImageInfoTO> imageInfos) {
       this.imageInfos = imageInfos;
    }

    public List<ImageInfoTO> getImageInfos() {
        return imageInfos;
    }

}
