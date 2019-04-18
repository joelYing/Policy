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
    @Select("select * from policies where title like concat('%', #{title}, '%') " +
            "and source_name like concat('%', #{sourceName}, '%') " +
            "order by published ${rank}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sourceId", column = "source_id"),
            @Result(property = "sourceName", column = "source_name"),
            @Result(property = "country", column = "country"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "tag", column = "tag"),
            @Result(property = "url", column = "url"),
            @Result(property = "title", column = "title"),
            @Result(property = "content", column = "content"),
            @Result(property = "published", column = "published"),
    })
    List<Policy> getAllPolicies(@Param("title") String title,
                                @Param("sourceName") String sourceName,
                                @Param("rank") String rank);

    @Insert("insert into source_list(source_id, url, tag, header, regular, monitor) values(#{sourceId}, " +
            "#{url}, #{tag}, #{header}, #{regular}, #{monitor})")
    int insertSourceList(@Param("sourceId") int sourceId, @Param("url") String url,
                    @Param("tag") String tag, @Param("header") String header,
                    @Param("regular") String regular, @Param("monitor") int monitor);


}
