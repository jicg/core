package com.jicg.service.core.manager.bean;

import com.jicg.service.core.annos.XlsAlias;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jicg on 2021/3/31
 */
@Data
public class ColumnInfo {
    //名称
    @XlsAlias("名称")
    private String name;
    //描述
    @XlsAlias("描述")
    private String remark;
    //序号
    @XlsAlias("序号")
    private Integer orderno;

    //显示类型
    @XlsAlias("显示类型")
    private ColumnType view_type;
    //显示长度
    @XlsAlias("显示长度")
    private int view_size = 1;
    @XlsAlias("列长度")
    private int list_size = 0;
    @XlsAlias("是否列显示")
    private String isListCol = "Y";
    //选项
    @XlsAlias("选项")
    private String selGroup;
    //是否查询
    @XlsAlias("是否查询")
    private String isQuery = "N";
    @XlsAlias("默认查询")
    private String queryDefault = "";
    @XlsAlias("是否排序")
    private String isSort = "Y";
    @XlsAlias("关联表")
    private String linkTable = "";
    @XlsAlias("api名称")
    private String apiName;
    @XlsAlias("是否可用")
    private String isactive = "Y";
    private String linkTableName = "";


    private List<SelectOps> selectOps = new ArrayList<>();
}
