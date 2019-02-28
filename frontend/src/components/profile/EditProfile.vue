<template>
  <material-card color="green" :title="title" :text="subtitle" @keyup.enter="submit">
    <v-form>
      <v-container py-0>
        <v-layout wrap>
          <v-flex xs12 md4>
            <v-text-field
              v-validate="'required|min:2|max:50'"
              :data-vv-name="name"
              :error-messages="errors.collect(name)"
              v-model="vo.name"
              :label="name"
              class="green-input"
            />
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-validate="'required|min:2|max:50'"
              :data-vv-name="surname"
              :error-messages="errors.collect(surname)"
              v-model="vo.surname"
              :label="surname"
              class="green-input"
            />
          </v-flex>
          <v-flex xs12 md4>
            <v-text-field
              v-validate="'required|min:8|max:50'"
              :data-vv-name="phone"
              :error-messages="errors.collect(phone)"
              v-model="vo.phone"
              :label="phone"
              class="green-input"
            />
          </v-flex>
          <v-flex xs12 text-xs-right>
            <v-btn
              @click="submit"
              :loading="isLoading"
              class="mx-0 font-weight-light"
              color="success"
            >{{edit}}</v-btn>
          </v-flex>
        </v-layout>
      </v-container>
    </v-form>
  </material-card>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data() {
    return {
      vo: {},
      prefix: "Profile.edit",
      isLoading: false
    };
  },
  computed: {
    title() {
      return this.$t(`${this.prefix}.title`);
    },
    subtitle() {
      return this.$t(`${this.prefix}.subtitle`);
    },
    name() {
      return this.$t(`${this.prefix}.name`);
    },
    surname() {
      return this.$t(`${this.prefix}.surname`);
    },
    phone() {
      return this.$t(`${this.prefix}.phone`);
    },
    edit() {
      return this.$t(`${this.prefix}.edit`);
    },
    success() {
      return this.$t(`${this.prefix}.success`);
    }
  },
  methods: {
    ...mapMutations("snack", ["showSuccess", "showError"]),
    submit() {
      this.$validator.validate().then(valid => {
        if (valid) {
          this.isLoading = true;
          this.$store
            .dispatch("profile/editProfile", this.vo)
            .then(() => this.showSuccess(this.success))
            .catch(error => this.showError(error.message))
            .finally(() => (this.isLoading = false));
        }
      });
    }
  }
};
</script>
