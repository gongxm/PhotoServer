package com.gongxm.photo.mapper;

import com.gongxm.photo.pojo.ImageInfo;
import com.gongxm.photo.pojo.ImageInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageInfoMapper {
    int countByExample(ImageInfoExample example);

    int deleteByExample(ImageInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageInfo record);

    int insertSelective(ImageInfo record);

    List<ImageInfo> selectByExample(ImageInfoExample example);

    ImageInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageInfo record, @Param("example") ImageInfoExample example);

    int updateByExample(@Param("record") ImageInfo record, @Param("example") ImageInfoExample example);

    int updateByPrimaryKeySelective(ImageInfo record);

    int updateByPrimaryKey(ImageInfo record);

    List<ImageInfo> findImageInfoByGroupId(@Param("imageGroupId")String imageGroupId, @Param("offset")int offset,@Param("pageSize")int pageSize);

    List<ImageInfo> findUnCollectImageInfo(@Param("offset")int offset, @Param("pageSize")int pageSize);
}