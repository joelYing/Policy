package com.spider.policy.mapper;

import com.spider.policy.entity.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @PackageName com.spider.kan360.mapper
 * @Author joel
 * @Date 2019/2/1 11:59
 **/

@Repository
public interface PolicyMapper {
    /**
     * @return 查询全部视频信息的数据
     */
    @Select("select * from policies")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "policyUrl", column = "policy_url"),
            @Result(property = "policyTitle", column = "policy_title"),
            @Result(property = "policyContent", column = "policy_content"),
            @Result(property = "policySource", column = "policy_source"),
            @Result(property = "policyKeywords", column = "policy_keywords"),
            @Result(property = "publishTime", column = "publish_time"),
    })
    List<Policy> getAllPolicies();


    /**
     * @return 可按照年代、类型、地区、影片名称模糊匹配多条件查询获取影片列表，可按照创建时间、更新时间排序
     * 多条件模糊查询时要注意，之前老是出现  Parameter 'vedioName' not found. Available parameters are [arg1, arg0, param1, param2]
     * 的问题 原因大概是 Dao层接口定义和Mapper中的参数类型不一致
     *
     * Controller层 有多个参数时，需要加上参数注解 @RequestParam("")
     * 而与之对应的 在 Mapper 中也要加上参数注解  @Param("")
     *
     * 传入 order by ... 时需要注意
     * # 是把传入的数据当作字符串，如#{field}传入的是id,则sql语句生成是这样，order by "id",   根据id排序时会报错．． 
     * $ 传入的数据直接生成在sql里，如${field}传入的是id,则sql语句生成是这样，order by id,   根据id排序这种写法是正确的． 
     */
    @Select("select * from kan_allvedio where name like concat('%', #{vedioName}, '%') " +
            "and type like concat('%', #{types}, '%') " +
            "and year like concat('%', #{year}, '%') " +
            "and area like concat('%', #{area}, '%') order by ${timeby} ${rank}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vedioId", column = "type_id"),
            @Result(property = "vedioUrl", column = "vedio_url"),
            @Result(property = "vedioType", column = "vedio_type"),
            @Result(property = "vedioName", column = "name"),
            @Result(property = "coverImg", column = "picture_url"),
            @Result(property = "pay", column = "pay"),
            @Result(property = "score", column = "score"),
            @Result(property = "types", column = "type"),
            @Result(property = "year", column = "year"),
            @Result(property = "area", column = "area"),
            @Result(property = "daoyan", column = "daoyan"),
            @Result(property = "yanyuan", column = "yanyuan"),
            @Result(property = "introduce", column = "introduce"),
            @Result(property = "source", column = "source"),
    })
    List<VedioInfos> getVedioByMulConditions(@Param("vedioName") String vedioName,
                                                @Param("types") String types,
                                                @Param("year") String year,
                                                @Param("area") String area,
                                                @Param("timeby") String timeby,
                                                @Param("rank") String rank);

}
