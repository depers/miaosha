package cn.bravedawn.exception;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/4/22 15:35
 */
public enum ExceptionEnum implements CommonError{

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(1001,"参数不合法"),
    UNKNOWN_ERROR(1002,"未知错误"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(2001,"用户不存在"),
    USER_LOGIN_FAIL(2002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(2003,"用户还未登陆"),
    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(3001,"库存不足"),
    MQ_SEND_FAIL(3002,"库存异步消息失败"),
    RATELIMIT(3003,"活动太火爆,请稍后再试"),
            ;

    private int errCode;
    private String errMsg;

    ExceptionEnum(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
