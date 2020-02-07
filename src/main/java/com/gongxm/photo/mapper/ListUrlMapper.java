package com.gongxm.photo.mapper;

import com.gongxm.photo.pojo.ListUrl;
import com.gongxm.photo.pojo.ListUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ListUrlMapper {
    int countByExample(ListUrlExample example);

    int deleteByExample(ListUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ListUrl record);

    int insertSelective(ListUrl record);

    List<ListUrl> selectByExample(ListUrlExample example);

    ListUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ListUrl record, @Param("example") ListUrlExample example);

    int updateByExample(@Param("record") ListUrl record, @Param("example") ListUrlExample example);

    int updateByPrimaryKeySelective(ListUrl record);

    int updateByPrimaryKey(ListUrl record);

	List<ListUrl> findUnCollectListUrl(@Param("offset")int offset, @Param("pageSize")int pageSize);
}