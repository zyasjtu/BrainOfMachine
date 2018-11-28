package org.cora.brainofmachine.form;

import lombok.Data;
import org.cora.brainofmachine.constant.Constants;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Colin
 * @date 2018/8/15
 */

@Data
public class UserForm {

    @NotBlank(groups = {Insert.class, Delete.class, Update.class, Select.class}, message = "手机号" + Constants.NOT_BLANK)
    private String mobile;

    @NotBlank(groups = {Insert.class, Delete.class, Update.class, Select.class}, message = "密码" + Constants.NOT_BLANK)
    private String password;

    @NotBlank(groups = {Update.class}, message = "新密码" + Constants.NOT_BLANK)
    private String pwd;

    public interface Insert {
    }

    public interface Delete {
    }

    public interface Update {
    }

    public interface Select {
    }
}
