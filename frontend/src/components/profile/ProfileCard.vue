<template>
  <material-card class="v-card-profile" style="width: 100%;">
    <v-avatar slot="offset" class="mx-auto d-block" size="130">
      <img :src="`${apiEndpint}image/${profile.avatar}.jpg`">
    </v-avatar>
    <v-card-text class="text-xs-center">
      <h4 class="card-title font-weight-light">{{profile.name}} {{profile.surname}}</h4>
      <v-list dense>
        <v-list-tile>
          <v-list-tile-content>{{email}}</v-list-tile-content>
          <v-list-tile-content class="align-end">{{profile.email}}</v-list-tile-content>
        </v-list-tile>
        <v-list-tile v-if="profile.phone">
          <v-list-tile-content>{{phone}}</v-list-tile-content>
          <v-list-tile-content class="align-end">{{profile.phone}}</v-list-tile-content>
        </v-list-tile>
        <v-list-tile>
          <v-list-tile-content>{{createdAt}}</v-list-tile-content>
          <v-list-tile-content class="align-end">{{profile.createdAt}}</v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-card-text>
  </material-card>
</template>

<script>
import { API_ENDPOINT } from "@/config";
import { mapState } from "vuex";

export default {
  data() {
    return {
      prefix: "Profile.card",
      apiEndpint: API_ENDPOINT
    };
  },
  created() {
    this.$store.dispatch("profile/fetchProfile");
  },
  computed: {
    ...mapState("profile", ["profile"]),
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    phone() {
      return this.$t(`${this.prefix}.phone`);
    },
    createdAt() {
      return this.$t(`${this.prefix}.createdAt`);
    }
  }
};
</script>
