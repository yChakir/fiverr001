<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md5>
        <pf-profile-card/>
        
        <pf-change-password/>
      </v-flex>
      <v-flex xs12 md5>
        <pf-edit-profile/>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import ChangePassword from "@/components/profile/ChangePassword";
import ProfileCard from "@/components/profile/ProfileCard";
import EditProfile from "@/components/profile/EditProfile";

export default {
  data() {
    return {
      profile: { avatar: "default" },
      prefix: "Profile.card"
    };
  },
  created() {
    this.$store
      .dispatch("profile/fetchProfile")
      .then(response => (this.profile = { ...response }));
  },
  computed: {
    email() {
      return this.$t(`${this.prefix}.email`);
    },
    phone() {
      return this.$t(`${this.prefix}.phone`);
    },
    createdAt() {
      return this.$t(`${this.prefix}.createdAt`);
    }
  },
  components: {
    "pf-change-password": ChangePassword,
    "pf-profile-card": ProfileCard,
    "pf-edit-profile": EditProfile
  }
};
</script>
