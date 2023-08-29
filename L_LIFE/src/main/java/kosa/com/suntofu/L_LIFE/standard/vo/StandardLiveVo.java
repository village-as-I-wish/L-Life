package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardLiveVo {

    private String lStreamName; //라이브 방송 이름
    private String l_stream_time; // 라이브 방송 날짜 (timestamp)
    private String streamDate; // 라이브 날짜
    private String streamTime; // 라이브 시간
    private String lfPackageName; // 라이브 패키지 이름
    private int lfPackageStCoin;  // 라이브 패키지 코인 개수
    private int lfPackageType;  // 라이브패키지 타입 0이 스탠다드
    private String lfPackageImg; // 패키지 이미지
}


