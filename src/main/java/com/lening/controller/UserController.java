package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.service.UserService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            return new ResultInfo(true, "编辑成功");
        }catch (Exception e){
            return new ResultInfo(false, "编辑失败");
        }
    }

    //给用户分配职位
    @RequestMapping("/getUserInfo")
    public UserBean getUserInfo(Long id){
        return userService.getUserInfo(id);
    }

    @RequestMapping("/getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub,HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);
        if (userBean==null){
            return new ResultInfo(false, "登录失败,用户名或者密码错误");
        }else {
            request.getSession().setAttribute("ub",userBean);
            return new ResultInfo(true, "登录成功");
        }
    }
    @RequestMapping("/getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        /**
         * 这里需要知道是谁登录的
         * 还要查询出不是按钮的菜单
         */
        UserBean ub = (UserBean) request.getSession().getAttribute("ub");

        return userService.getMeunList(ub);
    }

    //去给用户选择部门
    @RequestMapping("/getUserDeptById")
    public UserBean getUserDeptById(Long id){
        return userService.getUserDeptById(id);
    }

    @RequestMapping("/saveUserDept")
    public ResultInfo saveUserDept(Long id,@RequestBody Long [] deptids){
        try {
            userService.saveUserDept(id,deptids);
            return new ResultInfo(true, "保存成功");
        }catch (Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }












    @RequestMapping("getUserList")
    public List<UserBean> getUserList(){
        return userService.getUserList();
    }
    @RequestMapping("getUserListConn")
    public Page<UserBean> getUserListConn(@RequestBody UserBean userBean, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        System.out.println(userBean);
        return userService.getUserListConn(userBean,pageNum,pageSize);
    }

}
