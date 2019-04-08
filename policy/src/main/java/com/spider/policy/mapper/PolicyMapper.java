package com.spider.policy.mapper;

import com.spider.policy.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @PackageName com.spider.policy.mapper
 * @Author joel
 * @Date 2019/2/1 11:59
 **/

@Repository
public interface PolicyMapper {
    /**
     * @return 查询全部视频信息的数据
     */
    @Select("select * from policies where policy_title like concat('%', #{policyTitle}, '%') " +
            "and policy_source like concat('%', #{policySource}, '%') " +
            "order by ${timeby} ${rank}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "policyUrl", column = "policy_url"),
            @Result(property = "policyTitle", column = "policy_title"),
//            @Result(property = "policyContent", column = "policy_content"),
            @Result(property = "policySource", column = "policy_source"),
            @Result(property = "policyKeywords", column = "policy_keywords"),
            @Result(property = "publishTime", column = "publish_time"),
    })
    List<Policy> getAllPolicies(@Param("policyTitle") String vedioName,
                                @Param("policySource") String types,
                                @Param("timeby") String timeby,
                                @Param("rank") String rank);

}
