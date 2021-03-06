<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md4>
        <material-card color="primary" :title="title" :text="subtitle" @keyup.enter="submit">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 v-if="!disabled.email">
                  <v-text-field
                    v-validate="'required|email|min:3|max:100'"
                    :data-vv-name="email"
                    :error-messages="errors.collect(email)"
                    v-model="vo.email"
                    :label="email"
                    class="primary-input"
                  />
                </v-flex>
                <v-flex xs12 v-if="!disabled.token">
                  <v-text-field
                    v-validate="'required|min:30|max:50'"
                    :data-vv-name="token"
                    :error-messages="errors.collect(token)"
                    v-model="vo.token"
                    :label="token"
                    class="primary-input"
                  />
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-validate="{required: true, min: 8, max: 50, regex: /^.*(([a-zA-Z]+.*[0-9]+)|([0-9]+.*[a-zA-Z]+)).*$/}"
                    :data-vv-name="password"
                    :error-messages="errors.collect(password)"
                    v-model="vo.password"
                    type="password"
                    :label="password"
                    class="primary-input"
                  />
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-validate="'required|confirmed:password'"
                    :data-vv-name="confirmation"
                    :error-messages="errors.collect(confirmation)"
                    v-model="vo.confirmation"
                    type="password"
                    :label="confirmation"
                    class="primary-input"
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn
                    @click="submit"
                    :loading="isLoading"
                    class="mx-0 font-weight-light"
                    color="primary"
                    block
                  >{{validate}}</v-btn>
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
      vo: {},
      disabled: {},
      isLoading: false,
      responsive: false,
      prefix: "ResetPassword.form"
    };
  },
  computed: {
    title() {
      return this.$t(`${this.prefix}.title`);
    },
    subtitle() {
      return this.$t(`${this.prefix}.subtitle`);
    },
    validate() {
      return this.$t(`${this.prefix}.validate`);
    },
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    password() {
      return this.$t(`${this.prefix}.password`);
    },
    confirmation() {
      return this.$t(`${this.prefix}.confirmation`);
    },
    token() {
      return this.$t(`${this.prefix}.token`);
    },
    success() {
      return this.$t(`${this.prefix}.success`);
    }
  },
  created() {
    const email = this.$route.query.email;
    const token = this.$route.query.token;

    if (email) {
      this.vo.email = email;
      this.disabled.email = true;
    }
    if (token) {
      this.vo.token = token;
      this.disabled.token = true;
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
            .dispatch("auth/resetPassword", this.vo)
            .then(() => {
              this.showSuccess(this.success);
              this.$router.push("login");
            })
            .catch(error => this.showError(error.message))
            .finally(() => (this.isLoading = false));
        }
      });
    }
  }
};
</script>
