package  com.jicg.service.core.manager.bean;

import cn.hutool.db.sql.Query;
import cn.hutool.db.sql.SqlBuilder;
import cn.hutool.json.JSONUtil;
import  com.jicg.service.core.annos.XlsAlias;
import  com.jicg.service.core.manager.bean.ColumnInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jicg on 2021/3/31
 */
@Data
public class TableInfo {
    // 名称
    @XlsAlias("名称")
    private String name;
    // 实表
    @XlsAlias("实表")
    private String realTable;
    // 描述
    @XlsAlias("描述")
    private String remark;
    // 序号
    @XlsAlias("序号")
    private Integer orderno;
    // 地址
    @XlsAlias("地址")
    private String url;
    // 是否菜单项
    @XlsAlias("是否菜单项")
    private String ismenu;
    // 是否可用
    @XlsAlias("是否可用")
    private String isactive;

    @XlsAlias("读写规则")
    private String readRule;
    @XlsAlias("显示列")
    private String showColumn;

    @XlsAlias("所属父表")
    private String upTable = "";
    @XlsAlias("关联字段")
    private String upTableColumnName = "";


    private List<TableInfo> itemTables = new ArrayList<>();
    private List<ColumnInfo> columns = new ArrayList<>();
    private List<ButtonInfo> buttons = new ArrayList<>();


//    public static void alias(ExcelReader excelReader) {
//        excelReader.getHeaderAlias().put("名称", "name");
//        excelReader.getHeaderAlias().put("实表", "realTable");
//        excelReader.getHeaderAlias().put("描述", "remark");
//        excelReader.getHeaderAlias().put("序号", "orderno");
//        excelReader.getHeaderAlias().put("地址", "url");
//        excelReader.getHeaderAlias().put("是否菜单项", "ismenu");
//        excelReader.getHeaderAlias().put("是否可用", "isactive");
//    }


}
