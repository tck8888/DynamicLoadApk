package com.tck.nepluginapk;

import java.util.List;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/26</p>
 *
 * @author tck
 * @version 1.0
 */
public class GroupBean {

    public String name;
    public int replyNum = 100;
    public int page = 1;
    public List<ChildBean> childBeans;

    public boolean isOpen;
}
