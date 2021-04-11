package  com.jicg.service.core.manager.bean;

import  com.jicg.service.core.annos.XlsAlias;
import lombok.Data;

/**
 * @author jicg on 2021/4/3
 */
@Data
public class ButtonInfo {
    // 表
    @XlsAlias("表")
    private String table;
    //名称
    @XlsAlias("名称")
    private String name;
    //描述
    @XlsAlias("描述")
    private String remark;
    //按钮位置
    @XlsAlias("按钮位置")
    private String position;
    //序号
    @XlsAlias("序号")
    private Integer order;
    //js方法
    @XlsAlias("js方法")
    private String js;
    //多选条件
    @XlsAlias("多选条件")
    private String isMutiSel = "N";
    //存储过程
    @XlsAlias("存储过程")
    private String procName;
}
