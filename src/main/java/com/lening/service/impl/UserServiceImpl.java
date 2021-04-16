package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.*;
import com.lening.mapper.DeptMapper;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.service.UserService;
import com.lening.utils.MD5key;
import com.lening.utils.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MeunMapper meunMapper;
    @Autowired
    private DeptMapper deptMapper;
    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    public Page<UserBean> getUserListConn(UserBean userBean,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserBeanExample userBeanExample = new UserBeanExample();
        UserBeanExample.Criteria criteria = userBeanExample.createCriteria();
        if (userBean!=null){
            if (userBean.getUname()!=null&&userBean.getUname().length()>=1){
                criteria.andUnameLike("%"+userBean.getUname()+"%");
            }
            if (userBean.getAge()!=null){
                criteria.andAgeGreaterThanOrEqualTo(userBean.getAge());
            }
            if (userBean.getEage()!=null){
                criteria.andAgeLessThanOrEqualTo(userBean.getEage());
            }
        }
        List<UserBean> list = userMapper.selectByExample(userBeanExample);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    public List<MeunBean> getMeunList(UserBean ub) {
        if (ub!=null){
            //先展示不是按钮的菜单
            MeunBeanExample example = new MeunBeanExample();
            MeunBeanExample.Criteria criteria = example.createCriteria();
            criteria.andIsbuttonEqualTo(0);
            List<MeunBean> list = meunMapper.selectByExample(example);
            return list;
        }
        return null;
    }

    public UserBean getUserDeptById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        //查询用户的部门
        Long [] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> dlist = deptMapper.selectByExample(null);
        userBean.setDlist(dlist);
        return userBean;
    }

    public void saveUserDept(Long id, Long [] deptids) {
        //先删除再添加
        userMapper.deleteUserPostById(id);
        userMapper.deleteByUserId(id);
        if (deptids!=null && deptids.length>=1){
            for (Long deptid : deptids) {
                userMapper.saveUserDept(id, deptid);
            }
        }

    }

    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /*
        开始查询用户有没有部门
         */
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        //开始循环部门  查询部门里面的职位
        if (dlist!=null&&dlist.size()>=1){
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getPostById(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(id,deptBean.getId());
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    public void saveUserPost(UserBean userBean) {
        //先删除用户的职位

        if (userBean!=null){
            userMapper.deleteUserPostById(userBean.getId());
            if (userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    if (deptBean.getPostids()!=null&&deptBean.getPostids().length>=1){
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(),postid);
                        }
                    }


                }
            }
        }

    }

    public UserBean getLogin(UserBean ub) {
        //先用用户名或者手机号，都是用用户名接收的
        //都是string
        if(ub!=null){
            List<UserBean> list = userMapper.getLogin(ub);
            if (list!=null&&list.size()==1){
                //到这表示用户名或者手机号查到了
                //开始比对密码，比对密码需要加盐加密
                //加密算法有很多 常用的md5，bscript
                UserBean userBean = list.get(0);
                //这是页面用户输入的密码，进行加密加盐后和数据库的密码比较
                //相等正确，不相等错误
                String pwd = ub.getPwd();
                //这里的加盐加密规则和注册的要一致，都是一个人负责的
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                //相等就返回  其他都是空
                if (newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }


    @Test
    public void test(){
        String pwd = "123456";
        pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }
    //回显用的
    public List<MeunBean> getMeunList2() {
        Long [] ids = {1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id : ids) {
            for (MeunBean meunBean : list) {
                if (id.equals(meunBean.getId())){
                    meunBean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }
}
