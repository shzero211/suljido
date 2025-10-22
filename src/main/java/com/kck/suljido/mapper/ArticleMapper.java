package com.kck.suljido.mapper;

import com.kck.suljido.model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ArticleMapper {
    public List<Article> selectAll();

}
