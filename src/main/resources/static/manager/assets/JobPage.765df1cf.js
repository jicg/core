var a=Object.defineProperty,e=Object.prototype.hasOwnProperty,o=Object.getOwnPropertySymbols,l=Object.prototype.propertyIsEnumerable,t=(e,o,l)=>o in e?a(e,o,{enumerable:!0,configurable:!0,writable:!0,value:l}):e[o]=l;import{s as r,c as i,e as s,A as n,d as p,B as d,p as u,j as c,r as b,o as m,l as g,q as j,y as w,z as y,F as f,w as h}from"./index.e1bc62c6.js";import"./vendor.25d1acb5.js";let k=r({jobs:[],loading:!0}),_=a=>{i.get("/sys/api/job/resume",{params:{jobName:a.jobName,jobGroup:a.groupName}}).then((a=>{D()})).catch((a=>{s(a),k.loading=!1}))},C=a=>{i.get("/sys/api/job/pause",{params:{jobName:a.jobName,jobGroup:a.groupName}}).then((a=>{D()})).catch((a=>{s(a),k.loading=!1}))},N=a=>{i.get("/sys/api/job/trigger",{params:{jobName:a.jobName,jobGroup:a.groupName}}).then((a=>{n("已经触发任务！")})).catch((a=>{s(a)}))};function v(a,e){i.get("/sys/api/job/cron",{params:{jobName:a.jobName,jobGroup:a.groupName,cron:e}}).then((a=>{D()})).catch((a=>{s(a),k.loading=!1}))}let D=()=>{k.loading=!0,i.get("/sys/api/job/list").then((a=>{k.jobs=a.data,k.loading=!1})).catch((a=>{s(a),k.loading=!1}))};var V=p({name:"JobPage",props:{},setup:()=>{let a=r({row:{},cron:""}),i=d(!1);return((a,r)=>{for(var i in r||(r={}))e.call(r,i)&&t(a,i,r[i]);if(o)for(var i of o(r))l.call(r,i)&&t(a,i,r[i]);return a})({dialogVisible:i,showCronDialog:e=>{a.row=e,a.cron=e.cron,i.value=!0},dialogData:a,setCron:()=>{i.value=!1,v(a.row,a.cron)}},(D(),{tableData:k,resume:_,pause:C,cron:v,trigger:N}))}});const z=h();u("data-v-86978912");const O=w("暂停"),P=w("运行"),G=w("设置时间"),U=w("开始 "),A=w("暂停"),E=w("执行"),S={class:"dialog-footer"},x=w("取 消"),I=w("确 定");c();const q=z(((a,e,o,l,t,r)=>{const i=b("el-table-column"),s=b("el-tag"),n=b("el-button"),p=b("el-table"),d=b("el-input"),u=b("el-dialog");return m(),g(f,null,[j(p,{size:"small",data:a.tableData.jobs,border:"",style:{width:"100%"}},{default:z((()=>[j(i,{prop:"order",label:"序号",width:"60"}),j(i,{prop:"name",label:"名称",width:"200"}),j(i,{prop:"cron",label:"时间",width:"120"}),j(i,{label:"任务标识"},{default:z((a=>[w(y(a.row.jobName+"@"+a.row.groupName),1)])),_:1}),j(i,{prop:"status",label:"状态",width:"80"},{default:z((a=>["PAUSED"===a.row.status?(m(),g(s,{key:0,size:"mini",type:"success"},{default:z((()=>[O])),_:1})):(m(),g(s,{key:1,size:"mini",type:"warning"},{default:z((()=>[P])),_:1}))])),_:1}),j(i,{prop:"remark",label:"备注",width:"180"}),j(i,{width:"280",label:"操作"},{default:z((e=>[j(n,{size:"mini",onClick:o=>a.showCronDialog(e.row)},{default:z((()=>[G])),_:2},1032,["onClick"]),"PAUSED"===e.row.status?(m(),g(n,{key:0,size:"mini",onClick:o=>a.resume(e.row),type:"warning"},{default:z((()=>[U])),_:2},1032,["onClick"])):(m(),g(n,{key:1,size:"mini",onClick:o=>a.pause(e.row),type:"primary"},{default:z((()=>[A])),_:2},1032,["onClick"])),j(n,{size:"mini",onClick:o=>a.trigger(e.row),type:"primary"},{default:z((()=>[E])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),j(u,{title:"设置时间-"+a.dialogData.row.name,modelValue:a.dialogVisible,"onUpdate:modelValue":e[4]||(e[4]=e=>a.dialogVisible=e)},{footer:z((()=>[j("span",S,[j(n,{onClick:e[2]||(e[2]=e=>a.dialogVisible=!1)},{default:z((()=>[x])),_:1}),j(n,{type:"primary",onClick:e[3]||(e[3]=e=>a.setCron())},{default:z((()=>[I])),_:1})])])),default:z((()=>[j(d,{placeholder:"时间",modelValue:a.dialogData.cron,"onUpdate:modelValue":e[1]||(e[1]=e=>a.dialogData.cron=e)},null,8,["modelValue"])])),_:1},8,["title","modelValue"])],64)}));V.render=q,V.__scopeId="data-v-86978912";export default V;