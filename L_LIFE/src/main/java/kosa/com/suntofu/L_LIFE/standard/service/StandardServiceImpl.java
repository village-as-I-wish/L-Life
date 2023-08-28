package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.dao.StandardDAO;
import kosa.com.suntofu.L_LIFE.standard.vo.StandardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StandardServiceImpl implements StandardService{

    private final StandardDAO standardDAO;
    public List<StandardVO> getAllStandard() {
        return standardDAO.selectAllStandard();
    }


}
