package com.blogapp.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class CloudinaryCloudServiceStorageImpl implements CloudStorageService{
    Cloudinary cloudinary;

    @Autowired
    public CloudinaryCloudServiceStorageImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<Object, Object> uploadImage(Map<Object, Object> imageProperties) throws IOException {
        return cloudinary.uploader().upload(imageProperties.get("file"), ObjectUtils.emptyMap());
    }
}
