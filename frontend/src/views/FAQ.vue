<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex md12>
        <material-card color="green" :title="title" :text="subtitle">
          <v-layout justify-center wrap>
            <v-flex xs12 sm6 md4>
              <v-text-field :label="search" v-model="criteria" outline></v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-expansion-panel v-model="panel" expand>
                <v-expansion-panel-content v-for="(item, i) in items" :key="i" hide-actions>
                  <div slot="header">{{item.question}}</div>
                  <v-card>
                    <v-card-text class="grey lighten-3">{{item.answer}}</v-card-text>
                  </v-card>
                </v-expansion-panel-content>
              </v-expansion-panel>
            </v-flex>
          </v-layout>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    criteria: ""
  }),
  computed: {
    items() {
      const result = [
        {
          question: this.$t("FAQ.data.reset-passwor-question"),
          answer: this.$t("FAQ.data.reset-passwor-answer")
        },
        {
          question: this.$t("FAQ.data.validate-account-question"),
          answer: this.$t("FAQ.data.validate-account-answer")
        }
      ];

      return result.filter(
        item =>
          !this.criteria ||
          (item.question.toLowerCase().includes(this.criteria.toLowerCase()) ||
            item.answer.toLowerCase().includes(this.criteria.toLowerCase()))
      );
    },
    panel() {
      return [...this.items.keys()].map(_ => true);
    },
    title() {
      return this.$t("FAQ.data.title");
    },
    subtitle() {
      return this.$t("FAQ.data.subtitle");
    },
    search() {
      return this.$t("FAQ.data.search");
    }
  }
};
</script>
