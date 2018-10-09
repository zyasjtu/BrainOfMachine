package org.cora.constant;

import java.util.HashMap;

/**
 * @author Colin
 * @date 2018/10/8
 */
public class FaceApiConstants {

    public static final String FACE_DETECT_URL = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    public static final String FACE_COMPARE_URL = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    public static final String PLATE_RECOGNIZE_URL = "https://api-cn.faceplusplus.com/imagepp/v1/licenseplate";
    public static final HashMap<String, String> API_MAP = new HashMap<String, String>() {
        {
            put("api_key", "THK3iDEykQSRZH3X8miQPfCyE1WNFVW9");
            put("api_secret", "LnemLDiGWn8tW36WtdHa5qErCigL5Gum");
            put("return_landmark", "2");
        }
    };
    public static final String IMAGE_FILE = "image_file";
    public static final String IMAGE_FILE_1 = "image_file1";
    public static final String IMAGE_FILE_2 = "image_file2";
    public static final String ERROR_MESSAGE = "error_message";
    public static final String FACE_RECTANGLE = "face_rectangle";
    public static final String TOP = "top";
    public static final String LEFT = "left";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String FACES = "faces";
    public static final String FACES_1 = "faces1";
    public static final String FACES_2 = "faces2";
    public static final String LANDMARK = "landmark";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String CONFIDENCE = "confidence";
    public static final String RESULTS = "results";
    public static final String PLATE_NUMBER = "license_plate_number";
    public static final String BOUND = "bound";

    private FaceApiConstants() {
    }
}
