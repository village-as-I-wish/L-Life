package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.dao.StandardDAO;
import kosa.com.suntofu.L_LIFE.standard.vo.SearchRequestVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardLiveVo;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService {

    private final StandardDAO standardDAO;

    @Override
    public List<StandardVo> getAllStandard() {

        return standardDAO.selectAllStandard();
    }

    @Override
    public List<StandardLiveVo> getAllLiveStream() {

        return standardDAO.selectAllLiveStream();
    }

    @Override
    public List<StandardVo> getStandardByCategory(int fCategoryId) {

        return standardDAO.selectStandardProductByCategory(fCategoryId);
    }

    @Override
    public List<StandardVo> getStandardProductByKeyword(String keyword) {

        return standardDAO.selectStandardProductByKeyword(keyword);
    }

    @Override
    public List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo) {

        return standardDAO.searchStandardProductByFilter(requestVo);
    }
}
