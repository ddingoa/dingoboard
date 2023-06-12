<template>
  <v-row justify="left" class="pa-2">
    <v-col cols="12" sm="10" md="8" lg="6">
      <v-card ref="form">
        <v-card-text>
          <ValidationProvider v-slot="{ errors }" name="Name" rules="required">
            <v-text-field
              v-model="eqpName"
              :error-messages="errors"
              label="EQP Name"
              placeholder="John Doe"
              required
            ></v-text-field>
          </ValidationProvider>

          <ValidationProvider v-slot="{ errors }" name="Name" rules="required">
            <v-select
              v-model="eqpTypeItemsValue"
              :items="eqpTypeItems"
              :error-messages="errors"
              label="Select"
              data-vv-name="select"
              required
            ></v-select>
          </ValidationProvider>
          <ValidationProvider v-slot="{ errors }" name="Name" rules="required">
            <v-text-field
              v-model="RecipeName"
              :error-messages="errors"
              label="Recipe Name"
              placeholder="John Doe"
              required
            ></v-text-field>
          </ValidationProvider>
          <ValidationProvider v-slot="{ errors }" name="Name" rules="required">
            <v-text-field
              v-model="ParamterName"
              :error-messages="errors"
              label="Paramter Name"
              placeholder="John Doe"
              required
            ></v-text-field>
          </ValidationProvider>

          <ValidationProvider
            name="select"
            rules="required"
          >
            <v-autocomplete
              v-model="country"
              :items="countries"
              label="Country"
              placeholder="Select..."
              required
            ></v-autocomplete>
          </ValidationProvider>
        </v-card-text>
        <v-divider class="mt-12"></v-divider>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { required, max } from "vee-validate/dist/rules";
import {
  extend,
  ValidationObserver,
  ValidationProvider,
  setInteractionMode
} from "vee-validate";

setInteractionMode("eager");

extend("required", {
  ...required,
  message: "{_field_} can not be empty"
});

extend("max", {
  ...max,
  message: "{_field_} may not be greater than {length} characters"
});

export default {
  name: "eqpcard",
  components: {
    ValidationProvider
  },
  data: () => ({
    eqpName: "",
    RecipeName: "",
    ParamterName: "",
    country: "",
    countries: [],
    eqpTypeItemsValue: "",
    eqpTypeItems: ["Processing EQP", "Measuring EQP"]
  })
};
</script>

<style scoped></style>
