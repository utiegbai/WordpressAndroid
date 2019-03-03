package com.entpress.entpress.utility.data;

import android.content.Context;
import android.util.Log;

//import com.entpress.entpress.utility.notification.FileUploadNotification;

import com.entpress.entpress.utility.notification.FileUploadNotification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

//import static com.entpress.entpress.utility.api.ApiUrl.UPLOAD_URL;

/**
 * Created by ray on 15/03/2018.
 */
public class FileUploader {
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;
    private String charset = "UTF-8";
    private String fieldName = "uploaded_file[]";
    private String uploadTitle = "uploading moments";


   // private FileUploadNotification fileUploadNotification;
    private int total_size = 0;
    private int sentData = 0;

    public FileUploader(Context context,String uploadTitle, int total_size)
            throws IOException {
        this.uploadTitle = uploadTitle;
        this.total_size = total_size;
       // fileUploadNotification = new FileUploadNotification(context);

        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";

        //URL url = new URL(UPLOAD_URL);
        //httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setChunkedStreamingMode(1024);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("ENCTYPE", "multipart/form-data");
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }

    /**
     * Adds a form field to the request
     * @param name field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        writer.append("--").append(boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"").append(name).append("\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=").append(charset).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(File uploadFile, FileUploadNotification fileUploadNotification, int uniqueID)
            throws IOException {
        String fileName = uploadFile.getName();
        //int sentData = 0;
        writer.append("--").append(boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(fileName).append("\"")
                .append(LINE_FEED);
        writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);

        //int length = inputStream.available();
        //fileUploadNotification.setFileSize(length);

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
            sentData += bytesRead;
            int progress = (int) ((sentData / (float) total_size) * 100);
            Log.e("upload progress",String.valueOf(progress) + " of "+String.valueOf(total_size));
           // fileUploadNotification.updateNotification(uniqueID,String.valueOf(progress),uploadTitle,String.valueOf(progress) + "%");
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a header field to the request.
     * @param name - name of the header field
     * @param value - value of the header field
     */
    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public String finish() throws IOException {
        String response = "";
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response = line;
            }
            reader.close();
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }

        return response;
    }
}