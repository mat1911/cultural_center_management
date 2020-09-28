package com.app.cultural_center_management.service;

import com.app.cultural_center_management.exceptions.DropboxFileException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.UUID;


@Service
public class DropboxService {

    private DbxClientV2 client;
    private String dataFolder = "/CulturalCenter/";
    private String accessToken = "";

    @PostConstruct
    public void initialize(){
        DbxRequestConfig config = DbxRequestConfig.newBuilder("culturalCenterApp").build();
        client = new DbxClientV2(config, accessToken);
    }

    public String uploadFile(MultipartFile file){
        try (InputStream in = file.getInputStream()) {
            String generatedFileName = generateFileName(file);
            FileMetadata metadata = client.files().uploadBuilder(dataFolder + generatedFileName)
                    .uploadAndFinish(in);

            return getSharedUrl(generatedFileName);

        }catch (Exception e){
            throw new DropboxFileException(String.format("File %s cannot be uploaded! %s", file.getName(), e.getMessage()));
        }
    }

    public String getSharedUrl(String fileName){
        try {
            SharedLinkMetadata sharedLinkMetadata = client.sharing().createSharedLinkWithSettings(dataFolder + fileName);
            String url = sharedLinkMetadata.getUrl();
            url = url.split("\\?")[0];

            return  url + "?raw=1";
        } catch (DbxException e) {
            deleteFile(fileName);
            throw new DropboxFileException(String.format("File %s cannot be uploaded! %s", fileName, e.getMessage()));
        }
    }

    public void deleteFile(String fileUrl){
        try {
            if (fileUrl != null && fileExists(getFileNameFromUrl(fileUrl))) {
                client.files().deleteV2(dataFolder + getFileNameFromUrl(fileUrl));
            }
        } catch (DbxException e) {
            throw new DropboxFileException(String.format("File %s cannot be uploaded! %s", fileUrl, e.getMessage()));
        }
    }

    public boolean fileExists(String fileName){
        try {
            client.files().getMetadata(dataFolder + fileName);
            return true;
        } catch (DbxException e) {
            return false;
        }
    }

    public String generateFileName(MultipartFile multipartFile){
        String[] fileNameParts = multipartFile.getOriginalFilename().split("\\.");
        String extension = fileNameParts[fileNameParts.length - 1];
        String filenamePart1 = UUID.randomUUID().toString().replaceAll("\\W", "");
        String filenamePart2 = String.valueOf(System.currentTimeMillis());

        return filenamePart1 + filenamePart2 + '.' + extension;
    }

    private String getFileNameFromUrl(String url){
        String[] splitUrl = url.split("\\?");
        splitUrl = splitUrl[0].split("/");

        return splitUrl[splitUrl.length - 1];
    }
}
