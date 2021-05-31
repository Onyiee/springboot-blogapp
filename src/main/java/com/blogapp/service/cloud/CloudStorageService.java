package com.blogapp.service.cloud;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface CloudStorageService {
    public Map<Object, Object> uploadImage(Map<Object, Object> imageProperties) throws IOException;
}
