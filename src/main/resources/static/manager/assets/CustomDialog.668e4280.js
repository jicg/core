import{z as e,A as a,b as l,B as s,c as t,_ as o,h as n,s as i,p as c,i as d,r,o as p,k as u,m,v as g,C as f,D as b,E as v,y as _}from"./index.5fcea91f.js";function j(){const l=e();return{dialogRef:l,openDialog:(e,s,t,n)=>{if("string"==typeof s){const i=a((()=>o((()=>__import__(`${s}`)),void 0)));return l.value.openDialog(e,i,t,n)}return l.value.openDialog(e,s,t,n)},openObjectDialog:(e,a,s)=>{l.value.openObjectDialog(e,{tableName:a,id:s})}}}function y(e,a,o){if(e.js)window[e.js]&&window[e.js](a,o);else if(e.procName){const n=this.$loading({lock:!0,text:"执行【"+e.remark+"】...",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});l.post(`/sys/manager/api/${e.table}/run/${e.name}`,[a.map((e=>e.id)).join(",")]).then((e=>{s("执行成功！"+e.data),n.close(),o&&o()})).catch((e=>{n.close(),t(e)}))}}var D=n({props:{title:{type:String,default:"对话框"},is:{type:Object,default:n({name:"temp-dialog-default",render:()=>"<div>欢迎使用👏</div>"})},param:{type:Object,default:{}},events:{type:Object,default:{}}},setup(l,s){let t=e(!1),n=e(!1),c=e("对话框"),d=i({comp:l.is,param:l.param,events:{}});return{fullScreen:t,dialogVisible:n,openObjectDialog:(e,l)=>{d.param=l,d.comp=a((()=>o((()=>__import__("./ObjectPage.80893d31.js")),["./assets/ObjectPage.80893d31.js","./assets/ObjectPage.b6f3c6f0.css","./assets/index.5fcea91f.js","./assets/index.b6960b20.css","./assets/vendor.25d1acb5.js"]))),n.value=!0},data:d,Title:c,openDialog:(e,a,l,s)=>(c.value=e,d.param=l,d.comp=a,d.events=s,n.value=!0,{close:()=>{n.value=!1}})}},name:"CustomDialog"});const O=_();c("data-v-61da3666");const V={style:{size:"12px","padding-left":"6px"}};d();const h=O(((e,a,l,s,t,o)=>{const n=r("el-icon"),i=r("el-col"),c=r("el-dialog");return p(),u(c,{"custom-class":e.fullScreen?"":"custom-el-dialog",width:"90%",fullscreen:e.fullScreen,"lock-scroll":"","append-to-body":"",modelValue:e.dialogVisible,"onUpdate:modelValue":a[2]||(a[2]=a=>e.dialogVisible=a),"destroy-on-close":""},{title:O((()=>[m(n,{class:"el-icon-reading"}),m("span",V,g(e.Title),1),m(i,{style:{width:"100%"}}),m("i",{onClick:a[1]||(a[1]=a=>e.fullScreen=!e.fullScreen),class:"el-dialog__headerbtn2 el-icon-full-screen"})])),default:O((()=>[(p(),u(f(e.data.comp),b(e.data.param,v(e.data.events)),null,16))])),_:1},8,["custom-class","fullscreen","modelValue"])}));D.render=h,D.__scopeId="data-v-61da3666";export{D as _,y as e,j as g};
