package org.cora.brainofmachine.constant;

import java.util.HashMap;

/**
 * @author Colin
 * @date 2018/10/8
 */
public class FaceApiConstants {

    public static final String FACE_DETECT_URL = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    public static final String FACE_COMPARE_URL = "https://api-cn.faceplusplus.com/facepp/v3/compare";
    public static final String PLATE_RECOGNIZE_URL = "https://api-cn.faceplusplus.com/imagepp/v1/licenseplate";
    public static final String ID_CARD_OCR_URL = "https://api-cn.faceplusplus.com/cardpp/v1/ocridcard";
    public static final String BANK_CARD_OCR_URL = "https://api-cn.faceplusplus.com/cardpp/v1/ocrbankcard";
    public static final String TEXT_OCR_URL = "https://api-cn.faceplusplus.com/imagepp/v1/recognizetext";
    public static final String BODY_DETECT_URL = "https://api-cn.faceplusplus.com/humanbodypp/v1/detect";
    public static final String SKELETON_DETECT_URL = "https://api-cn.faceplusplus.com/humanbodypp/v1/skeleton";

    public static final HashMap<String, String> API_MAP = new HashMap<String, String>() {
        {
            put("api_key", "THK3iDEykQSRZH3X8miQPfCyE1WNFVW9");
            put("api_secret", "LnemLDiGWn8tW36WtdHa5qErCigL5Gum");
        }
    };
    public static final String RETURN_LANDMARK = "return_landmark";
    public static final String RETURN_ATTRIBUTES = "return_attributes";
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
    public static final String HUMAN_BODIES = "humanbodies";
    public static final String HUMAN_BODY_RECTANGLE = "humanbody_rectangle";
    public static final String BODY_RECTANGLE = "body_rectangle";
    public static final String SKELETONS = "skeletons";
    public static final String BANK_CARDS = "bank_cards";
    public static final String NUMBER = "number";
    public static final String BANK = "bank";
    public static final String ORGANIZATION = "organization";
    public static final String RIGHT_BOTTOM = "right_bottom";
    public static final String LEFT_BOTTOM = "left_bottom";
    public static final String RIGHT_TOP = "right_top";
    public static final String LEFT_TOP = "left_top";
    public static final String CARDS = "cards";
    public static final String BIRTHDAY = "birthday";
    public static final String ADDRESS = "address";
    public static final String GENDER = "gender";
    public static final String RACE = "race";
    public static final String NAME = "name";
    public static final String ID_CARD_NUMBER = "id_card_number";
    public static final String ATTRIBUTES = "attributes";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String UPPER_BODY_CLOTH = "upper_body_cloth";
    public static final String UPPER_BODY_CLOTH_COLOR = "upper_body_cloth_color";
    public static final String LOWER_BODY_CLOTH = "lower_body_cloth";
    public static final String LOWER_BODY_CLOTH_COLOR = "lower_body_cloth_color";
    public static final String WHITE = "white";

    private FaceApiConstants() {
    }
}
