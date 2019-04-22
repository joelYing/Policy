package com.spider.policy;

import com.spider.policy.entity.SourceList;
import com.spider.policy.utils.InfoSave;
import static com.spider.policy.utils.GetSource.getByGenerals;
import static com.spider.policy.utils.GetSource.getByTools;

/**
 * @PackageName com.spider.policy
 * @Author joel
 * @Date 2019/4/22 10:08
 **/
public class NewPolicyTest {
    public static void getSourceLists(int id) {
        SourceList sourceList = InfoSave.selectAPolicyList(id);
        System.out.println(sourceList.getUrl());
        if (sourceList.getUseTool() == 1) {
            getByTools(sourceList);
        }
        if (sourceList.getUseTool() == 0) {
            getByGenerals(sourceList);
        }
    }

    public static void main(String[] args) {
        getSourceLists(78);
    }
}
