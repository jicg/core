import{_ as d}from"./index.8b621e0d.js";import{C as p,D as m,a as C,d as g,s as O,r as c,o as f,c as D,n as _,m as v,y as j,B as w,E as B,G as F,H as V}from"./vendor.257a1e03.js";import{c as h,s as $}from"./comm.155bb935.js";import{_ as S}from"./plugin-vue_export-helper.5a098b48.js";function x(){const e=p();return{dialogRef:e,openDialog:(a,o,l,i)=>{if(typeof o=="string"){const r=m(()=>d(()=>import(`${o}`),[]));return e.value.openDialog(a,r,l,i)}else return e.value.openDialog(a,o,l,i)},openObjectDialog:(a,o,l)=>{e.value.openObjectDialog(a,{tableName:o,id:l})}}}function H(e,t,s){if(e.js)window[e.js]&&window[e.js](t,s);else if(e.procName){const a=this.$loading({lock:!0,text:"\u6267\u884C\u3010"+e.remark+"\u3011...",spinner:"el-icon-loading",background:"rgba(0, 0, 0, 0.7)"});C.post(`/sys/manager/api/${e.table}/run/${e.name}`,[t.map(o=>o.id).join(",")]).then(o=>{h("\u6267\u884C\u6210\u529F\uFF01"+o.data),a.close(),s&&s()}).catch(o=>{a.close(),$(o)})}}const k=g({props:{title:{type:String,default:"\u5BF9\u8BDD\u6846"},is:{type:Object,default:g({name:"temp-dialog-default",render:()=>"<div>\u6B22\u8FCE\u4F7F\u7528\u{1F44F}</div>"})},param:{type:Object,default:{}},events:{type:Object,default:{}}},setup(e,t){let s=p(!1),a=p(!1),o=p("\u5BF9\u8BDD\u6846"),l=O({comp:e.is,param:e.param,events:{}});return{fullScreen:s,dialogVisible:a,openObjectDialog:(u,n)=>{l.param=n,l.comp=m(()=>d(()=>import("./ObjectPage.9c5c37d7.js"),["assets/ObjectPage.9c5c37d7.js","assets/ObjectPage.2e44c286.css","assets/vendor.257a1e03.js","assets/comm.155bb935.js","assets/plugin-vue_export-helper.5a098b48.js","assets/index.8b621e0d.js","assets/index.522c9f17.css"])),a.value=!0},data:l,Title:o,openDialog:(u,n,b,y)=>(o.value=u,l.param=b,l.comp=n,l.events=y,a.value=!0,{close:()=>{a.value=!1}})}},name:"CustomDialog"}),E={style:{size:"12px","padding-left":"6px"}};function P(e,t,s,a,o,l){const i=c("el-icon"),r=c("el-col"),u=c("el-dialog");return f(),D(u,{"custom-class":e.fullScreen?"":"custom-el-dialog",width:"90%",fullscreen:e.fullScreen,"lock-scroll":"","append-to-body":"",modelValue:e.dialogVisible,"onUpdate:modelValue":t[1]||(t[1]=n=>e.dialogVisible=n),"destroy-on-close":""},{title:_(()=>[v(i,{class:"el-icon-reading"}),j("span",E,w(e.Title),1),v(r,{style:{width:"100%"}}),j("i",{onClick:t[0]||(t[0]=n=>e.fullScreen=!e.fullScreen),class:"el-dialog__headerbtn2 el-icon-full-screen"})]),default:_(()=>[(f(),D(B(e.data.comp),F(e.data.param,V(e.data.events)),null,16))]),_:1},8,["custom-class","fullscreen","modelValue"])}var I=S(k,[["render",P]]);export{I as C,H as e,x as g};
