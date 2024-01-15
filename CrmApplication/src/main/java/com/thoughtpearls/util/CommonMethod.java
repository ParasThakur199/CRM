package com.thoughtpearls.util;

import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.exception.HttpHeaderException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

public class CommonMethod {
    public static byte[] compressImage(byte[] originalImageBytes, AtomicLong compressedSizeKB) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(originalImageBytes);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Thumbnails.of(bais)
                    .size(100, 100)
                    .outputFormat("jpg")
                    .outputQuality(0.8)
                    .toOutputStream(baos);
            byte[] compressedImageBytes = baos.toByteArray();
            compressedSizeKB.set(compressedImageBytes.length / 1024);
            baos.close();

            return compressedImageBytes;
        } catch (IOException e) {
            throw new IllegalStateException(ExceptionMessage.failedCompress, e);
        }
    }
    public static void checkImageformat(MultipartFile file)
    {
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException(ExceptionMessage.notImage);
        }
    }

    public static Map<String,String> putMetaData(MultipartFile file, AtomicLong compressedSizeKB,Map<String,String> metadata) {
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(compressedSizeKB.get()));
        return metadata;
    }

    public static void validateHeader(String authorizationHeader){
        if(authorizationHeader.isEmpty()){
            throw new HttpHeaderException(ExceptionMessage.missingRequestHeader);
        }
    }
}
