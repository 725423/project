package cn.zb.project.controller;


import cn.zb.project.entity.News;
import cn.zb.project.service.NewsService;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.NewsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @RequestMapping("/toNews")
    public String toNews(){
        return "news/news";
    }

    @RequestMapping("/listNews")
    @ResponseBody
    public DataView listNews(NewsVo newsVo){
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(newsVo.getTitle()), "title", newsVo.getTitle());
        IPage<News> iPage = new Page<>(newsVo.getPage(), newsVo.getLimit());
        newsService.page(iPage, queryWrapper);
        return new DataView(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 新增或修改
     * @param
     * @return
     */
    @RequestMapping("/addOrUpdateNews")
    @ResponseBody
    public DataView addOrUpdate(News news){
        newsService.saveOrUpdate(news);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或修改成功");
        return dataView;
    }



    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        newsService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除数据成功");
        return dataView;
    }
}
