package com.bbodek.meboard.util.commonMsg;

import lombok.Getter;

@Getter
public class  MessageCode {

    //region MAST Msg
    public static final String EQP_NON_DATA_MSG = "설비 데이터를 찾지 못했습니다.";
    public static final String LINE_NON_DATA_MSG = "라인 데이터를 찾지 못했습니다.";
    public static final String FAB_NON_DATA_MSG = "공장 데이터를 찾지 못했습니다.";

    public static final String NON_FAB_MSG = "존재 하지 않는 공장 입니다.";
    public static final String NON_EQP_MSG = "존재 하지 않는 설비 입니다.";
    public static final String NON_LINE_MSG = "존재 하지 않는 라인 입니다.";
    public static final String COM_DEL_ERROR_MSG = "삭제중 오류가 발생하였습니다.";
    public static final String COM_UP_ERROR_MSG = "업데이트중 오류가 발생하였습니다.";
    public static final String COM_SAVE_ERROR_MSG = "저장중 오류가 발생하였습니다.";
    public static final String COM_SAVE_MSG = "저장에 성공 하였습니다.";
    public static final String COM_DEL_MSG = "삭제에 성공 하였습니다.";
    public static final String COM_UP_MSG = "업데이트에 성공 하였습니다.";
    public static final String COM_GET_DATA_MSG = "조회에 성공 하였습니다.";
    public static final String COM_CHECK_DATA_MSG = "입력 데이터를 확인해 주세요.";
    public static final String PRODUCT_NON_DATA_MSG = "제품 데이터를 찾지 못했습니다.";
    //endregion

    public static final String OPSWORK_NON_DATA_MSG = "작업 데이터를 찾지 못했습니다.";
    public static final String NON_OPSWORK_MSG = "존재 하지 않는 공장 입니다.";
    public static final String NON_OPSWORKER_MSG = "존재 하지 않는 설비 입니다.";
    public static final String NON_OPSWORKTAG_MSG = "존재 하지 않는 라인 입니다.";



    public static final String LABEL_NON_DATA_MSG = "라벨 데이터를 찾지 못했습니다.";

    public static final String LABEL_SAVE_DATA_MSG = "작업 데이터를 저장 못했습니다.";

    public static final String COM_LABEL_MSG = "반납에 성공 하였습니다.";
    public static final String COM_NON_LABEL_MSG = "이미 반납 처리 되었습니다.";
}
