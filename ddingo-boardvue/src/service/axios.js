import axios from "axios";
import VueCookies from "vue-cookies";
import store from "../store/index";

// Add a request interceptor
axios.interceptors.request.use(async function(config) {
    // Do something before request is senta

    //config.headers.bbdekToken = "BBodek " + getCookieValue("accessToken");
    config.headers.refreshToken = "BBodek " + getCookieValue("refreshToken");

    return config;
}, function(error){
    // Do something with request error
    return Promise.reject(error);
});

// Add a response interceptor
axios.interceptors.response.use(
  function(response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response;
  },
  async function(error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    console.log("에러일 경우", error.config);
    const errorAPI = error.config;
    if (error.code === "ERR_BAD_REQUEST" && errorAPI.retry === undefined) {
      errorAPI.retry = true;
      console.log("토큰이 이상한 오류일 경우");
      await store.dispatch("refresh_token_reissue");
      this.$router.go(0);
      return await axios(errorAPI);
    }
    console.log("반복 에러");
    return Promise.reject(error);
  }
);

const getCookieValue = (key) => {
    let cookieKey = key + "=";
    let result = "";
    const cookieArr = document.cookie.split(";");

    for(let i = 0; i < cookieArr.length; i++) {
        if(cookieArr[i][0] === " ") {
            cookieArr[i] = cookieArr[i].substring(1);
        }

        if(cookieArr[i].indexOf(cookieKey) === 0) {
            result = cookieArr[i].slice(cookieKey.length, cookieArr[i].length);
            return result;
        }
    }
    return result;
}

export default axios;
