package cn.bravedawn.exception;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/22 15:34
 */
public class BusinessException extends RuntimeException implements CommonError{

    private CommonError commonError;

    public BusinessException(CommonError commonError, String... msgs) {
        super(String.format(commonError.getErrMsg(), msgs));
        this.commonError = commonError;
    }

    public BusinessException(CommonError commonError) {
        super(String.join("[", commonError.getErrCode()+"", "]", commonError.getErrMsg()));
        this.commonError = commonError;
    }

    public BusinessException(CommonError commonError, Throwable e) {
        this(String.join("[", commonError.getErrCode()+"", "]", commonError.getErrMsg()), e);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause, true, true);
    }

    @Override
    public int getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }

    public CommonError getCommonError() {
        return commonError;
    }
}
