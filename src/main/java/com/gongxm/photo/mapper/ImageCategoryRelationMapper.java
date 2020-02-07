package com.gongxm.photo.mapper;

import com.gongxm.photo.pojo.ImageCategoryRelation;
import com.gongxm.photo.pojo.ImageCategoryRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageCategoryRelationMapper {
    int countByExample(ImageCategoryRelationExample example);

    int deleteByExample(ImageCategoryRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImageCategoryRelation record);

    int insertSelective(ImageCategoryRelation record);

    List<ImageCategoryRelation> selectByExample(ImageCategoryRelationExample example);

    ImageCategoryRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ImageCategoryRelation record, @Param("example") ImageCategoryRelationExample example);

    int updateByExample(@Param("record") ImageCategoryRelation record, @Param("example") ImageCategoryRelationExample example);

    int updateByPrimaryKeySelective(ImageCategoryRelation record);

    int updateByPrimaryKey(ImageCategoryRelation record);
}