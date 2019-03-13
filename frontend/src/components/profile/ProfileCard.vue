<template>
  <material-card class="v-card-profile" style="width: 100%;">
    <v-avatar slot="offset" class="mx-auto d-block" size="130">
      <img :src="avatar">
    </v-avatar>
    <v-btn absolute round dark color="primary" top right class="jbtn-file">
      <fa-icon icon="camera" size="lg"/>
      <input
        v-validate="'required|image'"
        name="image"
        type="file"
        ref="file"
        @change="onFileChanged"
      >
    </v-btn>
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
import { mapState, mapMutations } from "vuex";

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
    },
    success() {
      return this.$t(`${this.prefix}.success`);
    },
    avatar() {
      let result = `${this.apiEndpint}/api/v1/images/${this.profile.avatar}`;
      if (this.profile.avatar === "default") {
        result = `${this.apiEndpint}/image/${this.profile.avatar}.jpg`;
      }
      return result;
    }
  },
  methods: {
    ...mapMutations("snack", ["showSuccess", "showError"]),
    onFileChanged(event) {
      this.imageName = event.srcElement.value.replace("C:\\fakepath\\", "");
      this.image = this.$refs.file.files[0];

      const formData = new FormData();
      formData.append("image", this.image);

      this.isLoading = true;
      this.$store
        .dispatch("profile/editAvatar", formData)
        .then(() => {
          this.$store
            .dispatch("profile/fetchProfile")
            .then(() => this.showSuccess(this.success));
        })
        .catch(error => this.showError(error.message))
        .finally(() => (this.isLoading = false));
    }
  }
};
</script>
<style scoped>
.jbtn-file {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.jbtn-file input[type="file"] {
  position: absolute;
  top: 0;
  right: 0;
  min-width: 100%;
  min-height: 100%;
  font-size: 100px;
  text-align: right;
  filter: alpha(opacity=0);
  opacity: 0;
  outline: none;
  cursor: inherit;
  display: block;
}
</style>
