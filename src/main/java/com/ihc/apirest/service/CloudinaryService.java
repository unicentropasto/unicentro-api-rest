package com.ihc.apirest.service;

import java.util.Map;

public interface CloudinaryService 
{

  Map<String, String> getAllImageUrl() throws Exception;

  String loadImage(String imageName) throws Exception;
}
