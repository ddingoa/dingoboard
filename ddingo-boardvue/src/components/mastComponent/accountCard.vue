<template>
  <v-form>
    <v-row>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.ceoNm"
          label="대표자"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.bizStatus"
          label="업태"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.adjDate"
          label="정산일"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.HOLIDAYS_APPLY"
          label="휴일 적용 여부"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.cmsNo"
          label="CMS 코드"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="2" sm="1">
        <v-text-field
          v-model="this.childVaule.KIDS_NOTE_CODE"
          label="키즈 노트 코드"
          readonly
        ></v-text-field>
      </v-col>
      <v-col cols="3" sm="2">
        <v-text-field
            v-model="this.childVaule.deliveryType"
            label="배송 형태"
            readonly
        ></v-text-field>
      </v-col>
      <v-col cols="3" sm="2">
        <v-text-field
            v-model="this.childVaule.remark"
            label="비고"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
export default {
  name: "accountCard",
  data: () => ({
    message4: "ddd"
  }),
  props: ["childVaule"]
};
</script>

<style scoped></style>
