<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md5>
        <material-card color="green" :title="title" :text="subtitle" @keyup.enter="submit">
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md12>
                  <v-layout wrap>
                    <v-flex xs12 md6>
                      <v-text-field
                        autofocus
                        v-validate="'required|min:2|max:50'"
                        :data-vv-name="name"
                        :error-messages="errors.collect(name)"
                        v-model="vo.name"
                        :label="name"
                        class="green-input"
                      />
                    </v-flex>
                    <v-flex xs12 md6>
                      <v-text-field
                        v-validate="'required|min:2|max:50'"
                        :data-vv-name="surname"
                        :error-messages="errors.collect(surname)"
                        v-model="vo.surname"
                        :label="surname"
                        class="green-input"
                      />
                    </v-flex>
                    <v-flex xs12>
                      <v-text-field
                        v-validate="'required|email|min:3|max:100'"
                        :data-vv-name="email"
                        :error-messages="errors.collect(email)"
                        v-model="vo.email"
                        :label="email"
                        class="green-input"
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
                        class="green-input"
                      />
                    </v-flex>
                  </v-layout>
                  <v-flex xs12>
                    <router-link to="/login">{{alreadyRegistered}}</router-link>
                  </v-flex>
                  <v-flex xs12 text-xs-right>
                    <v-btn
                      @click="submit"
                      :loading="isLoading"
                      class="mx-0 font-weight-light"
                      color="success"
                      block
                    >{{signup}}</v-btn>
                  </v-flex>
                </v-flex>
                <v-divider v-if="false" vertical></v-divider>
                <v-flex v-if="false" xs12 md4>
                  <v-layout wrap>
                    <v-flex xs12>
                      <v-btn class="mx-0 font-weight-light" color="danger" block>
                        <fa-icon :icon="['fab', 'google']" size="lg" class="v-icon--left"/>
                        {{signupGoogle}}
                      </v-btn>
                    </v-flex>
                    <v-flex xs12>
                      <v-btn block class="mx-0 font-weight-light" color="indigo">
                        <fa-icon :icon="['fab', 'facebook-f']" size="lg" class="v-icon--left"/>
                        {{signupFacebook}}
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
      vo: {},
      isLoading: false,
      responsive: false,
      prefix: "SignUp.form"
    };
  },
  computed: {
    title() {
      return this.$t(`${this.prefix}.title`);
    },
    subtitle() {
      return this.$t(`${this.prefix}.subtitle`);
    },
    signup() {
      return this.$t(`${this.prefix}.signup`);
    },
    signupGoogle() {
      return this.$t(`${this.prefix}.signup-google`);
    },
    signupFacebook() {
      return this.$t(`${this.prefix}.signup-facebook`);
    },
    name() {
      return this.$t(`${this.prefix}.name`);
    },
    surname() {
      return this.$t(`${this.prefix}.surname`);
    },
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    password() {
      return this.$t(`${this.prefix}.password`);
    },
    alreadyRegistered() {
      return this.$t(`${this.prefix}.alreadyRegistered`);
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
      this.$validator.validate().then(valid => {
        if (valid) {
          this.isLoading = true;
          this.$store
            .dispatch("auth/register", this.vo)
            .then(() => {
              this.showSuccess(this.success);
              this.$router.push(`email-validation?email=${this.vo.email}`);
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
