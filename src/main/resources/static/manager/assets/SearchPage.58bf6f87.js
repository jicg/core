var e=Object.defineProperty,a=Object.prototype.hasOwnProperty,l=Object.getOwnPropertySymbols,t=Object.prototype.propertyIsEnumerable,o=(a,l,t)=>l in a?e(a,l,{enumerable:!0,configurable:!0,writable:!0,value:t}):a[l]=t,r=(e,r)=>{for(var i in r||(r={}))a.call(r,i)&&o(e,i,r[i]);if(l)for(var i of l(r))t.call(r,i)&&o(e,i,r[i]);return e};import{g as i,_ as n}from"./ObjectPage.4d75760b.js";import{s as d,e as s,f as p,g as u,h as m,i as c,k as g,j as f,t as b,u as h,l as y,d as w,p as N,a as _,r as k,m as v,o as C,c as q,q as D,x,F as P,y as S,z as j,A as O,b as I,w as V}from"./index.b9440fcd.js";import"./vendor.25d1acb5.js";var z=w({props:{tableName:{type:String,required:!0},isSingle:{type:Boolean,default:!1}},emits:["closeDialog"],setup:(e,a)=>r({},function(e,a){let l=d({}),t=d({loading:!1,tableInfo:{}}),o=d({loading:!1,result:[],page:1,pageSize:10,orders:[],total:0,totalPage:0}),n=d([]),w=e=>{let a={};t.tableInfo.columns.forEach((e=>{let t=l[e.apiName];t&&("datenumber"==e.view_type?t.length>0&&(a[e.apiName]="BETWEEN "+s(t[0])+" AND "+s(t[1])):"date"==e.view_type?t.length>0&&(a[e.apiName]=[p(t[0]),p(t[1])]):a[e.apiName]=l[e.apiName])})),o.loading=!0,u.post("/sys/manager/api/"+t.tableInfo.name+"/list",{where:a,page:e,pageSize:o.pageSize,orders:o.orders}).then((e=>{o.result=e.data.datas,o.total=e.data.total,o.page=e.data.page,o.pageSize=e.data.pageSize,o.totalPage=e.data.totalPage,o.loading=!1})).catch((e=>{m(e),o.loading=!1}))},N=i();h(),t.loading=!0,u.get("/sys/manager/api/table?name="+e.tableName).then((e=>{t.tableInfo=e.data,t.loading=!1,t.tableInfo.url||w(1)})).catch((e=>{m(e),t.loading=!1}));let _=c((()=>{var e,a;return null==(a=null==(e=t.tableInfo)?void 0:e.columns)?void 0:a.filter((e=>"Y"===e.isQuery))}));g();let k=c((()=>y()));return f(t,(e=>{e.tableInfo.columns.forEach((e=>{l[e.apiName]=""}))})),r(r({tableData:t,shortcuts:k,queryParam:l,queryCols:_,queryData:w,selectLabel:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].remark:""},selectColor:(e,a)=>{let l=a.selectOps.filter((a=>a.name==e));return l.length>0?l[0].color:""},dataGrid:o,handleSortChange:(e,a,l)=>{o.orders=e.prop?[{field:e.prop,order:"ascending"==e.order?"asc":"desc"}]:[],w(o.page)},handleSelectionChange:e=>{console.log(e)},handleRowDbClick:(e,a,l)=>{b(n).filter((a=>a.id===e.id)).pop()||n.push({id:e.id,sql:e.id,type:0,tag:e[t.tableInfo.showColumn+""]})},handleOnload:()=>{},findMutiObject:e=>{let a=N.openDialog("查询-"+e.remark,"../../views/table/SearchPage.vue",{tableName:e.linkTable},{closeDialog:function(t){l[e.apiName]=JSON.stringify(t),l[e.apiName+"_desc"]=t.desc,a.close()}})},addMutiObject:e=>{}},N),{tags:n,addOk:function(){let e=t.tableInfo.realTable||t.tableInfo.name,l=[],o=[],r=n.filter((e=>0==e.type));r.length>0&&(l.push(`select ${t.tableInfo.name}.id  \n                from ${e} ${t.tableInfo.name}  \n                where ${t.tableInfo.name}.id in (${r.map((e=>e.id)).join(",")})`),o.push("包含("+r.map((e=>e.tag)).join(",")+")"));let i=n.filter((e=>1==e.type));i.length>0&&i.forEach((e=>{l.push(e.sql),o.push(e.tag)}));let d,s="";l.length>0&&(d=`in ( ${l.join(" union all ")})`,s=o.map((e=>`(${e})`)).join(" 或者 ")),a.emit("closeDialog",{desc:s,sql:d})},addAllRow:function(){o.result.forEach((e=>{b(n).filter((a=>a.id===e.id)).pop()||n.push({id:e.id,sql:e.id,type:0,tag:e[t.tableInfo.showColumn+""]})}))},delTag:function(e){let a=n.filter((a=>a.id==e.id)).pop();a&&n.splice(n.indexOf(a),1)},delAll:function(e){n.splice(0,n.length)},addAll:function(){let e={};t.tableInfo.columns.forEach((a=>{let t=l[a.apiName];t&&("datenumber"==a.view_type?t.length>0&&(e[a.apiName]="BETWEEN "+s(t[0])+" AND "+s(t[1])):"date"==a.view_type?t.length>0&&(e[a.apiName]=[p(t[0]),p(t[1])]):e[a.apiName]=l[a.apiName])})),u.post("/sys/manager/api/"+t.tableInfo.name+"/sql",{where:e}).then((e=>{let a=n.filter((e=>"-1"==e.id)).pop();a&&n.splice(n.indexOf(a),1),n.push({id:"-1",tag:e.data.desc,sql:e.data.sql,type:1})})).catch((e=>{m(e)}))}})}(e,a)),components:{CustomDialog:n},name:"SearchPage"});const E=V();N("data-v-ac326710");const G=I("查询"),U=x("span",{style:{flex:"1"}},null,-1),A=I("添加全部"),Y=I("添加当前页"),T=I("确定"),M=I("重置");_();const R=E(((e,a,l,t,o,r)=>{const i=k("el-option"),n=k("el-select"),d=k("el-input"),s=k("el-date-picker"),p=k("el-form-item"),u=k("el-col"),m=k("el-row"),c=k("el-form"),g=k("el-button"),f=k("el-container"),b=k("el-card"),h=k("el-table-column"),y=k("el-tag"),w=k("el-icon"),N=k("el-table"),_=k("el-pagination"),V=k("el-scrollbar"),z=k("custom-dialog"),R=v("loading");return C(),q(P,null,[D(x(f,{style:{"flex-direction":"column"},"element-loading-text":"拼命加载中","element-loading-spinner":"el-icon-loading"},{default:E((()=>[x(b,{shadow:"always",style:{margin:"10px"}},{default:E((()=>[x(c,{class:"custom-form","label-width":"80px",size:"mini"},{default:E((()=>[x(m,{gutter:10,style:{width:"100%"}},{default:E((()=>[(C(!0),q(P,null,S(e.queryCols,(l=>(C(),q(u,{xs:24,sm:12,md:6},{default:E((()=>[x(p,{label:l.remark},{default:E((()=>["select"===l.view_type?(C(),q(n,{key:0,class:"border-all",modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请选择"},{default:E((()=>[x(i,{label:"全部",value:""}),(C(!0),q(P,null,S(l.selectOps,(e=>(C(),q(i,{label:e.remark,value:e.name},null,8,["label","value"])))),256))])),_:2},1032,["modelValue","onUpdate:modelValue"])):"object"===l.view_type?(C(),q(d,{key:1,class:"border-all",modelValue:e.queryParam[l.apiName+"_desc"],"onUpdate:modelValue":a=>e.queryParam[l.apiName+"_desc"]=a,placeholder:"请输入内容"},{suffix:E((()=>[x("i",{class:"el-icon-search",onClick:a=>e.findMutiObject(l,!0),style:{padding:"3px"}},null,8,["onClick"])])),_:2},1032,["modelValue","onUpdate:modelValue"])):"date"===l.view_type?(C(),q(s,{key:2,class:" border-all",style:{width:"100%",border:"1px solid #dcdfe6"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"datetimerange","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts,format:"YYYYMMDD HH:mm:ss"},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):"datenumber"===l.view_type?(C(),q(s,{key:3,class:"border-all",style:{width:"100%",border:"1px solid #dcdfe6"},modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,type:"daterange",format:"YYYYMMDD","unlink-panels":"","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",shortcuts:e.shortcuts},null,8,["modelValue","onUpdate:modelValue","shortcuts"])):(C(),q(d,{key:4,class:"border-all",onKeyup:a[1]||(a[1]=j((a=>e.queryData(1)),["enter"])),modelValue:e.queryParam[l.apiName],"onUpdate:modelValue":a=>e.queryParam[l.apiName]=a,placeholder:"请输入查询内容"},null,8,["modelValue","onUpdate:modelValue"]))])),_:2},1032,["label"])])),_:2},1024)))),256))])),_:1})])),_:1}),x(f,null,{default:E((()=>[x(g,{type:"primary",onClick:a[2]||(a[2]=a=>{e.delAll(),e.queryData(1)})},{default:E((()=>[G])),_:1}),U,x(g,{type:"primary",onClick:a[3]||(a[3]=a=>e.addAll())},{default:E((()=>[A])),_:1}),x(g,{type:"primary",onClick:a[4]||(a[4]=a=>e.addAllRow())},{default:E((()=>[Y])),_:1}),x(g,{type:"primary",onClick:a[5]||(a[5]=a=>e.addOk())},{default:E((()=>[T])),_:1}),x(g,{type:"warning",onClick:a[6]||(a[6]=a=>e.delAll())},{default:E((()=>[M])),_:1})])),_:1})])),_:1}),x(f,null,{default:E((()=>[x(f,{style:{"flex-direction":"column"}},{default:E((()=>[x(f,{style:{border:"1px solid #eee",margin:"10px"}},{default:E((()=>[D(x(N,{size:"mini",onSortChange:e.handleSortChange,onRowDblclick:e.handleRowDbClick,onLoad:e.handleOnload,data:e.dataGrid.result,height:332,border:""},{default:E((()=>[(C(!0),q(P,null,S(e.tableData.tableInfo.columns,(a=>(C(),q(P,null,["id"===a.apiName?(C(),q(h,{key:0,sortable:"N"!==a.isSort&&"custom","show-overflow-tooltip":!0,"min-width":55,width:"60",prop:a.apiName,label:a.remark},{default:E((a=>[x("a",{href:"javascript:;",onClick:l=>e.openObjectDialog(e.tableData.tableInfo.remark,e.tableData.tableInfo.name,a.row.id)},O(a.row.id),9,["onClick"])])),_:2},1032,["sortable","prop","label"])):"select"===a.view_type?(C(),q(h,{key:1,sortable:"N"!==a.isSort&&"custom",prop:a.apiName,"show-overflow-tooltip":!0,"min-width":30*a.list_size+40,label:a.remark,width:"120"},{default:E((l=>[x(y,{size:"mini",style:{color:"white"},color:e.selectColor(l.row[a.apiName],a)},{default:E((()=>[I(O(e.selectLabel(l.row[a.apiName],a)),1)])),_:2},1032,["color"])])),_:2},1032,["sortable","prop","min-width","label"])):"object"===a.view_type?(C(),q(h,{key:2,sortable:"N"!==a.isSort&&"custom",prop:a.apiName,"show-overflow-tooltip":!0,"min-width":30*a.list_size+40,label:a.remark},{default:E((l=>[x(w,{class:"el-icon-link",onClick:t=>e.openObjectDialog(a.linkTableName,a.linkTable,l.row[a.apiName])},null,8,["onClick"]),x("span",null,O(l.row[a.apiName+"_desc"]),1)])),_:2},1032,["sortable","prop","min-width","label"])):(C(),q(h,{key:3,sortable:"N"!==a.isSort&&"custom","show-overflow-tooltip":!0,prop:a.apiName,"min-width":30*a.list_size+40,label:a.remark},{default:E((e=>[I(O(e.row[a.apiName]),1)])),_:2},1032,["sortable","prop","min-width","label"]))],64)))),256))])),_:1},8,["onSortChange","onRowDblclick","onLoad","data"]),[[R,e.dataGrid.loading]])])),_:1}),x(_,{onSizeChange:a[7]||(a[7]=a=>{e.dataGrid.pageSize=a,e.queryData(e.dataGrid.page)}),onCurrentChange:a[8]||(a[8]=a=>e.queryData(a)),currentPage:e.dataGrid.page,"onUpdate:currentPage":a[9]||(a[9]=a=>e.dataGrid.page=a),onPrevClick:a[10]||(a[10]=a=>e.queryData(e.dataGrid.page-1)),onNextClick:a[11]||(a[11]=a=>e.queryData(e.dataGrid.page+1)),"page-sizes":[10,30,50,100],"page-size":e.dataGrid.pageSize,layout:"sizes,prev,jumper,next,total",total:e.dataGrid.total},null,8,["currentPage","page-size","total"])])),_:1}),x(u,{span:6,style:{padding:"16px","min-width":"248px",overflow:"auto"}},{default:E((()=>[x(V,null,{default:E((()=>[(C(!0),q(P,null,S(e.tags,(a=>(C(),q(y,{class:"text_nowrap",type:1===a.type?"warning":"info",style:{margin:"3px"},closable:!0,onClose:l=>e.delTag(a)},{default:E((()=>[I(O(a.tag),1)])),_:2},1032,["type","onClose"])))),256))])),_:1})])),_:1})])),_:1})])),_:1},512),[[R,!1]]),x(z,{ref:"dialogRef"},null,512)],64)}));z.render=R,z.__scopeId="data-v-ac326710";export default z;