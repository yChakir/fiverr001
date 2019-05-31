<template>
  <v-img width="100%" height="100%" position="absolute" src="https://cdn.vuetifyjs.com/images/parallax/material.jpg">
    <v-layout fill-height align-center justify-center>
      <v-flex xs10 md6>
        <div :class="display + ' text-xs-center font-weight-bold white--text'">This is a placeholder for the main content</div>
      </v-flex>
    </v-layout>
  </v-img>
</template>

<script>
export default {
  data() {
    return {};
  },
  created() {},
  computed: {
    display() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          return "display-1";
        case "sm":
          return "display-2";
        case "md":
          return "display-3";
        case "lg":
          return "display-3";
        case "xl":
          return "display-4";
        default:
          return "display-3"
      }
    }
  }
};
</script>


