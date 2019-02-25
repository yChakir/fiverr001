<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md6>
        <material-card @keyup.enter="submit" color="green" :title="title" :text="subtitle">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    v-model="vo.email"
                    :label="email"
                    :disabled="disabled.email"
                    class="green-input"
                  />
                </v-flex>
                <v-flex xs12>
                  <v-text-field
                    v-model="vo.token"
                    :disabled="disabled.token"
                    :label="token"
                    class="green-input"
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn
                    @click="submit"
                    :loading="isLoading"
                    class="mx-0 font-weight-light"
                    color="success"
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
      prefix: "ValidateEmail.form"
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

    if (this.disabled.email && this.disabled.token) {
      this.submit();
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
    ...mapMutations("snack", ["showError", "showSuccess"]),
    onResponsiveInverted() {
      if (window.innerWidth < 991) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    },
    submit() {
      this.isLoading = true;
      this.$store
        .dispatch("auth/validateEmail", this.vo)
        .then(() => {
          this.showSuccess(this.success);
          this.$router.push("login");
        })
        .catch(error => {
          this.showError(error.message);
        })
        .finally(() => (this.isLoading = false));
    }
  }
};
</script>
