package org.theponies.ponecrafter.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.theponies.ponecrafter.exceptions.SavePackageException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SavePackage {
    private final Map<String, byte[]> files;

    public SavePackage () {
        files = new HashMap<>();
    }

    public Map<String, byte[]> getFiles () {
        return files;
    }

    public void addImage (String name, Image image) throws SavePackageException {
        try {
            files.put(name, getImageBytes(image));
        } catch (IOException e) {
            throw new SavePackageException("Failed to add image to save package.", e);
        }
    }

    public void addFile (String name, byte[] data) {
        files.put(name, data);
    }

    public void saveToZip (String path) throws IOException {
        File file = new File(path);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
        for (String filename : files.keySet()) {
            out.putNextEntry(new ZipEntry(filename));
            out.write(files.get(filename));
            out.closeEntry();
        }
        out.close();
    }

    private byte[] getImageBytes (Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", output);
            return output.toByteArray();
        }
    }
}
