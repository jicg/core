var e=Object.defineProperty,a=Object.defineProperties,l=Object.getOwnPropertyDescriptors,t=Object.getOwnPropertySymbols,o=Object.prototype.hasOwnProperty,r=Object.prototype.propertyIsEnumerable,i=(a,l,t)=>l in a?e(a,l,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[l]=t,n=(e,a)=>{for(var l in a||(a={}))o.call(a,l)&&i(e,l,a[l]);if(t)for(var l of t(a))r.call(a,l)&&i(e,l,a[l]);return e};import{g as d,_ as s}from"./CustomDialog.3974482c.js";import{s as p,e as u,v as m,x as c,y as f,u as g,a as b,f as y,p as h,g as w,r as N,h as _,o as k,c as v,w as C,i as D,F as q,j as x,z as O,t as P,k as j,l as S}from"./vendor.045e14f3.js";import{d as I,a as V,s as z,b as E}from"./index.35d46640.js";function G(e,t){let o=p({}),r=p({loading:!1,tableInfo:{}}),i=p({loading:!1,result:[],page:1,pageSize:10,orders:[],total:0,totalPage:0}),s=p([]),y=e=>{let a={};r.tableInfo.columns.forEach((e=>{let l=o[e.apiName];l&&("datenumber"==e.view_type?l.length>0&&(a[e.apiName]="BETWEEN "+I(l[0])+" AND "+I(l[1])):"date"==e.view_type?l.length>0&&(a[e.apiName]=[V(l[0]),V(l[1])]):a[e.apiName]=o[e.apiName])})),i.loading=!0,u.post("/sys/manager/api/"+r.tableInfo.name+"/list",{where:a,page:e,pageSize:i.pageSize,orders:i.orders}).then((e=>{i.result=e.data.datas,i.total=e.data.total,i.page=e.data.page,i.pageSize=e.data.pageSize,i.totalPage=e.data.totalPage,i.loading=!1})).catch((e=>{z(e),i.loading=!1}))},h=d();g(),r.loading=!0,u.get("/sys/manager/api/table?name="+e.tableName).then((e=>{r.tableInfo=e.data,r.loading=!1,r.tableInfo.url||y(1)})).catch((e=>{z(e),r.loading=!1}));let w=m((()=>{var e,a;return null==(a=null==(e=r.tableInfo)?void 0:e.columns)?void 0:a.filter((e=>"Y"===e.isQuery))}));b();let N=m((()=>E()));c(r,(e=>{e.tableInfo.columns.forEach((e=>{o[e.apiName]=""}))}));return _=n({tableData:r,shortcuts:N,queryParam:o,queryCols:w,queryData:y,selectLabel:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].remark:""},selectColor:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].color:""},dataGrid:i,handleSortChange:(e,a,l)=>{i.orders=e.prop?[{field:e.prop,order:"ascending"==e.order?"asc":"desc"}]:[],y(i.page)},handleSelectionChange:e=>{console.log(e)},handleRowDbClick:(e,a,l)=>{f(s).filter((a=>a.id===e.id)).pop()||s.push({id:e.id,sql:e.id,type:0,tag:e[r.tableInfo.showColumn+""]})},handleOnload:()=>{},findMutiObject:e=>{let a=h.openDialog("查询-"+e.remark,U,{tableName:e.linkTable},{closeDialog:function(l){o[e.apiName]=JSON.stringify(l),o[e.apiName+"_desc"]=l.desc,a.close()}})},addMutiObject:e=>{}},h),a(_,l({tags:s,addOk:function(){let e=r.tableInfo.realTable||r.tableInfo.name,a=[],l=[],o=s.filter((e=>0==e.type));o.length>0&&(a.push(`select ${r.tableInfo.name}.id  \n                from ${e} ${r.tableInfo.name}  \n                where ${r.tableInfo.name}.id in (${o.map((e=>e.id)).join(",")})`),l.push("包含("+o.map((e=>e.tag)).join(",")+")"));let i=s.filter((e=>1==e.type));i.length>0&&i.forEach((e=>{a.push(e.sql),l.push(e.tag)}));let n,d="";a.length>0&&(n=`in ( ${a.join(" union all ")})`,d=l.map((e=>`(${e})`)).join(" 或者 ")),t.emit("closeDialog",{desc:d,sql:n})},addAllRow:function(){i.result.forEach((e=>{f(s).filter((a=>a.id===e.id)).pop()||s.push({id:e.id,sql:e.id,type:0,tag:e[r.tableInfo.showColumn+""]})}))},delTag:function(e){let a=s.filter((a=>a.id==e.id)).pop();a&&s.splice(s.indexOf(a),1)},delAll:function(e){s.splice(0,s.length)},addAll:function(){let e={};r.tableInfo.columns.forEach((a=>{let l=o[a.apiName];l&&("datenumber"==a.view_type?l.length>0&&(e[a.apiName]="BETWEEN "+I(l[0])+" AND "+I(l[1])):"date"==a.view_type?l.length>0&&(e[a.apiName]=[V(l[0]),V(l[1])]):e[a.apiName]=o[a.apiName])})),u.post("/sys/manager/api/"+r.tableInfo.name+"/sql",{where:e}).then((e=>{let a=s.filter((e=>"-1"==e.id)).pop();a&&s.splice(s.indexOf(a),1),s.push({id:"-1",tag:e.data.desc,sql:e.data.sql,type:1})})).catch((e=>{z(e)}))}}));var _}var U=y({props:{tableName:{type:String,required:!0},isSingle:{type:Boolean,default:!1}},emits:["closeDialog"],setup:(e,a)=>n({},G(e,a)),components:{CustomDialog:s},name:"SearchPage"});const A=S();h("data-v-ac326710");const Y=j("查询"),T=D("span",{style:{flex:"1"}},null,-1),M=j("添加全部"),R=j("添加当前页"),$=j("确定"),L=j("重置");w();const B=A(((e,a,l,t,o,r)=>{const i=N("el-option"),n=N("el-select"),d=N("el-input"),s=N("el-date-picker"),p=N("el-form-item"),u=N("el-col"),m=N("el-row"),c=N("el-form"),f=N("el-button"),g=N("el-container"),b=N("el-card"),y=N("el-table-column"),h=N("el-tag"),w=N("el-icon"),S=N("el-table"),I=N("el-pagination"),V=N("el-scrollbar"),z=N("custom-dialog"),E=_("loading");return k(),v(q,null,[C(D(g,{style:{"flex-direction":"column"},"element-loading-text":"拼命加载中","element-loading-spinner":"el-icon-loading"},{default:A((()=>[D(b,{shadow:"always",style:{margin:"10px"}},{default:A((()=>[D(c,{class:"custom-form","label-width":"80px",size:"mini"},{default:A((()=>[D(m,{gutter:10,style:{width:"100%"}},{default:A((()=>[(k(!0),v(q,null,x(e.queryCols,(l=>(k(),v(u,{xs:24,sm:12,md:6},{default:A((()=>[D(p,{label:l.remark},{default:A((()=>["select"===l.view_type?(k(),v(n,{key:0,class:"border-all",modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请选择"},{default:A((()=>[D(i,{label:"全部",value:""}),(k(!0),v(q,null,x(l.selectOps,(e=>(k(),v(i,{label:e.remark,value:e.name},null,8,["label","value"])))),256))])),_:2},1032,["modelValue","onUpdate:modelValue"])):"object"===l.view_type?(k(),v(d,{key:1,class:"border-all",modelValue:e.queryParam[l.apiName+"_desc"],"onUpdate:modelValue":a=>e.queryParam[l.apiName+"_desc"]=a,placeholder:"请输入内容"},{suffix:A((()=>[D("i",{class:"el-icon-search",onClick:a=>e.findMutiObject(l,!0),style:{padding:"3px"}},null,8,["onClick"])])),_:2},1032,["modelValue","onUpdate:modelValue"])):"date"===l.view_type?(k(),v(s,{key:2,class:" border-all",style:{width:"100%",border:"1px solid #dcdfe6"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"datetimerange","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts,format:"YYYYMMDD HH:mm:ss"},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):"datenumber"===l.view_type?(k(),v(s,{key:3,class:"border-all",style:{width:"100%",border:"1px solid #dcdfe6"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"daterange",format:"YYYYMMDD","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):(k(),v(d,{key:4,class:"border-all",onKeyup:a[1]||(a[1]=O((a=>e.queryData(1)),["enter"])),modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请输入查询内容"},null,8,["modelValue","onUpdate:modelValue"]))])),_:2},1032,["label"])])),_:2},1024)))),256))])),_:1})])),_:1}),D(g,null,{default:A((()=>[D(f,{type:"primary",onClick:a[2]||(a[2]=a=>{e.delAll(),e.queryData(1)})},{default:A((()=>[Y])),_:1}),T,D(f,{type:"primary",onClick:a[3]||(a[3]=a=>e.addAll())},{default:A((()=>[M])),_:1}),D(f,{type:"primary",onClick:a[4]||(a[4]=a=>e.addAllRow())},{default:A((()=>[R])),_:1}),D(f,{type:"primary",onClick:a[5]||(a[5]=a=>e.addOk())},{default:A((()=>[$])),_:1}),D(f,{type:"warning",onClick:a[6]||(a[6]=a=>e.delAll())},{default:A((()=>[L])),_:1})])),_:1})])),_:1}),D(g,null,{default:A((()=>[D(g,{style:{"flex-direction":"column"}},{default:A((()=>[D(g,{style:{border:"1px solid #eee",margin:"10px"}},{default:A((()=>[C(D(S,{size:"mini",onSortChange:e.handleSortChange,onRowDblclick:e.handleRowDbClick,onLoad:e.handleOnload,data:e.dataGrid.result,height:332,border:""},{default:A((()=>[(k(!0),v(q,null,x(e.tableData.tableInfo.columns,(a=>(k(),v(q,null,["id"===a.apiName?(k(),v(y,{key:0,sortable:"N"!==a.isSort&&"custom","show-overflow-tooltip":!0,"min-width":55,width:"60",prop:a.apiName,label:a.remark},{default:A((a=>[D("a",{href:"javascript:;",onClick:l=>e.openObjectDialog(e.tableData.tableInfo.remark,e.tableData.tableInfo.name,a.row.id)},P(a.row.id),9,["onClick"])])),_:2},1032,["sortable","prop","label"])):"select"===a.view_type?(k(),v(y,{key:1,sortable:"N"!==a.isSort&&"custom",prop:a.apiName,"show-overflow-tooltip":!0,"min-width":30*a.list_size+40,label:a.remark,width:"120"},{default:A((l=>[D(h,{size:"mini",style:{color:"white"},color:e.selectColor(l.row[a.apiName],a)},{default:A((()=>[j(P(e.selectLabel(l.row[a.apiName],a)),1)])),_:2},1032,["color"])])),_:2},1032,["sortable","prop","min-width","label"])):"object"===a.view_type?(k(),v(y,{key:2,sortable:"N"!==a.isSort&&"custom",prop:a.apiName,"show-overflow-tooltip":!0,"min-width":30*a.list_size+40,label:a.remark},{default:A((l=>[D(w,{class:"el-icon-link",onClick:t=>e.openObjectDialog(a.linkTableName,a.linkTable,l.row[a.apiName])},null,8,["onClick"]),D("span",null,P(l.row[a.apiName+"_desc"]),1)])),_:2},1032,["sortable","prop","min-width","label"])):(k(),v(y,{key:3,sortable:"N"!==a.isSort&&"custom","show-overflow-tooltip":!0,prop:a.apiName,"min-width":30*a.list_size+40,label:a.remark},{default:A((e=>[j(P(e.row[a.apiName]),1)])),_:2},1032,["sortable","prop","min-width","label"]))],64)))),256))])),_:1},8,["onSortChange","onRowDblclick","onLoad","data"]),[[E,e.dataGrid.loading]])])),_:1}),D(I,{onSizeChange:a[7]||(a[7]=a=>{e.dataGrid.pageSize=a,e.queryData(e.dataGrid.page)}),onCurrentChange:a[8]||(a[8]=a=>e.queryData(a)),currentPage:e.dataGrid.page,"onUpdate:currentPage":a[9]||(a[9]=a=>e.dataGrid.page=a),onPrevClick:a[10]||(a[10]=a=>e.queryData(e.dataGrid.page-1)),onNextClick:a[11]||(a[11]=a=>e.queryData(e.dataGrid.page+1)),"page-sizes":[10,30,50,100],"page-size":e.dataGrid.pageSize,layout:"sizes,prev,jumper,next,total",total:e.dataGrid.total},null,8,["currentPage","page-size","total"])])),_:1}),D(u,{span:6,style:{padding:"16px","min-width":"248px",overflow:"auto"}},{default:A((()=>[D(V,null,{default:A((()=>[(k(!0),v(q,null,x(e.tags,(a=>(k(),v(h,{class:"text_nowrap",type:1===a.type?"warning":"info",style:{margin:"3px"},closable:!0,onClose:l=>e.delTag(a)},{default:A((()=>[j(P(a.tag),1)])),_:2},1032,["type","onClose"])))),256))])),_:1})])),_:1})])),_:1})])),_:1},512),[[E,!1]]),D(z,{ref:"dialogRef"},null,512)],64)}));U.render=B,U.__scopeId="data-v-ac326710";export default U;