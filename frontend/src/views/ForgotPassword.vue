<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md6>
        <material-card color="green" :title="title" :text="subtitle" @keyup.enter="submit">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field v-model="vo.email" :label="email" class="green-input"/>
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn
                    @click="submit"
                    :loading="isLoading"
                    class="mx-0 font-weight-light"
                    color="success"
                    block
                  >{{send}}</v-btn>
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
      isLoading: false,
      responsive: false,
      prefix: "ForgotPassword.form"
    };
  },
  computed: {
    title() {
      return this.$t(`${this.prefix}.title`);
    },
    subtitle() {
      return this.$t(`${this.prefix}.subtitle`);
    },
    send() {
      return this.$t(`${this.prefix}.send`);
    },
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    success() {
      return this.$t(`${this.prefix}.success`);
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
      this.isLoading = true;
      this.$store
        .dispatch("auth/forgotPassword", this.vo)
        .then(() => {
          this.showSuccess(this.success);
          this.$router.push(`reset-password?email=${this.vo.email}`);
        })
        .catch(error => this.showError(error.message))
        .finally(() => (this.isLoading = false));
    }
  }
};
</script>
