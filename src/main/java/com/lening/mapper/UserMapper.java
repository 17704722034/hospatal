package com.lening.mapper;

import com.lening.entity.DeptBean;
import com.lening.entity.UserBean;
import com.lening.entity.UserBeanExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    Long[] getUserDeptidsById(@Param("id") Long id);

    void saveUserDept(@Param("userid") Long id,@Param("deptid") Long deptid);

    void deleteByUserId(Long userid);

    void deleteUserPostById(@Param("userid") Long id);

    List<DeptBean> getUserDeptById(@Param("id") Long id);

    void saveUserPost(@Param("userid") Long userid, @Param("postid") Long postid);

    List<UserBean> getLogin(UserBean ub);
}