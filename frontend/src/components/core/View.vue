<template>
  <v-content>
    <div id="core-view">
      <core-snackbar/>

      <v-fade-transition mode="out-in">
        <router-view/>
      </v-fade-transition>
    </div>
    <core-footer/>
  </v-content>
</template>

<script>
import Snackbar from "@/components/core/Snackbar";

export default {
  metaInfo() {
    return {
      title: "PORTAL.NAME"
    };
  },
  components: {
    "core-snackbar": Snackbar
  }
};
</script>

<style>
#core-view {
  padding-bottom: 100px;
}
</style>
