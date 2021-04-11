package  com.jicg.service.core.manager.bean;

import  com.jicg.service.core.annos.XlsAlias;
import lombok.Data;

/**
 * @author jicg on 2021/3/31
 */
@Data
public class SelectOps {
    //组名
    @XlsAlias("组名")
    private String groupname;
    //名称
    @XlsAlias("名称")
    private String name;
    //描述
    @XlsAlias("描述")
    private String remark;
    //序号
    @XlsAlias("序号")
    private Integer orderno;

    @XlsAlias("颜色")
    private String color;

}
