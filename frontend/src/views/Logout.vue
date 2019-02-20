<template>
  <div>Please wait...</div>
</template>

<script>
export default {
  created() {
    this.$store.commit('auth/logout')
    this.$router.push('/login')
  }
}
</script>
