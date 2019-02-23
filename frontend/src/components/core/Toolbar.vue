<template>
  <v-toolbar app id="core-toolbar" prominent style="background: #eee;">
    <div class="v-toolbar-title">
      <v-toolbar-title class="tertiary--text font-weight-light">
        <v-btn v-if="true" class="default v-btn--simple" dark icon @click.stop="onClickBtn">
          <v-icon>mdi-view-list</v-icon>
        </v-btn>
        {{ $t(title) }}
      </v-toolbar-title>
    </div>

    <v-spacer/>
    <v-toolbar-items>
      <v-flex align-center layout py-2>
        <router-link v-if="!authenticated" v-ripple class="toolbar-items" to="/sign-up">{{$t('Core.Toolbar.sign-up')}}</router-link>
        <router-link v-if="!authenticated" v-ripple class="toolbar-items" to="/login">{{$t('Core.Toolbar.login')}}</router-link>
        <v-menu offset-y>
          <v-btn slot="activator" flat><div style="color: #4caf50;">{{$i18n.locale}}</div></v-btn>
          <v-list v-for="(item) in language.accepted" :key="item.code">
            <v-list-tile @click="setLanguage(item.code)">
              <v-list-tile-title>{{item.name}}</v-list-tile-title>
            </v-list-tile>
          </v-list>
        </v-menu>
      </v-flex>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    title: null,
    responsive: false,
    responsiveInput: false
  }),

  watch: {
    $route(val) {
      this.title = val.name;
    }
  },

  mounted() {
    this.onResponsiveInverted();
    window.addEventListener("resize", this.onResponsiveInverted);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.onResponsiveInverted);
  },
  computed: {
    ...mapState("auth", ["authenticated"]),
    ...mapState("lang", ["language"])
  },
  methods: {
    ...mapMutations("app", ["setDrawer", "toggleDrawer"]),
    onClickBtn() {
      this.setDrawer(!this.$store.state.app.drawer);
    },
    onClick() {
      //
    },
    onResponsiveInverted() {
      if (window.innerWidth < 991) {
        this.responsive = true;
        this.responsiveInput = false;
      } else {
        this.responsive = false;
        this.responsiveInput = true;
      }
    },
    setLanguage(code) {
      this.$store.commit("lang/setLanguage", code);
    }
  }
};
</script>

<style>
#core-toolbar a {
  text-decoration: none;
}
</style>
