import axios from "axios";
import VueCookies from "vue-cookies";
import router from "../router";
//로그인 처리 관련 저장소 모듈
export const login = {
  state: {
    accessToken: null,
    refreshToken: null
  },
  mutations: {
    removeToken() {
      VueCookies.remove("accessToken");
      VueCookies.remove("refreshToken");
    }
  },
  getters: {
    //쿠키에 저장된 토큰 가져오기
    getToken() {
      let accessToken = VueCookies.get("accessToken");
      let refreshToken = VueCookies.get("refreshToken");
      return {
        accessToken: accessToken,
        refreshToken: refreshToken
      };
    }
  },
  actions: {
    login: ({ commit }, params) => {
      return new Promise((resove, reject) => {
        axios
          .post("/auth/login", params)
          .then(res => {
            commit("loginToken", res.data.auth_info);
            resove(res);
          })
          .catch(err => {
            reject(err.message);
          });
      });
    },
    refresh_token_reissue: () => {
      // accessToken 재요청
      //accessToken 만료로 재발급 후 재요청시 비동기처리로는 제대로 처리가 안되서 promise로 처리함
      return new Promise(resolve => {
        var data = {
          refreshToken:  VueCookies.get("refreshToken")
        };

        axios
          .post("/auth/reissue", data)
          .then(res => {
            resolve(res);
          })
          .catch(() => {
            alert("서버와 통신이 되지 않습니다.");
            VueCookies.remove("refreshToken");
            router.push("/login");
          });
      })
        .then(function(data) {
          VueCookies.set("refreshToken", data.data.refreshToken);
          axios.defaults.headers.common["bbdekToken"] = "BBodek " + data.data.tokenDto.accessToken;
        })
        .catch(() => {
          alert("서버와 통신이 되지 않습니다.");
          VueCookies.remove("refreshToken");
          router.push("/login");
        });
    },
    logout: ({ commit }) => {
      // 로그아웃
      commit("removeToken");
      location.reload();
    }
  }
};
