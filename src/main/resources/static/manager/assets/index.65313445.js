var e=Object.defineProperty,t=Object.getOwnPropertySymbols,a=Object.prototype.hasOwnProperty,n=Object.prototype.propertyIsEnumerable,r=(t,a,n)=>a in t?e(t,a,{enumerable:!0,configurable:!0,writable:!0,value:n}):t[a]=n;import{r as o,o as s,c as l,d as i,b as u,s as c,u as m,a as p,e as d,f as g,p as _,g as f,h,w as b,i as D,F as y,j as v,t as P,k as x,l as E,m as w,n as O,q as Y,I as j,_ as T}from"./vendor.045e14f3.js";const M={name:"App"};let k;M.render=function(e,t,a,n,r,i){const u=o("router-view");return s(),l(u)};const I={},L=function(e,t){if(!t)return e();if(void 0===k){const e=document.createElement("link").relList;k=e&&e.supports&&e.supports("modulepreload")?"modulepreload":"preload"}return Promise.all(t.map((e=>{if(e in I)return;I[e]=!0;const t=e.endsWith(".css"),a=t?'[rel="stylesheet"]':"";if(document.querySelector(`link[href="${e}"]${a}`))return;const n=document.createElement("link");return n.rel=t?"stylesheet":k,t||(n.as="script",n.crossOrigin=""),n.href=e,document.head.appendChild(n),t?new Promise(((e,t)=>{n.addEventListener("load",e),n.addEventListener("error",t)})):void 0}))).then((()=>e()))};function S(e){u.success({message:e,type:"success"})}function A(e){e&&e.response?u.error(JSON.stringify(e.response.data)):u.error(JSON.stringify(e))}function R(){return[{text:"今天",value:(()=>{const e=new Date;return[new Date(e.getFullYear(),e.getMonth(),e.getDate()),e]})()},{text:"昨天",value:(()=>{const e=new Date;return e.setTime(e.getTime()-864e5),[new Date(e.getFullYear(),e.getMonth(),e.getDate()),new Date(e.getFullYear(),e.getMonth(),e.getDate(),24)]})()},{text:"前天",value:(()=>{const e=new Date;return e.setTime(e.getTime()-1728e5),[new Date(e.getFullYear(),e.getMonth(),e.getDate()),new Date(e.getFullYear(),e.getMonth(),e.getDate(),24)]})()}]}function V(e){return i(e).format("YYYYMMDD")}function F(e){return i(e).format("YYYYMMDD HH:mm:ss")}function N(e){return i(e,"YYYYMMDD").toDate()}let $=c({loading:!1,menus:[]});function J(){$.loading=!0,d.get("/sys/manager/api/menu/list2").then((e=>{$.menus=e.data,$.loading=!1})).catch((e=>{A(e),$.loading=!1})),m();const e=p();return{menuData:$,select:(t,a)=>{e.push("/main/table/"+t)}}}var q=g({props:{},setup:(e,o)=>((e,o)=>{for(var s in o||(o={}))a.call(o,s)&&r(e,s,o[s]);if(t)for(var s of t(o))n.call(o,s)&&r(e,s,o[s]);return e})({},J()),name:"ManagerPage"});const z=E();_("data-v-54b133c6");const H=x("管理"),W=D("i",{class:"el-icon-location"},null,-1),C=D("i",{class:"el-icon-location"},null,-1);f();const B=z(((e,t,a,n,r,i)=>{const u=o("el-header"),c=o("el-menu-item"),m=o("el-submenu"),p=o("el-menu"),d=o("el-aside"),g=o("router-view"),_=o("el-main"),f=o("el-container"),E=h("loading");return b((s(),l(f,{style:{height:"100%"}},{default:z((()=>[D(u,{style:{height:"60px","text-align":"center","line-height":"60px"}},{default:z((()=>[H])),_:1}),D(f,{style:{"max-height":"calc(100% - 60px)",border:"1px solid #eee"}},{default:z((()=>[D(d,{width:"200px",style:{"background-color":"rgb(238, 241, 246)"}},{default:z((()=>[D(p,{"default-active":e.$route.params.tableName,onSelect:e.select},{default:z((()=>[(s(!0),l(y,null,v(e.menuData.menus,(e=>(s(),l(y,null,[e.group?(s(),l(m,{key:0,index:e.group},{title:z((()=>[W,D("span",null,P(e.group),1)])),default:z((()=>[(s(!0),l(y,null,v(e.menus,(e=>(s(),l(c,{index:e.name},{default:z((()=>[x(P(e.remark),1)])),_:2},1032,["index"])))),256))])),_:2},1032,["index"])):(s(),l(c,{key:1,index:e.name},{title:z((()=>[C,D("span",null,P(e.remark),1)])),_:2},1032,["index"]))],64)))),256))])),_:1},8,["default-active","onSelect"])])),_:1}),D(_,{style:{"max-height":"100%",padding:"6px"}},{default:z((()=>[D(g,{key:e.$route.path})])),_:1})])),_:1})])),_:1},512)),[[E,e.menuData.loading]])}));q.render=B,q.__scopeId="data-v-54b133c6";var G=Object.freeze({__proto__:null,[Symbol.toStringTag]:"Module",default:q});L((()=>import("./SearchPage.83a02783.js")),["./assets/SearchPage.83a02783.js","./assets/SearchPage.c3a206aa.css","./assets/CustomDialog.25bbff2f.js","./assets/vendor.045e14f3.js"]);const K=w({history:O(),routes:[{path:"/",redirect:"/main"},{path:"/main",component:()=>L((()=>Promise.resolve().then((function(){return G}))),void 0),children:[{path:"",component:()=>L((()=>import("./Welcome.7879a40d.js")),["./assets/Welcome.7879a40d.js","./assets/Welcome.df43020b.css","./assets/vendor.045e14f3.js"])},{path:"table/:tableName",component:()=>L((()=>import("./TablePage.bf9b76a8.js")),["./assets/TablePage.bf9b76a8.js","./assets/vendor.045e14f3.js","./assets/CustomDialog.25bbff2f.js","./assets/SearchPage.c3a206aa.css","./assets/SearchPage.83a02783.js","./assets/JobPage.aaedc5a2.js","./assets/JobPage.ce92ac4e.css","./assets/ObjectPage.578fb003.js","./assets/ObjectPage.6f0d2d73.css"])}]},{path:"/table/:tableName",component:()=>L((()=>import("./TablePage.bf9b76a8.js")),["./assets/TablePage.bf9b76a8.js","./assets/vendor.045e14f3.js","./assets/CustomDialog.25bbff2f.js","./assets/SearchPage.c3a206aa.css","./assets/SearchPage.83a02783.js","./assets/JobPage.aaedc5a2.js","./assets/JobPage.ce92ac4e.css","./assets/ObjectPage.578fb003.js","./assets/ObjectPage.6f0d2d73.css"])},{path:"/job",component:()=>L((()=>import("./JobPage.aaedc5a2.js")),["./assets/JobPage.aaedc5a2.js","./assets/JobPage.ce92ac4e.css","./assets/vendor.045e14f3.js"])},{path:"/object/:tableName/:id(\\d+)",component:()=>L((()=>import("./ObjectPage.578fb003.js")),["./assets/ObjectPage.578fb003.js","./assets/ObjectPage.6f0d2d73.css","./assets/CustomDialog.25bbff2f.js","./assets/SearchPage.c3a206aa.css","./assets/vendor.045e14f3.js"])}]});let Q=Y(M);window.vue=Q.use(K).use(j,{locale:T,size:"mini"}).mount("#app"),Q.config.globalProperties.$http=d;export{L as _,F as a,R as b,S as c,V as d,N as e,A as s};
