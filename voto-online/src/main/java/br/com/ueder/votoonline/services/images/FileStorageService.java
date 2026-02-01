package br.com.ueder.votoonline.services.images;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileStorageService implements IFileStorage {

    @Value("${images.upload-dir}")
    private String pathImage;

    @Override
    public void saveImage(MultipartFile fileUpload) {
        File directory = new File(getPathDirectory());
        if (!directory.exists()){
            directory.mkdirs();
        }
        File fileDestination = new File(getPathDirectory()+"/"+fileUpload.getOriginalFilename());
        try {
            fileUpload.transferTo(fileDestination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsImage(String fileUpload) {
        File directory = new File(getPathDirectory());
        if (!directory.exists()){
            return false;
        } else {
            File fileDestination = new File(getPathDirectory() + "/" + fileUpload);
            return fileDestination.exists();
        }
    }

    @Override
    public void deleteImage(MultipartFile fileUpload) {
        File directory = new File(getPathDirectory());
        if (!directory.exists()){
            directory.mkdirs();
        }
        File fileDestination = new File(getPathDirectory()+"/"+fileUpload.getOriginalFilename());
        fileDestination.delete();
    }

    @Override
    public void deleteImage(String fileUpload) {
        File directory = new File(getPathDirectory());
        if (!directory.exists()){
            directory.mkdirs();
        }
        File fileDestination = new File(getPathDirectory()+"/"+fileUpload);
        fileDestination.delete();
    }

    @Override
    public String getPathDirectory() {
        return pathImage;
    }
}
