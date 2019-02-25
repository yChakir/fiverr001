<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8>
        <material-card @keyup.enter="submit" color="green" :title="title" :text="subtitle">
          <v-form lazy-validation>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md7>
                  <v-layout wrap>
                    <v-flex xs12>
                      <v-text-field
                        v-validate="'required|email|min:3|max:100'"
                        data-vv-name="email"
                        :error-messages="errors.collect('email')"
                        v-model="credentials.username"
                        :label="email"
                        class="green-input"
                      />
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field
                        v-validate="'required|min:8|max:50'"
                        data-vv-name="password"
                        :error-messages="errors.collect('password')"
                        v-model="credentials.password"
                        type="password"
                        :label="password"
                        class="green-input"
                      />
                    </v-flex>
                  </v-layout>
                  <v-flex xs12>
                    <router-link to="/sign-up">{{dontHaveAccount}}</router-link>
                  </v-flex>
                  <v-flex xs12>
                    <router-link to="/forgot-password">{{forgotPassword}}</router-link>
                  </v-flex>
                  <v-flex xs12 text-xs-right>
                    <v-btn
                      @click="submit"
                      :loading="isLoading"
                      class="mx-0 font-weight-light"
                      color="success"
                      block
                    >{{login}}</v-btn>
                  </v-flex>
                </v-flex>
                <v-divider v-if="!responsive" vertical></v-divider>
                <v-flex xs12 md4>
                  <v-layout wrap>
                    <v-flex xs12>
                      <v-btn class="mx-0 font-weight-light" color="danger" block>
                        <fa-icon :icon="['fab', 'google']" size="lg" class="v-icon--left"/>
                        {{loginGoogle}}
                      </v-btn>
                    </v-flex>
                    <v-flex xs12>
                      <v-btn block class="mx-0 font-weight-light" color="indigo">
                        <fa-icon :icon="['fab', 'facebook-f']" size="lg" class="v-icon--left"/>
                        {{loginFacebook}}
                      </v-btn>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
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
      isLoading: false,
      responsive: false
    };
  },
  computed: {
    title() {
      return this.$t("Login.form.title");
    },
    subtitle() {
      return this.$t("Login.form.subtitle");
    },
    login() {
      return this.$t("Login.form.login");
    },
    loginGoogle() {
      return this.$t("Login.form.login-google");
    },
    loginFacebook() {
      return this.$t("Login.form.login-facebook");
    },
    email() {
      return this.$t("Login.form.email");
    },
    password() {
      return this.$t("Login.form.password");
    },
    dontHaveAccount() {
      return this.$t("Login.form.dontHaveAccount");
    },
    forgotPassword() {
      return this.$t("Login.form.forgotPassword");
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
    }
  }
};
</script>
