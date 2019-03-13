<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex md12>
        <material-card color="primary" :title="title" :text="subtitle">
          <v-layout justify-center wrap>
            <v-flex xs12 sm6 md4>
              <v-text-field :label="search" v-model="criteria" outline></v-text-field>
            </v-flex>
            <v-flex xs12>
              <v-expansion-panel v-model="panel" expand>
                <v-expansion-panel-content v-for="(item, i) in items" :key="i" hide-actions>
                  <div slot="header" v-html="item.question"></div>
                  <v-card>
                    <v-card-text class="grey lighten-3" v-html="item.answer"></v-card-text>
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

      const filtered = result.filter(
        item =>
          !this.criteria ||
          (item.question.toLowerCase().includes(this.criteria.toLowerCase()) ||
            item.answer.toLowerCase().includes(this.criteria.toLowerCase()))
      );

      return this.highlight(filtered, this.criteria);
    },
    panel() {
      return [...this.items.keys()].map(() => true);
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
  },
  methods: {
    highlight(array, criteria) {
      if (array && criteria) {
        const iQuery = new RegExp(criteria, "ig");
        array.forEach(obj => {
          obj.question = obj.question.replace(iQuery, function(
            matchedTxt,
            a,
            b
          ) {
            return "<span class='highlight'>" + matchedTxt + "</span>";
          });
          obj.answer = obj.answer.replace(iQuery, function(matchedTxt, a, b) {
            return "<span class='highlight'>" + matchedTxt + "</span>";
          });
        });
      }
      return array;
    }
  }
};
</script>

<style>
.highlight {
  background-color: yellow;
}
</style>

