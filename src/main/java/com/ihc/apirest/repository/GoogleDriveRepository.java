package com.ihc.apirest.repository;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.ihc.apirest.service.GoogleService;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@Repository
public class GoogleDriveRepository implements GoogleService
{

  private static final String APPLICATION_NAME = "unicentro-app-mall-api-rest";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";
  private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

  @Value("${google.credentials}")
  private String CREDENTIALS_FILE_PATH;

  private  Drive service;
  


  public GoogleDriveRepository() throws IOException, GeneralSecurityException 
  {
    service = getInstance();
  }


  /**
   * Método que permite retonar una instancia del servicio de google drive
   * @return Instancia del servicio google drive
   * @throws IOException
   * @throws GeneralSecurityException
   */
  private Drive getInstance() throws IOException, GeneralSecurityException 
  {
    // Build a new authorized API client service.
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                              .setApplicationName(APPLICATION_NAME)
                              .build();

    return service;
  }


  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException 
  {
    // Load client secrets.
    InputStream in = GoogleService.class.getResourceAsStream("/credentials.json");
    
    if (null == in) 
    {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }

    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                                                                      .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                                                                      .setAccessType("offline")
                                                                      .build();

    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    
    // returns an authorized Credential object.
    return credential;
      }


  /**
   * Metodo que permite obtener el contenido de un archivo del repositorio google drive
   * @return Lista de filas del archivo
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @Override
  public List<List<String>> loadDataFileGoogleDrive(String fileName, String sheetName, int numColumn) throws IOException, GeneralSecurityException 
  {
    List<List<String>> lstDataExcel = new ArrayList<>();

    // Se busca el archivo en todo el google drive
    File fileGoogleDrive = getFileGoogleDrive(fileName);

    if(null != fileGoogleDrive)
    {
        OutputStream outputStream = new ByteArrayOutputStream();
  
        // Descargando el archivo desde google drive a memoria en el outputStream
        service.files().get(fileGoogleDrive.getId()).executeMediaAndDownloadTo(outputStream);
  
        // Se convierte el archivo en memoria outputStream a un archivo de entrada inputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream)outputStream).toByteArray());
        
        // Se carga el contenido del archivo inputStream al obejto excel
        XSSFWorkbook myWorkBook = new XSSFWorkbook(inputStream);
        XSSFSheet mySheet = myWorkBook.getSheet(sheetName);
        Iterator<Row> rowIterator = mySheet.iterator();
  

        while (rowIterator.hasNext()) 
        {
          Row row = rowIterator.next();

          // Se valida q no sea la primera fila ya que esta contiene los encabezados
          if(0 != row.getRowNum())
          {
            List<String> lstRowsExcel = new ArrayList<>();

            for(int i = 0; i < numColumn; i++)
            {
              lstRowsExcel.add(row.getCell(i).toString());
            }
            
            lstDataExcel.add(lstRowsExcel);
          }
        }
  
        myWorkBook.close();
        inputStream.close();
        outputStream.close();
      }
  
    return lstDataExcel;
  }


  /**
   * Método que permite buscar un archivo en el drive de google según el nonmbre
   * @param fileName Nombre del archivo a buscar
   * @return Archivo encontrado
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public File getFileGoogleDrive(String fileName) throws IOException, GeneralSecurityException 
  {
    File fileGoogleDrive = null;
    String pageToken = null;

    do
    {
      FileList result = service.files().list()
                                      // .setQ("mimeType='image/jpeg'")
                                      .setSpaces("drive")                                    
                                      .setFields("nextPageToken, files(id, name)")
                                      .setPageToken(pageToken)
                                      .execute();

      List<File> lstFiles = result.getFiles();      
      
      for (File file : lstFiles) 
      {
        if(fileName.equals(file.getName()))
        {
          fileGoogleDrive = file;
          break;
        }
      }

      pageToken = result.getNextPageToken();
    }
    while(pageToken != null);

    return fileGoogleDrive;    
  }
}
