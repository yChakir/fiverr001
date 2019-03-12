<template>
  <v-navigation-drawer
    id="app-drawer"
    v-model="inputValue"
    app
    dark
    floating
    right
    persistent
    mobile-break-point="991"
    width="260"
  >
    <v-layout class="fill-height" tag="v-list" column>
      <v-list-tile
        v-for="(link, i) in links"
        :key="i"
        :to="link.to"
        :active-class="color"
        avatar
        class="v-list-item"
      >
        <v-list-tile-action>
          <fa-icon :icon="link.icon" size="lg"/>
        </v-list-tile-action>
        <v-list-tile-title v-text="$t(link.text)"/>
      </v-list-tile>
    </v-layout>
  </v-navigation-drawer>
</template>

<script>
// Utilities
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    responsive: false
  }),
  computed: {
    ...mapState("app", ["image", "color"]),
    ...mapState("auth", ["authenticated"]),
    inputValue: {
      get() {
        return this.$store.state.app.drawer;
      },
      set(val) {
        this.setDrawer(val);
      }
    },
    items() {
      return this.$t("Layout.View.items");
    },
    links() {
      const prefix = "Core.Drower";
      const result = [
        {
          to: "/",
          icon: "home",
          text: `${prefix}.home`
        },
        {
          to: "/user-profile",
          icon: "user",
          text: `${prefix}.user-profile`,
          requireAuth: true
        },
        {
          to: "/faq",
          icon: "question",
          text: `${prefix}.faq`
        },
        {
          to: "/contact",
          icon: "address-book",
          text: `${prefix}.contact`
        },
        {
          to: "/logout",
          icon: "sign-out-alt",
          text: `${prefix}.logout`,
          requireAuth: true
        }
      ];

      return result.filter(item => !item.requireAuth || this.authenticated);
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
    ...mapMutations("app", ["setDrawer", "toggleDrawer"]),
    onResponsiveInverted() {
      if (window.innerWidth < 991) {
        this.responsive = true;
      } else {
        this.responsive = false;
      }
    }
  }
};
</script>

<style lang="scss">
#app-drawer {
  .v-list__tile {
    border-radius: 4px;

    &--buy {
      margin-top: auto;
      margin-bottom: 17px;
    }
  }

  .v-image__image--contain {
    top: 9px;
    height: 60%;
  }

  .search-input {
    margin-bottom: 30px !important;
    padding-left: 15px;
    padding-right: 15px;
  }
}
</style>
