var e=Object.defineProperty,a=Object.prototype.hasOwnProperty,l=Object.getOwnPropertySymbols,t=Object.prototype.propertyIsEnumerable,o=(a,l,t)=>l in a?e(a,l,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[l]=t,r=(e,r)=>{for(var n in r||(r={}))a.call(r,n)&&o(e,n,r[n]);if(l)for(var n of l(r))t.call(r,n)&&o(e,n,r[n]);return e};import{s as n,e as i,f as d,g as s,h as p,t as u,i as c,j as m,u as b,k as g,l as f,d as y,r as h,m as w,o as k,c as N,n as D,q as _,v,x,F as P,y as C,z as S,b as q,A as I}from"./index.b9440fcd.js";import{e as O,g as V,_ as j,a as z}from"./ObjectPage.4d75760b.js";import G from"./JobPage.c5cb1133.js";import"./vendor.25d1acb5.js";var U=y({props:{},setup:(e,a)=>r({},function(){let e=n({}),a=n({loading:!1,tableInfo:{}}),l=n({loading:!1,result:[],page:1,pageSize:30,orders:[],total:0,totalPage:0,selection:[]}),t=t=>{let o={};a.tableInfo.columns.forEach((a=>{let l=e[a.apiName];l&&("datenumber"==a.view_type?l.length>0&&(o[a.apiName]="BETWEEN "+i(l[0])+" AND "+i(l[1])):"date"==a.view_type?l.length>0&&(o[a.apiName]=[d(l[0]),d(l[1])]):"object"==a.view_type?l.length>0&&(o[a.apiName]=e[a.apiName]):o[a.apiName]=e[a.apiName])})),l.loading=!0,s.post("/sys/manager/api/"+a.tableInfo.name+"/list",{where:o,page:t,pageSize:l.pageSize,orders:l.orders}).then((e=>{l.result=e.data.datas,l.total=e.data.total,l.page=e.data.page,l.pageSize=e.data.pageSize,l.totalPage=e.data.totalPage,l.loading=!1})).catch((e=>{p(e),l.loading=!1}))},o=V();(()=>{const e=b();a.loading=!0,s.get("/sys/manager/api/table?name="+e.params.tableName).then((e=>{var o;a.tableInfo=e.data;try{l.orders=JSON.parse(null!=(o=a.tableInfo.defOrders)?o:"[]")}catch(r){p(r),console.log(r)}a.loading=!1,a.tableInfo.url||t(1)})).catch((e=>{p(e),a.loading=!1}))})();let y=c((()=>{var e,l;return null==(l=null==(e=a.tableInfo)?void 0:e.columns)?void 0:l.filter((e=>"Y"===e.isQuery))}));g();let h=c((()=>f()));return m(a,(a=>{a.tableInfo.columns.forEach((a=>{e[a.apiName]=""}))})),r({tableData:a,shortcuts:h,queryParam:e,queryCols:y,queryData:t,selectLabel:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].remark:""},selectColor:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].color:""},dataGrid:l,handleSortChange:(e,a,o)=>{l.orders=e.prop?[{field:e.prop,order:"ascending"==e.order?"asc":"desc"}]:[],t(l.page)},handleSelectionChange:e=>{l.selection=u(e)},handleRowDbClick:(e,l,t)=>{o.openObjectDialog(a.tableInfo.remark,a.tableInfo.name,e.id)},findMutiObject:a=>{let l=o.openDialog("查询-"+a.remark,"../../views/table/SearchPage.vue",{tableName:a.linkTable},{closeDialog:function(t){e[a.apiName]=JSON.stringify(t),e[a.apiName+"_desc"]=t.desc,l.close()}})},addMutiObject:e=>{o.openDialog("查询-"+e.remark,"../../views/table/SearchPage.vue",{tableName:e.linkTable},{closeDialog:function(){}})},execBtn:O},o)}()),name:"TablePage",components:{CustomDialog:j,JobPage:G,ObjectPage:z}});const Y=q("查询"),M=x("span",{style:{flex:"1"}},null,-1),E=x("span",{style:{flex:"1"}},null,-1);U.render=function(e,a,l,t,o,r){const n=h("el-option"),i=h("el-select"),d=h("el-input"),s=h("el-date-picker"),p=h("el-form-item"),u=h("el-col"),c=h("el-row"),m=h("el-form"),b=h("el-container"),g=h("el-button"),f=h("el-card"),y=h("el-pagination"),O=h("el-table-column"),V=h("el-tag"),j=h("el-icon"),z=h("el-table"),G=h("custom-dialog"),U=w("loading");return k(),N(P,null,[null!=e.tableData.tableInfo.name&&null!=e.tableData.tableInfo.url&&e.tableData.tableInfo.url.length>0&&e.tableData.tableInfo.url.indexOf("/")>=0?(k(),N("iframe",{key:0,style:{width:"100%",height:"calc(100% - 10px)",border:"none"},src:e.tableData.tableInfo.url},null,8,["src"])):null!=e.tableData.tableInfo.name&&null!=e.tableData.tableInfo.url&&e.tableData.tableInfo.url.length>0?(k(),N(D(e.tableData.tableInfo.url),{key:1})):_((k(),N(b,{key:2,style:{"flex-direction":"column"},"element-loading-text":"拼命加载中","element-loading-spinner":"el-icon-loading"},{default:v((()=>[x(f,{shadow:"always",style:{margin:"10px"}},{default:v((()=>[x(b,{style:{"flex-direction":"row","padding-top":"6px"}},{default:v((()=>[x(m,{class:"custom-form",size:"mini","label-width":"80px"},{default:v((()=>[x(c,{gutter:10,style:{width:"100%"}},{default:v((()=>[(k(!0),N(P,null,C(e.queryCols,(l=>(k(),N(u,{sm:24,md:12,lg:6},{default:v((()=>[x(p,{label:l.remark},{default:v((()=>["select"===l.view_type?(k(),N(i,{key:0,class:" border-all",modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请选择"},{default:v((()=>[x(n,{label:"全部",value:""}),(k(!0),N(P,null,C(l.selectOps,(e=>(k(),N(n,{label:e.remark,value:e.name},null,8,["label","value"])))),256))])),_:2},1032,["modelValue","onUpdate:modelValue"])):"object"===l.view_type?(k(),N(d,{key:1,class:" border-all",modelValue:e.queryParam[l.apiName+"_desc"],"onUpdate:modelValue":a=>e.queryParam[l.apiName+"_desc"]=a,placeholder:"请输入内容"},{suffix:v((()=>[x("i",{class:"el-icon-search",onClick:a=>e.findMutiObject(l,!0),style:{padding:"3px"}},null,8,["onClick"])])),_:2},1032,["modelValue","onUpdate:modelValue"])):"date"===l.view_type?(k(),N(s,{key:2,class:" border-all",style:{border:"1px solid #dcdfe6",width:"100%"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"datetimerange","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts,format:"YYYYMMDD HH:mm:ss"},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):"datenumber"===l.view_type?(k(),N(s,{key:3,class:" border-all",style:{border:"1px solid #dcdfe6",width:"100%"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"daterange",format:"YYYYMMDD","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):(k(),N(d,{key:4,class:" border-all",onKeyup:a[1]||(a[1]=S((a=>e.queryData(1)),["enter"])),modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请输入查询内容"},null,8,["modelValue","onUpdate:modelValue"]))])),_:2},1032,["label"])])),_:2},1024)))),256))])),_:1})])),_:1})])),_:1}),x(b,{style:{height:"28px",border:"none",padding:"0px"}},{default:v((()=>[x(g,{type:"primary",onClick:a[2]||(a[2]=a=>e.queryData(1))},{default:v((()=>[Y])),_:1}),M])),_:1})])),_:1}),x(b,{style:{border:"1px solid #eee",margin:"10px","flex-direction":"column"}},{default:v((()=>[x(b,{style:{height:"36px",border:"none",padding:"4px"}},{default:v((()=>[x(y,{class:"border-none",onSizeChange:a[3]||(a[3]=a=>{e.dataGrid.pageSize=a,e.queryData(e.dataGrid.page)}),onCurrentChange:a[4]||(a[4]=a=>e.queryData(a)),currentPage:e.dataGrid.page,"onUpdate:currentPage":a[5]||(a[5]=a=>e.dataGrid.page=a),onPrevClick:a[6]||(a[6]=a=>e.queryData(e.dataGrid.page-1)),onNextClick:a[7]||(a[7]=a=>e.queryData(e.dataGrid.page+1)),"page-sizes":[10,30,50,100],"page-size":e.dataGrid.pageSize,layout:"sizes, prev, pager, next,total",total:e.dataGrid.total},null,8,["currentPage","page-size","total"]),E,(k(!0),N(P,null,C(e.tableData.tableInfo.buttons,(a=>(k(),N(g,{type:"primary",id:a.name,"data-btninfo":JSON.stringify(a),onClick:l=>e.execBtn(a,e.dataGrid.selection,(()=>{e.queryData(1)}))},{default:v((()=>[q(I(a.remark),1)])),_:2},1032,["id","data-btninfo","onClick"])))),256))])),_:1}),_(x(z,{size:"mini",onSortChange:e.handleSortChange,onSelectionChange:e.handleSelectionChange,onRowDblclick:e.handleRowDbClick,data:e.dataGrid.result,height:480,border:""},{default:v((()=>[x(O,{type:"selection",width:"55"}),(k(!0),N(P,null,C(e.tableData.tableInfo.columns,(a=>(k(),N(P,null,["id"===a.apiName?(k(),N(O,{key:0,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,width:"60",prop:a.apiName,label:a.remark},null,8,["sortable","prop","label"])):"select"===a.view_type?(k(),N(O,{key:1,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,prop:a.apiName,label:a.remark,width:"120"},{default:v((l=>[x(V,{size:"mini",style:{color:"white"},color:e.selectColor(l.row[a.apiName],a)},{default:v((()=>[q(I(e.selectLabel(l.row[a.apiName],a)),1)])),_:2},1032,["color"])])),_:2},1032,["sortable","prop","label"])):"object"===a.view_type?(k(),N(O,{key:2,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,"min-width":30*a.list_size+40,prop:a.apiName,label:a.remark},{default:v((l=>[x(j,{class:"el-icon-link",onClick:t=>e.openObjectDialog(a.linkTableName,a.linkTable,l.row[a.apiName])},null,8,["onClick"]),x("span",null,I(l.row[a.apiName+"_desc"]),1)])),_:2},1032,["sortable","min-width","prop","label"])):(k(),N(O,{key:3,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,prop:a.apiName,"min-width":30*a.list_size+40,label:a.remark},{default:v((e=>[q(I(e.row[a.apiName]),1)])),_:2},1032,["sortable","prop","min-width","label"]))],64)))),256))])),_:1},8,["onSortChange","onSelectionChange","onRowDblclick","data"]),[[U,e.dataGrid.loading]])])),_:1})])),_:1},512)),[[U,e.tableData.loading]]),x(G,{ref:"dialogRef"},null,512)],64)};export default U;
