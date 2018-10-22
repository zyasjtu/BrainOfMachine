//package org.cora.service;
//
//import com.alibaba.fastjson.JSONObject;
//import org.cora.dao.UserDao;
//import org.cora.form.UserForm;
//import org.cora.model.User;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import java.util.Date;
//
//import static org.cora.constant.ResponseJson.EMPTY;
//import static org.cora.constant.ResponseJson.FAIL;
//import static org.cora.constant.ResponseJson.SUCCESS;
//
///**
// * @author Colin
// * @date 2018/8/15
// */
//
//@Service
//public class UserService {
//
//    private static final String WRONG_PASSWORD = "用户名/密码错误";
//
//    @Autowired
//    private UserDao userDao;
//
//    /**
//     * delete
//     *
//     * @param userForm userForm
//     * @return JSONObject
//     */
//    public JSONObject delete(UserForm userForm) {
//        User user = new User();
//        BeanUtils.copyProperties(userForm, user);
//        return userDao.delete(user) > 0 ? SUCCESS.toJSONObject() : EMPTY.toJSONObject(WRONG_PASSWORD);
//    }
//
//    /**
//     * insert
//     *
//     * @param userForm userForm
//     * @return JSONObject
//     */
//    public JSONObject insert(UserForm userForm) {
//        User user = new User();
//        BeanUtils.copyProperties(userForm, user);
//        Date now = new Date();
//        user.setCreateTime(now);
//        user.setUpdateTime(now);
//        return userDao.insert(user) > 0 ? SUCCESS.toJSONObject() : FAIL.toJSONObject();
//    }
//
//    /**
//     * select
//     *
//     * @param userForm userForm
//     * @return JSONObject
//     */
//    public JSONObject select(UserForm userForm) {
//        User user = new User();
//        BeanUtils.copyProperties(userForm, user);
//        user = userDao.select(user);
//        if (ObjectUtils.isEmpty(user)) {
//            return EMPTY.toJSONObject(WRONG_PASSWORD);
//        }
//        JSONObject response = SUCCESS.toJSONObject();
//        response.put("user", user);
//        return response;
//    }
//
//    /**
//     * update
//     *
//     * @param userForm userForm
//     * @return JSONObject
//     */
//    public JSONObject update(UserForm userForm) {
//        User user = new User();
//        BeanUtils.copyProperties(userForm, user);
//        user.setUpdateTime(new Date());
//        return userDao.update(user) > 0 ? SUCCESS.toJSONObject() : EMPTY.toJSONObject(WRONG_PASSWORD);
//    }
//}
