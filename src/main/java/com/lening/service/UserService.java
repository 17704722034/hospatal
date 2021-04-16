package com.lening.service;

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.utils.Page;

import java.util.List;

public interface UserService {

    List<UserBean> getUserList();

    Page<UserBean> getUserListConn(UserBean userbean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunList(UserBean ub);

    UserBean getUserDeptById(Long id);

    void saveUserDept(Long id,Long [] deptids);

    UserBean getUserInfo(Long id);

    void saveUserPost(UserBean userBean);

    UserBean getLogin(UserBean ub);
}
