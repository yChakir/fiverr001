<template>
  <material-card color="green" :title="title" :text="subtitle">
    <v-container py-0>
      <v-layout wrap>
        <v-flex xs12>
          <v-text-field v-model="vo.currentPassword" :label="current" type="password" class="green-input"/>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="vo.newPassword" :label="newPassword" type="password" class="green-input"/>
        </v-flex>
        <v-flex xs12>
          <v-text-field v-model="vo.confirmation" :label="confirmation" type="password" class="green-input"/>
        </v-flex>
        <v-flex xs12 text-xs-right>
          <v-btn class="mx-0 font-weight-light" @click="submitForm" :loading="loading" color="success">{{submit}}</v-btn>
        </v-flex>
      </v-layout>
    </v-container>
  </material-card>
</template>

<script>
export default {
  data() {
    return {
      vo: {},
      prefix: "Profile.password",
      loading: false
    };
  },
  computed: {
    title() {
      return this.$t(`${this.prefix}.title`);
    },
    subtitle() {
      return this.$t(`${this.prefix}.subtitle`);
    },
    current() {
      return this.$t(`${this.prefix}.current`);
    },
    newPassword() {
      return this.$t(`${this.prefix}.new`);
    },
    confirmation() {
      return this.$t(`${this.prefix}.confirmation`);
    },
    submit() {
      return this.$t(`${this.prefix}.submit`);
    }
  },
  methods: {
    submitForm() {
      this.loading = true;
      const vo = {...this.vo};
      this.error = '';
      console.log(vo)
      this.$store
        .dispatch('profile/changePassword', vo)
        .then(() => {
          this.$router.push('user-profile')
        })
        .catch(
          error => this.error = error
        )
        .finally(() => (this.loading = false));
    }
  }
};
</script>
