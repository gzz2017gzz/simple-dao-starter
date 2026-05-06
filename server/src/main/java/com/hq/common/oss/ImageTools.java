package com.hq.common.oss;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 高振中
 * @summary 图片工具类
 * @date 2024-07-21 21:44:15
 **/
public final class ImageTools {

    private  ImageTools(){}
    /**
     * 长边大于800像素时压缩图片
     **/
    private static final Integer maxSize = 800;

    /**
     * 压缩图片，如果长边大于800像素，则按比例压缩
     *
     * @param inputFile  输入图片文件
     * @param outputFile 输出图片文件
     */
    @SneakyThrows
    public static void compressImage(File inputFile, File outputFile) {
        // 读取原始图片
        BufferedImage originalImage = ImageIO.read(inputFile);
        if (originalImage == null) {
            throw new IOException("无法读取图片文件: " + inputFile.getPath());
        }

        // 获取图片的宽度和高度
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        if (originalWidth > maxSize || originalHeight > maxSize) {
            // 按比例缩放
            if (originalWidth > originalHeight) {
                newWidth = maxSize;
                newHeight = (int) ((double) originalHeight / originalWidth * maxSize);
            } else {
                newHeight = maxSize;
                newWidth = (int) ((double) originalWidth / originalHeight * maxSize);
            }
        }

        // 创建缩放后的图片
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();

        // 保存压缩后的图片
        String formatName = getFileExtension(outputFile.getName());
        ImageIO.write(resizedImage, formatName, outputFile);
    }

    /**
     * 获取文件扩展名（格式）
     *
     * @param fileName 文件名
     * @return 文件扩展名（如 "jpg", "png"）
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("文件名没有扩展名: " + fileName);
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    public static void main(String[] args) {

        File inputFile = new File("D:/grade/score/227.0周芷伊.jpg"); // 输入图片路径
        File outputFile = new File("D:/grade/score/1.jpg"); // 输出图片路径

        // 压缩图片
        compressImage(inputFile, outputFile);
        System.out.println("图片压缩完成！");

    }
}