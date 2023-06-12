<template>
  <div id="app">
    <v-app id="inspire">
      <v-navigation-drawer v-model="$store.state.drawer" app clipped>
        <v-list dense>
          <!--          <v-list-item link router :to="{ name: 'qrcode' }">-->
          <!--            <v-list-item-action>-->
          <!--              <v-icon>mdi-cog</v-icon>-->
          <!--            </v-list-item-action>-->
          <!--            <v-list-item-content>-->
          <!--              <v-list-item-title>QR 코드 리더기</v-list-item-title>-->
          <!--            </v-list-item-content>-->
          <!--          </v-list-item>-->

          <v-list-item link router :to="{ name: 'index' }">
            <v-list-item-action>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Dashboard</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

<!--          <v-list-item link router :to="{ name: 'qrreader' }">-->
<!--            <v-list-item-action>-->
<!--              <v-icon>mdi-ballot</v-icon>-->
<!--            </v-list-item-action>-->
<!--            <v-list-item-content>-->
<!--              <v-list-item-title>QRCode</v-list-item-title>-->
<!--            </v-list-item-content>-->
<!--          </v-list-item>-->

          <v-list-group :value="false" no-action sub-group v-if="$store.state.userSet">
            <template v-slot:activator>
              <v-list-item-title>공장 마스터 데이터</v-list-item-title>
            </template>

            <v-list-item link router :to="{ name: 'fabmast' }">
              <v-list-item-icon>
                <v-icon>mdi-vector-square</v-icon>
              </v-list-item-icon>
              <v-list-item-title>FAB_Setting</v-list-item-title>
            </v-list-item>

            <v-list-item link router :to="{ name: 'linemast' }">
              <v-list-item-action>
                <v-icon>mdi-order-numeric-ascending</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>라인 등록</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'eqpmast' }">
              <v-list-item-action>
                <v-icon>mdi-robot-industrial</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>설비 등록</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'fabworkmast' }">
              <v-list-item-action>
                <v-icon>mdi-clipboard-pulse</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>생산 계획 설정</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'opswork' }">
              <v-list-item-action>
                <v-icon>mdi-file-tree</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>작업 등록</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'opsworker' }">
              <v-list-item-action>
                <v-icon>mdi-account-hard-hat</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>작업 인력 계획</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>

          <v-list-group :value="false" no-action sub-group v-if="$store.state.userSet">
            <template v-slot:activator>
              <v-list-item-title>마스터 데이터</v-list-item-title>
            </template>
            <v-list-item link router :to="{ name: 'account' }">
              <v-list-item-action>
                <v-icon>mdi-store-cog</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>거래처 마스터 데이터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'product' }">
              <v-list-item-action>
                <v-icon>mdi-food-variant</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>제품 마스터 데이터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'usermast' }">
              <v-list-item-action>
                <v-icon>mdi-smart-card-outline</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>사용자 마스터 데이터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'iteminventory' }">
              <v-list-item-action>
                <v-icon>mdi-storage-tank</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>제품 재고 마스터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'ordertview' }">
              <v-list-item-action>
                <v-icon>mdi-application-edit</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>주문 마스터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'orderkidsview' }">
              <v-list-item-action>
                <v-icon>mdi-bag-personal-tag</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>KIDS 주문 마스터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>

            <v-list-item link router :to="{ name: 'kidsdelivery' }">
              <v-list-item-action>
                <v-icon>mdi-truck-delivery</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>KIDS 배송 마스터</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>

          <v-list-item link router :to="{ name: 'opsworkerview' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-ballot</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>작업 인력 배치 현황</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'enter' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-application-import</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>입고 등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'nonenter' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-application-variable</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>고객사별 입고 & 미수거 현황</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'opsaworkresult' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-chart-tree</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>생산 작업 조회</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'opsaorder' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-factory</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>생산 작업</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'opsaorderwork' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-download-network</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>작업 실적 등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'opsaorderedit' }" v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-timeline-text</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>당일 작업 실적 수정(Admin)</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'opworkdf' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-download-network-outline</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>검수 실적 등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'pkorder' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-package-check</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>포장 작업</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'pkwork' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-archive-arrow-down</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>포장 실적 등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'pkkidsorder' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-bag-personal</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>KIDS 포장 실적 등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <!--          <v-list-item link router :to="{ name: 'pktag' }">-->
          <!--            <v-list-item-action>-->
          <!--              <v-icon>mdi-archive-check</v-icon>-->
          <!--            </v-list-item-action>-->
          <!--            <v-list-item-content>-->
          <!--              <v-list-item-title>박스 태그</v-list-item-title>-->
          <!--            </v-list-item-content>-->
          <!--          </v-list-item>-->

          <v-list-item link router :to="{ name: 'isdfv' }"  v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-delete-off</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>파손 불량 입력</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item link router :to="{ name: 'setting' }" v-if="$store.state.userSet">
            <v-list-item-action>
              <v-icon>mdi-cog</v-icon>
            </v-list-item-action>
            <v-list-item-content>
              <v-list-item-title>Settings</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-app-bar app clipped-left color="#002E75">
<!--        <img class="mr-3" :src="require('../../assets/bblogN.jpg')" height="40"/>-->
        <v-toolbar-title style="color: white"> dingo Board</v-toolbar-title>
        <div v-if="$store.state.iconflagSet == true">
          <v-app-bar-nav-icon style="color: white"
                              @click.stop="$store.state.drawer = !$store.state.drawer"
          ></v-app-bar-nav-icon>
        </div>

        <v-spacer></v-spacer>

        <v-tooltip bottom color="error">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                class="mx-2"
                color="cyan"
                dark
                fab
                small
                v-bind="attrs"
                v-on="on"
                @click="logOutUser()"
            >
              <v-icon>mdi-export</v-icon>
            </v-btn>
          </template>
          <span>로그아웃</span>
        </v-tooltip>
      </v-app-bar>

      <v-main>
        <router-view/>
      </v-main>

      <v-footer app>
        <span style=" font-size:10px;">© 2023 Co., Ltd. All rights reserved.</span>
      </v-footer>
    </v-app>
  </div>
</template>

<script>

export default {
  name: "navigation",
  data: () => ({
    iconflag: false,
  }),
  created() {
    this.$store.commit("drawerSetData", {drawer: false});
  },
  methods: {
    logOutUser() {
      this.$cookies.remove("accessToken");
      this.$cookies.remove("refreshToken");
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>


* {
  font-family: SpoqaHanSansNeoBold, SwitzerBlack, sans-serif;
  src: url('../../assets/font/SwitzerBlack.ttf');
  src: url('../../assets/font/SpoqaHanSansNeoBold.otf');
  text-align: center;
  font-weight: bold;
  font-style: normal;
  color: #191d21;
}


</style>
