package kosa.com.suntofu.L_LIFE.livestream.dao;


import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageProductVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LiveStreamDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LiveStreamDao {
    LiveStreamDetailVo selectLiveStreamById(int lStreamId);

    List<LfPackageProductVo> selectLiveStreamPackageById(int lfPackageId);
}
