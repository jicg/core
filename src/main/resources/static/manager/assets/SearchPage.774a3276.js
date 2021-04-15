var e=Object.defineProperty,a=Object.prototype.hasOwnProperty,l=Object.getOwnPropertySymbols,t=Object.prototype.propertyIsEnumerable,o=(a,l,t)=>l in a?e(a,l,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[l]=t,r=(e,r)=>{for(var n in r||(r={}))a.call(r,n)&&o(e,n,r[n]);if(l)for(var n of l(r))t.call(r,n)&&o(e,n,r[n]);return e};import{g as n}from"./ObjectPage.b831437a.js";import{s as i,a as d,b as s,c as p,e as u,f as m,h as c,g,u as b,i as y,d as h,p as f,j as k,r as N,k as w,n as v,o as C,l as S,q as _,F as P,v as q,x as D,y as V,z as j,w as z}from"./index.e1bc62c6.js";import"./vendor.25d1acb5.js";var O=h({props:{tableName:{type:String,required:!0},isSingle:{type:Boolean,default:!1}},setup:(e,a)=>r({},function(e,a){let l=i({}),t=i({loading:!1,tableInfo:{}}),o=i({loading:!1,result:[],page:1,pageSize:30,orders:[],total:0,totalPage:0}),h=e=>{let a={};t.tableInfo.columns.forEach((e=>{let t=l[e.apiName];t&&("datenumber"==e.view_type?t.length>0&&(a[e.apiName]="BETWEEN "+d(t[0])+" AND "+d(t[1])):"date"==e.view_type?t.length>0&&(a[e.apiName]=[s(t[0]),s(t[1])]):a[e.apiName]=l[e.apiName])})),o.loading=!0,p.post("/sys/manager/api/"+t.tableInfo.name+"/list",{where:a,page:e,pageSize:o.pageSize,orders:o.orders}).then((e=>{o.result=e.data.datas,o.total=e.data.total,o.page=e.data.page,o.pageSize=e.data.pageSize,o.totalPage=e.data.totalPage,o.loading=!1})).catch((e=>{u(e),o.loading=!1}))},f=n();b(),t.loading=!0,p.get("/sys/manager/api/table?name="+e.tableName).then((e=>{t.tableInfo=e.data,t.loading=!1,t.tableInfo.url||h(1)})).catch((e=>{u(e),t.loading=!1}));let k=m((()=>{var e,a;return null==(a=null==(e=t.tableInfo)?void 0:e.columns)?void 0:a.filter((e=>"Y"===e.isQuery))}));c();let N=m((()=>y()));return g(t,(e=>{e.tableInfo.columns.forEach((e=>{l[e.apiName]=""}))})),r({tableData:t,shortcuts:N,queryParam:l,queryCols:k,queryData:h,selectLabel:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].remark:""},selectColor:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].color:""},dataGrid:o,handleSortChange:(e,a,l)=>{o.orders=e.prop?[{field:e.prop,order:"ascending"==e.order?"asc":"desc"}]:[],h(o.page)},handleSelectionChange:e=>{console.log(e)},handleRowDbClick:(e,a,l)=>{f.openObjectDialog(t.tableInfo.remark,t.tableInfo.name,e.id)},findMutiObject:e=>{console.log(e),f.openDialog("查询-"+e.remark,"../../views/table/SearchPage.vue",{tableName:t.tableInfo.name})},addMutiObject:e=>{console.log(e)}},f)}(e)),name:"SearchPage"});const x=z();f("data-v-76b8812e");const I=V("查询");k();const G=x(((e,a,l,t,o,r)=>{const n=N("el-option"),i=N("el-select"),d=N("el-input"),s=N("el-date-picker"),p=N("el-form-item"),u=N("el-button"),m=N("el-form"),c=N("el-card"),g=N("el-table-column"),b=N("el-tag"),y=N("el-icon"),h=N("el-table"),f=N("el-container"),k=N("el-pagination"),z=w("loading");return v((C(),S(f,{style:{"flex-direction":"column"},"element-loading-text":"拼命加载中","element-loading-spinner":"el-icon-loading"},{default:x((()=>[_(c,{shadow:"always",style:{margin:"10px"}},{default:x((()=>[_(m,{size:"small",inline:!0,class:"demo-form-inline","label-width":"80px;"},{default:x((()=>[(C(!0),S(P,null,q(e.queryCols,(l=>(C(),S(p,{label:l.remark},{default:x((()=>["select"===l.view_type?(C(),S(i,{key:0,modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请选择"},{default:x((()=>[_(n,{label:"全部",value:""}),(C(!0),S(P,null,q(l.selectOps,(e=>(C(),S(n,{label:e.remark,value:e.name},null,8,["label","value"])))),256))])),_:2},1032,["modelValue","onUpdate:modelValue"])):"object"===l.view_type?(C(),S(d,{key:1,modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请输入内容"},{suffix:x((()=>[_("i",{class:"el-icon-search",onClick:a=>e.findMutiObject(l),style:{padding:"3px"}},null,8,["onClick"]),_("i",{class:"el-icon-plus",onClick:a=>e.addMutiObject(l),style:{padding:"3px"}},null,8,["onClick"])])),_:2},1032,["modelValue","onUpdate:modelValue"])):"date"===l.view_type?(C(),S(s,{key:2,modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"datetimerange","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts,format:"YYYYMMDD HH:mm:ss"},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):"datenumber"===l.view_type?(C(),S(s,{key:3,modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"daterange",format:"YYYYMMDD","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):(C(),S(d,{key:4,onKeyup:a[1]||(a[1]=D((a=>e.queryData(1)),["enter"])),modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请输入查询内容"},null,8,["modelValue","onUpdate:modelValue"]))])),_:2},1032,["label"])))),256)),_(p,null,{default:x((()=>[_(u,{type:"primary",onClick:a[2]||(a[2]=a=>e.queryData(1))},{default:x((()=>[I])),_:1})])),_:1})])),_:1})])),_:1}),_(f,{style:{border:"1px solid #eee",margin:"10px"}},{default:x((()=>[v(_(h,{size:"small",onSortChange:e.handleSortChange,onSelectionChange:e.handleSelectionChange,onRowDblclick:e.handleRowDbClick,data:e.dataGrid.result,height:480,border:""},{default:x((()=>[_(g,{type:"selection",width:"55"}),(C(!0),S(P,null,q(e.tableData.tableInfo.columns,(a=>(C(),S(P,null,["id"===a.apiName?(C(),S(g,{key:0,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,width:"60",prop:a.apiName,label:a.remark},null,8,["sortable","prop","label"])):"select"===a.view_type?(C(),S(g,{key:1,sortable:"N"!==a.isSort,prop:a.apiName,label:a.remark,width:"120"},{default:x((l=>[_(b,{size:"mini",style:{color:"white"},color:e.selectColor(l.row[a.apiName],a)},{default:x((()=>[V(j(e.selectLabel(l.row[a.apiName],a)),1)])),_:2},1032,["color"])])),_:2},1032,["sortable","prop","label"])):"object"===a.view_type?(C(),S(g,{key:2,sortable:"N"!==a.isSort,prop:a.apiName,label:a.remark},{default:x((l=>[_(y,{class:"el-icon-link",onClick:t=>e.openDialog(a.linkTableName,a.linkTable,l.row[a.apiName])},null,8,["onClick"]),_("span",null,j(l.row[a.apiName+"_desc"]),1)])),_:2},1032,["sortable","prop","label"])):(C(),S(g,{key:3,sortable:"N"!==a.isSort,"show-overflow-tooltip":!0,prop:a.apiName,label:a.remark},{default:x((e=>[V(j(e.row[a.apiName]),1)])),_:2},1032,["sortable","prop","label"]))],64)))),256))])),_:1},8,["onSortChange","onSelectionChange","onRowDblclick","data"]),[[z,e.dataGrid.loading]])])),_:1}),_(k,{onSizeChange:a[3]||(a[3]=a=>{e.dataGrid.pageSize=a,e.queryData(e.dataGrid.page)}),onCurrentChange:a[4]||(a[4]=a=>e.queryData(a)),currentPage:e.dataGrid.page,"onUpdate:currentPage":a[5]||(a[5]=a=>e.dataGrid.page=a),onPrevClick:a[6]||(a[6]=a=>e.queryData(e.dataGrid.page-1)),onNextClick:a[7]||(a[7]=a=>e.queryData(e.dataGrid.page+1)),"page-sizes":[10,30,50,100],"page-size":e.dataGrid.pageSize,layout:"sizes, prev, pager, next,total",total:e.dataGrid.total},null,8,["currentPage","page-size","total"])])),_:1},512)),[[z,!1]])}));O.render=G,O.__scopeId="data-v-76b8812e";export default O;