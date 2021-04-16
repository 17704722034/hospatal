package com.lening.service;

import com.lening.entity.DeptBean;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;

public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptById(Long id);

    void saveDeptPost(Long id, Long[] postids);
}
