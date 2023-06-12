import Vue from 'vue'
import VueRouter from 'vue-router'
import Loogin from '../views/login'
import VueCookies from "vue-cookies";
import Main from '../views/index.vue'
import deliv from "../views/deliv";
import defined from "@/views/Defined";

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "*",
    component: deliv
  },
  {
    path: "/define",
    component: defined,
    meta: { requiresAuth: true }
  },
  {
    path: "/login",
    name: "Login",
    component: Loogin,
    meta: { unauthorized: true }
  },
  {
    path: "/bb/index",
    name: "index",
    component: Main,
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/qrreader",
    name: "qrreader",
    component: () =>
        import(/* webpackChunkName: "about" */ "../views/otherView/QrReader"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/fabmast",
    name: "fabmast",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/mastView/FabMast.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/linemast",
    name: "linemast",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/mastView/LineMast.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/eqpmast",
    name: "eqpmast",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/mastView/EqpMast.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/usermast",
    name: "usermast",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/mastView/UserMast.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/account",
    name: "account",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/otherView/accountView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/product",
    name: "product",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/otherView/ProductView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/enter",
    name: "enter",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/EnterItemView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/nonenter",
    name: "nonenter",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/NonEnterItemView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opswork",
    name: "opswork",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/opsWorkView/OpsWorkView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opsworker",
    name: "opsworker",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/opsWorkView/OpsWorkerView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opsworkerview",
    name: "opsworkerview",
    component: () =>
      import(
        /* webpackChunkName: "about" */ "../views/opsWorkView/OpsWorkerTreeView.vue"
      ),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/isdfv",
    name: "isdfv",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/ISDFView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opworkdf",
    name: "opworkdf",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/OpIsWorkDFView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opsaorder",
    name: "opsaorder",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/OpSaOrderView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opsaorderwork",
    name: "opsaorderwork",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/OpSaOrderWorkView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/pkorder",
    name: "pkorder",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/packingView/PkOrderViewN.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/pkwork",
    name: "pkwork",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/packingView/PkWorkVidwN.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/pktag",
    name: "pktag",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/packingView/PkTagBoxView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/opsaorder",
    name: "opsaorder",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/orderView/OpSaOrderView.vue"),
    meta: { requiresAuth: true }
  },
  {
    path: "/bb/setting",
    name: "setting",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/otherView/SettingView.vue")
  },
  {
    path: "/bb/qrcode",
    name: "qrcode",
    component: () => import(/* webpackChunkName: "about" */ "../views/otherView/QrReader.vue")
  },
  {
    path: "/bb/fabworkmast",
    name: "fabworkmast",
    component: () => import(/* webpackChunkName: "about" */ "../views/mastView/FabWorkMast")
  },
  {
    path: "/bb/iteminventory",
    name: "iteminventory",
    component: () => import(/* webpackChunkName: "about" */ "../views/otherView/ItemInventoryView")
  },
  {
    path: "/bb/opsaorderedit",
    name: "opsaorderedit",
    component: () => import(/* webpackChunkName: "about" */ "../views/adminEditView/OpSaOrderEditView")
  },
  {
    path: "/bb/ordertview",
    name: "ordertview",
    component: () => import(/* webpackChunkName: "about" */ "../views/otherView/Order_T_View")
  }
  ,
  {
    path: "/bb/orderkidsview",
    name: "orderkidsview",
    component: () => import(/* webpackChunkName: "about" */ "../views/otherView/Order_T_KidsView")
  }
  ,
  {
    path: "/bb/pkkidsorder",
    name: "pkkidsorder",
    component: () => import(/* webpackChunkName: "about" */ "../views/packingView/PKKidsWorkView")
  }  ,
  {
    path: "/bb/kidsdelivery",
    name: "kidsdelivery",
    component: () => import(/* webpackChunkName: "about" */ "../views/otherView/KidsDeliveryView")
  },
  {
    path: "/bb/opsaworkresult",
    name: "opsaworkresult",
    component: () => import(/* webpackChunkName: "about" */ "../views/orderView/OpSaWorkResultView")
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach(async (to, from, next) => {
  if (
      to.matched.some(record => record.meta.unauthorized) ||
      VueCookies.get("refreshToken")
  ) {
    return next();
  }
  if (to.path === "/define") {
    alert("비밀번호를 재설정해주세요.");
    return next(defined);
  } else {
    alert("로그인 해주세요");
    return next(Loogin);
  }
});


export default router
