package br.com.ueder.votoonline.services.images;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorage {

    void saveImage(MultipartFile fileUpload);

    boolean existsImage(String fileUpload);

    void deleteImage(MultipartFile fileUpload);

    void deleteImage(String fileUpload);

    String getPathDirectory();

}
