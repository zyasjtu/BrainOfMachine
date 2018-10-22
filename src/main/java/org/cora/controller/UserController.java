//package org.cora.controller;
//
//import com.alibaba.fastjson.JSON;
//import org.cora.constant.ResponseJson;
//import org.cora.form.UserForm;
//import org.cora.service.UserService;
//import org.cora.util.ResponseUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author Colin
// * @date 2018/8/15
// */
//
//@RestController
//@RequestMapping(method = RequestMethod.POST)
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * delete
//     *
//     * @param userForm userForm
//     * @return int
//     */
//    @RequestMapping(value = "/deleteUser.json")
//    public String delete(@Validated(UserForm.Delete.class) UserForm userForm, BindingResult br) {
//        if (br.hasErrors()) {
//            return ResponseUtils.handleBindingResult(br);
//        }
//        return userService.delete(userForm).toString();
//    }
//
//    /**
//     * insert
//     *
//     * @param userForm userForm
//     * @return int
//     */
//    @RequestMapping(value = "/insertUser.json")
//    public String insert(@Validated(UserForm.Insert.class) UserForm userForm, BindingResult br) {
//        if (br.hasErrors()) {
//            return ResponseUtils.handleBindingResult(br);
//        }
//        return userService.insert(userForm).toString();
//    }
//
//    /**
//     * select
//     *
//     * @param userForm userForm
//     * @return User
//     */
//    @RequestMapping(value = "/selectUser.json")
//    public String select(@Validated(UserForm.Select.class) UserForm userForm, BindingResult br) {
//        if (br.hasErrors()) {
//            return ResponseUtils.handleBindingResult(br);
//        }
//        return JSON.toJSONStringWithDateFormat(userService.select(userForm), JSON.DEFFAULT_DATE_FORMAT);
//    }
//
//    /**
//     * update
//     *
//     * @param userForm userForm
//     * @return int
//     */
//    @RequestMapping(value = "/updateUser.json")
//    public String update(@Validated(UserForm.Update.class) UserForm userForm, BindingResult br) {
//        if (br.hasErrors()) {
//            return ResponseUtils.handleBindingResult(br);
//        }
//        return userService.update(userForm).toString();
//    }
//}
