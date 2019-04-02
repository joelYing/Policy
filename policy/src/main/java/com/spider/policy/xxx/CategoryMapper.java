package com.spider.policy.xxx;

import java.util.List;

import com.spider.policy.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @PackageName com.spider.kan360.mapper
 * @Author joel
 * @Date 2019/4/2 15:24
 **/
@Mapper
public interface CategoryMapper {
    @Select("select * from category_") // 查询所有
    List<Category> findAll();

    @Insert("insert into category_ (name) values (#{name})") // 添加
    public int save(Category category);

    @Delete("delete from category_ where id=#{id} ") // 删除
    public void delete(int id);

    @Select("select * from category_ where id=#{id}  ") // 查询一个
    public Category get(int id);

    @Update("update category_ set name = #{name} where id=#{id} ") // 修改
    public int update(Category category);
}
