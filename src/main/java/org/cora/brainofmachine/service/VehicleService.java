package org.cora.brainofmachine.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.cora.brainofmachine.constant.FaceApiConstants;
import org.cora.brainofmachine.constant.ResponseJson;
import org.cora.brainofmachine.util.FaceApiUtils;
import org.cora.brainofmachine.util.FileUtils;
import org.cora.brainofmachine.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Colin
 * @date 2018/10/9
 */

@Service
public class VehicleService {

    private static final Logger LOGGER = Logger.getLogger(VehicleService.class);

    public JSONObject recognizePlate(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String fullPath = FileUtils.saveFile(file, "img/plateRecognize.jpg", request);
            File f = new File(fullPath);

            HashMap<String, byte[]> fileMap = new HashMap<>(2);
            fileMap.put(FaceApiConstants.IMAGE_FILE, FaceApiUtils.getBytesFromFile(f));
            byte[] bytes = FaceApiUtils.post(FaceApiConstants.PLATE_RECOGNIZE_URL, FaceApiConstants.API_MAP, fileMap);
            JSONObject responseJo = JSON.parseObject(new String(bytes));
            if (StringUtils.isNotBlank(responseJo.getString(FaceApiConstants.ERROR_MESSAGE))) {
                responseJo.putAll(ResponseJson.FAIL.toJSONObject());
                return responseJo;
            }

            BufferedImage bufferedImage = ImageIO.read(f);
            Graphics2D graphics = ImageUtils.getDefaultGraphics2D(bufferedImage);
            JSONArray plates = responseJo.getJSONArray(FaceApiConstants.RESULTS);
            for (Integer i = 0; i < plates.size(); i++) {
                String number = plates.getJSONObject(i).getString(FaceApiConstants.PLATE_NUMBER);
                Integer xMin = Integer.MAX_VALUE;
                Integer yMin = Integer.MAX_VALUE;
                Integer xMax = Integer.MIN_VALUE;
                Integer yMax = Integer.MIN_VALUE;

                JSONObject boundary = plates.getJSONObject(i).getJSONObject(FaceApiConstants.BOUND);
                for (Map.Entry<String, Object> entry : boundary.entrySet()) {
                    JSONObject location = (JSONObject) entry.getValue();
                    xMin = Math.min(xMin, location.getInteger(FaceApiConstants.X));
                    yMin = Math.min(yMin, location.getInteger(FaceApiConstants.Y));
                    xMax = Math.max(xMax, location.getInteger(FaceApiConstants.X));
                    yMax = Math.max(yMax, location.getInteger(FaceApiConstants.Y));
                }

                graphics.drawRect(xMin, yMin, xMax - xMin, yMax - yMin);
                graphics.drawString(number, xMin, yMin - 5);
            }

            ImageUtils.write(bufferedImage, fullPath);
            response.sendRedirect("../upload/" + fullPath.substring(fullPath.lastIndexOf(FileUtils.DEFAULT_PATH_SEPARATOR) + 1));

            responseJo.putAll(ResponseJson.SUCCESS.toJSONObject());
            return responseJo;
        } catch (Exception e) {
            LOGGER.error("recognize plate exception! ", e);
            return ResponseJson.FAIL.toJSONObject();
        }
    }
}
