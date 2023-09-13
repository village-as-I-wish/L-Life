package kosa.com.suntofu.L_LIFE.livestream.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LiveStreamDetailVo {

    private Integer lStreamId; // 라이브 방송 ID

    private Integer lfPackageId; // 라이브 방송 패키지 ID


    private String lStreamName; // 라이브 방송 명

    private String lStreamComment; // 라이브 방송 코멘트

    private String lStreamTags; // 라이브 방송 태그

    private String lStreamImg ; // 라이브 방송 사진
    private LocalDateTime lStreamTime; // 라이브 방송 날짜 (timestamp)

    private LfPackageVo lfPackage;

}
