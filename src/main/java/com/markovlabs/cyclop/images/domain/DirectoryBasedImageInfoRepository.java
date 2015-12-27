package com.markovlabs.cyclop.images.domain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.io.Files;
import com.markovlabs.cyclop.id.services.IdService;
import com.markovlabs.cyclop.model.tables.records.ImageRecord;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import javaslang.control.Try;
import static com.markovlabs.cyclop.model.tables.Image.IMAGE;

@Repository
public class DirectoryBasedImageInfoRepository implements ImageInfoRepository {
    
    private final DynamicStringProperty directoryPath;
    private final DSLContext cyclopDb;
    private final IdService idService;
    
    @Autowired
    public DirectoryBasedImageInfoRepository(DynamicPropertyFactory properties, DSLContext cyclopDb, IdService idService) {
        this.directoryPath = properties.getStringProperty("image.repository.directory", "img");
        this.cyclopDb = cyclopDb;
        this.idService = idService;
    }

    @Override
    public ImageInfo getImageInfo(long id) throws ImageInfoNotFoundException {
        return Try.of(() -> cyclopDb.selectFrom(IMAGE)
                                    .where(IMAGE.ID.equal(id))
                                    .fetchOneInto(ImageInfo.class))
                  .orElseThrow(e -> new ImageInfoNotFoundException(e));
    }

    @Override
    public List<ImageInfo> getAllImageInfo() {
        return cyclopDb.selectFrom(IMAGE)
                       .fetchInto(ImageInfo.class);
    }

    @Override
    public ImageInfo addImage(byte[] image) throws ImageInfoStorageException {
        return Try.of(() -> addImageToDbAndDir(image))
                  .orElseThrow(e -> new ImageInfoStorageException(e));
    }

    private ImageInfo addImageToDbAndDir(byte[] image) throws IOException {
        ImageInfo imageInfo = addImageInfoToDb(image);
        addImageInfoToDirectory(imageInfo);
        return imageInfo;
    }

    private void addImageInfoToDirectory(ImageInfo imageInfo) throws IOException {
        Files.write(imageInfo.getImage(), new File(imageInfo.getImagePath()));    
    }

    private ImageInfo addImageInfoToDb(byte[] image) {
        ImageRecord imageRecord = cyclopDb.newRecord(IMAGE);
        String imagePath = directoryPath.get() + "/" + newImageName();
        imageRecord.setImagePath(imagePath);
        imageRecord.setImage(image);
        imageRecord.insert();
        return new ImageInfo(imageRecord.getId(), imagePath, image);
    }

    private String newImageName() {
        return "img_" + idService.getUUID();
    }

}
