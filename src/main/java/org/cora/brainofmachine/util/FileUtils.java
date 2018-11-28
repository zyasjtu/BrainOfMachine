package org.cora.brainofmachine.util;

import org.cora.brainofmachine.constant.Constants;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Colin
 * @date 2018/10/8
 */
public class FileUtils {

    private FileUtils() {
    }

    public static final String DEFAULT_PATH_SEPARATOR = "/";

    private static void saveFile(MultipartFile file, String path) throws IOException {
        if (!file.isEmpty()) {
            file.transferTo(new File(path));
        } else {
            throw new IOException("文件" + Constants.NOT_BLANK);
        }
    }

    public static String saveFile(MultipartFile file, String defaultFileRelativePath,
                                  HttpServletRequest request) throws IOException {
        String bathPath = request.getSession().getServletContext().getRealPath("/");
        String fileName = UUID.randomUUID().toString() + "." + ImageUtils.DEFAULT_IMAGE_FORMAT;
        if (file.isEmpty()) {
            File f = new File(bathPath + defaultFileRelativePath);
            try (FileInputStream inputStream = new FileInputStream(f)) {
                file = new MockMultipartFile(f.getName(), inputStream);
            }
        }
        String fullPath = bathPath + "upload/" + fileName;
        FileUtils.saveFile(file, fullPath);
        return fullPath;
    }
}
