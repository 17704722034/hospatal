package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.MeunBean;
import com.lening.entity.PostBean;
import com.lening.entity.PostBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.PostService;
import com.lening.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private MeunMapper meunMapper;
    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if (postBean!=null){
            if (postBean.getPostname()!=null&&postBean.getPostname().length()>=1){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page(pageInfo.getPageNum()+"",total.intValue(),+pageInfo.getPageSize()+"");
        page.setList(list);
        System.out.println(page);
        return page;
    }

    public List<MeunBean> getMeListById(Long postid) {
        //全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);
        //当前职位拥有菜单  只要id
        List<Long> meunids = postMapper.getPostMeunIds(postid);
        if (meunids!=null&&meunids.size()>=1){
            for (Long meunid : meunids) {
                for (MeunBean bean : list) {
                    if (meunid.equals(bean.getId())) {
                        bean.setChecked(true);
                        break;
                    }
                }
            }

        }
        return list;
    }

    public void savePostMeun(Long postid, Long[] ids) {
        //先删除  后添加
        postMapper.deletePostMeunByPostid(postid);

        if (ids!=null&&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }
    }
}
