package com.vape.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private CloudinaryConfig cloudinaryConfig;
    public CloudinaryService(CloudinaryConfig cloudinaryConfig){
        this.cloudinaryConfig=cloudinaryConfig;
    }

    // upload ảnh lên cloudinary, up thành công sẽ trả về link ảnh
    public String uploadURl(MultipartFile file) {
        Cloudinary cloudinary=cloudinaryConfig.getCloudinary();
        try{
            if (file == null) return null;
            Map uploadResult=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        }
        catch (Exception exception){
            System.out.println(exception);
            return null;
        }

    }

    public String deleteFromUrl(String url) {
        String publicId = getPublicIdFromUrl(url);
        Cloudinary cloudinary = cloudinaryConfig.getCloudinary();
        Map deleteResult = null;
        try {
            if (publicId != null && !publicId.isEmpty()) {
                deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            } else {
                return "not found";
            }
        } catch (IOException e) {
            System.out.println("Exception destroy url: " + e);
            throw new RuntimeException(e);
        }
        return deleteResult.get("result").toString();
    }

    private String getPublicIdFromUrl(String url) {
        String[] parts = url.split("/");
        String publicIdWithExtension = parts[parts.length - 1];
        String[] publicIdParts = publicIdWithExtension.split("\\.");
        return publicIdParts[0];
    }

}
