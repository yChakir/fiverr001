<template>
  <v-btn @click="googleLogin()" class="mx-0 font-weight-light" color="danger" block>
    <fa-icon :icon="['fab', 'google']" size="lg" class="v-icon--left"/>
    {{loginGoogle}}
  </v-btn>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data: function() {
    return {
      credentials: {
        username: "",
        password: ""
      },
      google: {
        id:
          "420188269949-ncukksnudmcnb2176eaeo245hinvgc9r.apps.googleusercontent.com",
        base: "https://accounts.google.com/o/oauth2/v2/auth",
        scopes: [
          "https://www.googleapis.com/auth/userinfo.profile",
          "https://www.googleapis.com/auth/userinfo.email"
        ],
        redirect: "http://fiverr001.com:8080"
      },
      isLoading: false,
      responsive: false
    };
  },
  computed: {
    loginGoogle() {
      return this.$t("Login.form.login-google");
    },
    url() {
      return `${
        this.google.base
      }?scope=+&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=${
        this.google.redirect
      }&response_type=token&client_id=${this.google.id}`;
    }
  },
  mounted() {
    this.onResponsiveInverted();
    window.addEventListener("resize", this.onResponsiveInverted);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.onResponsiveInverted);
  },
  methods: {
    ...mapMutations("snack", ["showSuccess", "showError"]),
    onResponsiveInverted() {
      if (window.innerWidth < 991) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    },
    submit() {
      this.$validator.validate().then(valid => {
        if (valid) {
          this.isLoading = true;
          this.$store
            .dispatch("auth/login", this.credentials)
            .then(() => {
              this.$router.push("user-profile");
            })
            .catch(error => {
              this.showError(error.message);
            })
            .finally(() => (this.isLoading = false));
        }
      });
    },
    googleLogin() {
      window.location = ``;
    }
  }
};
</script>
