package org.cora.util;

import org.apache.commons.lang3.StringUtils;
import org.cora.constant.ResponseJson;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.propertyeditors.StringArrayPropertyEditor.DEFAULT_SEPARATOR;


/**
 * @author Colin
 * @date 2018/8/16
 */
public class ResponseUtils {

    private ResponseUtils() {
    }

    public static String handleBindingResult(BindingResult bindingResult) {
        List<String> messageList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messageList.add(fieldError.getDefaultMessage());
        }

        String message = StringUtils.join(messageList,DEFAULT_SEPARATOR);
        return ResponseJson.INVALID.toJSONString(message);
    }
}
