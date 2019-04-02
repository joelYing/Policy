package com.spider.policy.xxx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spider.policy.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @PackageName com.spider.kan360.web
 * @Author joel
 * @Date 2019/4/2 15:19
 **/
public class CategoryController {
    @Autowired
    private CategoryMapper categoryMapper;

    @RequestMapping("/listCategory")
    public String listCategory(Model m, @RequestParam(value="start",defaultValue="0")int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        //1. 在参数里接受当前是第几页 start ，以及每页显示多少条数据 size。 默认值分别是0和5。
        //2. 根据start,size进行分页，并且设置id 倒排序
        PageHelper.startPage(start,size,"id desc");
        //3. 因为PageHelper的作用，这里就会返回当前分页的集合了
        List<Category> cs = categoryMapper.findAll();
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Category> page = new PageInfo<>(cs);
        //5. 把PageInfo对象扔进model，以供后续显示
        m.addAttribute("page", page);
        //6. 跳转到listCategory.jsp
        return "listCategory";
    }

    @RequestMapping("/addCategory")
    public String addCategory(Category c)throws Exception{
        categoryMapper.save(c);
        return "redirect:listCategory";  //添加成功,重定向到分类查询页面
    }

    @RequestMapping("/deleteCategory")
    public String deleteCategory(int id)throws Exception{
        categoryMapper.delete(id);
        return "redirect:listCategory";  //删除成功,重定向到分类查询页面
    }

    @RequestMapping("/updateCategory")    //修改方法
    public String updateCategory(Category c)throws Exception{
        categoryMapper.update(c);
        return "redirect:listCategory";  //修改成功,重定向到分类查询页面
    }

    @RequestMapping("/editCategory")
    public String editCategory(int id ,Model m)throws Exception{
        Category c = categoryMapper.get(id); //根据id查询
        m.addAttribute("c", c); //查到展示到修改页面
        return "editCategory";  //跳转到修改页面
    }
}

