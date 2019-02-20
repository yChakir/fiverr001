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
        md6
      >
        <material-card
          color="green"
          :title="title"
          :text="subtitle"
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12>
                  <v-text-field
                    v-model="vo.email"
                    :label="email"
                    class="green-input"/>
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
                      {{send}}
                  </v-btn>
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
      vo: {},
      isLoading: false,
      responsive: false,
      error: ''
    };
  },
  computed: {
    title() {
      return this.$t('ForgotPassword.form.title');
    },
    subtitle() {
      return this.$t('ForgotPassword.form.subtitle');
    },
    send() {
      return this.$t('ForgotPassword.form.send');
    },
    email() {
      return this.$t('ForgotPassword.form.email');
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
        .dispatch('auth/forgotPassword', vo)
        .then(() => {
          this.$router.push(`reset-password?email=${this.vo.email}`)
        })
        .catch(
          error => this.error = error
        )
        .finally(() => (this.isLoading = false));
    }
  }
}
</script>
