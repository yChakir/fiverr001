<template>
  <v-snackbar :color="color" :top="true" :right="true" v-model="state">
    <fa-icon color="white" :icon="icon" size="lg" class="mr-3"/>
    <div>{{message}}</div>
    <v-icon size="16" @click="hide">mdi-close-circle</v-icon>
  </v-snackbar>
</template>

<script>
import { mapState } from "vuex";

export default {
  data: function() {
    return {};
  },
  computed: {
    ...mapState("snack", ["color", "message", "icon"]),
    state: {
      get() {
        return this.$store.state.snack.state;
      },
      set(val) {
        if (!val) {
          this.hide();
        }
      }
    }
  },
  methods: {
    hide() {
      this.$store.commit("snack/hide");
    }
  }
};
</script>