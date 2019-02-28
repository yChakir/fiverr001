
export default {
  setProfile: function (state, profile) {
    console.log(profile)
    state.profile = {...profile}
  }
}
