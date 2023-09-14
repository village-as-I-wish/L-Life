package kosa.com.suntofu.L_LIFE.livestream.service;

import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageProductVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LiveStreamDetailVo;

import java.util.List;

public interface LiveStreamService {
    LiveStreamDetailVo getLiveStreamDetail(int lStreamId);

    List<LfPackageProductVo> getLiveStreamPackage(int lStreamId);
}
