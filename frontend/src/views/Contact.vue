<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md10>
        <material-card color="green">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md6>
                  <v-layout wrap>
                    <v-flex xs12>
                      <v-text-field
                        @keyup.enter="submit"
                        autofocus
                        v-validate="'required|email|min:3|max:100'"
                        :data-vv-name="email"
                        :error-messages="errors.collect(email)"
                        v-model="vo.email"
                        :label="email"
                        :disabled="disabled.email"
                        class="green-input"
                      />
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field
                        @keyup.enter="submit"
                        v-validate="'required|min:2|max:50'"
                        :data-vv-name="name"
                        :error-messages="errors.collect(name)"
                        v-model="vo.name"
                        :label="name"
                        class="green-input"
                      />
                    </v-flex>
                    <v-flex xs12>
                      <v-textarea
                        v-validate="'required|min:10|max:250'"
                        :counter="250"
                        :data-vv-name="message"
                        :error-messages="errors.collect(message)"
                        v-model="vo.message"
                        class="green-input"
                        :label="message"
                      />
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
                </v-flex>
                <v-divider vertical></v-divider>
                <v-flex xs12 md5>
                  <div class="mapouter">
                    <div class="gmap_canvas">
                      <iframe
                        id="gmap_canvas"
                        width="100%"
                        height="100%"
                        src="https://maps.google.com/maps?q=google&t=&z=13&ie=UTF8&iwloc=&output=embed"
                        frameborder="0"
                        scrolling="no"
                        marginheight="0"
                        marginwidth="0"
                      />
                    </div>
                  </div>
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
      prefix: "Contact.form"
    };
  },
  computed: {
    send() {
      return this.$t(`${this.prefix}.send`);
    },
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    name() {
      return this.$t(`${this.prefix}.name`);
    },
    message() {
      return this.$t(`${this.prefix}.message`);
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
      this.$validator.validate().then(valid => {
        if (valid) {
          this.isLoading = true;
          this.$store
            .dispatch("contact/contact", this.vo)
            .then(() => {
              this.showSuccess(this.success);
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

<style>
.mapouter {
  text-align: right;
  height: 100%;
  width: 100%;
  display: inline;
  min-height: 500px;
}
.gmap_canvas {
  overflow: hidden;
  background: none !important;
  height: 100%;
  width: 100%;
}
</style>
