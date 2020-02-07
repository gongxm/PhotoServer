package com.gongxm.photo.mapper;

import com.gongxm.photo.pojo.ImagePage;
import com.gongxm.photo.pojo.ImagePageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImagePageMapper {
    int countByExample(ImagePageExample example);

    int deleteByExample(ImagePageExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImagePage record);

    int insertSelective(ImagePage record);

    List<ImagePage> selectByExample(ImagePageExample example);

    ImagePage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImagePage record, @Param("example") ImagePageExample example);

    int updateByExample(@Param("record") ImagePage record, @Param("example") ImagePageExample example);

    int updateByPrimaryKeySelective(ImagePage record);

    int updateByPrimaryKey(ImagePage record);

	List<ImagePage> findUnCollectImagePage(@Param("offset")int offset, @Param("pageSize")int pageSize);
}