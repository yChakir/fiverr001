<template>
  <v-container
    fill-height
    fluid
    grid-list-xl>
    <v-layout
      justify-center
      wrap
    >
      <v-flex
        xs12
        md8
      >
        <material-card
          color="green"
          :title="title"
          :text="subtitle"
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md7>
                    <v-layout wrap>
                        <v-flex xs12 md6>
                        <v-text-field
                            v-model="vo.name"
                            :label="name"
                            class="green-input"/>
                        </v-flex>
                        <v-flex xs12 md6>
                        <v-text-field
                            v-model="vo.surname"
                            :label="surname"
                            class="green-input"/>
                        </v-flex>
                        <v-flex xs12>
                        <v-text-field
                            v-model="vo.email"
                            :label="email"
                            class="green-input"/>
                        </v-flex>
                        <v-flex xs12>
                        <v-text-field
                            v-model="vo.password"
                            type="password"
                            :label="password"
                            class="green-input"/>
                        </v-flex>
                    </v-layout>               
                    <v-flex xs12>
                        <router-link to="/login">
                            {{alreadyRegistered}}
                        </router-link>
                    </v-flex>
                    <v-flex
                    xs12
                    text-xs-right>
                    <v-btn
                        @click="submit"
                        :loading="isLoading"
                        class="mx-0 font-weight-light"
                        color="success"
                        block>
                        {{signup}}
                    </v-btn>
                    </v-flex> 
                </v-flex>
                <v-divider v-if="!responsive" vertical></v-divider>
                <v-flex xs12 md4>
                    <v-layout wrap>
                        <v-flex xs12>
                        <v-btn
                            class="mx-0 font-weight-light"
                            color="danger"
                            block>
                            {{signupGoogle}}
                        </v-btn>
                        </v-flex>
                        <v-flex xs12>
                        <v-btn
                            block
                            class="mx-0 font-weight-light"
                            color="indigo">
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
export default {
  data: function() {
    return {
      vo: {
      },
      isLoading: false,
      responsive: false,
      error: ""
    };
  },
  computed: {
    title() {
      return this.$t('SignUp.form.title');
    },
    subtitle() {
      return this.$t('SignUp.form.subtitle');
    },
    signup() {
      return this.$t('SignUp.form.signup');
    },
    signupGoogle() {
      return this.$t('SignUp.form.signup-google');
    },
    signupFacebook() {
      return this.$t('SignUp.form.signup-facebook');
    },
    name() {
      return this.$t('SignUp.form.name');
    },
    surname() {
      return this.$t('SignUp.form.surname');
    },
    email() {
      return this.$t('SignUp.form.email');
    },
    password() {
      return this.$t('SignUp.form.password');
    },
    alreadyRegistered() {
      return this.$t('SignUp.form.alreadyRegistered');
    }
  },
  mounted () {
    this.onResponsiveInverted()
    window.addEventListener('resize', this.onResponsiveInverted)
  },
  beforeDestroy () {
    window.removeEventListener('resize', this.onResponsiveInverted)
  },
  methods: {
    onResponsiveInverted () {
      if (window.innerWidth < 991) {
        this.responsive = true
      } else {
        this.responsive = false
      }
    },
    submit() {
      this.isLoading = true;
      const vo = {...this.vo};
      this.error = '';
      this.$store
        .dispatch('auth/register', vo)
        .then(() => {
          this.$router.push(`email-validation?email=${this.vo.email}`)
        })
        .catch(
          error => this.error = error
        )
        .finally(() => (this.isLoading = false));
    }
  }
}
</script>
