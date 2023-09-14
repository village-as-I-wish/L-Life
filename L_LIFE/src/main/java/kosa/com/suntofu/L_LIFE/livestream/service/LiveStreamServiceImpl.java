package kosa.com.suntofu.L_LIFE.livestream.service;


import kosa.com.suntofu.L_LIFE.livestream.dao.LiveStreamDao;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageProductVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LfPackageVo;
import kosa.com.suntofu.L_LIFE.livestream.vo.LiveStreamDetailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveStreamServiceImpl implements LiveStreamService{

    private final LiveStreamDao liveStreamDao;

    @Override
    public LiveStreamDetailVo getLiveStreamDetail(int lStreamId) {
        return liveStreamDao.selectLiveStreamById(lStreamId); // 라이브 스트림 값이 null 인 경우
    }

    @Override
    public List<LfPackageProductVo> getLiveStreamPackage(int lfPackageId) {
        return liveStreamDao.selectLiveStreamPackageById(lfPackageId);
    }
}
