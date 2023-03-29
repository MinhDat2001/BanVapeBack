package com.vape.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
            Map uploadResult=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return  uploadResult.get("url").toString();
        }
        catch (Exception exception){
            System.out.println(exception);
            return null;
        }

    }
}
