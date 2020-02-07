package com.gongxm.photo.mapper;

import com.gongxm.photo.pojo.ImageGroupInfo;
import com.gongxm.photo.pojo.ImageGroupInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageGroupInfoMapper {
    int countByExample(ImageGroupInfoExample example);

    int deleteByExample(ImageGroupInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(ImageGroupInfo record);

    int insertSelective(ImageGroupInfo record);

    List<ImageGroupInfo> selectByExample(ImageGroupInfoExample example);

    ImageGroupInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ImageGroupInfo record, @Param("example") ImageGroupInfoExample example);

    int updateByExample(@Param("record") ImageGroupInfo record, @Param("example") ImageGroupInfoExample example);

    int updateByPrimaryKeySelective(ImageGroupInfo record);

    int updateByPrimaryKey(ImageGroupInfo record);

	List<ImageGroupInfo> findUnCollectImageGroup(@Param("offset")int offset, @Param("pageSize")int pageSize);

	List<ImageGroupInfo> findLastRecommend();

	List<ImageGroupInfo> findPageList(@Param("offset")int offset, @Param("pageSize")int pageSize);

	List<ImageGroupInfo> findSearchList(String keyword, @Param("offset")int offset, @Param("pageSize")int pageSize);

	List<ImageGroupInfo> findImageGroupByTag(@Param("tag")String tag,@Param("offset") int offset, @Param("pageSize")int pageSize);
}