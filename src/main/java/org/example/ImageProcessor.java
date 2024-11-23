package org.example;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    static {
        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static BufferedImage swapBackground(BufferedImage originalImage, String backgroundFilePath) throws Exception {
        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", byteArrayOutputStream); // Preserve alpha channel
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Decode the byte array to OpenCV Mat with alpha channel
        Mat mat = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_UNCHANGED);

        // Check if the image has an alpha channel
        List<Mat> channels = new ArrayList<>();
        Core.split(mat, channels);

        if (channels.size() < 4) {
            throw new IllegalArgumentException("Input image does not have an alpha channel.");
        }

        // Separate alpha channel (transparency)
        Mat alphaChannel = channels.get(3);

        // Merge RGB channels back without alpha
        Mat rgbImage = new Mat();
        Core.merge(channels.subList(0, 3), rgbImage);

        // Load the new background image
        Mat backgroundMat = Imgcodecs.imread(backgroundFilePath, Imgcodecs.IMREAD_COLOR);
        if (backgroundMat.empty()) {
            throw new IllegalArgumentException("Background file not found: " + backgroundFilePath);
        }

        // Resize the foreground and background to 300x250
        Imgproc.resize(rgbImage, rgbImage, new Size(300, 250));
        Imgproc.resize(alphaChannel, alphaChannel, new Size(300, 250));
        Imgproc.resize(backgroundMat, backgroundMat, new Size(300, 250));

        // Prepare the final image by combining the foreground and background
        Mat result = new Mat(backgroundMat.size(), backgroundMat.type());
        for (int row = 0; row < result.rows(); row++) {
            for (int col = 0; col < result.cols(); col++) {
                double alpha = alphaChannel.get(row, col)[0] / 255.0; // Normalize alpha to [0, 1]
                double[] bgPixel = backgroundMat.get(row, col);
                double[] fgPixel = rgbImage.get(row, col);

                // Blend pixels using alpha transparency
                double[] blendedPixel = new double[3];
                for (int i = 0; i < 3; i++) {
                    blendedPixel[i] = alpha * fgPixel[i] + (1 - alpha) * bgPixel[i];
                }

                result.put(row, col, blendedPixel);
            }
        }

        // Encode the result to byte array
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".png", result, mob);

        // Convert the byte array back to BufferedImage
        return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
    }
}
