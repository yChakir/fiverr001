<template>
  <v-expansion-panel v-model="panel" expand>
    <v-expansion-panel-content hide-actions>
      <div slot="header">{{title}}</div>
      <v-form>
        <v-container py-0>
          <v-layout wrap>
            <v-flex xs12>
              <v-text-field
                v-validate="{required: true, min: 8, max: 50}"
                :data-vv-name="current"
                :error-messages="errors.collect(current)"
                v-model="vo.currentPassword"
                :label="current"
                type="password"
                class="primary-input"
              />
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-validate="{required: true, min: 8, max: 50}"
                :data-vv-name="newPassword"
                :error-messages="errors.collect(newPassword)"
                v-model="vo.newPassword"
                :label="newPassword"
                type="password"
                class="primary-input"
              />
            </v-flex>
            <v-flex xs12>
              <v-text-field
                v-validate="{required: true, confirmed: newPassword}"
                :data-vv-name="confirmation"
                :error-messages="errors.collect(confirmation)"
                v-model="vo.confirmation"
                :label="confirmation"
                type="password"
                class="primary-input"
              />
            </v-flex>
            <v-flex xs12 text-xs-right>
              <v-btn
                class="mx-0 font-weight-light"
                @click="submitForm"
                :loading="loading"
                color="primary"
              >{{submit}}</v-btn>
            </v-flex>
          </v-layout>
        </v-container>
      </v-form>
    </v-expansion-panel-content>
  </v-expansion-panel>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data() {
    return {
      vo: {},
      panel: 1,
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
    },
    success() {
      return this.$t(`${this.prefix}.success`);
    }
  },
  methods: {
    ...mapMutations("snack", ["showSuccess", "showError"]),
    submitForm() {
      this.loading = true;
      this.$store
        .dispatch("profile/changePassword", this.vo)
        .then(() => {
          this.showSuccess(this.success);
          this.$router.push("user-profile");
        })
        .catch(error => this.showError(error.message))
        .finally(() => (this.loading = false));
    }
  }
};
</script>
