package com.gaboot.backend.common.service;

import com.gaboot.backend.common.constant.Storage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Service
public class ImageService {

    public String uploadImage(MultipartFile file, String filename){
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(Storage.USER_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Define the path to save the file
//        Path filePath = Paths.get(Storage.USER_DIR + file.getOriginalFilename());
        Path filePath = Paths.get(Storage.USER_DIR + filename + "." + getFileExtension(file));
        String imagePath = "";

        try {
            if(filePath.toFile().exists()) deleteFile(filePath.toString());
            Files.copy(file.getInputStream(), filePath);
            imagePath = Storage.USER_DIR + filePath.getFileName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public String uploadImageThumb(MultipartFile file, String filename) {
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(Storage.USER_DIR_THUMB);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Define the path to save the file
        Path filePath = Paths.get(Storage.USER_DIR_THUMB + filename + "." + getFileExtension(file));
        String imagePath = "";

        try {
            if(filePath.toFile().exists()) deleteFile(filePath.toString());
            final String fileExtension = getFileExtension(file);
            BufferedImage resizedImage = resizeImage(file.getInputStream()); // Set dimensions
            saveImage(resizedImage, fileExtension, filePath.toString());
            imagePath = Storage.USER_DIR_THUMB + filePath.getFileName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if(file.isFile()) file.delete();
    }

    // ============================================================================

    public BufferedImage resizeImage(InputStream inputImage) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputImage);
        final int width = Math.round((float) originalImage.getWidth() * 0.2f);
        final int height = Math.round((float) originalImage.getHeight() * 0.2f);
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());

        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, width, height, null);
        graphics.dispose();

        return resizedImage;
    }

    public void saveImage(BufferedImage image, String formatName, String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        ImageIO.write(image, formatName, outputFile);
    }

    public String getFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } else {
            return ""; // No extension found
        }
    }
}