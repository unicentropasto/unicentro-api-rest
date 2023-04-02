package com.ihc.apirest.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.services.drive.model.File;

import org.springframework.stereotype.Service;


@Service
public interface GoogleService 
{
  List<List<String>> loadDataFileGoogleDrive(String fileName, String sheetName, int numColumn) throws  IOException, GeneralSecurityException;

  File getFileGoogleDrive(String fileName) throws IOException, GeneralSecurityException;
}
