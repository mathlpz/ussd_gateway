package com.lpz.ussd.enums;

/**
 * CommandID字段表示USSD业务应用协议消息的类型。
 * 
 * @author lpz
 * @version 2018年12月10日 下午2:34:09
 */
public enum UssdCommandID {

    // UssdBind
    USSD_BIND(0x00000065),
    // UssdUnBind
    USSD_UNBIND(0x00000066),
    // UssdBindResp
    USSD_BIND_RESP(0x00000067),
    // UssdUnBindResp
    USSD_UNBIND_RESP(0x00000068),
    // UssdBegin
    USSD_BEGIN(0x0000006F),
    // UssdContinue
    USSD_CONTINUE(0x00000070),
    // UssdEnd
    USSD_END(0x00000071),
    // UssdAbort
    USSD_ABORT(0x00000072),
    // UssdSwitch
    USSD_SWITCH(0x00000074),
    // UssdChargeInd
    USSD_CHARGEIND(0x00000075),
    // UssdChargeIndResp
    USSD_CHARGEIND_RESP(0x00000076),
    // UssdShake
    USSD_SHAKE(0x00000083),
    // UssdShakeResp
    USSD_SHAKE_RESP(0x00000084);

    private long value;

    private UssdCommandID(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static UssdCommandID fromValue(long value) {
        for (UssdCommandID sc : UssdCommandID.values()) {
            if (sc.getValue() == value) {
                return sc;
            }
        }
        return null;
    }

}
