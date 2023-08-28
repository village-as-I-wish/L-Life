package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberVo {
    private int mId;
    private String mName;
    private String mGender;
    private int mPoint;
    private int mClubStatus;
    private String mAddress;
    private String mEmail;
    private String mProfile;
    private String mPhone;
}
